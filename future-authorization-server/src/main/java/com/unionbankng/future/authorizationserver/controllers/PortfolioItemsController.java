package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.services.PortfolioItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class PortfolioItemsController {

    private final PortfolioItemService portfolioItemService;

    @GetMapping("/v1/portfolio_items/find_by_user_id/{userId}")
    public ResponseEntity<APIResponse> findPortfolioItemByUserId(@PathVariable Long userId,
                                                              @RequestParam int pageNo, @RequestParam int limit) {

        Page<PortfolioItem> portfolioItems = portfolioItemService.findAllByUserId(userId,PageRequest.of(pageNo, limit,
                Sort.by("createdAt").ascending()));

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,portfolioItems));

    }

    @PostMapping("/v1/portfolio_items/create_new")
    public ResponseEntity<APIResponse> addNewPortfolioItem(@RequestBody PortfolioItemRequest request) {

        PortfolioItem portfolioItem = portfolioItemService.saveFromRequest(request,new PortfolioItem());
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,portfolioItem));

    }

    @PutMapping("/v1/portfolio_items/update_existing")
    public ResponseEntity<APIResponse> updateExperience(@RequestBody PortfolioItemRequest request) {

        PortfolioItem portfolioItem = portfolioItemService.findById(request.getPortfolioItemId()).orElse(null);
        if(portfolioItem == null)
            return ResponseEntity.ok().body(new APIResponse("PortfolioItem not found",false,null));

        portfolioItem = portfolioItemService.saveFromRequest(request,portfolioItem);

        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,portfolioItem));

    }

    @DeleteMapping("/v1/portfolio_items/delete/{portfolioItemId}")
    public ResponseEntity<APIResponse> deleteExperience(@PathVariable Long portfolioItemId){
        portfolioItemService.deleteById(portfolioItemId);
        return ResponseEntity.ok().body(new APIResponse("Request Successful",true,null));
    }

}
