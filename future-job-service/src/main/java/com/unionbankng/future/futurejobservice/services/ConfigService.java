package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.entities.Config;
import com.unionbankng.future.futurejobservice.enums.ConfigReference;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.repositories.*;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final App app;
    private  final ConfigRepository configRepository;


    public List<Config> getConfigs(){
        return  configRepository.findAll();
    }
    public Config getConfigByKey(ConfigReference reference){
        return  configRepository.findByReference(reference).orElse(null);
    }

    public APIResponse initializeConfigs(){
         configRepository.deleteAll();
        this.updateConfig(ConfigReference.TOTAL_JOBS,"0");
        this.updateConfig(ConfigReference.TOTAL_JOBS_COMPLETED,"0");
        this.updateConfig(ConfigReference.TOTAL_JOBS_REJECTED,"0");
        this.updateConfig(ConfigReference.UBN_INCOME,"5");
        this.updateConfig(ConfigReference.VAT_RATE,"7.5");
        List<Config> initiatedConfigs=configRepository.findAll();
        return  new APIResponse("success",true,initiatedConfigs);
    }
    public Config updateConfig(ConfigReference reference, String value){
        Config existingConfig=configRepository.findByReference(reference).orElse(null);
        if(existingConfig!=null) {
            existingConfig.setValue(value);
            existingConfig.setCreatedAt(new Date());
            return  configRepository.save(existingConfig);
        }else {
            //create new config
            Config config = new Config();
            config.setUid(app.makeUIID());
            config.setReference(reference);
            config.setValue(value);
            config.setCreatedAt(new Date());
            if (reference.equals(ConfigReference.VAT_RATE) || reference.equals(ConfigReference.UBN_INCOME))
                config.setType("percentage");
            else
                config.setType("value");
            return configRepository.save(config);
        }
    }

}
