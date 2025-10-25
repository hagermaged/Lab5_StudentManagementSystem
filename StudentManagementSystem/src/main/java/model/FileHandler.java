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
            while ((line = rf.readLine()) != null) {
                String[] dataofonestudent = line.split(",");
                if (data.length == 6) {
                try {
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    double age = Double.parseDouble(data[2].trim());
                    String gender = data[3].trim();
                    String dept = data[4].trim();
                    double gpa = Double.parseDouble(data[5].trim());
                    students.add(new Student(id, name, age, gender, dept, gpa));
                } catch (NumberFormatException | IllegalArgumentException ex) {
                    // Skip bad record but continue reading others
                    Logger.getLogger(FileHandler.class.getName())
                          .log(Level.WARNING, "Skipping invalid record: " + line, ex);
                }
            }
        }
    } catch (IOException ex) {
        Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
    }

        return students;

    }
}
