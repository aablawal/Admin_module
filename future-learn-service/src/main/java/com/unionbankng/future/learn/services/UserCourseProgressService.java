package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.UserCourseProgress;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.pojo.UserCourseProgressRequest;
import com.unionbankng.future.learn.repositories.LectureRepository;
import com.unionbankng.future.learn.repositories.UserCourseProgressRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserCourseProgressService {

    private final UserCourseProgressRepository userCourseProgressRepository;
    private final LectureRepository lectureRepository;


    public UserCourseProgress save(UserCourseProgress userCourseProgress){
        return userCourseProgressRepository.save(userCourseProgress);
    }


    public UserCourseProgress computePercentageAndSaveProgress(UserCourseProgressRequest courseProgressRequest,OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return computePercentageAndSaveProgress(courseProgressRequest,jwtUserDetail.getUserUUID());
    }

    public UserCourseProgress computePercentageAndSaveProgress(UserCourseProgressRequest courseProgressRequest,String userUUID){

        //compute
        Long courseLectureCount = lectureRepository.countAllByCourseId(courseProgressRequest.getCourseId());
        UserCourseProgress progress = new UserCourseProgress();

        if(courseProgressRequest.getProgressId() != null){
            progress =  userCourseProgressRepository.findByCourseIdAndUserUUID(courseProgressRequest.getCourseId(),userUUID).orElse(

                    new UserCourseProgress()
            );
        }

        int progressLectureCount = progress.getLecturesTaken().size();
        int courseLectureCountInteger = courseLectureCount.intValue();

        Double percent = (double)(progressLectureCount / courseLectureCountInteger) * 100;

        progress.setCourseId(courseProgressRequest.getCourseId());
        progress.setCurrentLectureId(courseProgressRequest.getCurrentLectureIndex());
        if(courseProgressRequest.getLecturesTaken() != null)
            progress.getLecturesTaken().add(courseProgressRequest.getLecturesTaken());
        progress.setProgressPercentage(percent);
        progress.setUserUUID(userUUID);

        return userCourseProgressRepository.save(progress);
    }

    public UserCourseProgress findByCourseIdAndUserUUID( Long courseId, String userUUID){
        return userCourseProgressRepository.findByCourseIdAndUserUUID(courseId,userUUID).orElseThrow(

                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No course progress found for user")
        );

    }

    public UserCourseProgress findMyCourseProgressByCourseId(Long courseId, OAuth2Authentication authentication){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        return findByCourseIdAndUserUUID(courseId,jwtUserDetail.getUserUUID());

    }





}
