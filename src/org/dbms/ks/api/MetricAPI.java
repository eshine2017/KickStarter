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
import org.json.JSONArray;

@Path("/metric")
public class MetricAPI {
	
	@Path("/rank/{rank_by}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response searchProjects(@PathParam("rank_by") String rankBy) {
		JSONArray response = new JSONArray();
		ArrayList<Project> projects = DBUtil.getAll("metric." + rankBy, Project.class);
		for (Project project : projects)
			response.put(project._getRaw());
		return Response.ok(response.toString()).build();
	}

	
}
