package org.example.restexam2.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.restexam2.domain.Product;
import org.example.restexam2.dto.ProductDTO;
import org.example.restexam2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;

    @Transactional(readOnly = true )
    public List<ProductDTO> getProducts() {

        List<Product> products = productRepository.findAll();


        return products.stream()
                .map(ProductDTO::formEntity)
                //.map(product -> ProductDTO.formEntity(product) 과 같은
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true )
    public ProductDTO getProduct(Long id) {
        Product product =productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        return ProductDTO.formEntity(product);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {


        Product product = Product.formDTO(productDTO);

        try {
            Product saveroduct =  productRepository.save(product);

            return ProductDTO.formEntity(saveroduct);
        }catch (RuntimeException e) {
            throw  new  RuntimeException("Error while creating product");
        }


    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {


        if(!productRepository.existsById(productDTO.getId()) || productDTO.getId() == null) {
            log.info("Product not found");
            throw new RuntimeException("Product not found");
        }

        log.info("Updating product");

        Product foundProduct = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional.ofNullable(productDTO.getName()).ifPresent(foundProduct::setName);
        Optional.ofNullable(productDTO.getDescription()).ifPresent(foundProduct::setDescription);
        Optional.ofNullable(productDTO.getPrice()).ifPresent(foundProduct::setPrice);

        //foundProduct.setPrice(productDTO.getPrice());



        return  ProductDTO.formEntity(foundProduct);
    }

    /*

타입
double: 기본형(primitive) 타입

Double: 참조형(reference) 타입, 객체 wrapper 클래스

특징
double:

실제 값을 직접 저장

null 사용 불가능

산술 연산 가능

메모리의 스택(stack) 영역에 저장

Double:

객체로 값을 저장

null 초기화 및 사용 가능

직접적인 산술 연산 불가능 (언박싱 필요)

실제 값은 힙(heap) 영역에 저장, 스택에는 참조 주소 저장

메모리 사용
double: 8바이트 고정

Double: 객체 오버헤드로 인해 더 많은 메모리 사용

성능
double: 직접 값을 저장하므로 연산이 더 빠름

Double: 객체 접근과 언박싱 과정으로 인해 상대적으로 느림

활용
double: 단순 수치 계산에 적합

Double: null 값이 필요한 경우나 객체로 다뤄야 할 때 사용

     */

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

/*
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return product;
    }
    */

    /*

엔티티 (Entity):

데이터베이스 테이블과 직접적으로 매핑되는 객체입니다.

JPA를 사용할 경우, @Entity 어노테이션을 사용하여 엔티티 클래스를 정의합니다.

데이터베이스 테이블의 컬럼에 해당하는 필드를 가지고 있습니다.

주로 데이터베이스와의 상호작용을 위해 사용됩니다.

엔티티는 데이터베이스 스키마와 밀접하게 연결되어 있으므로, 프레젠테이션 계층이나 서비스 계층에서 직접 사용하는 것은 권장되지 않습니다. (집안에서 외출복)

DTO (Data Transfer Object):

계층 간 데이터 교환을 위한 객체입니다.

데이터베이스 테이블 구조와 독립적입니다.

프레젠테이션 계층에서 필요한 데이터를 담아 서비스 계층으로 전달하거나, 서비스 계층에서 처리된 데이터를 프레젠테이션 계층으로 반환하는 데 사용됩니다.

엔티티와 달리, 필요한 데이터만 담을 수 있도록 커스터마이징이 가능합니다.

계층 간 결합도를 낮추고, 데이터의 보안성을 높이는 데 기여합니다. (잠옷)





     */




}
