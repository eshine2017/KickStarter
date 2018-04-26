package org.dbms.ks.util;

import static org.dbms.ks.util.ConfigUtil.get;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dbms.ks.models.BaseModel;
import org.json.JSONObject;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class DBUtil {
	private static PoolDataSource pds = null;
	
	private static boolean isCPEnabled = false;
	
	public static void initConnectionPool() throws SQLException {
	   pds= PoolDataSourceFactory.getPoolDataSource();
	   //Setting connection properties of the data source
	   pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
	   pds.setURL(get("C_STR"));
	   pds.setUser(get("UNAME"));
	   pds.setConnectionPoolName("ks_pool");
	   pds.setPassword(get("PASS"));
	   //Setting pool properties
	   pds.setInitialPoolSize(3);
	   pds.setMinPoolSize(3);
	   pds.setMaxPoolSize(6);
	}
	
	public static DBConnection getConnection() throws SQLException {
		if(pds == null && isCPEnabled) {
		  initConnectionPool();
		}
		
		Connection con = isCPEnabled ? pds.getConnection() : DriverManager.getConnection(get("C_STR"), get("UNAME"), get("PASS"));
		return new DBConnection(con);  
	}
	
	public static void _safeClose(AutoCloseable resource) {
		try {
			if(resource!=null) {
				resource.close();
				resource = null;
			}
		}catch(Exception e) {
			//NO OP
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T getFirst(String query, Class <? extends BaseModel> schema, Object ...args){
		DBConnection con = null;
		try {
			con = getConnection();
			con.prepareQuery(query);
			if(args!=null) {
				for(int i=0; i < args.length; i++) {
					con.setQueryParam(i+1, args[i]);
				}
			}
			con.executeQuery();
			return (T) (con.hasNext() ? con.getNext(schema) : null); 
		} catch(Exception e) {
			// LOG
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con.safeClose();
			}
		}
		return null;
	}

	public static <T extends BaseModel> ArrayList<T> getAll(String query, Class <T> schema, Object ...args){
		DBConnection con = null;
		ArrayList<T> returnList = new ArrayList<>();
		try {
			con = getConnection();
			con.prepareQuery(query);
			if(args!=null) {
				for(int i=0; i < args.length; i++) {
					con.setQueryParam(i+1, args[i]);
				}
			}
			con.executeQuery();
			returnList = (ArrayList<T>) con.getAll(schema);
		} catch(Exception e) {
			// LOG
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con.safeClose();
			}
		}
		return returnList;
	}
	
	public static class DBConnection {
		private Connection con;
		private PreparedStatement pstm;
		private ResultSet rs;
		
		public DBConnection(Connection con) {
			this.con = con;
		}
	
		public DBConnection prepareQuery(String key) throws SQLException {
			_safeClose(pstm);
			pstm = con.prepareStatement(QueryUtil.get(key));
			return this;
		}
		
		public DBConnection setQueryParam(int idx, Object value) throws SQLException {
			if(pstm!=null) {
				pstm.setObject(idx, value);
			}
			return this;
		}
		
		public ResultSet executeQuery() throws SQLException {
			rs = pstm.executeQuery();
			return rs;
		}
		
		public int executeUpdate() throws SQLException {
			return pstm.executeUpdate();
		}
		
		@SuppressWarnings("unchecked")
		public <T extends BaseModel>T getNext(Class<T> schema) throws SQLException {
			return (T) getModelObject(rs, schema);	
		}
		
		public <T extends BaseModel> List<T> getAll(Class<T> schema) throws SQLException {
			ArrayList<T> list = new ArrayList<>();
			while(hasNext()) {
				list.add(getNext(schema));
			}
			return list;
		}
		
		public boolean hasNext() throws SQLException {
			return rs.next();
		}
		
		//TODO make it return to pool
		public void safeClose() {
			_safeClose(rs);
			_safeClose(pstm);
			_safeClose(con);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends BaseModel> T getModelObject(ResultSet rs, Class<? extends BaseModel> schema) throws SQLException {
		try {
			Method m = schema.getMethod("load", JSONObject.class);
			return (T) m.invoke(null, toJSON(rs));
		}catch(Exception e) {
			throw new SQLException(e);
		}
	}
	
	private static JSONObject toJSON(ResultSet rs) throws SQLException {
		JSONObject json = new JSONObject();
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			json.put(rs.getMetaData().getColumnName(i).toLowerCase(), rs.getObject(i));
		}
		return json;
	}
}
