package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class PortfolioItemRequest {

    private Long portfolioItemId;
    @NotNull
    private Long profileId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private String link;
    private String media;
}
