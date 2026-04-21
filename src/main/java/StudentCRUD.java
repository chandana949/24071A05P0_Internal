import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {

    private static final String URL = "jdbc:mysql://localhost:3306/students";
    private static final String USER = "root";
    private static final String PASSWORD = "Chandana@123";
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        createTable();

        int choice;

        do {
            System.out.println("\n=== STUDENT CRUD ===");
            System.out.println("1. CREATE  2. READ  3. UPDATE  4. DELETE  5. EXIT");
            System.out.print("Choose: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> createStudent(sc);
                case 2 -> readStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> System.out.println("Bye!");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }

    // CREATE TABLE
    public static void createTable() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = """
                CREATE TABLE students (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    course VARCHAR(100)
                )
            """;

            con.createStatement().execute(sql);
            System.out.println("Table created!");

        } catch (SQLException e) {
            System.out.println("Table already exists.");
        }
    }

    // CREATE
    public static void createStudent(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Course: ");
            String course = sc.nextLine();

            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, course);

            ps.executeUpdate();
            System.out.println("Student Added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    public static void readStudents() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM students");

            System.out.printf("%-5s %-15s %-5s %-15s%n", "ID", "NAME", "AGE", "COURSE");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-5d %-15s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UPDATE
    public static void updateStudent(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("New Name: ");
            String name = sc.nextLine();

            String sql = "UPDATE students SET name = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            if (ps.executeUpdate() > 0)
                System.out.println("Updated!");
            else
                System.out.println("Not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public static void deleteStudent(Scanner sc) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM students WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0)
                System.out.println("Deleted!");
            else
                System.out.println("Not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
