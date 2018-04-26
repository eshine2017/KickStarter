package org.dbms.ks.models;
import static org.dbms.ks.models.ColumnConstants.OWNER_LOCATION_ID;
import static org.dbms.ks.models.ColumnConstants.OWNER_OID;
import static org.dbms.ks.models.ColumnConstants.OWNER_UID;
import static org.dbms.ks.models.ColumnConstants.OWNER_USER_NAME;

import java.util.ArrayList;
import java.util.List;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class Owner extends BaseModel{
	
	private Owner(JSONObject json) throws ValidationException {
		super(json);
	}
	
	@Override
	protected void validate() throws ValidationException {
		
	}
	
	public static Owner load(JSONObject json) throws ValidationException{
		return new Owner(json);
	}
	
	public static Owner fetch(int ownerId) {
		return DBUtil.getFirst("get.owner", Owner.class, ownerId);
	}
	
	public static Owner _fetch(int uid) {
		return DBUtil.getFirst("get.owner.byuid", Owner.class, uid);
	}

	// GETTERS AND SETTERS
	
	public int getUserID() {
		return get(OWNER_UID, -1);
	}

	public int getOwnerID() {
		return get(OWNER_OID, -1);
	}

	public String getName() {
		return get(OWNER_USER_NAME);
	}
	
	Location location = null;
	public Location getLocation() {
		if(location == null){
			location = DBUtil.getFirst("get.location", Location.class, get(OWNER_LOCATION_ID, -1));
		}
		return location;
	}
	
	ArrayList<Owner> nearbyOwners = null;
	public List<Owner> getNearbyOwners() {
		if(nearbyOwners == null) {
			float lat = getLocation().getLatitude(), lng = getLocation().getLongitude();
			nearbyOwners = DBUtil.getAll("get.nearby.creators", Owner.class, lat, lat, lng, lat-1, lat + 1, getLocation().getCountryCode(), getUserID());
		}		
		return nearbyOwners;
	}
	
	ProfilePic profilePicture = null;
	public ProfilePic getProfilePicture() {
		if(profilePicture == null) {
			profilePicture = ProfilePic.fetch(getUserID());
		}
		return profilePicture;
	}

	ArrayList<Owner> similarOwners = null;
	public List<Owner> getSimilarOwners() {
		if(similarOwners == null) {
			similarOwners = DBUtil.getAll("get.similar.creators", Owner.class, getUserID(), getUserID());
		}
		return similarOwners;
	}
	
	ArrayList<Project> createdProjects = null;
	public List<Project> getProjects() {
		if(createdProjects == null) {
			createdProjects = DBUtil.getAll("get.created.projects", Project.class, getOwnerID());
		}
		return createdProjects;
	}

	@Override
	protected void autoJoin() {
		joinMultiple(Location.class, ProfilePic.class);
	}
}
