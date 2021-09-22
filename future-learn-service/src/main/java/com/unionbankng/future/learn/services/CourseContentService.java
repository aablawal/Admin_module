package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.CourseContentRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseContentService {

    private final CourseContentRepository courseContentRepository;


    public CourseContent save(CourseContent courseContent){
        return courseContentRepository.save(courseContent);
    }


    public List<CourseContent> findAllByCourseId(Long courseId, Sort sort){
        return courseContentRepository.findAllByCourseId(courseId,sort);
    }

    public Page<CourseContent> findAllByCreatorUUID(OAuth2Authentication authentication, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        return findAllByCreatorUUID(jwtUserDetail.getUserUUID(),pageable);
    }

    public Page<CourseContent> findAllByCreatorUUID(String creatorUUID, Pageable pageable){
        return courseContentRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }



    public CourseContent createNewContent(CourseContentRequest request,OAuth2Authentication authentication){

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return createNewContent(request,jwtUserDetail.getUserUUID());
    }

    public CourseContent createNewContent(CourseContentRequest request,String creatorUUID){

        CourseContent courseContent = new CourseContent();
        courseContent.setCourseContentText(request.getCourseContentText());
        courseContent.setCourseId(request.getCourseId());
        courseContent.setCreatorUUID(creatorUUID);
        courseContent.setIndexNo(request.getIndex());

        return save(courseContent);
    }

    public CourseContent updateCourseContent(CourseContent request){

        CourseContent courseContent = new CourseContent();
        courseContent.setId(request.getId());
        courseContent.setCourseContentText(request.getCourseContentText());
        courseContent.setCourseId(request.getCourseId());
        courseContent.setIndexNo(request.getIndexNo());
        courseContent.setCreatorUUID(request.getCreatorUUID());

        return save(courseContent);
    }

    public void deleteById(Long contentId){

        courseContentRepository.deleteById(contentId);

    }

}
