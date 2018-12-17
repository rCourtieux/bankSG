package com.sg.java.client;

import java.util.concurrent.atomic.AtomicLong;

public class Client {

    private String clientID;
    private static AtomicLong balance = new AtomicLong(0);

    public Client(String clientID) {
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }

    public AtomicLong getBalance() {
        return balance;
    }

}
