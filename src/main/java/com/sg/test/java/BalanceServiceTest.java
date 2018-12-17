package com.sg.test.java;

import com.sg.java.client.Client;
import com.sg.java.service.BalanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BalanceServiceTest {

    private static BalanceService balanceService;
    private static Client client;

    @BeforeAll
    public static void initialize() {
        balanceService = new BalanceService(0L);
        client = new Client("pierre-jean");
    }

    @BeforeEach
    public void init() {
        client.getBalance().set(10000L);
    }

    @Test
    public void withdrawClassicFullTest() {
        Assertions.assertTrue(balanceService.withdraw(client, 1000L));
        Assertions.assertEquals(9000L, client.getBalance().get());
    }

    @Test
    public void withdrawClassicWithDecimalTest() {
        Assertions.assertFalse(balanceService.withdraw(client, 999L));
    }

    @Test
    public void withdrawClassicWithoutMultipleOf10Test() {
        Assertions.assertFalse(balanceService.withdraw(client, 2500L));
    }

    @Test
    public void withdrawWithZeroAmountTest() {
        Assertions.assertFalse(balanceService.withdraw(client, 0L));
    }

    @Test
    public void withdrawWithNegativeAmountTest() {
        Assertions.assertFalse(balanceService.withdraw(client, -1000L));
    }

    @Test
    public void withdrawWithoutClientTest() {
        Assertions.assertFalse(balanceService.withdraw(null, 1000L));
    }

    @Test
    public void withdrawWithoutAdequateFundsTest() {
        Assertions.assertFalse(balanceService.withdraw(client, 100000L));
    }

    @Test
    public void withdrawConcurencyTest() {
        Runnable task = () -> {
            while (client.getBalance().get() > balanceService.getExposed()) {
                System.out.println(Thread.currentThread().getName());
                balanceService.withdraw(client, 1000L);
            }
        };

        ArrayList<Thread> threadGroup = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threadGroup.add(new Thread(task));
        }

        for (Thread thread : threadGroup) {
            thread.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        Assertions.assertEquals(0L, client.getBalance().get());
    }

}
