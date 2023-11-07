package com.asdc.dalbites.model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Data
@Setter
@Table(name = "restaurant_owner")
public class RestaurantDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public short getIsActive() {
		return isActive;
	}

	public void setIsActive(short isActive) {
		this.isActive = isActive;
	}

	public short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Column(name="name", nullable = false)
    private String name;

    @Column(name="address", nullable = true)
    private String address;

    @Column(name="is_active", nullable = false)
    private short isActive = 1;

    @Column(name="is_deleted", nullable = false)
    private short isDeleted = 0;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id")
    private LoginDao loginDao;

    @Column(name="restaurant_name", nullable = true)
    private String restaurantName;

    @Column(name="restaurant_description", nullable = true)
    private String restaurantDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingDao buildingDao;

    @Column(name="restaurant_image", nullable = true)
    private String restaurantImage;
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MenuItem> menuItems = new ArrayList<>();

    public RestaurantDao() {
    }

    public RestaurantDao(String name, String address) {
        this.address = address;
        this.name = name;
    }
}
