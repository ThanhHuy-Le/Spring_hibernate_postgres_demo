package com.example.demo.DemoApplication;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    @Override
    public EntityModel<Customer> toModel(Customer customer){

        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("Customer"),
                linkTo(methodOn(CustomerController.class).getCustomerPurchases(customer.getId())).withRel("Purchases"));
    }
}
