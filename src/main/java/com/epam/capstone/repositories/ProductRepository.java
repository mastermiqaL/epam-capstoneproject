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



    Optional<Product> findById(Integer id);

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

    List<Product> findBySeller_Id(Integer id);

   // List<Product> findAll();
    //for home page
    Page<Product> findByCategoryIn(List<String> categories, Pageable pageable);


}