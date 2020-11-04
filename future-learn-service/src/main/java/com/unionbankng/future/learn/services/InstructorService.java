package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.entities.CourseContent;
import com.unionbankng.future.learn.entities.Instructor;
import com.unionbankng.future.learn.pojo.CourseContentRequest;
import com.unionbankng.future.learn.repositories.CourseContentRepository;
import com.unionbankng.future.learn.repositories.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;


    public Instructor save(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    public void saveAll(Iterable<Instructor> instructors){
         instructorRepository.saveAll(instructors);
    }


    public Optional<Instructor> findByInstructorUUID(String instructorUUID){
        return instructorRepository.findByInstructorUUID(instructorUUID);
    }

    public Boolean existsByInstructorUUID(String creatorUUID){
        return instructorRepository.existsByInstructorUUID(creatorUUID);
    }



}
