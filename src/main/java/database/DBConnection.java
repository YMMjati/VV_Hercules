package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private Connection conn;
	private String driver = "org.postgresql.Driver";
	private String url = "jdbc:postgresql://ec2-79-125-13-42.eu-west-1.compute.amazonaws.com:5432/dckgt94784tt8b";
	private String user = "lwmqbxaipjufpy";
	private String pass = "4ac66eef483ba75131e87d3dfcb4397faaa87cb03eb965d5e18fb936cd20903c";

	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try{
			if (conn == null ) {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url, user, pass);
			}
			return conn;
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException|SQLException e){
			return null;
		}
	}
	
}
