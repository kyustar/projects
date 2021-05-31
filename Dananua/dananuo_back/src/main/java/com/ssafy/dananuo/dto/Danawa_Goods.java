package com.ssafy.dananuo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "DANAWA_GOODS")
public class Danawa_Goods {

    @Id
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

    @Builder
    public Danawa_Goods(String gcode, String name, String manufacturer, String brand,
                        String imageURL, String big_category, String middle_category,
                        String small_category, String detail_category, String description) {

        this.gcode = gcode;
        this.name = name;
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.imageURL = imageURL;
        this.big_category = big_category;
        this.middle_category = middle_category;
        this.small_category = small_category;
        this.detail_category = detail_category;
        this.description = description;
    }
}


