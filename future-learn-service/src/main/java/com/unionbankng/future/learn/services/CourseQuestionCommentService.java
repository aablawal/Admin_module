package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.CourseQuestionComment;
import com.unionbankng.future.learn.pojo.AddCourseQuestionCommentRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.CourseQuestionCommentRepository;
import com.unionbankng.future.learn.repositories.CourseQuestionRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CourseQuestionCommentService {

    private final CourseQuestionCommentRepository courseQuestionCommentRepository;
    private final CourseQuestionRepository courseQuestionRepository;

    public CourseQuestionComment save(CourseQuestionComment courseQuestionComment){
        return courseQuestionCommentRepository.save(courseQuestionComment);
    }

    public Page<CourseQuestionComment> findAllByCourseQuestionId(Long courseQuestionId, Pageable pageable){
        return courseQuestionCommentRepository.findAllByCourseQuestionId(courseQuestionId,pageable);
    }

    public Long countAllByCourseQuestionId(Long courseQuestionId){
        return courseQuestionCommentRepository.countAllByCourseQuestionId(courseQuestionId);
    }

    public CourseQuestionComment addCommentToQuestion(AddCourseQuestionCommentRequest request, OAuth2Authentication authentication) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return addCommentToQuestion(request,jwtUserDetail.getUserUUID(),jwtUserDetail.getUserFullName());
    }


    public CourseQuestionComment addCommentToQuestion(AddCourseQuestionCommentRequest request, String creatorUUID, String creatorName) {

        if(!courseQuestionRepository.existsById(request.getCourseQuestionId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Oops it appears the question does not exist");

        CourseQuestionComment courseQuestionComment = new CourseQuestionComment();
        courseQuestionComment.setCourseQuestionId(request.getCourseQuestionId());
        courseQuestionComment.setCreatorName(creatorName);
        courseQuestionComment.setReply(request.getReply());
        courseQuestionComment.setCreatorUUID(creatorUUID);

        return save(courseQuestionComment);
    }


}
