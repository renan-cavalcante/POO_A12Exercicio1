package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPostgreSQL extends db.Connection{
	private  Connection conn;


	public  Connection getConnection() throws SQLException, ClassNotFoundException {

		Properties props = loadProperties();
		String url = props.getProperty("dburl");
		
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection(url, props);

		return conn;
	}

	private  Properties loadProperties() {
		Properties props = null;
		try (FileInputStream fs = new FileInputStream(getCaminho()+"postgresql.properties")) {
			props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException erro) {
			erro.printStackTrace();
		}
		return props;
	}

}
