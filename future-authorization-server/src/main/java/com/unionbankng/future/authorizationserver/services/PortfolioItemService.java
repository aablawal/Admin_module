package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.repositories.PortfolioItemRepository;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.utils.App;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioItemService {

    private final PortfolioItemRepository portfolioItemRepository;
    private final ProfileRepository profileRepository;
    private final FileStorageService fileStorageService;
    private final App app;

    public Page<PortfolioItem> findAllByUserId(Long userId, Pageable pageable){

        Long profileId = profileRepository.findByUserId(userId).get().getId();

        app.print(String.format("Portfolio Service: Fetching profileId from userId. ProfileId = %s", profileId.toString()));

        return portfolioItemRepository.findAllByProfileId(profileId,pageable);
    }

    public Optional<PortfolioItem> findById (Long id){
        return portfolioItemRepository.findById(id);
    }

    public void deleteById (Long id){
        PortfolioItem portfolioItem = portfolioItemRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "PortfolioItem Not Found"));

        if(portfolioItem.getPortfolioVideoMedia() != null) {
            int status = fileStorageService.deleteFileFromStorage(portfolioItem.getPortfolioVideoMedia(), BlobType.VIDEO);
            if (status != 200)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        if(portfolioItem.getPortfolioImageMedia() != null) {
            int status = fileStorageService.deleteFileFromStorage(portfolioItem.getPortfolioImageMedia(), BlobType.IMAGE);
            if (status != 200)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }

        portfolioItemRepository.deleteById(id);
    }

    public PortfolioItem saveFromRequest (MultipartFile img, MultipartFile video, PortfolioItemRequest request, PortfolioItem portfolioItem) throws IOException {

        Long profileId = profileRepository.findByUserId(request.getUserId()).get().getId();
        portfolioItem.setProfileId(profileId);
        portfolioItem.setTitle(request.getTitle());
        portfolioItem.setDescription(request.getDescription());
        portfolioItem.setLink(request.getLink());
            if (video != null) {
                app.print("Saving Video");
                String source = fileStorageService.storeFile(video, request.getProfileId(), BlobType.VIDEO);
                portfolioItem.setPortfolioVideoMedia(source);
            }
            if (img != null) {
                app.print("Saving Image");
                String source = fileStorageService.storeFile(img, request.getProfileId(), BlobType.IMAGE);
                portfolioItem.setPortfolioImageMedia(source);
            }

        app.print(portfolioItem);

        return portfolioItemRepository.save(portfolioItem);
    }

}
