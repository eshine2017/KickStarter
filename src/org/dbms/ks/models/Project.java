package org.dbms.ks.models;

import static org.dbms.ks.models.ColumnConstants.PROJ_BLURB;
import static org.dbms.ks.models.ColumnConstants.PROJ_CURRENCY;
import static org.dbms.ks.models.ColumnConstants.PROJ_DEADLINE;
import static org.dbms.ks.models.ColumnConstants.PROJ_GOAL;
import static org.dbms.ks.models.ColumnConstants.PROJ_ID;
import static org.dbms.ks.models.ColumnConstants.PROJ_LAUNCH_DATE;
import static org.dbms.ks.models.ColumnConstants.PROJ_LOCATION_ID;
import static org.dbms.ks.models.ColumnConstants.PROJ_MONEY_PLEDGED;
import static org.dbms.ks.models.ColumnConstants.PROJ_MONEY_PLEDGED_USD;
import static org.dbms.ks.models.ColumnConstants.PROJ_NAME;
import static org.dbms.ks.models.ColumnConstants.PROJ_OWNER_ID;
import static org.dbms.ks.models.ColumnConstants.PROJ_PHOTO;
import static org.dbms.ks.models.ColumnConstants.PROJ_STATUS;
import static org.dbms.ks.models.ColumnConstants.PROJ_SUBCATEGORY_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class Project extends BaseModel{
	private Project(JSONObject json) throws ValidationException{
		super(json);
		validate();
	}
	
	public static Project load(JSONObject json) throws ValidationException{
		return new Project(json);
	}
	
	public static Project fetch(int projectId) {
		return DBUtil.getFirst("get.project", Project.class, projectId);
	}
		
	@Override
	protected void validate() {

	}
	
	// GETTERS & SETTERS
	public int getID() {
		return get(PROJ_ID, -1);
	}
	
	public String getName() {
		return get(PROJ_NAME);
	}
	
	public String getBlurb() {
		return get(PROJ_BLURB);
	}
	
	public String getPhoto() {
		return get(PROJ_PHOTO);
	}
	
	public int getCurrency() {
		return get(PROJ_CURRENCY, 0);
	}
	
	@SuppressWarnings("deprecation")
	public Date getLaunchDate() {
		String date = get(PROJ_LAUNCH_DATE);
		return new Date(Date.parse(date));
	}

	@SuppressWarnings("deprecation")
	public Date getDeadline() {
		String date = get(PROJ_DEADLINE);
		return new Date(Date.parse(date));
	}
		
	public boolean isSuccessfull() {
		return get(PROJ_STATUS).equals("successfull");
	}
	
	public int getGoal() {
		return get(PROJ_GOAL, 0);
	}
	
	public int getPledged() {
		return get(PROJ_MONEY_PLEDGED, 0);
	}
	
	public int getPledgedUSD() {
		return get(PROJ_MONEY_PLEDGED_USD, 0);
	}
	
	Location location = null;
	public Location getLocation() {
		if(location == null){
			location = DBUtil.getFirst("get.location", Location.class, get(PROJ_LOCATION_ID, -1));
		}
		return location;
	}
	
	Owner owner = null;
	public Owner getOwner() {
		if(owner == null){
			owner = DBUtil.getFirst("get.owner", Owner.class, get(PROJ_OWNER_ID, -1));
		}
		return owner;
	}
	
	SubCategory subCategory = null;
	public SubCategory getSubCategory() {
		if(subCategory == null){
			subCategory = DBUtil.getFirst("get.subcategory", SubCategory.class, get(PROJ_SUBCATEGORY_ID, -1));
		}
		return subCategory;
	}
	
	ArrayList<Project> nearbyProjects = null;
	public List<Project> getNearbyProjects() {
		if(nearbyProjects == null) {
			float lat = getLocation().getLatitude(), lng = getLocation().getLongitude();
			nearbyProjects = DBUtil.getAll("get.nearby.projects", Project.class, lat, lat, lng, lat-1, lat + 1, getLocation().getCountryCode(), getID());
		}
		
		return nearbyProjects;
	}

	ArrayList<Project> similarProjects = null;
	public List<Project> getSimilarProjects() {
		if(similarProjects == null) {
			similarProjects = DBUtil.getAll("get.similar.projects", Project.class, getID(), getID());
		}
		return similarProjects;
	}

	ArrayList<Backer> backers = null;
	public ArrayList<Backer> getBackers() {
		if(backers == null) {
			backers = DBUtil.getAll("get.project.backers", Backer.class, getID());
		}
		return backers;
	}

	@Override
	protected void autoJoin() {
		joinMultiple(Owner.class, Location.class, SubCategory.class);
	}
}
