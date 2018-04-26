package org.dbms.ks.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.NewCookie;

import org.apache.tomcat.util.buf.HexUtils;
import org.dbms.ks.util.DBUtil.DBConnection;

public class SecurityUtil {
	public static String COOKIE_DOMAIN = "localhost";
	
	// The only unauthenticated URL is the login url so excluding by default
	@SuppressWarnings("serial")
	private static ArrayList<String> EXCLUDED_SERVLETS = new ArrayList<String>() { {
		add("/login.jsp");
		add("/api/login");
	}};
	
	public static boolean isAuthenticated(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		if(EXCLUDED_SERVLETS.contains(req.getServletPath()) || EXCLUDED_SERVLETS.contains(req.getServletPath() + req.getPathInfo())) {
			return true;
		}	
		return isAuthenticated(req);
	}
	

	//TODO authenticate from DB
	public static boolean isAuthenticated(HttpServletRequest request) {
		String cookie = getCookie(request, "_KSID");
		DBConnection con = null;
		try {
			con = DBUtil.getConnection();
			ResultSet rs = con.prepareQuery("auth.user")
							  .setQueryParam(1, cookie)
							  .executeQuery();
			return rs.next();
		} catch(Exception e) {
			//TODO log
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con.safeClose();
			}
		}
		return false;
	}
	
	private static String getCookie(HttpServletRequest request, String key) {
		if(request.getCookies() != null) {
			for(Cookie c : request.getCookies()) {
				if(c.getName().equals(key)) {
					return c.getValue();
				}
			}
		}
		return null;
	}


	public static NewCookie generateToken(int uid, String cookieDomain) throws Exception {
		DBConnection con = DBUtil.getConnection();
		try {
			byte[] bytes = new byte[100];
			SecureRandom.getInstance("sha1prng").nextBytes(bytes);
			String token = HexUtils.toHexString(bytes);
			int rowCnt = con.prepareQuery("insert.token")
							.setQueryParam(1, token)
							.setQueryParam(2, uid)
							.executeUpdate();
			if(rowCnt == 1) {
				return new NewCookie(new javax.ws.rs.core.Cookie("_KSID", token,"/", cookieDomain, 10000));
			}
		} finally {
			con.safeClose();
		}
		return null;
	}

	public static NewCookie authenticate(String username, String password, String cookieDomain) throws Exception{
		DBConnection con = DBUtil.getConnection();
		NewCookie authCookie = null;
		try {
			String passwordHash = HexUtils.toHexString(MessageDigest.getInstance("SHA-256").digest(password.getBytes()));
			ResultSet rs = con.prepareQuery("verify.password")
							  .setQueryParam(1, username)
							  .setQueryParam(2, passwordHash)
							  .executeQuery();
			if(rs.next()) {
				authCookie = generateToken(rs.getInt(1), cookieDomain);
			}
		}finally {
			con.safeClose();
		}
		return authCookie;
	}
}
