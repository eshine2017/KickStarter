package org.dbms.ks.models;

import static org.dbms.ks.models.ColumnConstants.*;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class MainCategory extends BaseModel {
	private MainCategory(JSONObject json) throws ValidationException {
		super(json);
	}
	
	@Override
	protected void validate() throws ValidationException {
		
	}
	
	public static MainCategory load(JSONObject json) throws ValidationException{
		return new MainCategory(json);
	}
	
	public static MainCategory fetch(int catId) {
		return DBUtil.getFirst("get.maincategory", MainCategory.class, catId);
	}

	
	// GETTERS AND SETTERS
	
	public int getID() {
		return get(MAINCAT_ID, -1);
	}

	public String getName() {
		return get(MAINCAT_NAME);
	}

	public int getColor() {
		return get(MAINCAT_COLOR, -1);
	}
	
	public int getPosition() {
		return get(MAINCAT_POSITION, -1);
	}

	@Override
	protected void autoJoin() {
		// Leaf Node		
	}
	
}
