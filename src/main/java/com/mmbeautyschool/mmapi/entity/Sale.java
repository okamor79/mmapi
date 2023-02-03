package com.mmbeautyschool.mmapi.entity;

import com.mmbeautyschool.mmapi.entity.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn
    private Client client;

    @OneToOne
    @JoinColumn
    private Course course;

    private String orderId;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date dateBuy;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date datePayment;

    private boolean payCheck;

    private String checkCode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDER_WAIT;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date expireDate;

}