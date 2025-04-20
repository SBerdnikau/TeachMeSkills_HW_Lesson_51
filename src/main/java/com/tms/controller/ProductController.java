package com.tms.controller;

import com.tms.model.Product;
import com.tms.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/product")
@Tag(name = "Product Controller", description = "Product Management")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Product creation", description = "Adds a new product to the system")
    @ApiResponse(responseCode = "201", description = "The product has been successfully created")
    @ApiResponse(responseCode = "409", description = "Conflict: Product not created")
    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product product) {
        Boolean result = productService.createProduct(product);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Adding a product by user", description = "Adding a product with ID by user with ID")
    @ApiResponse(responseCode = "200", description = "Product added")
    @ApiResponse(responseCode = "404", description = "Conflict: Product not added")
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<HttpStatus> addProductByUser(@PathVariable("userId")
                                                       @Parameter(name = "userId") Long userId ,
                                                       @PathVariable("productId")
                                                       @Parameter(name = "productId") Long productId) {
        Boolean result = productService.addProductByUser(userId, productId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Receiving the product", description = "Returns a product by ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id")
                                                  @Parameter(name ="id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @Operation(summary = "Product update", description = "Updates data of an existing product")
    @ApiResponse(responseCode = "200", description = "The product has been updated successfully.")
    @ApiResponse(responseCode = "409", description = "Conflict when updating product")
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Optional<Product> productUpdated = productService.updateProduct(product);
        if (productUpdated.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(productUpdated.get(), HttpStatus.OK);
    }

    @Operation(summary = "Removing a product", description = "Removes a product by ID")
    @ApiResponse(responseCode = "204", description = "The product has been successfully removed")
    @ApiResponse(responseCode = "409", description = "Error removing product")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id) {
        Boolean result = productService.deleteProduct(id);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
