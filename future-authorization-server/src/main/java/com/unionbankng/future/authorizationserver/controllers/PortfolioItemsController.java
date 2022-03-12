package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.services.PortfolioItemService;
import com.unionbankng.future.authorizationserver.utils.App;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class PortfolioItemsController {

    private final PortfolioItemService portfolioItemService;
    private final App app;

    @GetMapping("/v1/portfolio_items/find_by_profile_id/{userId}")
    public ResponseEntity<APIResponse<Page<PortfolioItem>>> findPortfolioItemByProfileId(@PathVariable Long userId,
                                                                                         @RequestParam int pageNo, @RequestParam int limit) {

        Page<PortfolioItem> portfolioItems = portfolioItemService.findAllByUserId(userId,PageRequest.of(pageNo, limit,
                Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItems));

    }


    @PostMapping(value = "/v1/portfolio_items/create_new")
    public ResponseEntity<APIResponse<PortfolioItem>> addNewPortfolioItem(@Valid @RequestBody PortfolioItemRequest request) throws IOException {
        app.print(" ###### Adding portfolio");
        app.print(request);
        app.print(request.getFiles());
        PortfolioItem portfolioItem = portfolioItemService.saveFromRequest(request.getFiles(),request,new PortfolioItem());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItem));

    }

    @PostMapping(value = "/v1/portfolio_items/update_existing",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<PortfolioItem>> updateExperience(@Nullable  @RequestParam("file") MultipartFile file, @Valid @RequestParam PortfolioItemRequest request)
            throws IOException {

        PortfolioItem portfolioItem = portfolioItemService.findById(request.getPortfolioItemId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PortfolioItem not found")
        );

        portfolioItem = portfolioItemService.saveFromRequest((List<MultipartFile>) file,request,portfolioItem);

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,portfolioItem));

    }

    @DeleteMapping("/v1/portfolio_items/delete/{portfolioItemId}")
    public ResponseEntity<APIResponse<String>> deleteExperience(@PathVariable Long portfolioItemId){
        portfolioItemService.deleteById(portfolioItemId);
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,"Request Successful"));
    }

}
