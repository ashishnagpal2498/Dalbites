package com.asdc.dalbites.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetupRestaurantAccountDTO {
    private Long building_id;
    private Long id;
    private String name;
    private String description;
    private String fileName;

    public SetupRestaurantAccountDTO(String name, Long buildingId, String description, String fileName) {
        this.building_id = buildingId;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
    }

    public SetupRestaurantAccountDTO(Long id, String name, Long buildingId, String description, String fileName) {
        this.id = id;
        this.building_id = buildingId;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
    }
}
