package src.main.java;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentDAOImpl implements StudentDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcDriver;
    private Connection jdbcConnection;

    public StudentDAOImpl() {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
            this.jdbcURL = props.getProperty("jdbc.url");
            this.jdbcUsername = props.getProperty("jdbc.username");
            this.jdbcPassword = props.getProperty("jdbc.password");
            this.jdbcDriver = props.getProperty("jdbc.driverClassName");
            Class.forName(this.jdbcDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, age, grade, address) VALUES (?, ?, ?, ?)";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getGrade());
            statement.setString(4, student.getAddress());
            statement.executeUpdate();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try {
            connect();
            Statement statement = jdbcConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                String address = resultSet.getString("address");
                Student student = new Student(id, name, age, grade, address);
                studentList.add(student);
            }
            resultSet.close();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student getStudentById(Long id) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE id = ?";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                String address = resultSet.getString("address");
                student = new Student(id, name, age, grade, address);
            }
            resultSet.close();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ?, address = ? WHERE id = ?";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setString(3, student.getGrade());
            statement.setString(4, student.getAddress());
            statement.setLong(5, student.getId());
            statement.executeUpdate();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
