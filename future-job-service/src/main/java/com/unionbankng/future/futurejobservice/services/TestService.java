package com.unionbankng.future.futurejobservice.services;

import com.unionbankng.future.futurejobservice.entities.Test;
import com.unionbankng.future.futurejobservice.repositories.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private  final TestRepository repository;
    public Test addTest(Test test){
        return repository.save(test);
    }

    public List<Test> getTests(){
        return  repository.findAll();
    }

}
