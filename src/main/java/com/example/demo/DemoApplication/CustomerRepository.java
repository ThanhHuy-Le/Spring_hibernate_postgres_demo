package com.example.demo.DemoApplication;


import org.springframework.data.jpa.repository.JpaRepository;


// this is an interface that extends Spring JPA's JpaRepository
// just making the interface means we can access the database for GET,PUT,DELETE,FIND actions
// Spring Data’s repository solution makes it possible to sidestep data store specifics and instead solve a majority of problems using domain-specific terminology.
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
