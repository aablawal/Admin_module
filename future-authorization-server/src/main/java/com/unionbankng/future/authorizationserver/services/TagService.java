package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    @Cacheable(value = "tags_by_name", key="#likelyName")
    public List<Tag> getTagsByTypeAndNameLike (TagType tagType, String likelyName){
        return tagRepository.findByTypeAndNameLike(tagType,likelyName);
    }


    @Cacheable(value = "tags", key="#tagType")
    public List<Tag> getAllTagsByType (TagType tagType){
        return tagRepository.findByType(tagType);
    }
}
