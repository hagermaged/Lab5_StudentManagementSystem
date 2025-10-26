package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    File file = new File(filename);
    if (!file.exists()) {
        System.out.println("File not found: " + filename);
        return students;
    }

    try (BufferedReader rf = new BufferedReader(new FileReader(filename))) {
        String line;
        int lineNumber = 0;
        
        while ((line = rf.readLine()) != null) {
            lineNumber++;
            try {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                
                String[] dataofonestudent = line.split(",");
                if (dataofonestudent.length != 6) {
                    System.err.println("Skipping invalid line " + lineNumber + ": " + line);
                    continue;
                }
                
                int id = Integer.parseInt(dataofonestudent[0].trim());
                String name = dataofonestudent[1].trim();
                double age = Double.parseDouble(dataofonestudent[2].trim());
                String gender = dataofonestudent[3].trim();
                String depart = dataofonestudent[4].trim();
                double gpa = Double.parseDouble(dataofonestudent[5].trim());

                Student s = new Student(id, name, age, gender, depart, gpa);
                students.add(s);
                
            } catch (Exception e) {
                System.err.println("Error parsing line " + lineNumber + ": " + line);
                System.err.println("Error: " + e.getMessage());
                // Continue with next line instead of stopping
            }
        }
        
        System.out.println("Loaded " + students.size() + " students from file");
        
    } catch (IOException | NumberFormatException e) {
        System.err.println("Error loading file: " + e.getMessage());
        Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, e);
    }

    return students;
}
  public static void debugFile() {
    try {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filename);
            return;
        }
        
        System.out.println("=== DEBUGGING FILE CONTENT ===");
        try (BufferedReader rf = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = rf.readLine()) != null) {
                lineNumber++;
                System.out.println("Line " + lineNumber + ": " + line);
                String[] parts = line.split(",");
                System.out.println("  Parts: " + Arrays.toString(parts));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
