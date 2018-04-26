package org.dbms.ks.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dbms.ks.models.Project;
import org.dbms.ks.models.Owner;
import org.dbms.ks.util.DBUtil;
import org.json.JSONArray;

@Path("/search")
public class SearchAPI {
	
	@Path("/projects/{key_words}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response searchProjects(@PathParam("key_words") String keyWords) {
		keyWords = preProcessKeys(keyWords);
		JSONArray response = new JSONArray();
		ArrayList<Project> projects = DBUtil.getAll("search.projects", Project.class, keyWords);
		for (Project project : projects)
			response.put(project._getRaw());
		return Response.ok(response.toString()).build();
	}
	
	@Path("/users/{key_words}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response searchUsers(@PathParam("key_words") String keyWords) {
		keyWords = preProcessKeys(keyWords);
		JSONArray response = new JSONArray();
		ArrayList<Owner> owners = DBUtil.getAll("search.users", Owner.class, keyWords);
		for (Owner owner : owners)
			response.put(owner._getRaw());
		return Response.ok(response.toString()).build();
	}
	
	/* Preprocess key words by separate each word with comma */
	private String preProcessKeys(String keyWords) {
		String res = "";
		keyWords = keyWords.trim();
		for (int i = 0; i < keyWords.length(); i++) {
			if (keyWords.charAt(i) == ' ') continue;
			int j = i;
			while (i < keyWords.length()) {
				i++;
				if (i == keyWords.length() || keyWords.charAt(i) == ' ')
					break;
			}
			if (res.length() != 0) res += ",";
			res += keyWords.substring(j, i);
		}
		return res;
	}
	
}
