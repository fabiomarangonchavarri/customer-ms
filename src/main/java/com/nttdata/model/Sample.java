package com.nttdata.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Sample {
    private String id;
    private String categoryId;
    private String categoryName;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private List<Tag> tags;
}

@Data
class Tag {
    private String id;
    private String name;
}
