package com.spring.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.data.Image;
import com.spring.data.Product;
import com.spring.db.repository.ImageRepository;

@Service
public class ImageService 
{
    @Autowired
    private ImageRepository repository;
    
    /** 
     * @param image
     */
    public void addImage( Image image ) 
    {
        repository.save( image );
    }
    
    /** 
     * @param product
     * @return List<Image>
     */
    public List<Image> getImages( Product product )
    {
        return repository.findImagesByProduct( product.getId() );
    }
}
