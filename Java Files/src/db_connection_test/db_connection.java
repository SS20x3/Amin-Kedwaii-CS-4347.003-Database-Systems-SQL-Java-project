package db_connection_test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public class db_connection {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/cs4347003groupprojectdb"; // connect to Database ran on mySQL WWorkbench
		String id = "User4347";
		String password = "1234567_8";

		Connection con = null;
		DatabaseMetaData metaData = null;
		Statement statement = null;
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.println("Connection is successful");
			metaData = con.getMetaData();
			statement = con.createStatement();
			/*
			 * //insert into db System.out.println("Inserting...");
			 *
			 * String toInsert =
			 * "INSERT INTO product (Product_id, Store_id, Supplier_name, Product_type, Purchace_price, Sales_price, Amount_inventory)"
			 * + "VALUES (6543, 2, 'Blue Cheese co.', 'Dairy', 5.15, 6.50, 18000)";
			 *
			 * statement.executeUpdate(toInsert);
			 *
			 * System.out.println("Insertion finished");
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String userinput = "";
		try (Scanner in = new Scanner(System.in)) {
			do {
				System.out.println(
						"Input action: Select View, Insert Employee, Get Above Average Salaries, Update Employee, Sort Product, Quit");
				userinput = in.nextLine();
				String query = null;
				ResultSet rset = null;
				if (userinput.equals("Select View")) { // select which table to view

					System.out.println("What table will you Select from: ");

					rset = metaData.getTables(null, null, "%", null);

					Vector<String> tableNames = new Vector<>();

					while (rset.next()) { // Prints list of all views
						if (rset.getString("TABLE_TYPE").equals("VIEW")
								&& rset.getString("TABLE_CAT").equals("cs4347003groupprojectdb")) {
							tableNames.add(rset.getString("Table_NAME"));
							System.out.println("Table name: " + tableNames.lastElement());
							System.out.println("Table type: " + rset.getString("TABLE_TYPE"));
							System.out.println();
						}
					}

					userinput = in.nextLine();

					if (tableNames.contains(userinput)) {

						query = "SELECT * FROM " + userinput;

						rset = statement.executeQuery(query);

						while (rset.next()) {
							for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
								System.out.print(rset.getString(i) + "\t");
							}
							System.out.println();
						}

						/*
						 * System.out.println("What element(s) of this table will you Select: ");
						 *
						 * Vector<String> columnNames = new Vector<String>();
						 *
						 * rset = metaData.getColumns(null, null, userinput, null); while (rset.next())
						 * { columnNames.add(rset.getString("COLUMN_NAME"));
						 * System.out.println("column name: " + columnNames.lastElement());
						 * System.out.println("column type: " + rset.getString("TYPE_NAME"));
						 * System.out.println(); }
						 *
						 * userinput = in.nextLine();
						 *
						 * Vector<String> items = new Vector<String>();
						 * items.addAll(Arrays.asList(userinput.split("\\s*,\\s*")));
						 *
						 * if (columnNames.containsAll(items)) { query = "SELECT " + userinput + query;
						 * rset = statement.executeQuery(query);
						 *
						 * while(rset.next()) { for(int i = 0; i < items.size(); i++) {
						 * System.out.print(rset.getString(items.elementAt(i)) + "\t"); }
						 * System.out.println(); } } else { System.out.
						 * println("One or more of the requested columns not exist in this context"); }
						 */

					} else {
						System.out.println("That table not exist in this context");
					}
				} else if (userinput.equals("Insert Employee")) { // Create new entry in Employee table
					System.out.println("Insert New Employee Info:");

					query = "INSERT INTO employee (Employee_id, Manager_id, Pay_rate, Store_id, First_name, Last_name, Middle_init, Address) "
							+ "VAlUES (";

					rset = metaData.getColumns(null, null, "employee", null);

					Vector<String> inputs = new Vector<>();

					while (rset.next()) {
						System.out.println("column name: " + rset.getString("COLUMN_NAME"));
						System.out.println("column type: " + rset.getString("TYPE_NAME"));
						System.out.println();
						userinput = in.nextLine();
						inputs.add(userinput);

					}
					// format the Insertion query
					query += inputs.elementAt(0) + ", " + inputs.elementAt(1) + ", " + inputs.elementAt(2) + ", "
							+ inputs.elementAt(3) + ", \'" + inputs.elementAt(4) + "\', \'" + inputs.elementAt(5)
							+ "\', '" + inputs.elementAt(6) + "\', \'" + inputs.elementAt(7) + "\')";

					try { // attempt to execute the Insertion query
						statement.executeUpdate(query);
						System.out.println("New Employee has been Inserted.");
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else if (userinput.equals("Get Above Average Salaries")) { // run a query to see all employees who make above average pay

					query = "SELECT Employee_id, First_name, Last_name, Pay_rate " + "FROM employee "
							+ "WHERE Pay_rate > (SELECT AVG(Pay_rate) " + "FROM employee);";

					try {
						rset = statement.executeQuery(query);
						while (rset.next()) {
							for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
								System.out.print(rset.getString(i) + "\t");
							}
							System.out.println();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (userinput.equals("Update Employee")) { // Update exiting entry in the Employee table
					System.out.println("Input Employee_id of Employee to be updated:");

					userinput = in.nextLine();

					query = "UPDATE employee SET ";
					String query2 = " WHERE employee_id = " + userinput;

					Vector<String> columnNames = new Vector<>();
					Vector<String> columnTypes = new Vector<>();

					rset = metaData.getColumns(null, null, "employee", null);
					while (rset.next()) {
						columnNames.add(rset.getString("COLUMN_NAME"));
						columnTypes.add(rset.getString("TYPE_NAME"));
						System.out.println("column name: " + columnNames.lastElement());
						System.out.println("column type: " + columnTypes.lastElement());
						System.out.println();
					}

					System.out.println("Which column should be updated?");

					userinput = in.nextLine();

					if (columnNames.contains(userinput)) {
						query += userinput + " = ";
						System.out.println("What should it be updated to?");
						String userinput2 = in.nextLine();

						if (columnTypes.elementAt(columnNames.indexOf(userinput)).equals("CHAR")) {
							userinput2 = "\'" + userinput2 + "\'";
						}
						query += userinput2 + query2;

					} else {
						System.out.println("One or more of the requested columns not exist in this context");
					}

					try {
						statement.executeUpdate(query);
						System.out.println("Employee " + userinput + " has been Updated.");

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else if (userinput.equals("Sort Product")) { // Lists available product by specified parameter
					System.out.println("Seclect the column that you wish to sort by");

					rset = metaData.getColumns(null, null, "product", null);
					Vector<String> columnNames = new Vector<>();
					while (rset.next()) {
						columnNames.add(rset.getString("COLUMN_NAME"));
						System.out.println("column name: " + columnNames.lastElement());
						System.out.println("column type: " + rset.getString("TYPE_NAME"));
						System.out.println();
					}

					userinput = in.nextLine();

					query = "SELECT * FROM product ORDER BY ";

					if (columnNames.contains(userinput)) {
						query += userinput;
						try {
							rset = statement.executeQuery(query);
							while (rset.next()) {
								for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
									System.out.print(rset.getString(i) + "\t");
								}
								System.out.println();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("The requested column does not exist in this context");
					}
				} else if (userinput.equals("Quit")) { // Close DB connection

					try {
						con.close();
						System.out.println("Connection has been terminated");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} while (!userinput.equals("Quit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
