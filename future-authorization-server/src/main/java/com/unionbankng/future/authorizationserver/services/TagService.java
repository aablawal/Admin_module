package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Page<Tag> getTagsByTypeAndNameLike (TagType tagType, String likelyName, Pageable pageable){
        return tagRepository.findByTypeAndNameLike(tagType,likelyName,pageable);
    }


    public Page<Tag> getAllTagsByType (TagType tagType,Pageable pageable){
        return tagRepository.findByType(tagType,pageable);
    }
}
