package org.dbms.ks.api;

import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONObject;

@Path("location")
public class LocationAPI {
	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getLocation(@PathParam("id") int locationID) {
		return Response.ok(Location.fetch(locationID).toString()).build();
	}
	
	@Path("/{id}/backers")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getOwnersByLocation(@PathParam("id") int locationID) {
		List<Backer> nearbyBackers = DBUtil.getAll("get.backers.bylocation", Backer.class, locationID);
		JSONArray response = new JSONArray();
		for(Backer backer : nearbyBackers) {
			response.put(backer._getRaw());
		}
		return Response.ok(response.toString()).build();
	}
	
	@Path("/{id}/creators")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getCreatorsByLocation(@PathParam("id") int locationID) {
		List<Owner> nearbyOwners = DBUtil.getAll("get.owners.bylocation", Owner.class, locationID);
		JSONArray response = new JSONArray();
		for(Owner owner : nearbyOwners) {
			response.put(owner._getRaw());
		}
		return Response.ok(response.toString()).build();
	}
	
	@Path("/{id}/projects")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getProjectsByLocation(@PathParam("id") int locationID) {
		List<Project> nearbyProjects = DBUtil.getAll("get.project.bylocation", Project.class, locationID);
		JSONArray response = new JSONArray();
		for(Project proj : nearbyProjects) {
			response.put(proj._getRaw());
		}
		return Response.ok(response.toString()).build();
	}
	
	@Path("/{id}/moneymovement")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getMoneyMovement(@PathParam("id") int locationID) {
		List<Location> moneyMovement = DBUtil.getAll("get.moneymovement.location", Location.class, locationID);
		JSONArray toArray = new JSONArray();
		for(Location loc : moneyMovement) {
			toArray.put(loc._getRaw());
		}
		JSONObject response = new JSONObject();
		response.put("from", Location.fetch(locationID)._getRaw());
		response.put("links", toArray);
		return Response.ok(response.toString()).build();
	}
}
