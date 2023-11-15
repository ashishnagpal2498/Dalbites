package com.asdc.dalbites.model.DTO;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RestaurantsDTO {
    public List<Long> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Long> buildings) {
		this.buildings = buildings;
	}

	private List<Long> buildings;
}
