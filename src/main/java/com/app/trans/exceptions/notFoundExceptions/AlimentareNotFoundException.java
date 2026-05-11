package com.app.trans.exceptions.notFoundExceptions;

@SuppressWarnings("unused")
public class AlimentareNotFoundException extends AnObjectNotFound {

    public AlimentareNotFoundException(long id) {
        super(id, "Alimentare");
    }
}
