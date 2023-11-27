package com.asdc.dalbites.model.REQUEST;

import org.springframework.web.multipart.MultipartFile;

public class SetupRestaurantRequest {
    private MultipartFile file;
    private String building;
    private String name;
    private String description;
    private String deliveryTime;

    public MultipartFile getFile() {
        return file;
    }

    public String getBuilding() {
        return building;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
