package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class Empdatabase {
    public  static List<Employee> fetchEmployee()
    {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue( new File("src/employee.json"), new TypeReference<List<Employee>>() {
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
return  null;
    }
}
