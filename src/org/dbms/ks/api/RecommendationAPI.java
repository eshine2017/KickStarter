package org.dbms.ks.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dbms.ks.models.Project;
import org.dbms.ks.util.DBUtil;
import org.dbms.ks.util.DBUtil.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/recommend")
public class RecommendationAPI {
	
	@Path("/projects")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getProjectRecommendations() {
		JSONArray response = new JSONArray();
		ArrayList<Project> projects =  DBUtil.getAll("recommend.top_projects", Project.class);
		for(Project p : projects) {
			response.put(p._getRaw());
		}
		return Response.ok(response.toString()).build();
	}
	
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getProjectCategories() {
		JSONArray response = new JSONArray();
		DBConnection con = null;
		try {
			con = DBUtil.getConnection();
			con.prepareQuery("recommend.all_categories").executeQuery();
			Project project;
			while(con.hasNext()){
				project = con.getNext(Project.class);
				response.put(project._getRaw());
			}
		} catch(Exception e) {
			//LOG
			e.printStackTrace();
		} finally {
			if(con!=null) con.safeClose();
		}
		return Response.ok(response.toString()).build();
	}
	
	@Path("/category/{cat_id}/topfive")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getProjectInCategory(@PathParam("cat_id") int catId) {
		JSONObject response = new JSONObject();
		DBConnection con = null;
		try {
			con = DBUtil.getConnection();
			con.prepareQuery("recommend.proj_maincategory")
			   .setQueryParam(1, catId)
			   .executeQuery();

			Project project = null;
			// Adding the top project
			if(con.hasNext()) {
				project = con.getNext(Project.class);
				response = project._getRaw();
			}
			
			// Adding the others
			JSONArray topFive = new JSONArray();
			while(con.hasNext()){
				project = con.getNext(Project.class);
				topFive.put(project._getRaw());
			}
			
			response.put("topfive", topFive);
		} catch(Exception e) {
			//LOG
			e.printStackTrace();
		} finally {
			if(con!=null) con.safeClose();
		}
		return Response.ok(response.toString()).build();
	}
}
