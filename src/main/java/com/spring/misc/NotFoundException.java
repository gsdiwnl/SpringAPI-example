package com.spring.misc;

public class NotFoundException
    extends
        RuntimeException
{
    /**
     * @param message
     */
    public NotFoundException( String message )
    {
        super( message );
    }

    /**
     * @param message
     * @param cause
     */
    public NotFoundException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public NotFoundException( Throwable cause )
    {
        super( cause );
    }
}
