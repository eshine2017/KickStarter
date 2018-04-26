package org.dbms.ks.models;
import static org.dbms.ks.models.ColumnConstants.OWNER_LOCATION_ID;
import static org.dbms.ks.models.ColumnConstants.OWNER_OID;
import static org.dbms.ks.models.ColumnConstants.OWNER_UID;
import static org.dbms.ks.models.ColumnConstants.OWNER_USER_NAME;

import java.util.ArrayList;
import java.util.List;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class Backer extends BaseModel{
	
	private Backer(JSONObject json) throws ValidationException {
		super(json);
	}
	
	@Override
	protected void validate() throws ValidationException {

	}
	
	public static Backer load(JSONObject json) throws ValidationException{
		return new Backer(json);
	}
	
	public static Backer fetch(int backerId) {
		return DBUtil.getFirst("get.backer", Backer.class, backerId);
	}
	
	// GETTERS AND SETTERS
	
	public int getUserID() {
		return get(OWNER_UID, -1);
	}

	public String getName() {
		return get(OWNER_USER_NAME);
	}
	
	public int getNumberOfBackings() {
		return get(OWNER_OID, -1);
	}
	
	Location location = null;
	public Location getLocation() {
		if(location == null){
			location = DBUtil.getFirst("get.location", Location.class, get(OWNER_LOCATION_ID, -1));
		}
		return location;
	}
	
	ArrayList<Backer> nearbyBackers = null;
	public List<Backer> getNearbyBackers() {
		if(nearbyBackers == null) {
			float lat = getLocation().getLatitude(), lng = getLocation().getLongitude();
			nearbyBackers = DBUtil.getAll("get.nearby.backers", Backer.class, lat, lat, lng, lat-1, lat + 1, getLocation().getCountryCode(), getUserID());
		}		
		return nearbyBackers;
	}
	
	ProfilePic profilePicture = null;
	public ProfilePic getProfilePicture() {
		if(profilePicture == null) {
			profilePicture = ProfilePic.fetch(getUserID());
		}
		return profilePicture;
	}
	
	ArrayList<Backer> similarBackers = null;
	public List<Backer> getSimilarBackers() {
		if(similarBackers == null) {
			similarBackers = DBUtil.getAll("get.similar.backers", Backer.class, getUserID(), getUserID());
		}
		return similarBackers;
	}
	
	ArrayList<Project> backedProjects = null;
	public List<Project> getProjects() {
		if(backedProjects == null) {
			backedProjects = DBUtil.getAll("get.backed.projects", Project.class, getUserID());
		}
		return backedProjects;
	}

	@Override
	protected void autoJoin() {
		joinMultiple(Location.class, ProfilePic.class);
	}
}
