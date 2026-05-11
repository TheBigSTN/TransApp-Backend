package com.app.trans.exceptions.notFoundExceptions;

@SuppressWarnings("unused")
public class LicentaNotFoundException extends AnObjectNotFound {

    public LicentaNotFoundException(long id) {
        super(id, "Client");
    }
}
