import java.util.List;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentDAO studentDAO = new StudentDAOImpl();
        StudentService studentService = new StudentService(studentDAO);

        while (true) {
            System.out.println("1. Add a new student");
            System.out.println("2. View all students");
            System.out.println("3. View a student by ID");
            System.out.println("4. Update a student");
            System.out.println("5. Delete a student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    Student student = new Student(null, name, age, grade, address);
                    studentService.addStudent(student);
                    System.out.println("Student added successfully.");
                    break;

                case 2:
                    List<Student> students = studentService.getAllStudents();
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    Long id = scanner.nextLong();
                    Student s = studentService.getStudentById(id);
                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine();  // Consume newline
                    Student updateStudent = studentService.getStudentById(updateId);
                    if (updateStudent != null) {
                        System.out.print("Enter new name: ");
                        updateStudent.setName(scanner.nextLine());
                        System.out.print("Enter new age: ");
                        updateStudent.setAge(scanner.nextInt());
                        scanner.nextLine();  // Consume newline
                        System.out.print("Enter new grade: ");
                        updateStudent.setGrade(scanner.nextLine());
                        System.out.print("Enter new address: ");
                        updateStudent.setAddress(scanner.nextLine());
                        studentService.updateStudent(updateStudent);
                        System.out.println("Student updated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter student ID: ");
                    Long deleteId = scanner.nextLong();
                    studentService.deleteStudent(deleteId);
                    System.out.println("Student deleted successfully.");
                    break;

                case 6:
                    System.out.println("Exiting application.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
