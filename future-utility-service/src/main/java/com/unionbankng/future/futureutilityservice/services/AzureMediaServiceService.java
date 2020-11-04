package com.unionbankng.future.futureutilityservice.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.microsoft.azure.management.mediaservices.v2018_07_01.*;
import com.microsoft.azure.management.mediaservices.v2018_07_01.implementation.MediaManager;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureMediaServiceService{

    @Value("${azure.mediaService.streamingEndpointName}")
    private String streamingEndpointName;
    @Value("${azure.mediaService.accountName}")
    private String accountName;
    @Value("${azure.mediaService.resourceGroup}")
    private String resourceGroup;


    private final MediaManager manager;
    private final Transform adaptiveEncodeTransform;
    private final BlobServiceClient blobServiceClient;


    public StreamingLocatorResponse uploadAndGetStreamingLocator(AzureMediaServiceRequest request) {

        // Creating a unique suffix so that we don't have name collisions if you run the sample
        // multiple times without cleaning up.
        UUID uuid = UUID.randomUUID();
        String uniqueness = uuid.toString();
        String jobName = "job-" + uniqueness.substring(0, 13);
        String locatorName = "locator-" + uniqueness;
        String outputAssetName = "output-" + uniqueness;
        String inputAssetName = "input-" + uniqueness;

        try {

            // Create a new input Asset and upload the specified local video file into it.
            Asset inputAsset = createInputAsset(manager, resourceGroup, accountName, inputAssetName,
                    request.getFileName(),request.getFileByte().toByteArray());

            // Output from the encoding Job must be written to an Asset, so let's create one. Note that we
            // are using a unique asset name, there should not be a name collision.
            System.out.println("Creating an output asset...");
            Asset outputAsset = createAsset(manager, resourceGroup, accountName,
                    outputAssetName);

            submitJob(manager, resourceGroup, accountName, adaptiveEncodeTransform.name(), jobName,
                    inputAsset.name(), outputAsset.name());

            long startedTime = System.currentTimeMillis();

            // In this demo code, we will poll for Job status. Polling is not a recommended best practice for production
            // applications because of the latency it introduces. Overuse of this API may trigger throttling. Developers
            // should instead use Event Grid. To see how to implement the event grid, see the sample
            // https://github.com/Azure-Samples/media-services-v3-java/tree/master/ContentProtection/BasicAESClearKey.
            Job job = waitForJobToFinish(manager, resourceGroup, accountName, adaptiveEncodeTransform.name(),
                    jobName);

            long elapsed = (System.currentTimeMillis() - startedTime) / 1000; // Elapsed time in seconds
            System.out.println("Job elapsed time: " + elapsed + " second(s)."+job.state());

            if(job.state() != JobState.FINISHED)
                return StreamingLocatorResponse.newBuilder().setSuccess(false).build();

                System.out.println("Job finished.");
                System.out.println();

                // Now that the content has been encoded, publish it for Streaming by creating
                // a StreamingLocator.
                StreamingLocator locator = getStreamingLocator(manager, resourceGroup, accountName,
                        outputAsset.name(), locatorName);

                return StreamingLocatorResponse.newBuilder().setAssetName(outputAsset.name()).setLocatorName(locator.name()).setSuccess(true).build();


        } catch (Exception e) {
            return StreamingLocatorResponse.newBuilder().setSuccess(false).build();
        }finally {
            cleanup(manager,resourceGroup,accountName,adaptiveEncodeTransform.name(),jobName,inputAssetName);
        }
    }


    public StreamLinksResponse getStreamingUrlsFromLocator(StreamLinkRequest request) {

        try {

                StreamingEndpoint streamingEndpoint = manager.streamingEndpoints()
                        .getAsync(resourceGroup, accountName, streamingEndpointName)
                        .toBlocking().first();

                if(streamingEndpoint == null)
                    return StreamLinksResponse.newBuilder().setSuccess(false).build();

                    // Start The Streaming Endpoint if it is not running.
                if (streamingEndpoint.resourceState() != StreamingEndpointResourceState.RUNNING) {
                        manager.streamingEndpoints().startAsync(resourceGroup, accountName, streamingEndpointName).await();

                    }

                    List<String> urls = getHlsAndDashStreamingUrls(manager, resourceGroup,
                            accountName, request.getLocatorName(), streamingEndpoint);

                return StreamLinksResponse.newBuilder().setSuccess(true).setCommaSeperatedStreamingLinks(String.join(",",urls)).build();

        } catch (Exception e) {
            return StreamLinksResponse.newBuilder().setSuccess(false).build();
        }
    }

    /**
     * Creates a new input Asset and uploads the specified local video file into it.
     *
     * @param manager           This is the entry point of Azure Media resource management.
     * @param resourceGroupName The name of the resource group within the Azure subscription.
     * @param accountName       The Media Services account name.
     * @param assetName         The name of the asset where the media file to uploaded to.
     * @param fileInBytes        The bytes of a media file to be uploaded into the asset.
     * @return                  The asset.
     */
    private Asset createInputAsset(MediaManager manager, String resourceGroupName, String accountName,
                                          String assetName, String fileName, byte[] fileInBytes) throws Exception {
        Asset asset;
        try {
            // In this example, we are assuming that the asset name is unique.
            // If you already have an asset with the desired name, use the Assets.getAsync method
            // to get the existing asset.
            asset = manager.assets().getAsync(resourceGroupName, accountName, assetName).toBlocking().first();
        }
        catch (NoSuchElementException nse) {
            asset = null;
        }

        if (asset == null) {
            System.out.println("Creating an input asset...");
            // Call Media Services API to create an Asset.
            // This method creates a container in storage for the Asset.
            // The files (blobs) associated with the asset will be stored in this container.
            asset = manager.assets().define(assetName).withExistingMediaservice(resourceGroupName, accountName).create();
        }
        else {
            // The asset already exists and we are going to overwrite it. In your application, if you don't want to overwrite
            // an existing asset, use an unique name.
            System.out.println("Warning: The asset named " + assetName + "already exists. It will be overwritten.");
        }

        // Use Media Services API to get back a response that contains
        // SAS URL for the Asset container into which to upload blobs.
        // That is where you would specify read-write permissions
        // and the expiration time for the SAS URL.
        ListContainerSasInput parameters = new ListContainerSasInput()
                .withPermissions(AssetContainerPermission.READ_WRITE).withExpiryTime(DateTime.now().plusHours(4));
        AssetContainerSas response = manager.assets()
                .listContainerSasAsync(resourceGroupName, accountName, assetName, parameters).toBlocking().first();
        URI sasUri = new URI(response.assetContainerSasUrls().get(0));

        // Use Storage API to get a reference to the Asset container
        // that was created by calling Asset's create method.
        System.out.println(assetName+" URL"+sasUri.getPath().substring(1));
        BlobContainerClient container = blobServiceClient.getBlobContainerClient(sasUri.getPath().substring(1));

        // Uploading from a local file:
        BlobClient blob = container.getBlobClient(fileName);

        // Use Storage API to upload the file into the container in storage.
        System.out.println("Uploading a media file to the asset...");
        InputStream targetStream = new ByteArrayInputStream(fileInBytes);
        blob.upload(targetStream,fileInBytes.length );

        return asset;
    }

    private Asset createAsset(MediaManager manager, String resourceGroup, String accountName,
                                     String assetName) {
        return manager.assets()
                .define(assetName)
                .withExistingMediaservice(resourceGroup, accountName)
                .create();
    }

    /**
     * Create and submit a job.
     * @param manager           The entry point of Azure Media resource management.
     * @param resourceGroup     The name of the resource group within the Azure subscription.
     * @param accountName       The Media Services account name.
     * @param transformName     The name of the transform.
     * @param jobName           The name of the job.
     * @param outputAssetName   The name of the asset that the job writes to.
     * @return                  The job created.
     */
    private Job submitJob(MediaManager manager, String resourceGroup, String accountName, String transformName,
                                 String jobName, String inputAssetName, String outputAssetName) {
        // Use the name of the created input asset to create the job input.
        JobInput jobInput = new JobInputAsset().withAssetName(inputAssetName);

        // Specify where the output(s) of the Job need to be written to
        List<JobOutput> jobOutputs = new ArrayList<>();
        jobOutputs.add(new JobOutputAsset().withAssetName(outputAssetName));

        Job job;
        try {
            System.out.println("Creating a job...");
            job = manager.jobs().define(jobName)
                    .withExistingTransform(resourceGroup, accountName, transformName)
                    .withInput(jobInput)
                    .withOutputs(jobOutputs)
                    .create();
        }
        catch (ApiErrorException exception) {
            System.out.println("ERROR: API call failed with error code " + exception.body().error().code() +
                    " and message '" + exception.body().error().message() + "'");
            throw exception;
        }

        return job;
    }


    private static Job waitForJobToFinish(MediaManager manager, String resourceGroup, String accountName,
                                          String transformName, String jobName) {
        final int SLEEP_INTERVAL = 10 * 1000;

        Job job;
        boolean exit = false;

        do {
            job = manager.jobs().getAsync(resourceGroup, accountName, transformName, jobName).toBlocking().first();

            if (job.state() == JobState.FINISHED || job.state() == JobState.ERROR || job.state() == JobState.CANCELED) {
                exit = true;
            } else {
                System.out.println("Job is " + job.state());

                int i = 0;
                for (JobOutput output : job.outputs()) {
                    System.out.print("\tJobOutput[" + i++ + "] is " + output.state() + ".");
                    if (output.state() == JobState.PROCESSING) {
                        System.out.print("  Progress: " + output.progress());
                    }
                    System.out.println();
                }

                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!exit);

        return job;
    }

    private static StreamingLocator getStreamingLocator(MediaManager manager, String resourceGroup, String accountName,
                                                        String assetName, String locatorName) {
        // Note that we are using one of the PredefinedStreamingPolicies which tell the Origin component
        // of Azure Media Services how to publish the content for streaming.
        System.out.println("Creating a streaming locator...");
        StreamingLocator locator = manager
                .streamingLocators().define(locatorName)
                .withExistingMediaservice(resourceGroup, accountName)
                .withAssetName(assetName)
                .withStreamingPolicyName("Predefined_ClearStreamingOnly")
                .create();

        return locator;
    }

    /**
     * Checks if the streaming endpoint is in the running state, if not, starts it.
     * @param manager       The entry point of Azure Media resource management.
     * @param resourceGroup The name of the resource group within the Azure subscription.
     * @param accountName   The Media Services account name.
     * @param locatorName   The name of the StreamingLocator that was created.
     * @param streamingEndpoint     The streaming endpoint.
     * @return              List of streaming urls.
     */
    private static List<String> getHlsAndDashStreamingUrls(MediaManager manager, String resourceGroup, String accountName,
                                                           String locatorName, StreamingEndpoint streamingEndpoint) {
        List<String> streamingUrls = new ArrayList<>();
        ListPathsResponse paths = manager.streamingLocators().listPathsAsync(resourceGroup, accountName, locatorName)
                .toBlocking().first();

        for (StreamingPath path: paths.streamingPaths()) {
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append("https://")
                    .append(streamingEndpoint.hostName())
                    .append("/")
                    .append(path.paths().get(0));

            if (path.streamingProtocol() == StreamingPolicyStreamingProtocol.HLS) {
                streamingUrls.add(uriBuilder.toString());
            }
            else if (path.streamingProtocol() == StreamingPolicyStreamingProtocol.DASH) {
                streamingUrls.add(uriBuilder.toString());
            }
        }
        return streamingUrls;
    }

    private void cleanup(MediaManager manager, String resourceGroupName, String accountName, String transformName, String jobName,
                                String inputAssetName) {
        if (manager == null) {
            return;
        }

        manager.jobs().deleteAsync(resourceGroupName, accountName, transformName, jobName).await();
        manager.assets().deleteAsync(resourceGroupName, accountName, inputAssetName).await();

    }

    public DeleteOutputAssetReponse deleteOutputAsset(DeleteOutputAssetRequest request) {
        if (manager == null) {
            return DeleteOutputAssetReponse.newBuilder().setSuccess(false).build();
        }

        manager.assets().deleteAsync(resourceGroup, accountName, request.getAssetName()).await();

        return DeleteOutputAssetReponse.newBuilder().setSuccess(true).build();
    }

}
