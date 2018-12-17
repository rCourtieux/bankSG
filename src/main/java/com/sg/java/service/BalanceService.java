package com.sg.java.service;

import com.sg.java.client.Client;

public class BalanceService {

    private long exposed;

    public BalanceService(long exposed) {
        this.exposed = exposed;
    }

    public synchronized boolean withdraw(Client client, long amount) {
        if (client != null && amount > 0L && ((amount / 100.00) % 10 == 0) && client.getBalance().get() - amount >= exposed) {
            client.getBalance().addAndGet(-amount);
            System.out.println("Withdraw completed.");
            printBalance(client);
            return true;
        }
        System.out.println("Withdraw rejected.");
        return false;
    }

    private void printBalance(Client client) {
        System.out.println("Balance : " + client.getBalance().get() / 100.00 + " EUR");
    }

    public long getExposed() {
        return exposed;
    }

}
