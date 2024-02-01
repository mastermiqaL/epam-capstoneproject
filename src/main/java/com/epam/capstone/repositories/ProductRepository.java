package com.epam.capstone.repositories;

import com.epam.capstone.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    //TODO:insertebi updatebi da delete operaciebi minda

    Optional<Product> findById(Integer id);
//    //for seller account information
//    @Query(value = "SELECT u FROM User u INNER JOIN Product p ON p.seller = u.id WHERE p.id = :productId",nativeQuery = true)
//    Optional<User> findSellerByProduct_Id(@Param("productId") Integer productId);
    //for search
    Page<Product> findByNameContaining(String name, Pageable pageable);

    //seller's products

    Page<Product> findBySellerUsername(String username, Pageable pageable);

    //for general products
    Page<Product> findAll(Pageable pageable);

    //main filtering method
    Page<Product> findAll(Specification specification,Pageable pageable);

    //seller product list
    Page<Product> findBySeller_IdOrderByRatingDesc(Integer id, Pageable pageable);

    //for home page
    Page<Product> findByCategoryIn(List<String> categories, Pageable pageable);

}