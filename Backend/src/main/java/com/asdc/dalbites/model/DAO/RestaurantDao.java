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
@Table(name = "restaurant_owner")
public class RestaurantDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private Long id;

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

    public RestaurantDao() {
    }

    public RestaurantDao(String name, String address) {
        this.address = address;
        this.name = name;
    }
}
