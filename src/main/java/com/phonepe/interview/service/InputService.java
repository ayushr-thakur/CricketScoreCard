package com.phonepe.interview.service;

import java.util.Scanner;

public class InputService {
    private final Scanner in = new Scanner(System.in);

    public String getNextString() {
        return in.nextLine();
    }

    public Integer getNextInteger() {
        return in.nextInt();
    }
}
