package com.getyourguide.demo.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private Long id;
    private String title;
    private double price;
    private String currency;
    private double rating;
    private boolean specialOffer;
    private Long supplierId;
}
