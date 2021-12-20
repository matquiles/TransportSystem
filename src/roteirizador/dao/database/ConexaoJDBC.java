package roteirizador.dao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexaoJDBC {
	
	private static Connection conn = null;
	private static String usuario = "SA";
	private static String senha = "";
	//windows 
	//private static String PathBase = "C:\\Users\\mathe\\eclipse-workspace\\CloneTotvs\\utilitarios\\base\\CloneTotvsDB";
	//pc system 
	private static String PathBase = "/home/matheus/workspaces/estagio/CloneTotvs/utilitarios/base/CloneTotvsDB";
	//linux
	//private static String PathBase = "/home/matheus/eclipse-workspace/CloneTotvs/utilitarios/base/CloneTotvsDB";
	
	private static final String URL = "jdbc:hsqldb:file:" + PathBase + ";";
	
	public static Connection getConnection() {
			if (conn==null) {
				try {
					Class.forName("org.hsqldb.jdbcDriver");
					conn = DriverManager.getConnection(URL, usuario, senha);
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				catch (SQLException e) {
					throw new DbException(e.getMessage());
				}
			} 

		return conn;
	}
	
	public static void closeConnectiopn() {
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try{
				st.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try{
				rs.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void doCloseConnection() {
		if(conn != null) {
			try{
				conn.close();
				conn = null;
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
