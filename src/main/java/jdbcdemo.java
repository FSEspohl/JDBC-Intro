import java.sql.*;

public class jdbcdemo {
    public static void main(String[] args) {
        System.out.println("JDBC Demo!");

        // Testung aller Methoden
        selectAllDemo();
        System.out.println(ausgabeAbgrenzung());
        insertStudentDemo("Peter Wrinkler", "p.wrinkler@tsn.at");
        System.out.println(ausgabeAbgrenzung());
        selectAllDemo();
        System.out.println(ausgabeAbgrenzung());
        updateStudentDemo("Peter Wrinkler", "Peter Winkler", "p.winkler@tsn.at");
        System.out.println(ausgabeAbgrenzung());
        selectAllDemo();
        System.out.println(ausgabeAbgrenzung());
        deleteStudentDemo("Peter Winkler");
        System.out.println(ausgabeAbgrenzung());
        selectAllDemo();

    }

    public static void deleteStudentDemo(String name) {
        System.out.println("Delete Demo with JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "123";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = con.prepareStatement(
                    "DELETE FROM student WHERE `student`.`name` = ?"
            );
            try{
                preparedStatement.setString(1, name);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der gelöschten Datensätze: " + affectedRows);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-DELETE Statement: "+ ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void updateStudentDemo(String oldName, String name, String email) {
        System.out.println("Update Demo with JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "123";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE `student` SET `name` = ?, `email` = ? WHERE `student`.`name` = ?"
            );
            try{
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, oldName);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze: " + affectedRows);
             } catch (SQLException ex) {
                System.out.println("Fehler im SQL-UPDATE Statement: "+ ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void insertStudentDemo(String name, String email) {
        System.out.println("Insert Demo with JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "123";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ?)"
            );
            try{
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println("Datensätze eingefügt: " + rowAffected);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-INSERT Statement: "+ ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }


    public static void selectAllDemo() {
        System.out.println("Select Demo with JDBC");
        String sqlSelectAllPersons = "SELECT * FROM `student`";
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "123";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                long id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID " + id + "; NAME " + name + "; EMAIL " + email + "]");
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static String ausgabeAbgrenzung(){
        String abgrenzung = "\n-";
        for(int i = 0; i < 40; i++){
            abgrenzung += "-";
        }
        abgrenzung += "\n";
        return abgrenzung;
    }
}
