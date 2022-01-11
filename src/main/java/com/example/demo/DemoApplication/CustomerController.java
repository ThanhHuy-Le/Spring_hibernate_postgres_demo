package com.example.demo.DemoApplication;

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

@RestController // Means data returned by each method will be written straight to response body, instead of rendering template
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerRepository repository; // Inject CustomerRepository

    // After making CustomerModelAssembler.java, we inject it into CustomerController here to save code
    //      and streamline code.
    private final CustomerModelAssembler assembler;
    public CustomerController(CustomerRepository repository, CustomerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // CollectionModel<> is another Spring HATEOAS container, a collection of entities, lets us include links
    // It Encapsulates collections of 'customer' resources, as per REST standard.
    // Should yield a RESTful representation of the root.
    // more on REST: https://restfulapi.net/
    @GetMapping("/customer") // GET method for reading
    CollectionModel<EntityModel<Customer>> getAllCustomers(){
        List<EntityModel<Customer>> customers = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(customers ,
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
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
    @GetMapping("/customer/{customerId}") // GET method for reading a customer by id
    EntityModel<Customer> getCustomerById(@PathVariable Long customerId){

        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        System.out.println("Hello hello");

        // This adds 2 more links to return, when returning the customer object
        // A link to itself (/customer/{id})
        // A link to all customers (/customer)
        //      Adding links have been delegated to CustomerModelAssembler
        EntityModel<Customer> found_customer = assembler.toModel(customer);
        return found_customer;
    }
}
