package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBDeleteAuto {

	private String databaseName;
	private Connection connection;
	private PreparedStatement statement;
	private String query;

	public DBDeleteAuto(String databaseName) {
		this.databaseName = databaseName;
	}
	
	/**
	 * Delete auto from database
	 * @param modelName the name of the model
	 */
	public void deleteAuto(String modelName) {

		if (DBConnection.openConnectionForCreatingTable(databaseName)) {
			try {
				connection = DBConnection.getConnection();

				// Retrieve auto_id
				query = "SELECT auto_id FROM auto WHERE model = ?";
				statement = (PreparedStatement) connection
						.prepareStatement(query);
				statement.setString(1, modelName);
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					int autoId = Integer.parseInt(rs.getString("auto_id"));
					
					// Retrieve optionset_id
					query = "SELECT optionset_id FROM auto_optionset WHERE auto_id = ?";
					statement = (PreparedStatement) connection
							.prepareStatement(query);
					statement.setInt(1, autoId);
					ResultSet optionSetRs = statement.executeQuery();
					while(optionSetRs.next()){
						int optionSetId = Integer.parseInt(optionSetRs.getString("optionset_id"));
						
						// Retrieve option_id
						query = "SELECT option_id FROM optionset_option WHERE optionset_id = ?";
						statement = (PreparedStatement) connection
								.prepareStatement(query);
						statement.setInt(1, optionSetId);
						ResultSet optionRs = statement.executeQuery();
						while(optionRs.next()){
							int optionId = Integer.parseInt(optionRs.getString("option_id"));
							
							// Delete from table options
							query = "DELETE FROM options WHERE option_id = ?";
							statement = (PreparedStatement) connection
									.prepareStatement(query);
							statement.setInt(1, optionId);
							statement.executeUpdate();
						}
						
						// Delete from table optionset
						query = "DELETE FROM optionset WHERE optionset_id = ?";
						statement = (PreparedStatement) connection
								.prepareStatement(query);
						statement.setInt(1, optionSetId);
						statement.executeUpdate();
						
					}
					
					// Delete from table auto
					query = "DELETE FROM auto WHERE auto_id = ?";
					statement = (PreparedStatement) connection
							.prepareStatement(query);
					statement.setInt(1, autoId);
					statement.executeUpdate();
					
				}
				System.out.println("Model '" + modelName + "' was successfully deleted from DB.");
				statement.close(); 
				
				
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				// finally block used to close resources
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException se2) {
				}
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
}
