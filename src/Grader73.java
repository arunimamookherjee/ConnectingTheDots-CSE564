import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Arunima Mookherjee - amookher
 */

public class Grader73 {
    private final int ASURITE_INDEX = 0;
    private List<String[]> csvData;
    private String filepath;
    private String[] csvHeader;
    private int TOTAL_COLS = 0;

    public void init(String filepath) {
        this.filepath = filepath;
    }

    public int loadData(StudentRepository studentRepository) throws FileNotFoundException {
        readFileData();
        int unknownStudents = 0;
        Iterator iter = studentRepository.getIterator();
        while (iter.hasNext()){
            Student student = (Student) iter.next();
            boolean isGradeAdded = addGradeDetailsToStudent(student);
            if (!isGradeAdded) {
                unknownStudents++;
            }
        }
        return unknownStudents;
    }

    private void readFileData() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filepath));
        csvData = new ArrayList<>();
        setCSVHeader(scanner);
        while (scanner.hasNext()) {
            String[] gradeDetails = scanner.nextLine().replace(" ", "").split(",");
            if (gradeDetails.length == TOTAL_COLS) {
                csvData.add(gradeDetails);
            }
        }
    }
    private void setCSVHeader(Scanner scanner) {
        if (scanner.hasNext()) {
            String[] gradeDetails = scanner.nextLine().split(",");
            csvHeader = gradeDetails;
            TOTAL_COLS = gradeDetails.length;
        }
    }

    private boolean addGradeDetailsToStudent(Student student) {
        String asurite = student.asurite;
        for (String[] gradeDetails : csvData) {
            if (asurite.equalsIgnoreCase(gradeDetails[ASURITE_INDEX])) {
                for (int i = 1; i < TOTAL_COLS; i++) {
                    String quizName = csvHeader[i];
                    String grade = gradeDetails[i];
                    StudentGrades studentGrades = new StudentGrades(quizName, grade, "100");
                    student.add(studentGrades);
                }
                return true;
            }
        }
        return false;
    }



}
