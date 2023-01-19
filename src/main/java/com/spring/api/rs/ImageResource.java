package com.spring.api.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.api.rs.util.DefaultResource;
import com.spring.data.Image;
import com.spring.db.service.ImageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("products/images")
public class ImageResource
    extends
        DefaultResource
{
    @Autowired
    private ImageService imageService;

    private Gson gson;
    
    public ImageResource()
    {
        gson = new Gson();
    }

    /**
     * @param image Image
     * @return ResponseEntity
     */
    @PostMapping()
    public ResponseEntity<String> createProduct( @RequestBody Image image )
    {
        try
        {
            imageService.addImage( image );
            
            return created( gson.toJson( image ));
        }
        catch( Exception e )
        {
            return internalServerError( e );
        }
    }
}
