package com.asdc.dalbites.controller;

import com.asdc.dalbites.exception.ResourceNotFoundException;
import com.asdc.dalbites.model.DAO.MenuItemDao;
import com.asdc.dalbites.model.DTO.MenuItemDTO;
import com.asdc.dalbites.repository.MenuItemRepository;
import com.asdc.dalbites.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/restaurants")
public class MenuItemController {
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuService menuService;

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<?> getMenu(@PathVariable Long restaurantId) throws Exception{
        //return menuItemRepository.getMenu(restaurantId);
        try {
            List<MenuItemDao> result = menuService.getMenu(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PostMapping("/{restaurantId}/add-menu-item")
    public ResponseEntity<?> addMenuItem(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") String price, @RequestParam("time") String time, @RequestParam("is_available") String is_available, @RequestParam("restaurant_id") String restaurant_id) throws Exception{
        try {
            Boolean isAvailable;
            if(is_available.equals("true")) isAvailable = true;
            else isAvailable = false;

            Long restaurantId = Long.parseLong(restaurant_id);
            Double Price = Double.parseDouble(price);
            Double Time = Double.parseDouble(time);
            String file_name = file.getOriginalFilename();

            MenuItemDTO menuItemDTO = new MenuItemDTO(name, description, Price, Time, isAvailable , file_name, restaurantId);

            List<MenuItemDao> result = menuService.addMenuItem(restaurantId, file, menuItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @PutMapping("/{restaurantId}/update-menu-item")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItemDTO menuItemDTO) throws Exception{
        try {
            Optional<MenuItemDao> tempMenuItem = menuItemRepository.findById(menuItemDTO.getId());
            if(tempMenuItem.isPresent()){
                
                MenuItemDao menuItem = tempMenuItem.get();
                menuItem.setName(menuItemDTO.getName());
                menuItem.setDescription(menuItemDTO.getDescription());
                menuItem.setTime(menuItemDTO.getTime());
                menuItem.setPrice(menuItemDTO.getPrice());
                menuItem.setIs_available(menuItemDTO.getIs_available());
                menuItem.setUpdated_at(new Date());

                final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItem);
                List<MenuItemDao> result = menuService.getMenu(restaurantId);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);

            } else{
                throw new ResourceNotFoundException("Menu item not found on :: " + menuItemDTO.getId());
            }
        } catch (Exception error) {
            System.out.println(error);
            return ResponseEntity.internalServerError().body(error);
        }
    }

    @DeleteMapping("/{restaurantId}/delete-menu-item/{menuId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(value="restaurantId") Long restaurantId, @PathVariable(value="menuId") Long menuId) throws Exception{
        try {
            Optional<MenuItemDao> menuItem = menuItemRepository.findById(menuId);
            if(menuItem.isPresent()){
                MenuItemDao menuItemDao = menuItem.get();
                menuItemDao.setIs_deleted((short)1);
                final MenuItemDao updatedMenuItem = menuItemRepository.save(menuItemDao);
                List<MenuItemDao> result = menuService.getMenu(restaurantId);
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else{
                throw new ResourceNotFoundException("Menu item not found on :: " + menuId);
            }
        } catch (Exception error) {
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
