package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for representing building information.
 */
@Getter
@Setter
public class BuildingDTO {
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of the building.
	 *
	 * @param description The new description of the building.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	private String name;
	private String description;
}
