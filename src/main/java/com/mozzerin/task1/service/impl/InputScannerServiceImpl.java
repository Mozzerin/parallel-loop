package com.mozzerin.task1.service.impl;

import com.mozzerin.task1.Constants;
import com.mozzerin.task1.service.InputScannerService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputScannerServiceImpl implements InputScannerService {
    @Override
    public long scanValidInput() {
        System.out.println("Please enter the number between 0 and 999999999999999 without special characters:");
        long scannedInput = -1;
        try (Scanner scanner = new Scanner(System.in)) {
            while (!scanner.hasNextLong()) {
                System.out.println("That's not a valid number!");
                scanner.next();
            }
            scannedInput = scanner.nextLong();
            while (scannedInput < 0 || scannedInput > Constants.MAX_VALUE) {
                System.out.println("Number range is incorrect");
                scannedInput = scanner.nextLong();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Please try again");
        }
        return scannedInput;
    }
}
