package com.collabs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

import javafx.util.Pair;

public class AllCollaborations {
    private final List<Employee> employees;
    private final List<Collaboration> collaborations;


    public AllCollaborations(String file) {
        this.employees = readEmployeesFromCSV(file);
        //Sort the employees by ID in order to avoid repeating entries
        //(for example EMP1 with EMP2 and EMP2 with EMP1)
        Collections.sort(employees);

        /*
        //Print all employees
        for(int i = 0; i < employees.size(); i++){
            System.out.println(employees.get(i));
        }
        */

        this.collaborations = new ArrayList<>();

        for(int i = 0; i < employees.size(); i++){
                int emp1ID = employees.get(i).getEmpID();
                int projID = employees.get(i).getProjID();
                for(int j = i + 1; j < employees.size(); j++){
                    int emp2ID = employees.get(j).getEmpID();
                    if (emp1ID != emp2ID && projID == employees.get(j).getProjID()) {
                        int days = timeWorkedTogether(employees.get(i), employees.get(j));
                        Collaboration c = new Collaboration(emp1ID, emp2ID, projID, days);

                        //Check if this pair of employees working on that
                        //project already exists in the list, and if true - only add the days
                        //Contains is not checking the field "days" because the methods it is
                        //using are overridden to skip it.
                        if(collaborations.contains(c)) {
                            collaborations.get(collaborations.indexOf(c)).addDays(days);
                        }
                        //Add new collaboration
                        else {
                            collaborations.add(c);
                        }
                    }
                }
        }

        /*
        //Print all collaborations
        for(int i = 0; i < collaborations.size(); i++) {
            System.out.println(collaborations.get(i));
        }
        */


    }

    //Getter for collaborations
    public List<Collaboration> getCollaborations()
    {
        return collaborations;
    }

    //Prints the IDs and work days of the first pair of employees who have worked
    //together on common projects for the longest period of time.
    //Also returns a pair with their IDs in order to find all their common projects for the table
    public Pair<Integer, Integer> mostTimeTogether() {
        int empID1;
        int empID2;
        int timeTogether;
        int maxEmpID1 = 0;
        int maxEmpID2 = 0;
        int maxTimeTogether = 0;

        for(int i = 0; i < collaborations.size(); i++) {
            timeTogether = collaborations.get(i).getDays();
            empID1 = collaborations.get(i).getEmp1ID();
            empID2 = collaborations.get(i).getEmp2ID();

            for(int j = i + 1; j < collaborations.size(); j++) {
                if(empID1 == collaborations.get(j).getEmp1ID() && empID2 == collaborations.get(j).getEmp2ID()) {
                    timeTogether += collaborations.get(j).getDays();
                }
            }
            if(timeTogether > maxTimeTogether) {
                maxTimeTogether = timeTogether;
                maxEmpID1 = collaborations.get(i).getEmp1ID();
                maxEmpID2 = collaborations.get(i).getEmp2ID();
            }
        }

        System.out.println("EmployeeID #1: " + maxEmpID1 + ", EmployeeID #2: " +
                maxEmpID2 + ", Days worked: " + maxTimeTogether);

        return new Pair<>(maxEmpID1, maxEmpID2);
    }

    private static List<Employee> readEmployeesFromCSV(String fileName) {
        List<Employee> employees = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                Employee employee = createEmployee(attributes);
                employees.add(employee);
                line = br.readLine();
            }

        } catch (IOException | ParseException ioe) {
            ioe.printStackTrace();
        }
        return employees;
    }

    private static Employee createEmployee(String[] attributes) throws ParseException {
        int empID = Integer.parseInt(attributes[0]);
        int projID = Integer.parseInt(attributes[1]);
        String dateFrom = attributes[2];
        String dateTo = attributes[3];

        return new Employee(empID, projID, dateFrom, dateTo);
    }

    private static int timeWorkedTogether(Employee e1, Employee e2) {
            Date endDate = e1.getDateTo().before(e2.getDateTo()) ? e1.getDateTo() : e2.getDateTo();
            Date startDate = e1.getDateFrom().after(e2.getDateFrom()) ? e1.getDateFrom() : e2.getDateFrom();
            long endInMs = endDate.getTime();
            long startInMs = startDate.getTime();
            long timeTogether = 0;

            if (endDate.after(startDate)) {
                timeTogether = endInMs - startInMs;
            }

        //returning DAYS worked together
        return (int) (timeTogether / (1000 * 60 * 60 * 24));
    }
}
