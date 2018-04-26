package org.dbms.ks.models;
import static org.dbms.ks.models.ColumnConstants.*;

import org.dbms.ks.util.DBUtil;
import org.json.JSONObject;

public class ProfilePic extends BaseModel{
	
	private static ProfilePic defaultProfilePic = null;
	
	private ProfilePic(JSONObject json) throws ValidationException {
		super(json);
	}
	
	@Override
	protected void validate() throws ValidationException {
		
	}
	
	public static ProfilePic load(JSONObject json) throws ValidationException{
		return new ProfilePic(json);
	}
	
	public static ProfilePic fetch(int ownerId) {
		ProfilePic pic = DBUtil.getFirst("get.profilepic", ProfilePic.class, ownerId);
		pic = pic == null ? getDefaultProfilePic() : pic;
		return pic;
	}

	
	// GETTERS AND SETTERS
	
	private static ProfilePic getDefaultProfilePic() {
		if(defaultProfilePic == null) {
			try {
				defaultProfilePic = new ProfilePic(new JSONObject("{\"url\":\"/dbms_ks/static/images/glyphicons/png/target.jpg\" }"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return defaultProfilePic;
	}

	public int getUserID() {
		return get(PROFILE_PIC_UID, -1);
	}

	public String getPhotoURL() {
		return get(PROFILE_PIC_URL);
	}

	@Override
	protected void autoJoin() {
		// Leaf Node		
	}
}
