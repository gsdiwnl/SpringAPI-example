package com.spring.db.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.data.Product;

public interface ProductRepository 
    extends
        JpaRepository<Product,Integer> 
{
    Product findByName( String name );

    @EntityGraph(attributePaths = {"images"})
    Product findById( int id );
}

