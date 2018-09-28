//Author: Hasan

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUser {
	
	private static final String DATABASE_PATH = "C:\\Users\\niklas.frank\\Documents\\Theoriephase\\4. Semester\\WIP Projekt\\\\GitHub Projekt\\fhdw_wip_dsgvo\\Webserver\\src\\userDatabase_notencrypted.db";

	private Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String url = "jdbc:sqlite:" + DATABASE_PATH;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void insert(int Id, String username, String password) {
		String insertStatement = "INSERT INTO Users(Id,username,password)" + "VALUES(?,?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(insertStatement)) {
			pstmt.setInt(1, Id);
			pstmt.setString(2, username);
			pstmt.setString(3, password);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		InsertUser app = new InsertUser();
		app.insert(2, "Niklas", "Fi$chbach2018!");
		app.insert(3, "Jana", "Fi$chbach2018!");
		app.insert(4, "TestUser", "TestPasswort!");
		app.insert(5, "client.dsgvo.testuser1@gmail.com", "Fi$chbach2018!");
	}
}