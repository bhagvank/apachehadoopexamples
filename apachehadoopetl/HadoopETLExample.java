import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HadoopETLExample {
	private static String driverClass = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String args[]) throws SQLException {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException exception) {

			exception.printStackTrace();
			System.exit(1);
		}
		Connection connection = DriverManager.getConnection("jdbc:hive2://", "", "");
		Statement statement = connection.createStatement();

		String table = "EMPLOYEE";
		try {
			statement.executeQuery("DROP TABLE " + table);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		try {
			statement.executeQuery("CREATE TABLE " + table + " (ID INT, NAME STRING, ADDR STRING)");
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		String sql = "SHOW TABLES '" + table + "'";
		System.out.println("Executing Show table: " + sql);
		ResultSet result = statement.executeQuery(sql);
		if (result.next()) {
			System.out.println("Table created is :" + result.getString(1));
		}

		sql = "INSERT INTO EMPLOYEE (ID,NAME,ADDR) VALUES (1, 'John', '4 WestDrive SJC' )";
		System.out.println("Inserting table into employee: " + sql);

		try {
			statement.executeUpdate(sql);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		sql = "SELECT * FROM " + table;
		result = statement.executeQuery(sql);
		System.out.println("Running: " + sql);
		result = statement.executeQuery(sql);
		while (result.next()) {
			System.out.println("Id=" + result.getString(1));
			System.out.println("Name=" + result.getString(2));
			System.out.println("Address=" + result.getString(3));
		}
		result.close();

		statement.close();

		connection.close();

	}
}
