package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.repositories.CourseContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<CourseContent> findAllByCreatorUUID(String creatorUUID, Pageable pageable){
        return courseContentRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }

    public CourseContent createNewContent(CourseContentRequest request){

        CourseContent courseContent = new CourseContent();
        courseContent.setCourseContentText(request.getCourseContentText());
        courseContent.setCourseId(request.getCourseId());
        courseContent.setCreatorUUID(request.getCreatorUUID());
        courseContent.setIndexNo(request.getIndex());

        return save(courseContent);
    }

}
