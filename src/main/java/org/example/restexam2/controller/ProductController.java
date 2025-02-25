package org.example.restexam2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.Product;
import org.example.restexam2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {

        List<Product> products = productService.getProducts();


        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Long id) {

        try {
               return ResponseEntity.ok(productService.getProduct(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {


         Product creatProduct =   productService.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(creatProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id, @RequestBody Product product) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);  // 경로 변수의 ID를 제품에 설정
        Product updatedProduct = productService.updateProduct(product);

        return ResponseEntity.ok(updatedProduct);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {
        boolean deleted =  productService.deleteProduct(id);
        if (deleted) {
            return new ResponseEntity<>("Event deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
    }









}
