package com.asdc.dalbites.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.dalbites.model.DAO.RestaurantDao;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDao, Long> {

    /**
     * Retrieves an optional {@link RestaurantDao} entity by its ID.
     *
     * @param restaurantid The ID of the restaurant to retrieve.
     * @return An optional containing the retrieved restaurant entity, or empty if not found.
     */
    Optional<RestaurantDao> findById(Long restaurantid);

    /**
     * Retrieves a {@link RestaurantDao} entity associated with a login ID.
     *
     * @param login_id The ID of the login associated with the restaurant.
     * @return The restaurant entity associated with the specified login ID.
     */
    @Query(value = "select * from restaurant_owner where login_id = ?1", nativeQuery = true)
    public RestaurantDao findByLogin_Id(Long login_id);

    /**
     * Sets up a restaurant with the provided details.
     *
     * @param name                 The name of the restaurant.
     * @param description          The description of the restaurant.
     * @param buildingId           The ID of the building where the restaurant is located.
     * @param image                The image associated with the restaurant.
     * @param id                   The ID of the restaurant to be set up.
     * @param estimatedDeliveryTime The estimated delivery time for the restaurant.
     */
    @Modifying
    @Transactional
    @Query(value = "update restaurant_owner set restaurant_name = ?1, restaurant_description = ?2, building_id = ?3, restaurant_image = ?4, estimated_delivery_time = ?6 where restaurant_id = ?5", nativeQuery = true)
    void setupRestaurant(String name, String description, Long buildingId, String image, Long id, String estimatedDeliveryTime);

    /**
     * Retrieves a list of {@link RestaurantDao} entities based on a list of building IDs.
     *
     * @param ids The list of building IDs to filter restaurants.
     * @return A list of restaurants associated with the specified building IDs.
     */
    @Query(value = "select * from restaurant_owner where building_id in (:ids)", nativeQuery = true)
    public List<RestaurantDao> getAll(List<Long> ids);

    /**
     * Retrieves a {@link RestaurantDao} entity by its ID.
     *
     * @param id The ID of the restaurant to retrieve.
     * @return The restaurant entity with the specified ID.
     */
    @Query(value = "select * from restaurant_owner where restaurant_id = ?1", nativeQuery = true)
    public RestaurantDao getRestaurant(Long id);
}
