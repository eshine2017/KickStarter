package org.dbms.ks.models;

public class ColumnConstants {
	// Empty Value
	public static final String EMPTY_VALUE = "--";
	
	// Location 
	public static final String LOC_ID = "location_id";
	public static final String LOC_NAME = "name";
	public static final String LOC_TYPE = "type";
	public static final String LOC_SLUG = "slug";
	public static final String LOC_COUNTRY_CODE = "country";
	public static final String LOC_DISPLAYABLE_NAME = "displayable_name";
	public static final String LOC_LOCALIZED_NAME = "localized_name";
	public static final String LOC_SHORT_NAME = "short_name";
	public static final String LOC_LATITUDE = "latitude";
	public static final String LOC_LONGITUDE = "longitude";
	
	
	// Project
	public static final String PROJ_ID = "project_id";
	public static final String PROJ_BLURB = "blurb";
	public static final String PROJ_NAME = "name";
	public static final String PROJ_PHOTO = "photo";
	public static final String PROJ_CURRENCY = "currency";
	public static final String PROJ_GOAL = "goal";
	public static final String PROJ_LAUNCH_DATE = "launch_date";
	public static final String PROJ_DEADLINE = "deadline";
	public static final String PROJ_STATUS = "status";
	public static final String PROJ_MONEY_PLEDGED = "money_pledged";
	public static final String PROJ_MONEY_PLEDGED_USD = "money_pledged_usd";
	public static final String PROJ_OWNER_ID = "owner_id";
	public static final String PROJ_LOCATION_ID= "location_id";
	public static final String PROJ_SUBCATEGORY_ID = "subcategory_id";


	// Owners
	public static final String OWNER_UID = "user_id";
	public static final String OWNER_OID = "creator_id";
	public static final String IS_REGISTERED = "is_registered";
	public static final String CHOSEN_CURRENCY = "chosen_currency";
	public static final String OWNER_USER_NAME = "user_name";
	public static final String OWNER_LOCATION_ID = "location_id";
	
	// Backers
	public static final String BACKER_UID = "user_id";
	public static final String BACKER_USER_NAME = "user_name";
	public static final String BACKER_NO_OF_BACKINGS = "NO_OF_BACKINGS";
	public static final String BACKER_LOCATION_ID = "location_id";

	// Main Category
	public static final String MAINCAT_ID = "category_id";
	public static final String MAINCAT_NAME = "name";
	public static final String MAINCAT_COLOR = "color";
	public static final String MAINCAT_POSITION = "position";
	
	// Sub Category
	public static final String SUBCAT_ID = "subcategory_id";
	public static final String SUBCAT_NAME = "name";
	public static final String SUBCAT_MAINCAT_ID = "category_id";
	
	// Profile Pictures
	public static final String PROFILE_PIC_UID = "user_id";
	public static final String PROFILE_PIC_URL = "url";
	
};