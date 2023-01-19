package com.spring.api.rs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.api.rs.util.DefaultResource;
import com.spring.data.Image;
import com.spring.data.Product;
import com.spring.db.service.ImageService;
import com.spring.db.service.ProductService;
import com.spring.misc.NotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("products")
public class ProductResource
    extends
        DefaultResource
{
    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    /** 
     * @return ResponseEntity<String>
     */
    @GetMapping
    public ResponseEntity<String> getProducts()
    {
        try
        {
            List<Product> products = productService.getProducts();

            return ok( toJson( products ));
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /**
     * @param product Product
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<String> createProduct( @RequestBody Product product )
    {
        try
        {
            productService.addProduct( product );
            
            return created( toJson( product ) );
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /** 
     * @return ResponseEntity<String>
     */
    @GetMapping("{productId}/images")
    public ResponseEntity<String> getProductWithImages( @PathVariable(value = "productId") int productId )
    {
        try
        {
            try
            {
                Product product = productService.getProduct( productId );
    
                if( product == null )
                {
                    throw new NotFoundException("Product not found for id: " + productId );
                }

                return ok( toJson( product ));
            }
            catch( NotFoundException e )
            {
                return notFound( e );
            }
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }

    /**
     * @param image Image
     * @return ResponseEntity
     */
    @PostMapping("{productId}/images")
    public ResponseEntity<String> createImage( @PathVariable(value = "productId") int productId,
                                               @RequestBody Image image )
    {
        try
        {
            try
            {
                Product product = productService.getProduct( productId );

                if( product == null )
                {
                    throw new NotFoundException( "Product not found for id: " + productId );
                }
                
                image.setProduct( product );
                imageService.addImage( image );
                
                return created( toJson( image));
            }
            catch( NotFoundException e )
            {
                return notFound( e );
            }
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }
}
