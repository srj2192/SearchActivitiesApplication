package com.getyourguide.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityResponse {
    private Long id;
    private String title;
    private String price;
    private double rating;
    private boolean specialOffer;
    private String supplierName;
    private String supplierLocation;
}
