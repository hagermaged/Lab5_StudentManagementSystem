package model;

/**
 *
 * @author KimoStore
 */
public class Student {

    private int id;

    private String FullName;
    private double Age;
    private String Gender;
    private String Department;
    private double GPA;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return FullName;
    }

    public double getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }

    public String getDepartment() {
        return Department;
    }

    public double getGPA() {
        return GPA;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("❌ ID must be a positive number.");
        }
        this.id = id;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Full name cannot be empty.");
        }
        // check that fullname contain letters only
        if (!fullName.matches("^[a-zA-Z ]+$")) {
            throw new IllegalArgumentException("❌ Full name must contain letters only.");
        }
        this.FullName = fullName.trim();
    }

    public void setAge(double age) {
        if (age < 15 || age > 100) {
            throw new IllegalArgumentException("❌ Age must be between 15 and 100.");
        }
        this.Age = age;
    }

    public void setGender(String gender) {
        if (gender == null) {
            throw new IllegalArgumentException("❌ Gender cannot be null.");
        }
        gender = gender.trim().toLowerCase();
        if (!(gender.equals("male") || gender.equals("female"))) {
            throw new IllegalArgumentException("❌ Gender must be 'Male' or 'Female'.");
        }
        // store first letter only capital
        this.Gender = gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
    }

    public void setDepartment(String Department) {
        if (Department== null || Department.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Department cannot be empty.");
        }
        this.Department =Department.trim();
    }

    public void setGpa(double GPA ) {
        if (GPA < 0.0 || GPA > 4.0) {
            throw new IllegalArgumentException("❌ GPA must be between 0.0 and 4.0.");
        }
        this.GPA = GPA;
    }

    public Student(int id, String FullName, double Age, String Gender, String Department, double GPA) {
         setId(id);
         setFullName(FullName);
         setAge(Age);
         setGender(Gender);
         setDepartment(Department);
         setGpa(GPA);
    }
    
    @Override
    public String toString()
    {
        return id+","+FullName+","+Age+","+Gender+","+Department+","+GPA;
        
    }

}