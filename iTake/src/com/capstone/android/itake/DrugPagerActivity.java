package com.capstone.android.itake;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

public class DrugPagerActivity extends FragmentActivity {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Drug> drugs = DrugBottle.get(this).getDrugs();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return drugs.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID drugId =  drugs.get(pos).getId();
                return DrugFragment.newInstance(drugId);
            }
        }); 

        UUID drugId = (UUID)getIntent().getSerializableExtra(DrugFragment.EXTRA_DRUG_ID);
        for (int i = 0; i < drugs.size(); i++) {
            if (drugs.get(i).getId().equals(drugId)) {
                mViewPager.setCurrentItem(i);
                break;
            } 
        }
    }
}
