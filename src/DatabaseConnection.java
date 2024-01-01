package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection koneksi;
    String host;
    String user;
    String pass;

    public DatabaseConnection() throws SQLException {
        host = "jdbc:mysql://localhost/crud_java";
        user = "root";
        pass = "";

        if (cekDriver()) {
            koneksi = DriverManager.getConnection(host, user, pass);
        }
    }

    public final boolean cekDriver() {
        boolean ada = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ada = true;
        } catch (ClassNotFoundException c) {
            System.out.println("Driver tidak ada");
        }
        return ada;
    }
}
