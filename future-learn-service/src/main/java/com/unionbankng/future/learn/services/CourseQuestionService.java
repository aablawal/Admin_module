package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.CourseQuestion;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.pojo.PublishCourseQuestionRequest;
import com.unionbankng.future.learn.repositories.CourseQuestionRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CourseQuestionService {

    private final CourseQuestionRepository courseQuestionRepository;

    public CourseQuestion save(CourseQuestion courseQuestion){
        return courseQuestionRepository.save(courseQuestion);
    }

    public Page<CourseQuestion> findAllByCourseId(Long courseId, Pageable pageable){
        return courseQuestionRepository.findAllByCourseId(courseId,pageable);
    }

    public Page<CourseQuestion> findAllByLectureId(Long lectureId, Pageable pageable){
        return courseQuestionRepository.findAllByLectureId(lectureId,pageable);
    }

    public Long countAllByCourseId(Long courseId){
        return courseQuestionRepository.countAllByCourseId(courseId);
    }

    public Long countAllByLectureId(Long lectureId){
        return courseQuestionRepository.countAllByLectureId(lectureId);
    }

    public CourseQuestion publishCourseQuestion(PublishCourseQuestionRequest request, OAuth2Authentication authentication) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return publishCourseQuestion(request,jwtUserDetail.getUserUUID(),jwtUserDetail.getUserFullName());
    }


    public CourseQuestion publishCourseQuestion(PublishCourseQuestionRequest request, String creatorUUID, String creatorName) {

        CourseQuestion courseQuestion = new CourseQuestion();
        courseQuestion.setCourseId(request.getCourseId());
        courseQuestion.setCreatorName(creatorName);
        courseQuestion.setDescription(request.getDescription());
        courseQuestion.setLectureId(request.getLectureId());
        courseQuestion.setTitle(request.getTitle());
        courseQuestion.setCreatorUUID(creatorUUID);

        return save(courseQuestion);
    }


}
