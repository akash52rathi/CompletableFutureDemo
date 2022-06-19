package com.example.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    public CompletableFuture<Void> sendRemindertoEmployee()
    {
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(()->{

            System.out.println("FetchEmoloyee  " + Thread.currentThread().getName());
            return Empdatabase.fetchEmployee();
        },executor).thenApplyAsync((employees)-> {
            System.out.println("New joinee employee " + Thread.currentThread().getName());
            return employees.stream().filter(e->"TRUE".equals(e.getNewJoiner())).collect(Collectors.toList());
        },executor).thenApplyAsync((employees)->{
            System.out.println("Trainig is pending "+ Thread.currentThread().getName());
            return  employees.stream().filter(e->"TRUE".equals(e.getLearningPending())).collect(Collectors.toList());
        },executor).thenApplyAsync(employees -> {
            System.out.println("get emails "+ Thread.currentThread().getName());
            return employees.stream().map(e->e.getEmail()).collect(Collectors.toList());
        },executor).thenAcceptAsync((emails)->{

            System.out.println(" send email ");
            emails.forEach(e->sendEmail(e));
        },executor);

        return voidCompletableFuture;
    }
    public  static void sendEmail(String email)
    {
        System.out.println("email sent to  " + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeReminderService empsrc = new EmployeeReminderService();
        empsrc.sendRemindertoEmployee().get();
    }
}
