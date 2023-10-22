package com.asdc.dalbites.model.DAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "restaurant")
public class RestaurantsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="address", nullable = true)
    private String address;

    @Column(name="password", nullable = true)
    private String password;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private BuildingDao buildingDao;

    public RestaurantsDao() {
    }

    public RestaurantsDao(String name, String address, String password, Long building_id){
        this.name = name;
        this.address = address;
        this.password = password;
        this.buildingDao.setId(building_id);
    }
}
