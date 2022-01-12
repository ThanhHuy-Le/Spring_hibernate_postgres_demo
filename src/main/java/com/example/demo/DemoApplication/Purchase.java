package com.example.demo.DemoApplication;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity     //JPA annotation to make object ready for storage in JPA-based data store, list postgras
@Table(name = "purchase")
@EntityListeners(AuditingEntityListener.class)
class Purchase {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO) // Id is automatically generated
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "date_purchased", nullable = false)
    private Date date;

    @Column(name = "time_purchased", nullable = false)
    private String time;

    // < Getters/setters function>
    public Long getId()      {return id;}
    public Long getCustomerId()     {return customerId;}
    public Long getProductId()      {return productId;}
    public Date getDate()           {return date;}
    public String getTime()         {return time;}

    //
    public void setDate(Date value)    {this.date = value;}
    public void setTime(String value)  {this.time = value;}

}