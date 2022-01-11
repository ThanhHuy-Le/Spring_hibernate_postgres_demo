package com.example.demo.DemoApplication;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity     //JPA annotation to make object ready for storage in JPA-based data store, list postgras
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Id is automatically generated
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "count_in_stock", nullable = false)
    private Long count;

    @Column(name = "made_in", nullable = true)
    private String country;

    // < Getters/setters function>
    public Long getId()         {return id;}
    public String getName()     {return this.name;}
    public Float getPrice()     {return this.price;}
    public Long getCount()      {return this.count;}
    public String getCountry()  {return this.country;}

    public void setId(long value)       {this.id = value;}
    public void setName(String value)   {this.name = value;}
    public void setPrice(Float value)   {this.price = value;}
    public void setCount(Long value)    {this.count = value;}
    public void setCountry(String value){this.country = value;}

}