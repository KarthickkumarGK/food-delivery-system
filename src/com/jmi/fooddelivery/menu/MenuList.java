package com.jmi.fooddelivery.menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import com.jmi.fooddelivery.dbconnection.DbConnection;


public class MenuList {
	Scanner scannerObj = new Scanner(System.in);

	public void showItems() {
		Connection conObj = null;
		Statement stmtObj = null;

		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();

			System.out.println("Enter Restaurant ID");
			int restId = scannerObj.nextInt();
			ResultSet rsObj = stmtObj.executeQuery("select resid from menulist where resid=" + restId);
			
			if(rsObj.next()) {
				ResultSet resultsetObj = stmtObj.executeQuery("select itemid,itemname,price from menulist where resid=" + restId);
				System.out.println("\t---MENU ITEMS---\n");
				System.out.println("Menu_ID\t  Items\t\t\tPrice\n");
				while (resultsetObj.next()) {
					System.out.println(resultsetObj.getInt(1) + "\t" + resultsetObj.getString(2) + "\r\t\t\t\t" + resultsetObj.getInt(3));
				}
				MenuItemsOperation menuItemsObj = new MenuItemsOperation();
				menuItemsObj.selectOptions(restId);
			}else {
				System.out.println("This ID not in the Restaurant List");
				showItems();

			}
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		finally {
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
	

	public void selectChoice() throws Exception {

		System.out.println("Select Particular Restaurant if any changes in Menu Items (y/n):");
		String choice = scannerObj.next();

		switch (choice) {
		case "y":
			showItems();
			break;

		case "n":
			System.out.println("Process Completed");
			System.out.println("Logging Out...");
			break;

		default:
			System.out.println("Invalid Option");
			break;
		}
	}
}
