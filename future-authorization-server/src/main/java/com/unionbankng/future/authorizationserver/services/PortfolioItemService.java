package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.repositories.PortfolioItemRepository;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioItemService {

    private final PortfolioItemRepository portfolioItemRepository;
    private final FileStorageService fileStorageService;

    public Page<PortfolioItem> findAllByUserId (Long userId, Pageable pageable){
        return portfolioItemRepository.findAllByUserId(userId,pageable);
    }

    public Optional<PortfolioItem> findById (Long id){
        return portfolioItemRepository.findById(id);
    }

    public void deleteById (Long id){
        PortfolioItem portfolioItem = portfolioItemRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PortfolioItem Not Found"));

        if(portfolioItem.getMedia() != null) {
            int status = fileStorageService.deleteFileFromStorage(portfolioItem.getMedia(), BlobType.IMAGE);
            if (status != 200)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        portfolioItemRepository.deleteById(id);
    }

    public PortfolioItem saveFromRequest (MultipartFile file,PortfolioItemRequest request, PortfolioItem portfolioItem) throws IOException {
        portfolioItem.setUserId(request.getUserId());
        portfolioItem.setTitle(request.getTitle());
        portfolioItem.setDescription(request.getDescription());
        portfolioItem.setLink(request.getLink());
        if (file != null){
            String source = fileStorageService.storeFile(file, request.getUserId(), BlobType.IMAGE);
            portfolioItem.setMedia(source);
        }

        return portfolioItemRepository.save(portfolioItem);
    }

}
