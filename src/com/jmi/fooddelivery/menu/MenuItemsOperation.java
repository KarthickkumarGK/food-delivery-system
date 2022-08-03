package com.jmi.fooddelivery.menu;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jmi.fooddelivery.dbconnection.DbConnection;

public class MenuItemsOperation {
	Scanner scannerObj = new Scanner(System.in);
	Connection conObj = null;
	Statement stmtObj = null;

	public void addMenuItem(int resId) {
		
		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
		
			System.out.println("---Add New Menu Item---");
			System.out.println("---Item Details---");

			System.out.println("Enter Menu Item Name:");
			String itemname = scannerObj.next();

			System.out.println("Enter Menu Item Price:");
			int price = scannerObj.nextInt();

			stmtObj.executeUpdate("insert into menulist (resid,Itemname,price)values(" + resId +",'" + itemname
					+ "'," + price + ")");

			System.out.println("Item Added Successfully...\n");
			itemList(resId);

		} catch (Exception e) {
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

	public void updateMenuItem(int resId) {

		
		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			System.out.println("---Update Menu Items---");


			System.out.println("Which Want to change in the Menu Item:");
			System.out.println("1. Item Name Change");
			System.out.println("2. Price Change");
			System.out.println("3. Name & Price Change");
			System.out.println("4. Exit");
			System.out.println("Enter Option to Change:");
			int choice = scannerObj.nextInt();
			switch (choice) {
			case 1:

				System.out.println("Enter Item ID:");
				int menuId = scannerObj.nextInt();
				ResultSet resultsetObj = stmtObj.executeQuery(
						"select itemid from menulist where itemid=" + menuId + " and resid =" + resId);
				if (resultsetObj.next()) {
					System.out.println("Enter Item Name:");
					String itemName = scannerObj.next();
					stmtObj.executeUpdate("update menulist set itemname='" + itemName + "' where itemid=" + menuId
							+ " and resid=" + resId);

					System.out.println("Updated Successfully \n");
					itemList(resId);

				} else {
					System.out.println("Item not Available in the List");
					System.out.println("select Correct Menu Item ID ");
					updateMenuItem(resId);
				}
				break;
			case 2:

				System.out.println("Enter item ID:");
				int itemId = scannerObj.nextInt();

				ResultSet resultObj = stmtObj.executeQuery(
						"select itemid from menulist where itemid=" + itemId + " and resid=" + resId);
				if (resultObj.next()) {

					System.out.println("Enter Item Price:");
					int price = scannerObj.nextInt();
					stmtObj.executeUpdate("update menulist set price=" + price + " where itemid=" + itemId + " and resid="
							+ resId);
					System.out.println("Update Price In Menu List \n");
					itemList(resId);

				} else {
					System.out.println("Menu item not Available in the List");
					System.out.println("Enter Correct Item ID ");
					updateMenuItem(resId);
				}
				break;
			case 3:

				System.out.println("Enter ItemID:");
				int item = scannerObj.nextInt();

				ResultSet rsObj = stmtObj.executeQuery(
						"select resid,itemid from menulist where itemid=" + item + " and resid=" + resId);
				if (rsObj.next()) {

					System.out.println("Enter Item Name:");
					String itemName = scannerObj.next();
					System.out.println("Enter Item Price:");
					int price = scannerObj.nextInt();
					stmtObj.executeUpdate("update menulist set price=" + price + ",itemname='" + itemName + "' where itemid="
							+ item + " and resid=" + resId);

					System.out.println("Update Price In Menu List \n");
					itemList(resId);

				} else {

					System.out.println("Menu Item not Available in the List");
					System.out.println("select Correct Item ID ");
					updateMenuItem(resId);
				}
				break;
			case 4:
				System.out.println("Process Done");
				break;
			default:
				System.out.println("Invalid Option");
				break;
			}
			

		} catch (Exception e) {
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

	public void deleteMenuItem(int resId) {
	
		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			
			System.out.println("---Delete Menu Item---");
			
			System.out.println("Enter Item ID:");
			int itemId = scannerObj.nextInt();
			
			ResultSet rsObj = stmtObj.executeQuery(
					"select resid,itemid from menulist where itemid=" + itemId + " and resid=" + resId);
			if (rsObj.next()) {

				stmtObj.executeUpdate("Delete from menulist where itemid=" + itemId + " and resid=" + resId);

				System.out.println("Menu Deleted Successfully \n");		
				itemList(resId);

			} else {

				System.out.println("Menu Item not Available in the List");
				System.out.println("select Correct Item ID ");
				deleteMenuItem(resId);
			}

		} catch (Exception e) {
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
	
public void itemList(int resId) throws SQLException {
		
		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			ResultSet resultsetObj = stmtObj.executeQuery("select itemid,itemname,price from menulist where resid=" + resId);
			System.out.println("\t---MENU ITEMS---\n");
			System.out.println("Menu_ID\t  Items\t\t\tPrice\n");
			while (resultsetObj.next()) {
				System.out.println(resultsetObj.getInt(1) + "\t" + resultsetObj.getString(2) + "\r\t\t\t\t" + resultsetObj.getInt(3));
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

	public void selectOptions(int resId) throws Exception {

		System.out.println("Select Options to Operate Menu Items");
		boolean enable = true;
		while (enable) {
			System.out.println("1. Adding New Items");
			System.out.println("2. Update Existing Items");
			System.out.println("3. Delete Existing Items");
			System.out.println("4.Exit");
			System.out.println("Enter Option :");
			int choice = scannerObj.nextInt();
			switch (choice) {
			case 1:
				addMenuItem(resId);
				break;
			case 2:
				updateMenuItem(resId);
				break;
			case 3:
				deleteMenuItem(resId);
				break;
			case 4:
				System.out.println("Process Done");
				MenuList choiceObj = new MenuList();
				choiceObj.selectChoice();
				enable = false;
				break;
			default:
				break;

			}
		}
	}


}
