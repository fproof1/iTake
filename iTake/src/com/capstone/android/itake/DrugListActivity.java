package com.capstone.android.itake;

import android.support.v4.app.Fragment;

public class DrugListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DrugListFragment();
    }
}
