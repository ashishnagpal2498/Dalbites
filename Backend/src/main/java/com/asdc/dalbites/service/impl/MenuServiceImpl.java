package com.asdc.dalbites.service.impl;

import com.asdc.dalbites.mappers.MenuItemMapper;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.model.DAO.MenuItem;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.repository.RestaurantRepository;
import com.asdc.dalbites.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemMapper menuItemMapper; // Uncomment this line if you intend to use MenuItemMapper

    @Override
    public MenuItemDTO createMenuItem(Long restaurantId, MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);

        menuItem.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found")));
        menuItem.setCreated_at(new Date()); // Corrected method name
        menuItem.setIsDeleted(0); // Corrected attribute name
        menuItem.setUpdated_at(new Date()); // Corrected method name

        menuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toDTO(menuItem);
    }

    @Override
    public List<MenuItemDTO> getAllItems(Long restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        List<MenuItem> menuItemList = menuItemRepository.findByRestaurantIdAndIsDeletedFalse(restaurantId);

        return menuItemList.stream()
                .map(menuItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}
