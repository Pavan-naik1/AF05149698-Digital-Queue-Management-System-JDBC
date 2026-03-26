package app;

import java.sql.*;
import java.util.Scanner;

public class QueueSystem {

    Scanner sc = new Scanner(System.in);
    Connection con = DBConnection.getConnection();

    // 1. ADD PATIENT
    public void addPatient() {
        try {
            System.out.print("Enter patient name: ");
            String name = sc.next();

            String q = "INSERT INTO patients(name,status) VALUES(?, 'Waiting')";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, name);
            ps.executeUpdate();

            System.out.println("Patient added! Token generated.");

        } catch (Exception e) {
            System.out.println("Error adding patient");
        }
    }

    // 2. VIEW QUEUE
    public void viewQueue() {
        try {
            String q = "SELECT * FROM patients WHERE status='Waiting' ORDER BY token ASC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "Token: " + rs.getInt("token") +
                    " | Name: " + rs.getString("name") +
                    " | Status: " + rs.getString("status")
                );
            }

            if (!found) {
                System.out.println("No patients in queue!");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 3. SERVE NEXT PATIENT
    public void serveNext() {
        try {
            String q = "SELECT * FROM patients WHERE status='Waiting' ORDER BY token ASC LIMIT 1";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            if (rs.next()) {
                int token = rs.getInt("token");

                String update = "UPDATE patients SET status='Served' WHERE token=?";
                PreparedStatement ps = con.prepareStatement(update);
                ps.setInt(1, token);
                ps.executeUpdate();

                System.out.println("Serving Patient:");
                System.out.println("Token: " + token + " | Name: " + rs.getString("name"));
            } else {
                System.out.println("No patients waiting!");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 4. LAST SERVED PATIENT
    public void lastServed() {
        try {
            String q = "SELECT * FROM patients WHERE status='Served' ORDER BY token DESC LIMIT 1";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            if (rs.next()) {
                System.out.println(
                    "Last Served -> Token: " + rs.getInt("token") +
                    " | Name: " + rs.getString("name")
                );
            } else {
                System.out.println("No patients served yet!");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}