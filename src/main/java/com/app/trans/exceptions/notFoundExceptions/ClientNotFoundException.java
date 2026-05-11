package com.app.trans.exceptions.notFoundExceptions;

@SuppressWarnings("unused")
public class ClientNotFoundException extends AnObjectNotFound {

    public ClientNotFoundException(long id) {
        super(id, "Client");
    }
}
