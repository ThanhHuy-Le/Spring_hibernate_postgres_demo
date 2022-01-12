package com.example.demo.DemoApplication;


import org.springframework.data.jpa.repository.JpaRepository;



// this is an interface that extends Spring JPA's JpaRepository
// just making the interface means we can access the database for GET,PUT,DELETE,FIND actions
// Spring Data’s repository solution makes it possible to sidestep data store specifics and instead solve a majority of problems using domain-specific terminology.
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Even though it's empty now, this is a very important interface
    // Read methods of JpaRepository here: https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

}
