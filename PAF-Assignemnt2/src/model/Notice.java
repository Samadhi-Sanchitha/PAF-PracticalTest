package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notice {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

			// For testing
			System.out.print("Successfully connected");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String readNotice() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>noticeType</th>" + "<th>noticeDesc</th>"

					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from notices";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String  noticeID = Integer.toString(rs.getInt("noticeID"));
				String noticeType = rs.getString("noticeType");
				String noticeDesc = rs.getString("noticeDesc");

				// Add into the html table

				output += "<tr><td><input id='hidNotIDUpdate' name='hidNotIDUpdate' type='hidden' value='" + noticeID
						+ "'>" + noticeType + "</td>";

				output += "<td>" + noticeDesc + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-noticeID='"
						+ noticeID + "'>" + "</td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Notices.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Insert notices
	public String insertNotice(String noticeType, String noticeDesc) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into notices (`noticeID`,`noticeType`,`noticeDesc`)" + " values (?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, noticeType);
			preparedStmt.setString(3, noticeDesc);
			

			// execute the statement
			preparedStmt.execute();
			con.close();

			// Create JSON Object to show successful msg.
			String newNotice = readNotice();
			output = "{\"status\":\"success\", \"data\": \"" + newNotice + "\"}";
		} catch (Exception e) {
			// Create JSON Object to show Error msg.
			output = "{\"status\":\"error\", \"data\": \"Error while Inserting Notice.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Update notices
	public String updateNotice(String noticeID, String noticeType, String noticeDesc) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE notices SET noticeType=?,noticeDesc=? WHERE noticeID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, noticeType);
			preparedStmt.setString(2, noticeDesc);
			

			preparedStmt.setInt(3, Integer.parseInt(noticeID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON object to show successful msg
			String newNotice = readNotice();
			output = "{\"status\":\"success\", \"data\": \"" + newNotice + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while Updating Notice Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteNotice(String noticeID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "DELETE FROM notices WHERE noticeID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(noticeID));
			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON Object
			String newNotice = readNotice();
			output = "{\"status\":\"success\", \"data\": \"" + newNotice + "\"}";
		} catch (Exception e) {
			// Create JSON object
			output = "{\"status\":\"error\", \"data\": \"Error while Deleting Notice.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}

}
