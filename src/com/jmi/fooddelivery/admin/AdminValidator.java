package com.jmi.fooddelivery.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jmi.fooddelivery.dbconnection.DbConnection;
import com.jmi.fooddelivery.restaurant.Restaurant;

public class AdminValidator {

	@SuppressWarnings("resource")
	public void adminValidation() {
		Scanner scannerObj = new Scanner(System.in);
		Connection conObj = null;
		Statement stmtObj = null;

		try {
			conObj = DbConnection.getconn();
			stmtObj = conObj.createStatement();
			System.out.println("---LOGIN PAGE--- \n");
			System.out.println("Enter User Name:");
			String user = scannerObj.next();
			System.out.println("Enter Password:");
			String password = scannerObj.next();

			ResultSet resultsetObj = stmtObj.executeQuery("select adminname,password from admin where adminname='" + user + "' AND password='" + password + "'");
			if (resultsetObj.next()) {
				System.out.println("LOGIN SUCCESSFUL \n");
				Restaurant restaurantObj = new Restaurant();
				restaurantObj.restaurantList();

			} else {
				System.out.println("INVALID CREDENTIAL \n");
				adminValidation();

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

	public static void main(String[] args) {
		AdminValidator adminObj = new AdminValidator();
		System.out.println("~~~FOOD DELIVERY APPLICATION~~~");

		adminObj.adminValidation();
	}
}
