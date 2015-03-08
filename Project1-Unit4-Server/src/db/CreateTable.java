package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	
	private Statement statement;
	private String databaseName;
	
	public CreateTable(String databaseName){
		this.databaseName = databaseName;
	}

	public void createDatabaseFromFile(String fileName) {
		if (DBConnection.openConnectionForCreatingTable(databaseName)) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				String query = sb.toString();

				statement = (Statement) DBConnection.getConnection()
						.createStatement();
				statement.executeUpdate(query);

				System.out
						.println("Table Created Successfully with File " + fileName);
				statement.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException se2) {
				}
				try {
					if (DBConnection.getConnection() != null)
						DBConnection.getConnection().close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
}
