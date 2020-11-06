package com.unionbankng.future.learn.services;

import com.unionbankng.future.futureutilityservice.grpcserver.StreamLinksResponse;
import com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse;
import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.pojo.CreateLectureRequest;
import com.unionbankng.future.learn.repositories.CourseContentRepository;
import com.unionbankng.future.learn.repositories.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final FutureStreamingService futureStreamingService;


    public Lecture save(Lecture lecture){
        return lectureRepository.save(lecture);
    }


    public List<Lecture> findAllByCourseContentId(Long courseContentId, Sort sort){
        return lectureRepository.findAllByCourseContentId(courseContentId,sort);
    }

    public Optional<Lecture> findById(Long lectureId){
        return lectureRepository.findById(lectureId);
    }

    public List<Lecture> findAllByCourseId(Long courseId){
        return lectureRepository.findAllByCourseId(courseId);
    }

    public Page<Lecture> findAllByCreatorUUID(String creatorUUID,Pageable pageable){
        return lectureRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }


    public Lecture createNewLecture(MultipartFile file, CreateLectureRequest request) throws IOException {

        if(request.getType().equals(LectureType.VIDEO)) {
            return createVideoLecture(file, request);
        }else{

            return createQuizLecture(request);

        }

    }

    public String generateStreamingLinks(Long lectureId){

        Lecture lecture = findById(lectureId).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecture Not Found")
        );

        if(!lecture.getType().equals(LectureType.VIDEO))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Streaming links can only be generated for video lectures");

        StreamLinksResponse response = futureStreamingService.getStreamingUrlsFromLocator(lecture.getStreamingLocatorName());

       return response.getSuccess() ? response.getCommaSeperatedStreamingLinks() : null;
    }

    private Lecture createVideoLecture(MultipartFile file, CreateLectureRequest request) throws IOException {

        if (file == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");

        StreamingLocatorResponse response = null;
        try {
            response = futureStreamingService.uploadAndGetStreamingLocator(file);
        } catch (InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");
        }

        if(response == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");

        if(!response.getSuccess())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");

        Lecture lecture = new Lecture();
        lecture.setCourseContentId(request.getCourseContentId());
        lecture.setCourseId(request.getCourseId());
        lecture.setCreatorUUID(request.getCreatorUUID());
        lecture.setDuration(request.getDuration());
        lecture.setOutputAssetName(response.getAssetName());
        lecture.setStreamingLocatorName(response.getLocatorName());
        lecture.setIndexNo(request.getIndex());
        lecture.setTitle(request.getTitle());
        lecture.setType(request.getType());

        return save(lecture);
    }

    private Lecture createQuizLecture(CreateLectureRequest request) throws IOException {

        if(request.getQuestionList().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questions can't be empty");

        Lecture lecture = new Lecture();
        lecture.setCourseContentId(request.getCourseContentId());
        lecture.setCourseId(request.getCourseId());
        lecture.setCreatorUUID(request.getCreatorUUID());
        lecture.setDuration(request.getDuration());
        lecture.setIndexNo(request.getIndex());
        lecture.setTitle(request.getTitle());
        lecture.setType(request.getType());

        lecture = save(lecture);
        //create quiz;
        for (Question q: request.getQuestionList()) {
            if(q.getAnswerIndex() == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An answer must be selected");

            if(q.getOptions().isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All questions must have options");

            q.setLectureId(lecture.getId());
        }

        lecture.setQuestions(request.getQuestionList());
        return save(lecture);
    }

}
