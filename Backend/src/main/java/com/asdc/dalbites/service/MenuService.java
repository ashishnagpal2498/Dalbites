package com.asdc.dalbites.service;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MenuService {

    public List<MenuItemDao> getMenu(Long restaurantId) throws Exception;

    public List<MenuItemDao> addMenuItem(Long restaurantId, MultipartFile file, MenuItemDTO menuItemDTO) throws Exception;
}
