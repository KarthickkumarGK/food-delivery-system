package com.jmi.fooddelivery.dbconnection;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DbConnection {

	private static String dbdrive = "dbdriver";
	private static String dburl = "dburl";
	private static String dbuser = "dbuser";
	private static String dbpwd = "dbpassword";

	
	public static Connection getconn() {
		Connection conn = null;
		Properties prop = null;
		try {
			prop=new Properties();
			InputStream inputprop=new FileInputStream("/home/karthick/Documents/Karthick Kumar/Java/FoodDeliverySystem/src/config.properties");
			prop.load(inputprop);
			Class.forName(prop.getProperty(dbdrive));
			conn=DriverManager.getConnection(prop.getProperty(dburl),prop.getProperty(dbuser),prop.getProperty(dbpwd));
		}

		catch (Exception e) {
			System.out.println(e);
		}
	
		return conn;
	}
	
}
