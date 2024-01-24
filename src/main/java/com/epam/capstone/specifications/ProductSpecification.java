package com.epam.capstone.specifications;

import com.epam.capstone.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public ProductSpecification(){}

    public static Specification<Product> hasCategory(List<String> categories) {
        return (root, query, criteriaBuilder) ->
                root.get("category").in(categories);
    }

    public static Specification<Product> hasPriceInRange(int minPrice, int maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    public static Specification<Product> isSecondHand(boolean isSecondHand) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("secondHand"), isSecondHand);
    }
}
