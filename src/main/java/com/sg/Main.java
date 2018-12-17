package com.sg;

import com.sg.java.client.Client;
import com.sg.java.service.BalanceService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BalanceService balanceService = new BalanceService(0);
        Client client = new Client("pierre-jean");

        client.getBalance().set(10000L);

        String name;
        System.out.println("Please insert your name : ");
        do {
            Scanner sc = new Scanner(System.in);
            name = sc.nextLine();
            if (!name.equals(client.getClientID())) {
                System.out.println("Unknown client, try again.");
            } else {
                System.out.println("Welcome " + name);
            }
        } while (!name.equals(client.getClientID()));

        long withdraw = 0L;
        System.out.println("How much do you want to withdraw : ");
        do {
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLong()) {
                withdraw = sc.nextLong() * 100L;
            }
        } while (!balanceService.withdraw(client, withdraw));
    }

}
