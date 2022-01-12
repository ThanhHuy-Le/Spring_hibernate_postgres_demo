package com.example.demo.DemoApplication;

public class PurchaseNotFoundException extends RuntimeException{
    public PurchaseNotFoundException(Long Pid) {
        super("Could not find purchase " + Pid);
    }
}
