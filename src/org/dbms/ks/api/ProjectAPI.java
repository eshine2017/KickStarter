package org.dbms.ks.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dbms.ks.models.Backer;
import org.dbms.ks.models.Location;
import org.dbms.ks.models.Owner;
import org.dbms.ks.models.Project;
import org.dbms.ks.util.DBUtil;
import org.dbms.ks.util.DBUtil.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/project")
public class ProjectAPI {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getProject(@PathParam("id") int id) {
		return Response.ok(Project.fetch(id)._getRaw().toString()).build();
	}
	
	JSONObject attr = new JSONObject("{ fill: \"#ff5722\", stroke: \"#ced8d0\"}");
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/moneymovement")
	public Response getProjectMoneyMovement(@PathParam("id") int id) {
		JSONObject resp = new JSONObject();
		DBConnection con = null;
		try {
			Project project = Project.fetch(id);
			con = DBUtil.getConnection();
			con.prepareQuery("get.moneymovement")
				.setQueryParam(1, id)
				.executeQuery();
			JSONObject plots = new JSONObject(),
					   links = new JSONObject();
			Location baseLocation = project.getLocation();
			JSONObject tooltip1 = new JSONObject();
			tooltip1.put("content", baseLocation.getDisplayableName());
			baseLocation._getRaw().put("text", tooltip1);
			baseLocation._getRaw().put("attrs", attr);
			Location loc;
			plots.put(baseLocation.getSlug(), baseLocation._getRaw());
			while(con.hasNext()) {
				loc = con.getNext(Location.class);
				if(!plots.has(loc.getSlug())) {
					JSONObject tooltip = new JSONObject();
					tooltip.put("content", loc.getDisplayableName());
					loc._getRaw().put("text", tooltip);
					plots.put(loc.getSlug(), loc._getRaw());
				}
				JSONObject link = new JSONObject();
				JSONArray latlong = new JSONArray();
				latlong.put(baseLocation._getRaw());
				latlong.put(loc._getRaw());
//				link.put("size", 0);
				link.put("between", latlong);
				links.put(baseLocation.getSlug() + loc.getSlug(), link);
			}
			resp.put("plots", plots);
			resp.put("links", links);
		} catch(Exception e) {
			//LOG
			e.printStackTrace();
		} finally {
			if(con!=null) con.safeClose();
		}
		return Response.ok(resp.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/nearby")
	public Response getNearbyProjects(@PathParam("id") int id) {
		JSONArray nearbyProjects = new JSONArray();
		try {
			Project project = Project.fetch(id);
			for(Project nearby : project.getNearbyProjects()) {
				nearbyProjects.put(nearby._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(nearbyProjects.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/similar")
	public Response getSimilarProjects(@PathParam("id") int id) {
		JSONArray similarProjects = new JSONArray();
		try {
			Project project = Project.fetch(id);
			for(Project similar : project.getSimilarProjects()) {
				similarProjects.put(similar._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(similarProjects.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/backers")
	public Response getBackers(@PathParam("id") int id) {
		JSONArray backers = new JSONArray();
		try {
			Project project = Project.fetch(id);
			for(Backer backer : project.getBackers()) {
				backers.put(backer._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(backers.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/backerlocations")
	public Response getProjectBackerLocations(@PathParam("id") int id) {
		JSONArray response = new JSONArray();
		for(Project p : DBUtil.getAll("get.project.backerlocations", Project.class, id)) {
			response.put(p._getRaw());
		}
		return Response.ok(response.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/owners")
	public Response getOwners(@PathParam("id") int id) {
		JSONArray owners = new JSONArray();
			Project project = Project.fetch(id);
			Owner owner = project.getOwner();
				owners.put(owner._getRaw());
			
		return Response.ok(owners.toString()).build();
	}
}
