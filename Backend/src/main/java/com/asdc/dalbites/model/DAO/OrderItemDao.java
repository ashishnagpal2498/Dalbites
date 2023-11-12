package com.asdc.dalbites.model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Data
@Table(name = "orders_items")
public class OrderItemDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;

//    @Column(name = "special_instruction")
//    private String specialIntstruction;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderDao order;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private  MenuItem item;
}
