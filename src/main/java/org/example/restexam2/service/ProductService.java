package org.example.restexam2.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.Product;
import org.example.restexam2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;

    @Transactional(readOnly = true )
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    @Transactional(readOnly = true )
    public Product getProduct(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public Product createProduct(Product product) {

        try {
            return productRepository.save(product);
        }catch (RuntimeException e) {
            throw  new  RuntimeException("Error while creating product");
        }


    }

    @Transactional
    public Product updateProduct(Product product) {


        if(!productRepository.existsById(product.getId()) || product.getId() == null) {
            log.info("Product not found");
            throw new RuntimeException("Product not found");
        }

        log.info("Updating product");

        Product foundProduct = productRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("Product not found"));

        foundProduct.setName(product.getName());
        foundProduct.setPrice(product.getPrice());

        return foundProduct;
    }

    @Transactional
    public Boolean deleteProduct(Long id) {
        try {
            if(productRepository.existsById(id)) {
                productRepository.deleteById(id);
                return true;
            } else {
                return false;
            }

        }catch (RuntimeException e) {
            throw  new  RuntimeException("Error while deleting product");
        }

    }

    @Transactional
    public Boolean existsById(Long id) {
        return productRepository.existsById(id);
    }




}
