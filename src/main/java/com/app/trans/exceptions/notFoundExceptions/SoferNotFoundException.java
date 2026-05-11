package com.app.trans.exceptions.notFoundExceptions;

@SuppressWarnings("unused")
public class SoferNotFoundException extends AnObjectNotFound {

    public SoferNotFoundException(long id) {
        super(id, "Sofer");
    }
}
