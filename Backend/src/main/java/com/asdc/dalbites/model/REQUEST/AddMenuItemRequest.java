package com.asdc.dalbites.model.REQUEST;
import org.springframework.web.multipart.MultipartFile;

public class AddMenuItemRequest {
    private MultipartFile file;
    private String name;
    private String description;
    private String price;
    private String time;
    private String is_available;
    private String restaurant_id;

    public MultipartFile getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public String getIsAvailable() {
        return is_available;
    }

    public String getRestaurantId() {
        return restaurant_id;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setIsAvailable(String is_available) {
        this.is_available = is_available;
    }

    public void setRestaurantId(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
