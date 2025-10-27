/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KimoStore
 */
public class FileHandler {

    private static final String filename = "Students.txt";

    public static void savetofile(List<Student> students) {
        try (BufferedWriter f = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                f.write(s.toString());
                f.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<Student> loadfromfile() throws IOException {

        List<Student> students = new ArrayList<>();

        try (BufferedReader rf = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = rf.readLine()) != null) {

                try {
                    String[] dataofonestudent = line.split(",");
                    if (dataofonestudent.length != 6) {
                        throw new IllegalArgumentException("Incorrect number of fields in the line.");
                    }

                    int id = Integer.parseInt(dataofonestudent[0].trim());
                    String name = dataofonestudent[1].trim();
                    double age = Double.parseDouble(dataofonestudent[2].trim());
                    String gender = dataofonestudent[3].trim();
                    String depart = dataofonestudent[4].trim();
                    double gpa = Double.parseDouble(dataofonestudent[5].trim());

                    Student s = new Student(id, name, age, gender, depart, gpa);
                    students.add(s);

                } catch (NumberFormatException e) {
                    System.err.println("️ Skipping line (Number format error): " + line);
                } catch (IllegalArgumentException e) {
                    System.err.println("⚠️ Skipping line (Validation failed in Student class): " + line + " -> " + e.getMessage());
                }

            }
        } catch (IOException e) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return students;

    }
}
