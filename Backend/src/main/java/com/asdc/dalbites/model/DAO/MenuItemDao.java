package com.asdc.dalbites.model.DAO;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a menu item entity in the database.
 */
@Entity
@Getter
@Setter
@Table(name = "menu_item")

public class MenuItemDao {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="is_deleted")
    private int is_deleted = 0;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updated_at;

    @Column(name="time")
    private Double time;

    @Column(name="price")
    private Double price;

    @Column(name="is_available")
    private Boolean is_available;

    public String getMenu_image() {
		return menu_image;
	}

	public void setMenu_image(String menu_image) {
		this.menu_image = menu_image;
	}

	public RestaurantDao getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDao restaurant) {
        this.restaurant = restaurant;
    }

    @Column(name="menu_image", nullable = true)
    private String menu_image;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id")
    @JsonIgnore
    private RestaurantDao restaurant;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItemDao> orderItems;

    /**
     * Default constructor for MenuItemDao.
     */
    public MenuItemDao() {
    }

    /**
     * Parameterized constructor for MenuItemDao.
     *
     * @param name        The name of the menu item.
     * @param description The description of the menu item.
     * @param price       The price of the menu item.
     * @param time        The preparation time of the menu item.
     * @param is_available The availability status of the menu item.
     * @param menu_image  The image associated with the menu item.
     * @param restaurant  The restaurant to which the menu item belongs.
     */
    public MenuItemDao(String name, String description, Double price, Double time, Boolean is_available, String menu_image, RestaurantDao restaurant) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.time = time;
        this. is_available = is_available;
        this.menu_image = menu_image;
        this.restaurant = restaurant;
    }
}
