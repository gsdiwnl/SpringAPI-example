package com.spring.misc;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class Error 
{
    private int code;
    private String status;
    private String message;

    /**
     * @param httpStatus HttpStatus
     * @param message String
     */
    public Error( HttpStatus httpStatus, String message )
    {
        this.code = httpStatus.value();
        this.message = message;
        this.status = httpStatus.getReasonPhrase();    
    }

    /**
     * @return String
     */
    public String toJson()
    {
        return new Gson().toJson( this );
    }
}
