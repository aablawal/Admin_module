package com.unionbankng.future.learn.services;

import com.unionbankng.future.futureutilityservice.grpcserver.StreamLinksResponse;
import com.unionbankng.future.learn.entities.Lecture;
import com.unionbankng.future.learn.entities.Question;
import com.unionbankng.future.learn.enums.LectureType;
import com.unionbankng.future.learn.pojo.*;
import com.unionbankng.future.learn.repositories.LectureRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import liquibase.util.file.FilenameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final FutureStreamingService futureStreamingService;
    private  final UtilityServiceInterfaceService utilityServiceInterfaceService;

    private String[] allowedVideoExtensions = new String[]{"mp4","3gp","mkv","avi"};

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

    public Page<Lecture> findAllWhereIamCreator(OAuth2Authentication authentication, Pageable pageable){
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        return findAllByCreatorUUID(jwtUserDetail.getUserUUID(),pageable);
    }

    public Page<Lecture> findAllByCreatorUUID(String creatorUUID,Pageable pageable){
        return lectureRepository.findAllByCreatorUUID(creatorUUID,pageable);
    }

    public Lecture createNewLecture(MultipartFile file, CreateLectureRequest request,OAuth2Authentication authentication) throws IOException {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();

        if(request.getType().equals(LectureType.VIDEO)) {
            return createVideoLecture(file, request,jwtUserDetail.getUserUUID(),details.getTokenValue());
        }else{

            return createQuizLecture(request,jwtUserDetail.getUserUUID());

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

    public Lecture createVideoLecture(MultipartFile file, CreateLectureRequest request, String creatorUUID,String token) throws IOException {

        if (file == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        // Convert String Array to List
        List<String> extensionList = Arrays.asList(allowedVideoExtensions);

        if (!extensionList.contains(extension))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File format not allowed");

        Long startTime = System.currentTimeMillis();

        Response<APIResponse<StreamingLocatorResponse>> responseResponse = utilityServiceInterfaceService.uploadVideoStream(token,file);

        Long endTime = System.currentTimeMillis() - startTime;
        System.out.println("=================== "+endTime);
        if(!responseResponse.isSuccessful())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");

        if(!responseResponse.body().isSuccess())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");

        if(!responseResponse.body().getPayload().getSuccess())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload failed");

        Lecture lecture = new Lecture();
        lecture.setCourseContentId(request.getCourseContentId());
        lecture.setCourseId(request.getCourseId());
        lecture.setCreatorUUID(creatorUUID);
        lecture.setDuration(request.getDuration());
        lecture.setOutputAssetName(responseResponse.body().getPayload().getAssetName());
        lecture.setStreamingLocatorName(responseResponse.body().getPayload().getLocatorName());
        lecture.setIndexNo(request.getIndex());
        lecture.setTitle(request.getTitle());
        lecture.setType(request.getType());

        return save(lecture);
    }

    public Lecture createQuizLecture(CreateLectureRequest request,String creatorUUID) {

        if(request.getQuestionList().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Questions can't be empty");

        Lecture lecture = new Lecture();
        lecture.setCourseContentId(request.getCourseContentId());
        lecture.setCourseId(request.getCourseId());
        lecture.setCreatorUUID(creatorUUID);
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
