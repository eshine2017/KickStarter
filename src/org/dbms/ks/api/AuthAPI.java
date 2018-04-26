package org.dbms.ks.api;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.dbms.ks.util.SecurityUtil;

@Path("/login")
public class AuthAPI {
	
	private Response redirectToLogin = Response.seeOther(URI.create("../login.jsp")).build();
	
	@Context UriInfo uri;
	
	@POST
	@Path("/")
	public Response doLogin(@FormParam("username") String username, @FormParam("password") String password) {
		Response res = redirectToLogin;
		try {
			NewCookie authCookie = SecurityUtil.authenticate(username, password, uri.getBaseUri().getHost());
			if(authCookie!=null) {
				res = Response.seeOther(URI.create("../index.jsp"))
							  .header("Access-Control-Allow-Credentials", "true")
							  .cookie(authCookie)
							  .build();
			}
		} catch(Exception e) {
			
		}
		return res;
	}
	
	
	
}
