package com.spring.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.data.Image;

public interface ImageRepository 
    extends
        JpaRepository<Image,Integer> 
{
    @Query( value = "SELECT * FROM images WHERE ref_product = :productId", nativeQuery = true )
    public List<Image> findImagesByProduct( @Param("productId") int productId );
}

