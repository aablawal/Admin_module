package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class TagsController {

    private final TagService tagService;

    @GetMapping("/v1/tags/find_by_type_likely_name")
    public ResponseEntity<APIResponse> getTagsByTypeAndName(@RequestParam TagType type, @RequestParam String name,
                                                            @RequestParam int pageNo, @RequestParam int limit) {

        Page<Tag> tags = tagService.getTagsByTypeAndNameLike(type,name, PageRequest.of(pageNo, limit, Sort.by("name").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,tags));

    }

    @GetMapping("/v1/tags/find_by_type")
    public ResponseEntity<APIResponse> getTagsByType(@RequestParam TagType type,
                                                            @RequestParam int pageNo, @RequestParam int limit) {

        Page<Tag> tags = tagService.getAllTagsByType(type, PageRequest.of(pageNo, limit, Sort.by("name").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,tags));

    }
}
