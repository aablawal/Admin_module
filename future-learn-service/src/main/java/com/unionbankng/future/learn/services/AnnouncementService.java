package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.Announcement;
import com.unionbankng.future.learn.entities.Course;
import com.unionbankng.future.learn.pojo.CreateAnnouncementRequest;
import com.unionbankng.future.learn.pojo.JwtUserDetail;
import com.unionbankng.future.learn.repositories.AnnouncementRepository;
import com.unionbankng.future.learn.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final CourseService courseService;

    public Announcement save(Announcement announcement){
        return announcementRepository.save(announcement);
    }

    public Page<Announcement> findAllByCourseId(Long courseId, Pageable pageable){
        return announcementRepository.findAllByCourseId(courseId,pageable);
    }

    public void deleteById(Long courseId){
        announcementRepository.deleteById(courseId);
    }


    @Transactional
    public Announcement createOrUpdateAnnouncement(CreateAnnouncementRequest request, OAuth2Authentication authentication) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        return createOrUpdateAnnouncement(request,jwtUserDetail.getUserUUID());
    }

    @Transactional
    public Announcement createOrUpdateAnnouncement(CreateAnnouncementRequest request, String posterUUID) {

        //confirm poster is instructor or course owner
       Course course =  courseService.findById(request.getCourseId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found"));

        List<String> UUIDs = course.getInstructors().stream().map(i -> i.getInstructorUUID()).collect(Collectors.toList());
        UUIDs.add(course.getCreatorUUID());

        if(!UUIDs.contains(posterUUID))
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only instructors and course creators can post announcements");


        Announcement announcement = new Announcement();
        announcement.setId(request.getId());
        announcement.setAnnouncementText(request.getAnnouncementText());
        announcement.setCourseId(request.getCourseId());
        announcement.setPosterUUID(posterUUID);

        return save(announcement);
    }
}
