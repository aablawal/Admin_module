package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.UserSocialMedia;
import com.unionbankng.future.authorizationserver.enums.SocialMedia;
import com.unionbankng.future.authorizationserver.pojos.LongLivedInstagramResponse;
import com.unionbankng.future.authorizationserver.pojos.ShortlivedInstagramResponse;
import com.unionbankng.future.authorizationserver.repositories.UserSocialMediaRepository;
import com.unionbankng.future.authorizationserver.utils.InstagramHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSocialMediaService {

    private final Logger logger = LoggerFactory.getLogger(UserSocialMediaService.class);
    private final UserSocialMediaRepository userSocialMediaRepository;

    private final InstagramHandler instagramHandler;

    @Cacheable(value = "userSocialMedia", key="#userId")
    public Optional<UserSocialMedia> findByUserId(Long userId){
        return userSocialMediaRepository.findByUserId(userId);
    }

    @CacheEvict(value = "userSocialMedia", key="#userSocialMedia.userId")
    public UserSocialMedia save (UserSocialMedia userSocialMedia){
        return userSocialMediaRepository.save(userSocialMedia);
    }

    public Boolean existByUserId (Long userId){
        return userSocialMediaRepository.existsByUserId(userId);
    }

    @CacheEvict(value = "userSocialMedia", key="#userId")
    public void deleteAllByUserId (Long userId){
        userSocialMediaRepository.deleteAllByUserId(userId);
    }

    public UserSocialMedia linkUserSocialMedia(Long userId, SocialMedia socialMedia,String socialCode){

        UserSocialMedia userSocialMedia = findByUserId(userId).orElse(new UserSocialMedia());

        logger.info("Doing existing social check");
        if (!socialMedia.equals(SocialMedia.INSTAGRAM))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Social media not supported");

        logger.info("Social Media selected is instagram, proceeding to get short lived token");

        ShortlivedInstagramResponse shortlivedInstagramResponse = instagramHandler.getShortLivedToken(socialCode);

        if(shortlivedInstagramResponse == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error occurred while getting short lived token");

        logger.info("Short lived token received, proceeding to get long lived token");

        LongLivedInstagramResponse longLivedInstagramResponse = instagramHandler.getLongLivedToken(shortlivedInstagramResponse.getAccess_token());

        if(longLivedInstagramResponse == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not link your instagram account an error occurred");

        logger.info("All done, saving");

        userSocialMedia.setSocialMedia(socialMedia);
        userSocialMedia.setToken(longLivedInstagramResponse.getAccess_token());
        userSocialMedia.setUserId(userId);

        return save(userSocialMedia);

    }


}
