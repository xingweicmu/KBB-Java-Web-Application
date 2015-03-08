package driver;

import adapter.BuildAuto;
import db.CreateSchema;
import db.CreateTable;

public class Unit6Test {

	public static void main(String[] args) {

		System.out.println("**UNIT 6 DATABASE TESTING**");
		System.out.println("===========================");

		// Load the database from file
		CreateSchema createSchema = new CreateSchema();
		createSchema.createDatabaseFromFile("sql/create_database.sql");

		// Load the table from file
		CreateTable createTable = new CreateTable("JSPHDEV");
		createTable.createDatabaseFromFile("sql/auto.sql");
		createTable.createDatabaseFromFile("sql/optionset.sql");
		createTable.createDatabaseFromFile("sql/auto_optionset.sql");
		createTable.createDatabaseFromFile("sql/option.sql");
		createTable.createDatabaseFromFile("sql/optionset_option.sql");

		/*
		 * The following is a demonstration of inserting auto into database.
		 * After inserting, all the data concerning auto will be stored in the
		 * table of auto, optionset, auto_optionset, option, optionset_option in
		 * database "JSPHDEV".
		 * 
		 * You can use sql to query the data from these tables in the line. For
		 * example, you can use "Select * from auto" to check the existence of
		 * auto info in the database.
		 */
		System.out.println("\n[TEST INSERTING...]");
		BuildAuto buildAuto = new BuildAuto();
		buildAuto.BuildAuto("input_Ford_SSS", "SSS");
		buildAuto.printAuto("SSS");

		/*
		 * The following is a demonstration of removing auto from database.
		 * After removing, all the data concerning a specific auto will be
		 * removed from the table of auto, optionset, auto_optionset, option,
		 * optionset_option in database "JSPHDEV".
		 * 
		 * You can use sql to query the data from these tables in the line. For
		 * example, you can use "Select * from auto" to check the removal of
		 * auto info in the database.
		 */
		System.out.println("\n[TEST REMOVING...]");
		buildAuto.removeAuto("SSS");

		/*
		 * The following is a demonstration of updating opiton price in the
		 * database. After updating, the price concerning a specific optionset
		 * in a specified auto will be updated in the table of option in
		 * database "JSPHDEV".
		 * 
		 * You can use sql to query the data from these tables in the line. For
		 * example, you can use "Select * from options" to check the updated
		 * price in the database.
		 */
		System.out.println("\n[TEST UPDATING...]");
		buildAuto.BuildAuto("input_Ford_SSS", "SSS");
		buildAuto.updateOptionPrice("SSS", "Transmission", "automatic", 1000);
		System.out.println("\n[AFTER UPDATING...]");
		buildAuto.printAuto("SSS");

	}
}
