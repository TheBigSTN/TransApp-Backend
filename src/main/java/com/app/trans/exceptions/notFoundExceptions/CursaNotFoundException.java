package com.app.trans.exceptions.notFoundExceptions;

@SuppressWarnings("unused")
public class CursaNotFoundException extends AnObjectNotFound {

    public CursaNotFoundException(long id)
    {
        super(id, "Cursa");
    }
}
