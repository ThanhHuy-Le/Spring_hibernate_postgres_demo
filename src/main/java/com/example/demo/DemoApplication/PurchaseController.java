package com.example.demo.DemoApplication;

import com.example.demo.DemoApplication.Purchase;
import com.example.demo.DemoApplication.PurchaseModelAssembler;
import com.example.demo.DemoApplication.PurchaseNotFoundException;
import com.example.demo.DemoApplication.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
// Means data returned by each method will be written straight to response body, instead of rendering template
@RequestMapping("/api/v1")
public class PurchaseController {

    @Autowired
    private PurchaseRepository repository; // Inject PurchaseRepository

    // After making PurchaseModelAssembler.java, we inject it into PurchaseController here to save code
    //      and streamline code.
    private final PurchaseModelAssembler assembler;
    public PurchaseController(PurchaseRepository repository, PurchaseModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // CollectionModel<> is another Spring HATEOAS container, a collection of entities, lets us include links
    // It Encapsulates collections of 'Purchase' resources, as per REST standard.
    // Should yield a RESTful representation of the root.
    // more on REST: https://restfulapi.net/
    @GetMapping("/purchase") // GET method for reading
    CollectionModel<EntityModel<Purchase>> getAllPurchases(){
        List<EntityModel<Purchase>> purchases = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(purchases ,
                linkTo(methodOn(com.example.demo.DemoApplication.PurchaseController.class).getAllPurchases()).withSelfRel());
    }

    // https://howtodoinjava.com/spring5/hateoas/spring-hateoas-tutorial/
    // RepresentationModel – is a container for a collection of Links and provides APIs to add those links to the model.
    // EntityModel – represents RepresentationModel containing only single entity and related links.

    // CollectionModel – is a wrapper for a collection of entities (entity as well as collection). To create collection models, use it’ constructors (e.g. CollectionModel(List) or CollectionModel(Iterable)) or toCollectionModel() provided by model assemblers.

    // PagedModel – is similar to CollectionModel with underlying pageable collection of entities.
    // RepresentationModelAssembler – It’s implementation classes (such as RepresentationModelAssemblerSupport) provides methods to convert a domain object into a RepresentationModel.
    // WebMvcLinkBuilder – It helps to ease building Link instances pointing to Spring MVC controllers.

    // Link – represents a single link added to representation model.
    // LinkRelationProvider – provides API to add link relations ("rel" type) in Link instances.

    // ResponseEntity is meant to represent the entire HTTP response. You can control anything that goes into it: status code, headers, and body.
    // @ResponseBody is a marker for the HTTP response body and @ResponseStatus declares the status code of the HTTP response.
    // @ResponseStatus isn't very flexible. It marks the entire method so you have to be sure that your handler method will always behave the same way. And you still can't set the headers. You'd need the HttpServletResponse.
    // Basically, ResponseEntity lets you do more.
    @GetMapping("/purchase/{id}") // GET method for reading a purchase by id
    EntityModel<Purchase> getPurchaseById(@PathVariable Long id){

        Purchase purchase = repository.findById(id)
                .orElseThrow(() -> {
                    return new PurchaseNotFoundException(id);
                });

        // This adds 2 more links to return, when returning the purchase object
        // A link to itself (/purchase/{id})
        // A link to all purchases (/purchase)
        //      Adding links have been delegated to PurchaseModelAssembler
        EntityModel<Purchase> found_purchase = assembler.toModel(purchase);
        return found_purchase;
    }
}