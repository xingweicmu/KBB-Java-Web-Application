package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Automobile;
import model.Automobile.OptionSet;
import model.Automobile.OptionSet.Option;

public class DBInsertAuto {

	private String databaseName;
	private Connection connection;
	private PreparedStatement statement;
	private String query;

	public DBInsertAuto(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Insert auto to the database
	 * @param auto the Automobile
	 */
	public void insertAuto(Automobile auto) {

		if (DBConnection.openConnectionForCreatingTable(databaseName)) {
			try {
				connection = DBConnection.getConnection();

				// insert model, make, and base price to auto table
				String model = auto.getModel();
				String make = auto.getMake();
				float basePrice = auto.getBasePrice();
				query = "INSERT INTO auto (model, make, base_price) VALUES (?,?,?)";
				statement = (PreparedStatement) connection
						.prepareStatement(query);
				statement.setString(1, model);
				statement.setString(2, make);
				statement.setFloat(3, basePrice);
				statement.executeUpdate();

				// Get auto_id
				int auto_id = 0;
				query = "SELECT auto_id FROM auto WHERE model=? ORDER BY auto_id DESC LIMIT 1";
				statement = (PreparedStatement) connection.prepareStatement(query);
				statement.setString(1, model);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					auto_id = Integer.parseInt(rs.getString("auto_id"));
				}

				for (OptionSet optionSet : auto.getOpetionSetList()) {

					// insert name to optioset table
					String optionSetName = optionSet.getName();
					query = "INSERT INTO optionset (optionset_name) VALUES (?)";
					statement = (PreparedStatement) connection
							.prepareStatement(query);
					statement.setString(1, optionSetName);
					statement.executeUpdate();

					// Get optionset_id
					int optionset_id = 0;
					query = "SELECT optionset_id FROM optionset WHERE optionset_name = ? ORDER BY optionset_id DESC LIMIT 1";
					statement = (PreparedStatement) connection.prepareStatement(query);
					statement.setString(1, optionSetName);
					rs = statement.executeQuery();
					if (rs.next()) {
						optionset_id = Integer.parseInt(rs
								.getString("optionset_id"));
					}

					// insert auto_id and opitonset_id into the auto_optionset
					// table
					if (auto_id != 0 && optionset_id != 0) {
						query = "INSERT INTO auto_optionset (auto_id, optionset_id) VALUES (?,?)";
						statement = (PreparedStatement) connection.prepareStatement(query);
						statement.setInt(1, auto_id);
						statement.setInt(2, optionset_id);
						statement.executeUpdate();
					}

					for (Option option : optionSet.getOption()) {
						// insert name, and price to options table
						String optionName = option.getName();
						float optionPrice = option.getPrice();
						query = "INSERT INTO options (option_name, option_price) VALUES (?,?)";
						statement = (PreparedStatement) connection.prepareStatement(query);
						statement.setString(1, optionName);
						statement.setFloat(2, optionPrice);
						statement.executeUpdate();

						// Get option_id
						int option_id = 0;
						query = "SELECT option_id FROM options WHERE option_name = ? ORDER BY option_id DESC LIMIT 1";
						statement = (PreparedStatement) connection.prepareStatement(query);
						statement.setString(1, optionName);
						rs = statement.executeQuery();
						if (rs.next()) {
							option_id = Integer.parseInt(rs
									.getString("option_id"));
						}

						// insert optionset_id and option_id into the
						// optionset_option table
						if (option_id != 0 && optionset_id != 0) {
							query = "INSERT INTO optionset_option (optionset_id, option_id) VALUES (?,?)";
							statement = (PreparedStatement) connection.prepareStatement(query);
							statement.setInt(1, optionset_id);
							statement.setInt(2, option_id);
							statement.executeUpdate();
						}
					}
				}
				System.out.println("Model '" + model
						+ "' was successfully added to DB.");
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
