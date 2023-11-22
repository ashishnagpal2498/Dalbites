package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItemDao, Long> {

    /*
    Redundant Methods
    
    List<MenuItemDao> findByRestaurantId(Long restaurantId);
    List<MenuItem> findByRestaurantIdAndIsDeletedFalse(Long restaruantId);
    */
    /**
     * Retrieves a list of non-deleted {@link MenuItemDao} entities associated with a specific restaurant.
     *
     * @param restaurant_id The ID of the restaurant for which menu items are to be retrieved.
     * @return A list of non-deleted menu items associated with the specified restaurant.
     */
    @Query(value = "select * from menu_item where restaurant_id = ?1 and is_deleted = 0", nativeQuery = true)
    public List<MenuItemDao> getMenu(Long restaurant_id);

    /*
    Redundant Methods

    @Modifying
    @Transactional
    @Query(value = "update menu_item set name = ?2, description = ?3, time = ?4, price = ?5, is_available = ?6 where id = ?1", nativeQuery = true)
    public void updateMenuItem(Long id, String name, String description, Double time, Double price, Boolean is_available);

    @Modifying
    @Transactional
    @Query(value = "update menu_item set is_deleted = 1 where id = ?1", nativeQuery = true)
    public void deleteMenuItem(Long id);
    */
}
