package com.unionbankng.future.futurejobservice.services;

import com.unionbankng.future.futurejobservice.entities.Bundle;
import com.unionbankng.future.futurejobservice.enums.Status;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.ActivityLog;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurejobservice.repositories.BundleRepository;
import com.unionbankng.future.futurejobservice.repositories.JobTeamDetailsRepository;
import com.unionbankng.future.futurejobservice.util.App;
import com.unionbankng.future.futurejobservice.util.AppLogger;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BundleService {

    private  final BundleRepository bundleRepository;
    private  final FileStoreService fileStoreService;
    private final JobService jobService;
    private final App app;
    private final AppLogger appLogger;
    private Logger logger = LoggerFactory.getLogger(BundleService.class);

    public APIResponse postABundle(OAuth2Authentication authentication, String bundleData, MultipartFile[] supporting_files) {
        try {

            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
            String supporting_file_names = null;
            Bundle bundle = app.getMapper().readValue(bundleData, Bundle.class);
            bundle.setStatus(Status.AC);

            app.print("Bundle Request:");
            app.print(bundle);


            app.print("Attached Files");
            app.print("Supporting Files:" + supporting_files.length);
            //save files if not null
            if (supporting_files.length > 0)
                supporting_file_names = this.fileStoreService.storeFiles(supporting_files, "kula");

            //cross verify if attached files processed
            if (supporting_file_names != null)
                bundle.setSupportingFiles(supporting_file_names);

            Bundle savedBundle = bundleRepository.save(bundle);
            app.print("saved Bundle: "+ savedBundle);

                //############### Activity Logging ###########
                ActivityLog log = new ActivityLog();
                log.setDescription("Created new Job");
                log.setRequestObject(app.toString(bundle));
                log.setResponseObject(app.toString(savedBundle));
                log.setUsername(currentUser.getUserEmail());
                log.setUserId(currentUser.getUserUUID());
                appLogger.log(log);
                //#########################################

            return  new APIResponse("Request Successful",true, savedBundle);
        }catch (Exception e){
            e.printStackTrace();
            return  new APIResponse(e.getMessage(),false, null);
        }

    }

    public APIResponse findBundleById(Long id) {
        Bundle bundle = bundleRepository.findById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bundle not found"));
        return  new APIResponse("Request Successful",true, bundle);
    }

    public APIResponse findAllBundles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Bundle> bundles = bundleRepository.findAll(pageable).toList();
        app.print("bundles>>>>>>>: "+bundles);

        if(bundles.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bundles not found");

        app.print("bundles: "+ bundles);
        return  new APIResponse("Request Successful",true, bundles);
    }

    public APIResponse postJobWithBundle(OAuth2Authentication authentication, Long id) {
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        Bundle bundle = bundleRepository.findById(id).orElseThrow(  ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bundle not found"));
        app.print("bundle: "+ bundle);
        String jobData = bundle.toString();
        try {
            return jobService.postAJob(authentication, jobData, null);

        } catch (IOException e) {
           app.print("Exception:  "+ e.getMessage());
           return new APIResponse(e.getMessage(),false, null);
        }
    }
}
