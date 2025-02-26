package org.example.restexam2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.restexam2.dto.ProductDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
@Builder
public class Product {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    // DTO -> entity로 변환하는 메서드
    public static Product formDTO(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .build();
    }


}
