package com.spring.misc;

public class UnauthorizedException
    extends
        RuntimeException
{
    /**
     * @param message
     */
    public UnauthorizedException( String message )
    {
        super( message );
    }

    /**
     * @param message
     * @param cause
     */
    public UnauthorizedException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public UnauthorizedException( Throwable cause )
    {
        super( cause );
    }
}
