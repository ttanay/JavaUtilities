package JDBC;

import java.sql.*;

public class DbConnect {

		public String connectedDB = null, dbName = null;
		public boolean connected = false;
		
	public DbConnect(String name){
		con = null;
		if(name.equals(null))
			name = "Practice";
		try{
			Class.forName("org.postgresql.Driver");
			//Change DB details 
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + name, "postgres", "password");
			System.out.println("Opened Database \"" + name + "\" successfully");
			connectedDB = name;
			connected = true;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getClass().getName() +": " + e.getMessage());
			//System.exit(0);
		}
	}
	
	public void executeStatement(String query){
		//can only be called after connect()
		Statement stmt = null;
		try{
			if(connected == false)
				throw new Exception();
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			System.out.println("Executed statement: " + query);
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			//System.exit(0);
		}
	}
	
	public void createTable(String tableName, String schema){
		executeStatement("CREATE TABLE " + tableName + " (" + schema + ")");
	}
	
	public void insertRow(String query){
		try{
			con.setAutoCommit(false);
			executeStatement(query);
			con.commit();
			con.setAutoCommit(true);
			System.out.println("Record inserted sucessfully");
		} catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			//System.exit(0);
		}
	}
	
	public ResultSet select(String selectStatement){
		Statement stmt = null;
		try{
			if(connected == false)
				throw new Exception();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectStatement);
			stmt.close();
			System.out.println("Executed statement" + selectStatement);
			return rs;
		}catch(Exception e){
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			//System.exit(0);
			return null;
		}
	}
	
	public void disconnect(){
		try{
			con.close();
			System.out.println("Connection disconnected");
		}catch(Exception e){
			System.out.println("Connection unable to close");
		}
	}
	
}
