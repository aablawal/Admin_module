package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.repositories.PortfolioItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioItemService {

    private final PortfolioItemRepository portfolioItemRepository;

    public Page<PortfolioItem> findAllByUserId (Long userId, Pageable pageable){
        return portfolioItemRepository.findAllByUserId(userId,pageable);
    }

    public Optional<PortfolioItem> findById (Long id){
        return portfolioItemRepository.findById(id);
    }

    public void deleteById (Long id){
        portfolioItemRepository.deleteById(id);
    }

    public PortfolioItem saveFromRequest (PortfolioItemRequest request, PortfolioItem portfolioItem){
        portfolioItem.setUserId(request.getUserId());
        portfolioItem.setTitle(request.getTitle());
        portfolioItem.setDescription(request.getDescription());
        portfolioItem.setLink(request.getLink());
        portfolioItem.setMedia(request.getMedia());
        return portfolioItemRepository.save(portfolioItem);
    }

}
