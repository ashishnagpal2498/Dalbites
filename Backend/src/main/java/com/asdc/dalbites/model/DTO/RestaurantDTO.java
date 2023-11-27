package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing restaurants.
 */
@Getter
@Setter
public class RestaurantDTO {
    private Long building_id;
    public Long getBuilding_id() {
		return building_id;
	}

	public void setBuilding_id(Long building_id) {
		this.building_id = building_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private Long id;
    private String name;
    private String description;
    private String fileName;
    private String deliveryTime;

	/**
	 * Constructor for creating a new RestaurantDTO.
	 *
	 * @param name          The name of the restaurant.
	 * @param buildingId    The ID of the building to which the restaurant belongs.
	 * @param description   The description of the restaurant.
	 * @param fileName      The file name associated with the restaurant.
	 * @param deliveryTime  The delivery time of the restaurant.
	 */
    public RestaurantDTO(String name, Long buildingId, String description, String fileName, String deliveryTime) {
        this.building_id = buildingId;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.deliveryTime = deliveryTime;
    }

	/**
	 * Constructor for creating a new RestaurantDTO with an existing ID.
	 *
	 * @param id            The unique identifier of the restaurant.
	 * @param name          The name of the restaurant.
	 * @param buildingId    The ID of the building to which the restaurant belongs.
	 * @param description   The description of the restaurant.
	 * @param fileName      The file name associated with the restaurant.
	 * @param deliveryTime  The delivery time of the restaurant.
	 */
    public RestaurantDTO(Long id, String name, Long buildingId, String description, String fileName, String deliveryTime) {
        this.id = id;
        this.building_id = buildingId;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
        this.deliveryTime = deliveryTime;
    }

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
}
