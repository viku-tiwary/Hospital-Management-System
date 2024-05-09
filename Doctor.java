package com.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection ;

    public Doctor (Connection connection){
        this.connection = connection;

    }

    public void viewDoctor(){
        String query = "select * from doctor ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("***************** Doctor Details *****************");
            System.out.println("+--------------+----------------+--------------------+");
            System.out.println("|  Doctor_Id   |   Doctor_Name  |  Doctor_Department |");
            System.out.println("+--------------+----------------+--------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                System.out.printf("| %-12s | %-13s | %-19s |\n" , id, name, department);
                System.out.println("+--------------+----------------+--------------------+");
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
    }
    public boolean getDoctorById(int Id){
        String query = "select * from doctor where id = ?";
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
