package com.unionbankng.future.learn.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.CreateCourseRequest;
import com.unionbankng.future.learn.services.CourseBulkUploadService;
import com.unionbankng.future.learn.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CoursesController {

    private final CourseService courseService;
    private final CourseBulkUploadService courseBulkUploadService;


    @GetMapping("/v1/courses/get_where_iam_instructor")
    public ResponseEntity<APIResponse<Page<Course>>> getCoursesByInstructor(@ApiIgnore OAuth2Authentication authentication
    , @RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllWhereIamInstructor(authentication, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @GetMapping("/v1/courses/get_where_iam_creator")
    public ResponseEntity<APIResponse<Page<Course>>> getCoursesByCreator(@ApiIgnore OAuth2Authentication authentication
            , @RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllWhereIamCreator(authentication, PageRequest.of(page,size));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @GetMapping("/v1/courses/{courseId}")
    public ResponseEntity<APIResponse<Course>> findById(@PathVariable Long courseId){

        Course course = courseService.findById(courseId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,course));
    }

    @GetMapping("/v1/courses")
    public ResponseEntity<APIResponse<Page<Course>>> getAllCourses(@RequestParam int page, @RequestParam int size){

        Page<Course> courses = courseService.findAllByIsPublished(true,PageRequest.of(page,size));
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }

    @GetMapping("/v1/courses_by_category")
    public ResponseEntity<APIResponse<Page<Course>>> getAllCoursesByCategory(@RequestParam Long id, @RequestParam int page, @RequestParam int size){
        Page<Course> courses = courseService.findAllByCategory(id,PageRequest.of(page,size));
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,courses));
    }


    @PostMapping("/v1/courses/create_course")
    public ResponseEntity<APIResponse<Course>> createCourseAPI(@RequestBody CreateCourseRequest request,
                                                               @ApiIgnore OAuth2Authentication authentication){

        Course course = courseService.createCourse(request,authentication);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,course));
    }

    @PostMapping(value = "/v1/courses/create_course_from_file", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> createCourseFromFileAPI(@RequestParam(value = "courseFile") MultipartFile courseFile, @ApiIgnore OAuth2Authentication authentication) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(courseBulkUploadService.uploadBulkCourseFiles(authentication,courseFile));
    }

    @PostMapping(value = "/v1/courses/create_course_from_url", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse> createCourseFromURL(@RequestParam(value = "course") String course, @RequestParam(value = "lectures") String lectures,  @ApiIgnore OAuth2Authentication authentication) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.createEmbeddedCourse(course,lectures,authentication));
    }

    @PostMapping(value = "/v1/courses/{courseId}/upload_course_img",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<Course>> uploadCourseImage(@NotNull @RequestPart("image") MultipartFile image,
                                                                @PathVariable Long courseId) throws IOException {

        Course course = courseService.updateCourseImg(image,courseId);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful",true,course));
    }


    @PostMapping("/v1/courses/publish/{courseId}")
    public ResponseEntity<APIResponse<Course>> createCourseAPI(@PathVariable Long courseId,
                                                               @ApiIgnore OAuth2Authentication authentication){
        courseService.publishCourse(courseId,authentication);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,"Course publish successful"));
    }


    @PutMapping("/v1/courses/update_course/{courseId}")
    public ResponseEntity<APIResponse<Course>> updateCourseAPI(@PathVariable Long courseId,@RequestBody CreateCourseRequest request,
                                                               @ApiIgnore OAuth2Authentication authentication){
        Course course = courseService.updateCourse(courseId,request,authentication);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,course));
    }

    @DeleteMapping("/v1/courses/delete_course/{courseId}")
    public ResponseEntity<APIResponse<Boolean>> deleteCourseById(@PathVariable Long courseId){
        Boolean state = courseService.deleteCourseById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,state));
    }

    @GetMapping("/v1/total/course/created/by/{uuid}")
    public ResponseEntity<APIResponse<Long>> getTotalCourseCreatedByUser(@PathVariable String  uuid){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,courseService.getTotalCourseCreatedByUserUUID(uuid)));
    }


}
