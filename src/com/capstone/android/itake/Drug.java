package com.capstone.android.itake;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Drug {

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DOSAGE = "dosage";
	private static final String JSON_PURPOSE = "purpose";
	private static final String JSON_PRESCRIBER = "prescriber";
	private static final String JSON_PHOTO = "photo";

	private UUID mId;
	private String mTitle;
	private String mDosage;
	private String mPurpose;
	private String mPrescriber;
	public boolean mCreated;
	private Photo mPhoto;

	public Drug() {
		mId = UUID.randomUUID();
	}

	public Drug(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mDosage = json.getString(JSON_DOSAGE);
		mPurpose = json.getString(JSON_PURPOSE);
		mPrescriber = json.getString(JSON_PRESCRIBER);
		if (json.has(JSON_PHOTO))
			mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
		
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_DOSAGE, mDosage);
		json.put(JSON_PURPOSE, mPurpose);
		json.put(JSON_PRESCRIBER, mPrescriber);
		if (mPhoto != null)
			json.put(JSON_PHOTO, mPhoto.toJSON());
		return json;
	}

//	public String databaseJSON = toJSON.toString();
	
//	@Override
//	public static String toString() {
//		return mTitle;
//	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getDosage() {
		return mDosage;
	}

	public void setDosage(String dosasge) {
		mDosage = dosasge;
	}
	
	public String getPurpose() {
        return mPurpose;
    }

    public void setPurpose(String purpose) {
        mPurpose = purpose;
    }

    public String getPrescriber() {
        return mPrescriber;
    }

    public void setPrescriber(String prescriber) {
        mPrescriber = prescriber;
    }
    
	public UUID getId() {
		return mId;
	}

	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo p) {
		mPhoto = p;
	}


}
