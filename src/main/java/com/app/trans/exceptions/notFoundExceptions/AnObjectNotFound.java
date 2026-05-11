package com.app.trans.exceptions.notFoundExceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnObjectNotFound extends RuntimeException {

    public AnObjectNotFound(long id, String objType){
        super(objType + " with id " + id + " doesn't exist!");
    }

//    public AnObjectNotFound() {
//    }
//
//    public AnObjectNotFound(String message) {
//        super(message);
//    }
}
