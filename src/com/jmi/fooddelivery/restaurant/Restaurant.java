package com.jmi.fooddelivery.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jmi.fooddelivery.dbconnection.DbConnection;
import com.jmi.fooddelivery.menu.MenuList;

public class Restaurant {
	Scanner scannerObj = new Scanner(System.in);

	Connection conObj = null;
	Statement stmtObj = null;
	public void restaurantList() throws Exception {


		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			ResultSet resultsetObj = stmtObj.executeQuery("select * from restaurantlist");
			System.out.println("\t\t\t---RESTAURANT LIST---\n");
			System.out.println("Rest.ID\t Restaurant Name\t  Address\t\tRating\t  Contact");
			while (resultsetObj.next()) {
				System.out.println("\r" + resultsetObj.getInt(1) + "\t" + resultsetObj.getString(2) + "\r\t\t\t\t"
						+ resultsetObj.getString(3) + "\t " + resultsetObj.getString(4) + "\t" + resultsetObj.getLong(5));
			}

			System.out.println("Adding/Deleting Restaurant if any (y/n):");
			String option = scannerObj.next();
			switch (option) {
			case "y":

				System.out.println("Select any One:\n ");
				System.out.println("1. Create New Restaurant");
				System.out.println("2. Delete Existing Restaurant");
				System.out.println("3. Exit");
				int choice = scannerObj.nextInt();
				switch (choice) {
				case 1:
					resCreate();
					break;
				case 2:
					deleteRes();
					break;
				case 3:
					System.out.println("Process Done");

					break;
				default:
					System.out.println("Invalid Option");
					break;
				}

				break;

			case "n":
				MenuList menuObj = new MenuList();
				menuObj.selectChoice();
				break;

			default:
				System.out.println("Invalid Option Select Valid Option");
				break;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmtObj!=null ) {
					stmtObj.close();
					
				}
				if(conObj!=null) {
					conObj.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void resCreate()  {

		
		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			System.out.println("---Add New Restaurant---");
			System.out.println("---Restaurant Details---");

			System.out.println("Enter Restaurant ID:");
			int restId = scannerObj.nextInt();

			System.out.println("Enter Restaurant Name:");
			String restName = scannerObj.next();

			System.out.println("Enter Restaurant Address:");
			String address = scannerObj.next();

			System.out.println("Enter Restaurant Rating:");
			float rating = scannerObj.nextFloat();

			System.out.println("Enter Restaurant Contact:");
			long contact = scannerObj.nextLong();

			stmtObj.executeUpdate("insert into restaurantlist(resid,restname,address,rating,contact)values(" + restId
					+ ",'" + restName + "','" + address + "'," + rating + "," + contact + ")");

			System.out.println("Added Successfully...");
			restaurantList();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				if(stmtObj!=null ) {
					stmtObj.close();
					
				}
				if(conObj!=null) {
					conObj.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public void deleteRes() throws Exception {

		System.out.println("---Delete Restaurant---");

		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();

			System.out.println("Which want to Delete Restaurant Enter Restaurant ID:");
			int restId = scannerObj.nextInt();
			ResultSet rsObj = stmtObj.executeQuery("select resid from restaurantlist where resid=" + restId);

			if (rsObj.next()) {
				stmtObj.executeUpdate("delete from menulist where resid=" + restId);
				stmtObj.executeUpdate("delete from restaurantlist where resid=" + restId);
				System.out.println("Deleted Successfully");
				restaurantList();

			} else {
				System.out.println("This Restaurant ID not in the list");
				deleteRes();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				if(stmtObj!=null ) {
					stmtObj.close();
					
				}
				if(conObj!=null) {
					conObj.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		}

	}
}
