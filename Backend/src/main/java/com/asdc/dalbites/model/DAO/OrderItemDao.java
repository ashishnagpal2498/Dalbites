package com.asdc.dalbites.model.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Represents an order item entity in the database.
 */
@Entity
@Getter
@Setter
@Data
@Table(name = "order_item")
public class OrderItemDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderDao order;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", referencedColumnName = "id")
    @JsonIgnore
    private  MenuItemDao item;
}
