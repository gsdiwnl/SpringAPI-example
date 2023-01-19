package com.spring.api.rs.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.misc.Error;

public class DefaultResource 
{
    private final ObjectMapper converter = new ObjectMapper();
    
    /** 
     * @param info
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> notFound( String info ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.NOT_FOUND, info ).toJson(), HttpStatus.NOT_FOUND );
    }

    /** 
     * @param exception
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> notFound( Throwable exception ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.NOT_FOUND, exception.getMessage() ).toJson(), HttpStatus.NOT_FOUND );
    }
    
    /** 
     * @param info
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> badRequest( String info ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.BAD_REQUEST, info ).toJson(), HttpStatus.BAD_REQUEST );
    }

    /** 
     * @param exception
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> badRequest( Throwable exception ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.BAD_REQUEST, exception.getMessage() ).toJson(), HttpStatus.BAD_REQUEST );
    }
    
    /** 
     * @param info
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> internalServerError( String info ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.INTERNAL_SERVER_ERROR, info ).toJson(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    /** 
     * @param exception
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> internalServerError( Throwable exception ) 
    {
        exception.printStackTrace();
        return new ResponseEntity<>( new Error( HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage() ).toJson(), HttpStatus.INTERNAL_SERVER_ERROR );
    }
    
    /** 
     * @param info
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> unauthorizedError( String info ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.UNAUTHORIZED, info ).toJson(), HttpStatus.UNAUTHORIZED );
    }

    /** 
     * @param exception
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> unauthorizedError( Throwable exception ) 
    {
        return new ResponseEntity<>( new Error( HttpStatus.UNAUTHORIZED, exception.getMessage() ).toJson(), HttpStatus.UNAUTHORIZED );
    }
    
    /** 
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> ok() 
    {
        return new ResponseEntity<>( HttpStatus.OK );
    } 
   
    /** 
     * @param jsonString
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> ok( String jsonString ) 
    {
        return new ResponseEntity<>( jsonString, HttpStatus.OK );
    }        

    /** 
     * @param jsonString
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> created() 
    {
        return new ResponseEntity<>( HttpStatus.CREATED );
    }     
    
    /** 
     * @param jsonString
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> created( String jsonString ) 
    {
        return new ResponseEntity<>( jsonString, HttpStatus.CREATED );
    }
    
    /** 
     * @return ResponseEntity<String>
     */
    protected ResponseEntity<String> noContent() 
    {
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    /**
     * @param object Object
     * @return String
     * @throws JsonProcessingException
     */
    public String toJson( Object object ) throws JsonProcessingException
    {
        return converter.writeValueAsString( object );
    }
}
