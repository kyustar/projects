package com.ssafy.dananuo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Goods {

    private String gcode;
    private String name;
    private String manufacturer;
    private String brand;
    private String imageURL;
    private String big_category;
    private String middle_category;
    private String small_category;
    private String detail_category;
    private String description;

}


