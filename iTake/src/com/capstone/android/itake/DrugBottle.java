package com.capstone.android.itake;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

import android.util.Log;

public class DrugBottle {
    private static final String TAG = "DrugBottle";
    private static final String FILENAME = "drugs.json";

    private ArrayList<Drug> mDrugs;
    private itakeJSONSerializer mSerializer;

    private static DrugBottle sDrugBottle;
    private Context mAppContext;

    private DrugBottle(Context appContext) {
        mAppContext = appContext;
        mSerializer = new itakeJSONSerializer(mAppContext, FILENAME);

        try {
            mDrugs = mSerializer.loadDrugs();
        } catch (Exception e) {
            mDrugs = new ArrayList<Drug>();
            Log.e(TAG, "Error loading drugs: ", e);
        }
    }

    public static DrugBottle get(Context c) {
        if (sDrugBottle == null) {
            sDrugBottle = new DrugBottle(c.getApplicationContext());
        }
        return sDrugBottle;
    }

    public Drug getDrug(UUID id) {
        for (Drug c : mDrugs) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public void addDrug(Drug c) {
        mDrugs.add(c);
        saveDrugs();
    }

    public ArrayList<Drug> getDrugs() {
        return mDrugs;
    }

    public void deleteDrug(Drug c) {
        mDrugs.remove(c);
        saveDrugs();
    }

    public boolean saveDrugs() {
        try {
            mSerializer.saveDrugs(mDrugs);
            Log.d(TAG, "drugs saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving drugs: " + e);
            return false;
        }
    }
}

