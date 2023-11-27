package com.asdc.dalbites.repository;

import com.asdc.dalbites.model.DAO.OrderDao;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDao, Long> {

    /**
     * Retrieves a list of {@link OrderDao} entities associated with a specific user.
     *
     * @param userId The ID of the user for whom orders are to be retrieved.
     * @return A list of orders associated with the specified user.
     */
    List<OrderDao> findAllByUser_UserId(Long userId);

    /**
     * Retrieves a list of {@link OrderDao} entities associated with a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant for which orders are to be retrieved.
     * @return A list of orders associated with the specified restaurant.
     */
    List<OrderDao> findAllByRestaurant_Id(Long restaurantId);
}
