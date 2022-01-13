package com.example.demo.DemoApplication;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity     //JPA annotation to make object ready for storage in JPA-based data store, list postgras
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
class Customer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) // Id is automatically generated
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private Date date_of_birth;

    @Column(name = "country_of_birth", nullable = true)
    private String country_of_birth;

    @OneToMany
    private List<Purchase> purchases;

    // < Getters/setters function>
    public Long getId(){return id;}
    public String getFirst_name()           {return first_name;}
    public String getLast_name()            {return last_name;}
    public String getFullName()             {return first_name + last_name;}
    public String getGender()               {return gender;}
    public String getEmail()                {return email;}
    public Date getDate_of_birth()          {return date_of_birth;}
    public String getCountry_of_birth()     {return country_of_birth;}
    public List<Purchase> getPurchases()    {return purchases;}

    public void setId(long value)                   {this.id = value;}
    public void setFirst_name(String value)         {this.first_name = value;}
    public void setLast_name(String value)          {this.last_name = value;}
    public void setGender(String value)             {this.gender = value;}
    public void setEmail(String value)              {this.email = value;}
    public void Date(Date value)                    {this.date_of_birth = value;}
    public void setCountry_of_birth(String value)   {this.country_of_birth = value;}
    public void setPurchases(List<Purchase> list)   {this.purchases = list;}

}
