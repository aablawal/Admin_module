package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class TagsController {

    private final TagService tagService;

    @GetMapping("/v1/tags/find_by_type_likely_name")
    public ResponseEntity<APIResponse<List<Tag>>> getTagsByTypeAndName(@RequestParam TagType type, @RequestParam String name) {

        List<Tag> tags = tagService.getTagsByTypeAndNameLike(type,name);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,tags));

    }

    @GetMapping("/v1/tags/find_by_type")
    public ResponseEntity<APIResponse<List<Tag>>> getTagsByType(@RequestParam TagType type) {

        List<Tag> tags = tagService.getAllTagsByType(type);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,tags));

    }

    @PutMapping("/v1/tags/create")
    public ResponseEntity<APIResponse<Tag>> createTag(@RequestBody Tag tag) {

        Tag created = tagService.save(tag);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,created));

    }
}
