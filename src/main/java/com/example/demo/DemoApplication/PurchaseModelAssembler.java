package com.example.demo.DemoApplication;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class PurchaseModelAssembler implements RepresentationModelAssembler<Purchase, EntityModel<Purchase>> {

    @Override
    public EntityModel<Purchase> toModel(Purchase purchase){

        return EntityModel.of(purchase,
                linkTo(methodOn(PurchaseController.class).getPurchaseById(purchase.getId())).withSelfRel(),
                linkTo(methodOn(PurchaseController.class).getAllPurchases()).withRel("Purchase"));
    }
}