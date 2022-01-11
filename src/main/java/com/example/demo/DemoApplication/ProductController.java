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
public class ProductController {

    @Autowired
    private ProductRepository repository; // Inject ProductRepository

    // After making ProductModelAssembler.java, we inject it into ProductController here to save code
    //      and streamline code.
    private final ProductModelAssembler assembler;
    public ProductController(ProductRepository repository, ProductModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // CollectionModel<> is another Spring HATEOAS container, a collection of entities, lets us include links
    // It Encapsulates collections of 'Product' resources, as per REST standard.
    // Should yield a RESTful representation of the root.
    // more on REST: https://restfulapi.net/
    @GetMapping("/product") // GET method for reading
    CollectionModel<EntityModel<Product>> getAllProducts(){
        List<EntityModel<Product>> products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products ,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
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
    @GetMapping("/product/{productId}") // GET method for reading a product by id
    EntityModel<Product> getProductById(@PathVariable Long productId){

        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        System.out.println("Hello hello");

        // This adds 2 more links to return, when returning the product object
        // A link to itself (/product/{id})
        // A link to all products (/product)
        //      Adding links have been delegated to ProductModelAssembler
        EntityModel<Product> found_product = assembler.toModel(product);
        return found_product;
    }
}