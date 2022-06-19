package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.util.CompletedFuture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class DemoApplication {

	public Void saveEmployee(File jsonFile) throws ExecutionException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		 CompletableFuture<Void>future =CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				try {
					List<Employee>employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
					});
					System.out.println("Thread  " + Thread.currentThread().getName() );
					System.out.println(employees);
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("Out side Try Thread  " + Thread.currentThread().getName() );

			}
		});

		return future.get();
	}



	public static void main(String[] args) throws ExecutionException, InterruptedException {

		DemoApplication demo = new DemoApplication();
		demo.saveEmployee(new File("src/employee.json"));


	}

}

