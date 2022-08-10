package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.entities.SocialLink;
import com.unionbankng.future.authorizationserver.enums.SocialMedia;
import com.unionbankng.future.authorizationserver.pojos.UserSocialLink;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.SocialLinkRepository;
import com.unionbankng.future.authorizationserver.utils.InstagramHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialLinkService {

    private final Logger logger = LoggerFactory.getLogger(SocialLinkService.class);
    private final SocialLinkRepository socialLinkRepository;

    private final ProfileRepository profileRepository;

    private final InstagramHandler instagramHandler;

    @Cacheable(value = "socialLink", key="#userId")
    public Optional<SocialLink> findByUserId(Long userId){
        return socialLinkRepository.findByUserId(userId);
    }

    public List<SocialLink> findAllByUserId(Long userId){

        List<SocialLink> socialLinkList = new ArrayList<>();
        Optional<List<SocialLink>> socialLinks = socialLinkRepository.findAllByUserId(userId);

        if(socialLinks.isPresent()){
            socialLinkList = socialLinks.get();
        }
        return socialLinkList;
    }

    @CacheEvict(value = "socialLink", key="#socialLink.userId")
    public SocialLink save (SocialLink socialLink){
        return socialLinkRepository.save(socialLink);
    }

    public Boolean existByUserId (Long userId){
        return socialLinkRepository.existsByUserId(userId);
    }

    @CacheEvict(value = "userSocialMedia", key="#userId")
    public void deleteAllByUserId (Long userId){
        socialLinkRepository.deleteAllByUserId(userId);
    }


    public void saveSocialLinkFromRequest(UserSocialLink userSocialLink) {

        logger.info("Deleting user previous social link");

        List<SocialLink> allUserSocialLink = socialLinkRepository.findAllByUserId(userSocialLink.getUserId()).
                orElse(new ArrayList<>());

        Profile profile = profileRepository.findByUserId(userSocialLink.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile not found")
        );

        if(!allUserSocialLink.isEmpty()){
            for (SocialLink socialLink : allUserSocialLink) {
                logger.info("deleting :" + socialLink.getSocialMedia().name());
                socialLinkRepository.delete(socialLink);
            }

            logger.info("previous social link successfully deleted");

            logger.info("Decrementing profile percentage completed");
            profile.decrementPercentageComplete(10);
            logger.info("Profile percentage completed decreased");
        }
        logger.info("Profile is --> " + profile);
        if(userSocialLink.getBehance() != null && !userSocialLink.getBehance().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.BEHANCE);
            socialLink.setUrl(userSocialLink.getBehance());
            socialLinkRepository.save(socialLink);
        }

        if(userSocialLink.getLinkedIn() != null && !userSocialLink.getLinkedIn().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.LINKEDIN);
            socialLink.setUrl(userSocialLink.getLinkedIn());
            socialLinkRepository.save(socialLink);
        }

        if(userSocialLink.getTwitter() != null && !userSocialLink.getTwitter().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.TWITTER);
            socialLink.setUrl(userSocialLink.getTwitter());
            socialLinkRepository.save(socialLink);
        }

        if(userSocialLink.getDribble() != null && !userSocialLink.getDribble().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.DRIBBLE);
            socialLink.setUrl(userSocialLink.getDribble());
            socialLinkRepository.save(socialLink);
        }

        if(userSocialLink.getGithub() != null && !userSocialLink.getGithub().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.GITHUB);
            socialLink.setUrl(userSocialLink.getGithub());
            socialLinkRepository.save(socialLink);
        }

        if(userSocialLink.getWebsite() != null && !userSocialLink.getWebsite().isBlank()){
            SocialLink socialLink = new SocialLink();
            socialLink.setUserId(userSocialLink.getUserId());
            socialLink.setSocialMedia(SocialMedia.WEBSITE);
            socialLink.setUrl(userSocialLink.getWebsite());
            socialLinkRepository.save(socialLink);
        }

        if (!socialLinkRepository.findAllByUserId(userSocialLink.getUserId()).isEmpty()){
            logger.info("Incrementing profile percentage complete");
            profile.incrementPercentageComplete(10);
            logger.info("profile percentage complete incremented");
        }

        logger.info("Profile after social links update --> " + profile);
        profileRepository.save(profile);
        logger.info("Social Link successfully updated");

    }

    public UserSocialLink findUserSocialLinks(Long userId) {

        List<SocialLink> socialLinks = socialLinkRepository.findAllByUserId(userId).orElse(new ArrayList<>());

        UserSocialLink userSocialLink = new UserSocialLink();

        for (SocialLink socialLink : socialLinks){
            if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.TWITTER))
                userSocialLink.setTwitter(socialLink.getUrl());
            else if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.LINKEDIN))
                userSocialLink.setLinkedIn(socialLink.getUrl());
            else if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.BEHANCE))
                userSocialLink.setBehance(socialLink.getUrl());
            else if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.DRIBBLE))
                userSocialLink.setDribble(socialLink.getUrl());
            else if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.GITHUB))
                userSocialLink.setGithub(socialLink.getUrl());
            else if(socialLink.getSocialMedia() != null && socialLink.getSocialMedia().equals(SocialMedia.WEBSITE))
                userSocialLink.setWebsite(socialLink.getUrl());
        }
        userSocialLink.setUserId(userId);
        return userSocialLink;
    }
}
