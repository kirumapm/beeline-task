package io.umid.order.entity;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders", schema = "public")
public class Order {

    @Id
    @GeneratedValue(generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "username", nullable = false)
    private String username;

    public Order(String name, String details, String username) {
        this.name = name;
        this.details = details;
        this.username = username;
    }
}
