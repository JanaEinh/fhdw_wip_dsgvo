//Author: Hasan

package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryApp {

	private Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String url = "jdbc:sqlite:C:\\Users\\Jana\\Documents\\FHDW\\4.Semester\\WIP\\Entwicklung\\fhdw_wip_dsgvo\\Webserver\\src\\userDatabase_notencrypted.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String getUserPassword(String username) {
		String query = "SELECT password " + "FROM users WHERE username = ?";

		String result = "";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = "Error";
		}
		return result;

	}

	public void getUsers() {
		String query = "SELECT * " + "FROM users";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("username") + "\n" + rs.getString("password") + "\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		QueryApp app = new QueryApp();
		System.out.println(app.getUserPassword("Hasman"));
		System.out.println("\n");
		app.getUsers();
	}

}
