package com.mozzerin.task1;

import com.mozzerin.task1.service.ApplicationService;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hi, let's start the execution!");
        ApplicationService applicationService = new ApplicationService();
        applicationService.startProgramExecution();
        System.out.println("Thanks, see you again!");
    }
}
