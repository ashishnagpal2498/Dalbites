package com.asdc.dalbites.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.asdc.dalbites.model.DAO.BuildingDao;
import com.asdc.dalbites.model.DTO.BuildingDTO;
import com.asdc.dalbites.repository.BuildingRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BuildingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BuildingServiceImplTest {
    @MockBean
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingServiceImpl buildingServiceImpl;

    /**
     * Method under test: {@link BuildingServiceImpl#addBuilding(BuildingDTO)}
     */
    @Test
    void testAddBuilding() throws Exception {
        BuildingDao buildingDao = new BuildingDao();
        buildingDao.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        buildingDao.setDescription("The characteristics of someone or something");
        buildingDao.setId(1L);
        buildingDao.setIs_deleted(1);
        buildingDao.setName("Name");
        buildingDao.setUpdated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(buildingRepository.save(Mockito.<BuildingDao>any())).thenReturn(buildingDao);

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setDescription("The characteristics of someone or something");
        buildingDTO.setName("Name");
        HashMap<String, Object> actualAddBuildingResult = buildingServiceImpl.addBuilding(buildingDTO);
        verify(buildingRepository).save(Mockito.<BuildingDao>any());
        assertEquals(1, actualAddBuildingResult.size());
    }

    /**
     * Method under test:  {@link BuildingServiceImpl#addBuilding(BuildingDTO)}
     */
    @Test
    void testAddBuilding2() throws Exception {
        when(buildingRepository.save(Mockito.<BuildingDao>any())).thenThrow(new DataIntegrityViolationException("message"));

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setDescription("The characteristics of someone or something");
        buildingDTO.setName("Name");
        assertThrows(Exception.class, () -> buildingServiceImpl.addBuilding(buildingDTO));
        verify(buildingRepository).save(Mockito.<BuildingDao>any());
    }

    /**
     * Method under test: {@link BuildingServiceImpl#getBuildings()}
     */
    @Test
    void testGetBuildings() {
        ArrayList<BuildingDao> buildingDaoList = new ArrayList<>();
        when(buildingRepository.findAll()).thenReturn(buildingDaoList);
        List<BuildingDao> actualBuildings = buildingServiceImpl.getBuildings();
        verify(buildingRepository).findAll();
        assertTrue(actualBuildings.isEmpty());
        assertSame(buildingDaoList, actualBuildings);
    }

    /**
     * Method under test:  {@link BuildingServiceImpl#getBuildings()}
     */
    @Test
    void testGetBuildings2() {
        when(buildingRepository.findAll()).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> buildingServiceImpl.getBuildings());
        verify(buildingRepository).findAll();
    }
}
