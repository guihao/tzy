package com.hd.mall.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_info")
@Data
public class Product_Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "lable_id")
    private Integer lableId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "price_size")
    private int priceSize;

    @Column(name = "product_desp")
    private String productDesp;

    @Column(name = "product_pic")
    private String productPic;
}
