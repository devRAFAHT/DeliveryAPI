package com.rafaelandrade.backend.services.exceptions;

public class EntityUpdateNotAllowedException extends Exception{
    public EntityUpdateNotAllowedException(String msg) {
        super(msg);
    }
}
