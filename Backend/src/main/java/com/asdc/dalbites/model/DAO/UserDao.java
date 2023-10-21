package com.asdc.dalbites.model.DAO;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student")
public class UserDao {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(name="name")
    private String name;
    
    @Column(name="email")
    private String email;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id")
    private LoginDao loginDao;

    public UserDao() {
    }

    public UserDao(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
