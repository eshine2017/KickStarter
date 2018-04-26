package org.dbms.ks.models;

import static org.dbms.ks.models.ColumnConstants.*;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class SubCategory extends BaseModel {
	public SubCategory(JSONObject json) throws ValidationException {
		super(json);
	}
	
	@Override
	protected void validate() throws ValidationException {
		
	}
	
	public static SubCategory load(JSONObject json) throws ValidationException{
		return new SubCategory(json);
	}
	
	public static SubCategory fetch(int catId) {
		return DBUtil.getFirst("get.subcategory", SubCategory.class, catId);
	}

	
	// GETTERS AND SETTERS
	
	public int getID() {
		return get(SUBCAT_ID, -1);
	}

	public String getName() {
		return get(SUBCAT_NAME);
	}
	
	MainCategory mainCategory = null;
	public MainCategory getMainCategory() {
		if(mainCategory==null) {
			mainCategory = DBUtil.getFirst("get.maincategory", MainCategory.class, get(SUBCAT_MAINCAT_ID, -1));
		}
		return mainCategory;
	}

	@Override
	protected void autoJoin() {
		joinMultiple(MainCategory.class);
	}
}
