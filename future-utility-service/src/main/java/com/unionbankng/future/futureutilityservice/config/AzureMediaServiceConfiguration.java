package com.unionbankng.future.futureutilityservice.config;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.mediaservices.v2018_07_01.BuiltInStandardEncoderPreset;
import com.microsoft.azure.management.mediaservices.v2018_07_01.EncoderNamedPreset;
import com.microsoft.azure.management.mediaservices.v2018_07_01.Transform;
import com.microsoft.azure.management.mediaservices.v2018_07_01.TransformOutput;
import com.microsoft.azure.management.mediaservices.v2018_07_01.implementation.MediaManager;
import com.microsoft.rest.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RefreshScope
@Configuration
public class AzureMediaServiceConfiguration {

    @Value("${azure.mediaService.clientId}")
    private String clientId;
    @Value("${azure.mediaService.tenantId}")
    private String tenantId;
    @Value("${azure.mediaService.clientSecret}")
    private String clientSecret;
    @Value("${azure.mediaService.subscriptionId}")
    private String subscriptionId;
    @Value("${azure.mediaService.accountName}")
    private String accountName;
    @Value("${azure.mediaService.resourceGroup}")
    private String resourceGroup;

    private static final String TRANSFORM_NAME = "AdaptiveBitrate";

   private Logger logger = LoggerFactory.getLogger(AzureMediaServiceConfiguration.class);

    @Bean
    public MediaManager initMediaManager() {
        MediaManager manager = null;
        try {
            ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(clientId, tenantId, clientSecret, AzureEnvironment.AZURE);
            credentials.withDefaultSubscriptionId(subscriptionId);

            manager = MediaManager
                    .configure()
                    .withLogLevel(LogLevel.BODY_AND_HEADERS)
                    .authenticate(credentials, credentials.defaultSubscriptionId());
            logger.info("Azure Media manager signed in");
        } catch (Exception e) {
            logger.info("Exception encountered : {}", e.getMessage());
        }

        return manager;
    }

    @Bean
    public Transform adaptiveTransform() {

        Transform transform;
        try {
            // Does a Transform already exist with the desired name? Assume that an existing Transform with the desired name
            // also uses the same recipe or Preset for processing content.
            System.out.println(initMediaManager().transforms()
                    .getAsync(resourceGroup, accountName, TRANSFORM_NAME)
                    .toBlocking()
                    .first());
            transform = initMediaManager().transforms()
                    .getAsync(resourceGroup, accountName, TRANSFORM_NAME)
                    .toBlocking()
                    .first();
        }
        catch(NoSuchElementException nse)
        {
            // Media Services V3 throws an exception when not found.
            transform = null;
        }

        if (transform == null) {
            List<TransformOutput> outputs = new ArrayList<>();
            outputs.add(new TransformOutput().withPreset( new BuiltInStandardEncoderPreset().withPresetName(EncoderNamedPreset.ADAPTIVE_STREAMING)));

            // Create the transform.
            transform = initMediaManager().transforms()
                    .define(TRANSFORM_NAME)
                    .withExistingMediaservice(resourceGroup, accountName)
                    .withOutputs(outputs)
                    .create();
        }

        return transform;
    }



}
