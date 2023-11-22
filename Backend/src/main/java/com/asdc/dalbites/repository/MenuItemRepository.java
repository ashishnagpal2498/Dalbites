package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.MenuItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItemDao, Long> {

    @Query(value = "select * from menu_item where restaurant_id = ?1 and is_deleted = 0", nativeQuery = true)
    public List<MenuItemDao> getMenu(Long restaurant_id);
}
