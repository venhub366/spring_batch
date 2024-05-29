package com.batch.example.BootBatch3X.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String product_id;
    private String title;
    private String description;
    private String price;
    private String discount;
    private String discount_price;
}
