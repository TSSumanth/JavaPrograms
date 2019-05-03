package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCCRUDOperations {

	String DBURL;
	String DBUser;
	String DBPassword;
	Connection connection;
	Statement statement;
	ResultSet resultset;
	public static void main(String[] args) {
		
		JDBCCRUDOperations object = new JDBCCRUDOperations();
		object.DBURL="jdbc:oracle:thin:@localhost:1521:orcl";
		object.DBUser="system";
		object.DBPassword="Tssr1993";
		object.startConnection(object.DBURL,object.DBUser,object.DBPassword);
		String[] columnnames = {"customer_id", "customer_name", "city"};
		String[] columnValues ={"115","sundasjs","kdjkas"}; 
		String query = object.buildInsertQuery("customers", columnnames, columnValues);
		System.out.println(query);
		object.executeQuery(query);
		String[][] updatecolumsnname= {{"customer_name","Sumanfher"},{"city","hyder"}};
		String wherecondition = "where customer_id=113";
		String updatequery = object.buildUpdateQuery("customers", updatecolumsnname, wherecondition);
		System.out.println(updatequery);
		object.executeQuery(updatequery);
		
	}
	
	
	public void startConnection(String DBurl, String Username, String Password)
	{
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection= DriverManager.getConnection(DBurl,Username,Password);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String buildInsertQuery(String TableName, String[] columnNames, String[] columnValues)
	{
		String query = "Insert into "+TableName+" (";
		for(int i=0;i<columnNames.length;i++)
		{
			if(i==columnNames.length-1)
			{
				query = query + columnNames[i];
				break;
			}
			query = query + columnNames[i] +",";
		}
		query = query + ") values (";
		for(int i=0;i<columnValues.length;i++)
		{
			if(i==columnValues.length-1)
			{
				query = query +"'" + columnValues[i] +"'";
				break;
			}
			
			query = query +"'" + columnValues[i] +"'" +",";
		}
		query = query + ")"; 
		
		return query;
	}
	
	public String buildUpdateQuery(String TableName, String[][] updateColumnNames, String whereCondition)
	{
		String query = "Update "+TableName+" Set ";
		for(int i=0;i<updateColumnNames.length;i++)
		{
			if(i==updateColumnNames.length-1)
			{
				query = query + updateColumnNames[i][0] +"=" + "'"+ updateColumnNames[i][1]+"'";
				break;
			}
			query = query + updateColumnNames[i][0] +"=" +"'"+ updateColumnNames[i][1]+ "'"+",";
		}
		
		query = query +" " +whereCondition;
		
		return query;
	}
	
	
	public void executeQuery(String query)
	{
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultset = statement.executeQuery(query);
		} catch ( SQLException e) {
			System.out.println("In catch bloack");
			e.printStackTrace();
		}
	}

}
