package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItemDao, Long> {

    /**
     * Retrieves a list of non-deleted {@link MenuItemDao} entities associated with a specific restaurant.
     *
     * @param restaurant_id The ID of the restaurant for which menu items are to be retrieved.
     * @return A list of non-deleted menu items associated with the specified restaurant.
     */
    @Query(value = "select * from menu_item where restaurant_id = ?1 and is_deleted = 0", nativeQuery = true)
    public List<MenuItemDao> getMenu(Long restaurant_id);
}
