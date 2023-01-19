package com.spring.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.data.Product;
import com.spring.db.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService 
{
    @Autowired
    private ProductRepository repository;

    /** 
     * @param product
     */
    public void addProduct( Product product ) 
    {
        repository.save( product );
    }

    /** 
     * @return List<Product>
     */
    public List<Product> getProducts() 
    {
        return repository.findAll();
    }

    /** 
     * @param id
     * @return Product
     */
    public Product getProduct( int id ) 
    {
        return repository.findById( id );
    }

    /** 
     * @param name
     * @return Product
     */
    public Product getProductByName( String name ) 
    {
        return repository.findByName( name );
    }

    /** 
     * @param id
     */
    public void deleteProduct( int id ) 
    {
        repository.deleteById( id );
    }

    /** 
     * @param product
     * @return Product
     */
    public Product updateProduct( Product product ) 
    {
        Product existingProduct = repository.findById( product.getId() );
        
        existingProduct.setName( product.getName() );
        existingProduct.setQuantity( product.getQuantity() );
        existingProduct.setPrice( product.getPrice() );

        return repository.save( existingProduct );
    }
}
