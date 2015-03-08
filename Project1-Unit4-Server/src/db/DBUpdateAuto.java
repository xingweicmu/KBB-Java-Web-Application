package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUpdateAuto {

	private String databaseName;
	private Connection connection;
	private PreparedStatement statement;
	private String query;

	public DBUpdateAuto(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Update the option price for auto.
	 * @param modelName the name of the model
	 * @param optionsetName the name of the optionset
	 * @param optionName the name of the option
	 * @param newPrice the new price for the option
	 */
	public void updateOptionPrice(String modelName, String optionsetName,
			String optionName, float newPrice) {

		if (DBConnection.openConnectionForCreatingTable(databaseName)) {
			try {
				connection = DBConnection.getConnection();
				int optionId = 0;
				query = "SELECT options.option_id"
						+ " FROM auto, auto_optionset, optionset, optionset_option, options"
						+ " WHERE auto.auto_id = auto_optionset.auto_id"
						+ " AND auto_optionset.optionset_id = optionset.optionset_id"
						+ " AND optionset.optionset_id = optionset_option.optionset_id"
						+ " AND optionset_option.option_id = options.option_id"
						+ " AND auto.model = ?"
						+ " AND optionset.optionset_name = ?"
						+ " AND options.option_name = ?";
				statement = (PreparedStatement) connection
						.prepareStatement(query);
				statement.setString(1, modelName);
				statement.setString(2, optionsetName);
				statement.setString(3, optionName);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					optionId = Integer.parseInt(rs.getString("option_id"));
				}
				query = "UPDATE options SET option_price = ? WHERE option_id = ?";
				statement = (PreparedStatement) connection
						.prepareStatement(query);
				statement.setFloat(1, newPrice);
				statement.setInt(2, optionId);
				statement.executeUpdate();
				statement.close();
				System.out.println("Model '" + modelName
						+ "' was successfully updated to DB.");

			} catch (Exception e) {
				e.printStackTrace();
			}
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
