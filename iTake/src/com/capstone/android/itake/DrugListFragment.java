package com.capstone.android.itake;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class DrugListFragment extends ListFragment {
    private ArrayList<Drug> mDrugs;
    private boolean mSubtitleVisible;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.drugs_title);
        mDrugs = DrugBottle.get(getActivity()).getDrugs();
        DrugAdapter adapter = new DrugAdapter(mDrugs);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {   
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }
        
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.drug_list_item_context, menu);
                    return true;
                }
            
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                        long id, boolean checked) {
                }
            
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_drug:
                            DrugAdapter adapter = (DrugAdapter)getListAdapter();
                            DrugBottle drugLab = DrugBottle.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    drugLab.deleteDrug(adapter.getItem(i));
                                }
                            }
                            mode.finish(); 
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
          
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
            
        }

        return v;
    }
    
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // get the Drug from the adapter
//        Drug c = ((DrugAdapter)getListAdapter()).getItem(position);
//        // start an instance of DrugPagerActivity
//        Intent i = new Intent(getActivity(), DrugPagerActivity.class);
//        i.putExtra(DrugFragment.EXTRA_DRUG_ID, c.getId());
//        startActivityForResult(i, 0);
//    }
//    
    
    
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the Drug from the adapter
        Drug c = ((DrugAdapter)getListAdapter()).getItem(position);
        // start an instance of DrugPagerActivity
        Intent i = new Intent(getActivity(), DrugPagerActivity.class);
        i.putExtra(DrugFragment.EXTRA_DRUG_ID, c.getId());
        startActivityForResult(i, 0);
    }
    
    
    
    
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((DrugAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_drug_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_drug:
                Drug drug = new Drug();
                DrugBottle.get(getActivity()).addDrug(drug);
                Intent i = new Intent(getActivity(), DrugActivity.class);
                i.putExtra(DrugFragment.EXTRA_DRUG_ID, drug.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
            	if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
            	}  else {
            		getActivity().getActionBar().setSubtitle(null);
            		 mSubtitleVisible = false;
            		item.setTitle(R.string.show_subtitle);
            	}
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.drug_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        DrugAdapter adapter = (DrugAdapter)getListAdapter();
        Drug drug = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_drug:
                DrugBottle.get(getActivity()).deleteDrug(drug);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private class DrugAdapter extends ArrayAdapter<Drug> {
        public DrugAdapter(ArrayList<Drug> drugs) {
            super(getActivity(), android.R.layout.simple_list_item_1, drugs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_drug, null);
            }

            // configure the view for this Drug
            Drug c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.drug_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            
           return convertView;
        }
    }
}

