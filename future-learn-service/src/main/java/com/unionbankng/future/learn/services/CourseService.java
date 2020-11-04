package com.unionbankng.future.learn.services;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.repositories.CourseRepository;
import com.unionbankng.future.learn.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final FileStorageService fileStorageService;

    public Course save(Course course){
        return courseRepository.save(course);
    }

    public Page<Course> findAllByCreatorUUID(String creatorUUID, Pageable pageable){
        return courseRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }

    public Page<Course> findAllByIsPublished(Boolean isPublished, Pageable pageable){
        return courseRepository.findAllByIsPublished(isPublished,pageable);
    }

    public Optional<Course> findById(Long courseId){
        return courseRepository.findById(courseId);
    }

    public List<Course> findAllByIdIn (List<Long> ids){
        return courseRepository.findAllByIdIn(ids);
    }

    public Page<Course> findAllByInstructorsIn(Instructor instructor, Pageable pageable){
        return courseRepository.findAllByInstructorsIn(instructor,pageable);
    }

    public Page<Course> findAllByInstructorsIn(String instructorUUID, Pageable pageable){
        Instructor instructor = instructorRepository.findByInstructorUUID(instructorUUID)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        return findAllByInstructorsIn(instructor,pageable);
    }

    @CachePut(value = "course", key="#courseId")
    public Course updateCourseImg(MultipartFile image , @ParameterValueKeyProvider Long courseId) throws IOException {

        Course course = courseRepository.findById(courseId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));
        if(course.getCourseImg() != null)
            fileStorageService.deleteFileFromStorage(course.getCourseImg(), BlobType.IMAGE);

        String source = fileStorageService.storeFile(image,courseId,BlobType.IMAGE);
        course.setCourseImg(source);
        return courseRepository.save(course);
    }

    public Course createCourse(CreateCourseRequest request){

        Course course = new Course();
        ArrayList<Instructor> instructors = new ArrayList<>();

        //instructor
        if(request.getInstructors() != null)
        for (Instructor i : request.getInstructors()){
            Instructor instructor = instructorRepository.findByInstructorUUID(i.getInstructorUUID()).orElse(new Instructor());
            instructor.setNumberOfCourses(instructor.getNumberOfCourses() == null ? 0: instructor.getNumberOfCourses() + 1);
            instructor.setInstructorUUID(i.getInstructorUUID());
            instructors.add(instructor);
        }

        course.setCourseTitle(request.getCourseTitle());
        course.setCreatorUUID(request.getCreatorUUID());
        course.setEstimatedTimeToComplete(request.getEstimatedTimeToComplete());
        course.setInstructors(instructors);
        course.setDescription(request.getDescription());
        course.setShortDesc(request.getShortDesc());
        course.setIsPaid(request.getIsPaid());
        course.setIsPublished(request.getIsPublished());
        course.setOutcomes(request.getOutcomes());
        course.setRequirements(request.getRequirements());
        course.setPrice(request.getPrice());

        return save(course);

    }//create course


}
