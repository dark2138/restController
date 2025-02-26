package org.example.restexam2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.Product;
import org.example.restexam2.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        List<ProductDTO> products = productService.getProducts();


       // return ResponseEntity.ok(products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long id) {

        try {
            //return ResponseEntity.ok(productService.getProduct(id));
               return  new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            //return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {


        ProductDTO creatProductDTO =   productService.createProduct(productDTO);

        //return ResponseEntity.status(HttpStatus.CREATED).body(creatProductDTO);
        return  new ResponseEntity<>(creatProductDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDTO productDTO) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productDTO.setId(id);  // 경로 변수의 ID를 제품에 설정
        ProductDTO updatedProductDTO = productService.updateProduct(productDTO);

       // return ResponseEntity.ok(updatedProductDTO);
        return  new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
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
