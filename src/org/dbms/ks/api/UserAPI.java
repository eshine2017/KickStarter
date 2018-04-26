package org.dbms.ks.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dbms.ks.models.Backer;
import org.dbms.ks.models.Owner;
import org.dbms.ks.models.Project;
import org.json.JSONArray;

@Path("/user")
public class UserAPI {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/creator/{id}")
	public Response getCreator(@PathParam("id") int id) {
		return Response.ok(fetchOwner(id)._getRaw().toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/backer/{id}")
	public Response getBacker(@PathParam("id") int id) {
		return Response.ok(Backer.fetch(id)._getRaw().toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/creator/{id}/projects")
	public Response getCreatedProjects(@PathParam("id") int id) {
		JSONArray createdProjects = new JSONArray();
		try {
			Owner owner = fetchOwner(id);
			for(Project project : owner.getProjects()) {
				createdProjects.put(project._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(createdProjects.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/backer/{id}/projects")
	public Response getBackedProjects(@PathParam("id") int id) {
		JSONArray backedProjects = new JSONArray();
		try {
			Backer backer = Backer.fetch(id);
			for(Project project : backer.getProjects()) {
				backedProjects.put(project._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(backedProjects.toString()).build();	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/creator/{id}/nearby")
	public Response getNearbyCreators(@PathParam("id") int id) {
		JSONArray nearbyCreators = new JSONArray();
		try {
			Owner owner = Owner.fetch(id);
			for(Owner nearby : owner.getNearbyOwners()) {
				nearbyCreators.put(nearby._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(nearbyCreators.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/creator/{id}/similar")
	public Response getSimilarCreators(@PathParam("id") int id) {
		JSONArray similarOwners = new JSONArray();
		try {
			Owner owner = Owner.fetch(id);
			for(Owner similar : owner.getSimilarOwners()) {
				similarOwners.put(similar._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(similarOwners.toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/backer/{id}/nearby")
	public Response getNearbyBackers(@PathParam("id") int id) {
		JSONArray nearbyBackers = new JSONArray();
		try {
			Backer backer = Backer.fetch(id);
			for(Backer nearby : backer.getNearbyBackers()) {
				nearbyBackers.put(nearby._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(nearbyBackers.toString()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/backer/{id}/similar")
	public Response getSimilarBackers(@PathParam("id") int id) {
		JSONArray similarBackers = new JSONArray();
		try {
			Backer backer = Backer.fetch(id);
			for(Backer similar : backer.getSimilarBackers()) {
				similarBackers.put(similar._getRaw());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Response.ok(similarBackers.toString()).build();
	}
	
	private static Owner fetchOwner(int uidOrOid) {
		Owner owner = Owner.fetch(uidOrOid);
		if(owner == null) {
			owner = Owner._fetch(uidOrOid);
		}
		return owner;
	}
}
