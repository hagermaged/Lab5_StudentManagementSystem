package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentManager {
    private List<Student> students;

    //constructor
    public StudentManager() {
        try {
            students = FileHandler.loadfromfile();
        } catch (IOException e) {
            students = new ArrayList<>();
        }
    }

    // Operations 

    public String addStudent(Student s) {
        // Prevent duplicate ID
        for (Student existing : students) {
            if (existing.getId() == s.getId()) {
                return "Student with ID " + s.getId() + " already exists.";
            }
        }

        students.add(s);
        FileHandler.savetofile(students);
        return "Student added successfully.";
    }

    public List<Student> viewStudents() {
        return new ArrayList<>(students); // Return a copy (to protect data)
    }

    public List<Student> sortByName() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(Student::getFullName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public List<Student> sortById() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingInt(Student::getId));
        return sorted;
    }

    public String updateStudent(int id, Student newData) {
        for (Student s : students) {
            if (s.getId() == id) {
                try {
                    s.setFullName(newData.getFullName());
                    s.setAge(newData.getAge());
                    s.setGender(newData.getGender());
                    s.setDepartment(newData.getDepartment());
                    s.setGpa(newData.getGPA());

                    FileHandler.savetofile(students);
                    return "Student with ID " + id + " updated successfully.";

                } catch (IllegalArgumentException e) {
                    return "Update failed: " + e.getMessage();
                }
            }
        }
        return "Student with ID " + id + " not found.";

    } 
    
    public String deleteStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                FileHandler.savetofile(students);
                return "Student with ID "+ id +"deleted successfully.";
            }
        }
        return "Student not found.";
    }

    public List<Student> searchStudent(String keyword) {
        List<Student> results = new ArrayList<>();

        for (Student s : students) {
            if (String.valueOf(s.getId()).equalsIgnoreCase(keyword)
                    || s.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(s);
            }
        }

        return results;
    }
}


