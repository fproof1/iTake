package com.capstone.android.itake;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class DrugActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        UUID drugId = (UUID)getIntent()
            .getSerializableExtra(DrugFragment.EXTRA_DRUG_ID);
        return DrugFragment.newInstance(drugId);
    }
}
