package model;

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
        } catch (IOException ex)
        {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<Student> loadfromfile() throws IOException {

        List<Student> students = new ArrayList<>();

        try (BufferedReader rf = new BufferedReader(new FileReader(filename))) {
            String line = rf.readLine();
            while (line != null) {
                String[] dataofonestudent = line.split(",");
                int id = Integer.parseInt(dataofonestudent[0]);
                String name = dataofonestudent[1];
                double age = Double.parseDouble(dataofonestudent[2]);
                String gender = dataofonestudent[3];
                String depart = dataofonestudent[4];
                double gpa = Double.parseDouble(dataofonestudent[5]);

                Student s = new Student(id, name, age, gender, depart, gpa);

                students.add(s);

            }
        } catch (IOException | NumberFormatException e)
        {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, e);
        }

        return students;

    }
}