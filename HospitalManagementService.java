package com.HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.xdevapi.DeleteStatement;
import com.sun.source.tree.BreakTree;

import javax.print.Doc;
import java.awt.*;
import java.awt.geom.RectangularShape;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementService {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String user_name = "root";
    private static final String password = "Viku2407";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, user_name, password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while (true) {
                System.out.println("*************************WELCOME TO HOSPITAL MANAGEMENT SERVICES*****************************************");
                System.out.println("1. Add Patient :");
                System.out.println("2. View Patient :");
                System.out.println("3. View Doctor : ");
                System.out.println("4. Book Appointment :");
                System.out.println("5. Exit !!");
                System.out.print("Enter your Choice : ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        bookAppoitment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANK YOU!! FOR USING HOSPITAL MANAGEMENT SYSTEM .");
                        return;
                    default:
                        System.out.print("Enter valid Options!!!!");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void bookAppoitment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient ID : ");
        int patient_id = scanner.nextInt();
        System.out.print("Enter Doctor ID : ");
        int doctor_id = scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD) : ");
        String appoitment_date = scanner.next();

        if (patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvability(doctor_id,appoitment_date ,connection)){
                String appointmentsQuery = "INSERT INTO appoitments(patient_id, doctor_id, appoitment_date) VALUES(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentsQuery);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appoitment_date);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected>0){
                        System.out.println("Appoitment Book");
                    }else {
                        System.out.println("Failed to Book Appointments");
                    }
                }catch (SQLException s){
                    s.printStackTrace();
                }
            }else {
                System.out.println("Doctor not available on this date !!!");
            }
        }else {
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    public static boolean checkDoctorAvability  (int doctor_id, String date, Connection connection) {
        String query = "SELECT COUNT(*) FROM appoitments WHERE doctor_id = ? AND appoitment_date = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctor_id);
            preparedStatement.setString(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return false;
    }
}
