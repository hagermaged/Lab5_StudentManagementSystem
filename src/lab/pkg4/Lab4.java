package lab.pkg4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class to comprehensively test the Student and FileHandler classes,
 * including all validation checks and robustness against corrupt data.
 * * @author Gemini
 */
public class Lab4 {

    private static final String FILENAME = "Students.txt";

    /**
     * Helper method to manually write a test file, including corrupt lines.
     */
    private static void createCorruptTestFile() {
        System.out.println("\n--- Creating Test File with Corrupt Data ---");
        List<String> fileLines = new ArrayList<>();

        // 1. VALID Student (Should Load)
        fileLines.add("201,Salem Mustafa,20.0,Male,Civil Engineering,3.15");

        // 2. CORRUPT: Number Format Error (Age is text)
        fileLines.add("202,Bad Age Student,Twenty,Female,Physics,3.5");

        // 3. CORRUPT: Student Validation Error (GPA > 4.0)
        fileLines.add("203,Bad Gpa Student,22.0,Male,Chemistry,5.0");

        // 4. CORRUPT: Validation Error (Gender is neither Male nor Female)
        fileLines.add("204,Bad Gender Student,19.0,Other,Arts,3.0");

        // 5. CORRUPT: Malformed Line (Missing the department field - 5 fields instead of 6)
        fileLines.add("205,Missing Field,21.0,Male,3.3");

        // 6. VALID Student (Should Load)
        fileLines.add("206,Laila Saeed,23.0,Female,Management,3.9");
        
        // 7. CORRUPT: Number Format Error (ID is text)
        fileLines.add("ID-X,Invalid ID,20.0,Male,IT,3.0");


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String line : fileLines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✅ Test file '" + FILENAME + "' created with 2 valid and 5 corrupt lines.");
        } catch (IOException e) {
            System.err.println("❌ Failed to write the test file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   Phase 1: Student Validation (Direct Test)      ");
        System.out.println("==================================================");

        // --- Phase 1: Test Student Constructor Validation ---
        
        System.out.println("Attempting to create students with invalid data (Expected: Errors)");
        
        // Test 1: ID <= 0
        try { new Student(0, "Test", 20, "Male", "CS", 3.0); } 
        catch (IllegalArgumentException e) { System.out.println("   [PASS] ID Check: " + e.getMessage()); }

        // Test 2: Name contains non-letters
        try { new Student(1, "Ahmed 123", 20, "Male", "CS", 3.0); } 
        catch (IllegalArgumentException e) { System.out.println("   [PASS] Name Check: " + e.getMessage()); }
        
        // Test 3: Age out of range
        try { new Student(2, "Test Age", 14.9, "Male", "CS", 3.0); } 
        catch (IllegalArgumentException e) { System.out.println("   [PASS] Age Check (Low): " + e.getMessage()); }
        
        // Test 4: Gender invalid
        try { new Student(3, "Test Gender", 20, "Non-Binary", "CS", 3.0); } 
        catch (IllegalArgumentException e) { System.out.println("   [PASS] Gender Check: " + e.getMessage()); }

        // Test 5: GPA out of range
        try { new Student(4, "Test Gpa", 20, "Male", "CS", 4.01); } 
        catch (IllegalArgumentException e) { System.out.println("   [PASS] GPA Check (High): " + e.getMessage()); }

        System.out.println("✅ Student validation tests passed successfully.");
        
        
        System.out.println("\n==================================================");
        System.out.println("   Phase 2 & 3: File Handler Robustness Test      ");
        System.out.println("==================================================");

        // --- Phase 2: Create Corrupt File ---
        createCorruptTestFile(); // Creates Students.txt with 2 valid and 5 invalid lines
        
        // --- Phase 3: Test Loading Corrupt Data (Crucial Robustness Check) ---
        List<Student> loadedStudents = new ArrayList<>();
        try {
            // Note: FileHandler.loadfromfile() will print the error messages for the 5 corrupt lines
            loadedStudents = FileHandler.loadfromfile();
        } catch (Exception e) {
            System.err.println("❌ Test Failed: Program crashed during loading. Error: " + e.getMessage());
            return;
        }

        System.out.println("\n>>> Phase 4: Final Verification <<<");
        
        // Expected result: Only 2 valid students should be loaded (201 and 206)
        int expectedValidCount = 2;
        
        if (loadedStudents.size() == expectedValidCount) {
            System.out.println("✅ Robustness Test Successful!");
            System.out.println("   - Program did NOT crash.");
            System.out.println("   - Loaded " + loadedStudents.size() + " students (Matching expected count).");
            System.out.println("Loaded Students:");
            for (Student s : loadedStudents) {
                 System.out.println("   -> ID: " + s.getId() + ", Name: " + s.getFullName());
            }
        } else {
            System.err.println("❌ Robustness Test FAILED!");
            System.err.println("   - Expected to load " + expectedValidCount + " students, but loaded " + loadedStudents.size() + ".");
            System.err.println("   - Check console logs for '⚠️ Skipping line' messages.");
        }
        
        System.out.println("\n==================================================");
        System.out.println("       All comprehensive tests concluded.         ");
        System.out.println("==================================================");
    }
}

