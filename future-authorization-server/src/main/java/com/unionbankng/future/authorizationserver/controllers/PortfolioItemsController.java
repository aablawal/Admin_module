package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.services.PortfolioItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PortfolioItemsController {

    private final PortfolioItemService portfolioItemService;

    @GetMapping("/v1/portfolio_items/find_by_profile_id/{profileId}")
    public ResponseEntity<APIResponse<Page<PortfolioItem>>> findPortfolioItemByProfileId(@PathVariable Long profileId,
                                                                                         @RequestParam int pageNo, @RequestParam int limit) {

        Page<PortfolioItem> portfolioItems = portfolioItemService.findAllByProfileId(profileId,PageRequest.of(pageNo, limit,
                Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItems));

    }

    @PostMapping(value = "/v1/portfolio_items/create_new",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<PortfolioItem>> addNewPortfolioItem(@Nullable  @RequestPart("file") MultipartFile file,
                                                           @Valid @RequestPart PortfolioItemRequest request) throws IOException {

        PortfolioItem portfolioItem = portfolioItemService.saveFromRequest(file,request,new PortfolioItem());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItem));

    }

    @PostMapping(value = "/v1/portfolio_items/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<PortfolioItem>> updateExperience(@Nullable  @RequestPart("file") MultipartFile file, @Valid @RequestBody PortfolioItemRequest request)
            throws IOException {

        PortfolioItem portfolioItem = portfolioItemService.findById(request.getPortfolioItemId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PortfolioItem not found")
        );

        portfolioItem = portfolioItemService.saveFromRequest(file,request,portfolioItem);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItem));

    }

    @DeleteMapping("/v1/portfolio_items/delete/{portfolioItemId}")
    public ResponseEntity<APIResponse<String>> deleteExperience(@PathVariable Long portfolioItemId){
        portfolioItemService.deleteById(portfolioItemId);
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,"Request Successful"));
    }

}
