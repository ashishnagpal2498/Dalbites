package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.OrderDao;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDao, Long> {
    List<OrderDao> findAllByUser_UserId(Long userId);
    List<OrderDao> findAllByRestaurant_Id(Long restaurantId);
}
