package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing menu items.
 */
@Getter
@Setter
public class MenuItemDTO {
	public Long getId() {
		return id;
	}
	public void setMenuId(Long id) {
		this.id = id;
	}
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsAvailable(){
		return is_available;
	}
	public void setIsAvailable(Boolean is_available){
		this.is_available = is_available;
	}
	public String getMenuImage(){
		return menu_image;
	}
	public void setMenuImage(String menu_image){
		this.menu_image = menu_image;
	}
	public Long getRestaurantId(){
		return restaurant_id;
	}
	public void setRestaurantId(Long restaurant_id){
		this.restaurant_id = restaurant_id;
	}

	private Long id;
	private String name;
    private Double time;
    private Double price;
    private String description;
	private Boolean is_available;
	private String menu_image;
	private Long restaurant_id;

	public MenuItemDTO() {
		
	}

	public MenuItemDTO(String name, String description, Double price, Double time, Boolean is_available, String menu_image, Long restaurant_id){
		this.name = name;
		this.description = description;
		this.price = price;
		this.time = time;
		this.is_available = is_available;
		this.menu_image = menu_image;
		this.restaurant_id = restaurant_id;
	}

	public MenuItemDTO(String name, String description, Double price, Double time, Boolean is_available, Long restaurant_id){
		this.name = name;
		this.description = description;
		this.price = price;
		this.time = time;
		this.is_available = is_available;
		this.restaurant_id = restaurant_id;
	}
}
