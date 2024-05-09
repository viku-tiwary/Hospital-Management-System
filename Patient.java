package com.HospitalManagementSystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection ;
    private Scanner scanner;

    public Patient (Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatient(){

        System.out.print("Enter Patient Name :");
        String Name = scanner.next();
        System.out.print("Enter Patient Age :");
        int age = scanner.nextInt();
        System.out.print("Enter Patient Gender :");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patients (Name, Age, gender ) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , Name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Add patient Successfully ");
            }else{
                System.out.println("Failed to add patient ");
            }

        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    public void viewPatient(){
        String query = "select * from patients ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("***************** Patient Details *****************");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
    public boolean getPatientById(int Id){
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return  true;
            }else {
                return false;
            }
        }catch (SQLException s ){
            s.printStackTrace();
        }
        return false;
    }
}
