package com.capstone.android.itake;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class DrugFragment extends Fragment {
	public static final String EXTRA_DRUG_ID = "itake.DRUG_ID";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 1;
	private static final int REQUEST_CONTACT = 2;

	Drug mDrug;
	EditText mEditTitleField;
	EditText mEditDosageField;
	EditText mEditPurposeField;
	EditText mEditPrescriberField;
	TextView mTitleField;
	TextView mDosageField;
	TextView mPurposeField;
	TextView mPrescriberField;
	Button mEditButton;
	Button mSaveButton;
	Button mAlarmButton;
	CheckBox mCheckBox;
	ImageButton mPhotoButton;
	ImageView mPhotoView;

	public static DrugFragment newInstance(UUID drugId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DRUG_ID, drugId);

		DrugFragment fragment = new DrugFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UUID drugId = (UUID) getArguments().getSerializable(EXTRA_DRUG_ID);
		mDrug = DrugBottle.get(getActivity()).getDrug(drugId);

		setHasOptionsMenu(true);
	}

	@Override
	@TargetApi(11)
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup parent, Bundle savedInstanceState) {

		View v = null;
		if (!mDrug.mCreated) {
			v = inflater.inflate(R.layout.fragment_drug, parent, false);
		} else {
			// If created medication not editable
			v = inflater.inflate(R.layout.created_fragment_drug, parent, false);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		if (!mDrug.mCreated) {

			mEditTitleField = (EditText) v.findViewById(R.id.drug_title);
			mEditTitleField.setText(mDrug.getTitle());
			mEditTitleField.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence c, int start,
						int before, int count) {
					mDrug.setTitle(c.toString());
				}

				public void beforeTextChanged(CharSequence c, int start,
						int count, int after) {
					// this space intentionally left blank
				}

				public void afterTextChanged(Editable c) {
				}
			});

			mEditDosageField = (EditText) v.findViewById(R.id.drug_dosage);
			mEditDosageField.setText(mDrug.getDosage());
			mEditDosageField.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence c, int start,
						int before, int count) {
					mDrug.setDosage(c.toString());
				}

				public void beforeTextChanged(CharSequence c, int start,
						int count, int after) {
					// this space intentionally left blank
				}

				public void afterTextChanged(Editable c) {
					// this one too
				}
			});

			mEditPurposeField = (EditText) v.findViewById(R.id.drug_purpose);
			mEditPurposeField.setText(mDrug.getPurpose());
			mEditPurposeField.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence c, int start,
						int before, int count) {
					mDrug.setPurpose(c.toString());
				}

				public void beforeTextChanged(CharSequence c, int start,
						int count, int after) {
					// this space intentionally left blank
				}

				public void afterTextChanged(Editable c) {
					// this one too
				}
			});

			mEditPrescriberField = (EditText) v
					.findViewById(R.id.drug_prescriber);
			mEditPrescriberField.setText(mDrug.getPrescriber());
			mEditPrescriberField.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence c, int start,
						int before, int count) {
					mDrug.setPrescriber(c.toString());
				}

				public void beforeTextChanged(CharSequence c, int start,
						int count, int after) {
					// this space intentionally left blank
				}

				public void afterTextChanged(Editable c) {
					// this one too
				}
			});

		} else {

			mTitleField = (TextView) v.findViewById(R.id.drug_title);
			mTitleField.setText(mDrug.getTitle());
			mDosageField = (TextView) v.findViewById(R.id.drug_dosage);
			mDosageField.setText(mDrug.getDosage());
			mPurposeField = (TextView) v.findViewById(R.id.drug_purpose);
			mPurposeField.setText(mDrug.getPurpose());
			mPrescriberField = (TextView) v.findViewById(R.id.drug_prescriber);
			mPrescriberField.setText(mDrug.getPrescriber());
		}

		if (mDrug.mCreated) {
			mEditButton = (Button) v.findViewById(R.id.drug_edit);
			mEditButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mDrug.mCreated = false;
					Intent i = new Intent(getActivity(),
							DrugPagerActivity.class);
					i.putExtra(DrugFragment.EXTRA_DRUG_ID, mDrug.getId());
					startActivityForResult(i, 0);
				}
			});
		} else {
			mSaveButton = (Button) v.findViewById(R.id.drug_save);
			mSaveButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mDrug.mCreated = true;
					Toast.makeText(getActivity(), "Medication saved!",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		if (!mDrug.mCreated) {
			mPhotoButton = (ImageButton) v.findViewById(R.id.drug_imageButton);
			mPhotoButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// launch the camera activity
					Intent i = new Intent(getActivity(),
							DrugCameraActivity.class);
					startActivityForResult(i, REQUEST_PHOTO);
				}
			});
			mAlarmButton = (Button) v.findViewById(R.id.alarm_button);
			mAlarmButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), AlarmTimer.class);
					AlarmTimer.DRUG_ALARM_ID =  mDrug.getId().toString();
					startActivity(i);
				}
			});

		}

		// if camera is not available, disable camera functionality
		PackageManager pm = getActivity().getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				&& !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
			mPhotoButton.setEnabled(false);
		}

		mPhotoView = (ImageView) v.findViewById(R.id.drug_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Photo p = mDrug.getPhoto();
				if (p == null)
					return;

				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename())
						.getAbsolutePath();
				ImageFragment.createInstance(path).show(fm, DIALOG_IMAGE);
			}
		});


		return v;
	}

	private void showPhoto() {
		// (re)set the image button's image based on our photo
		Photo p = mDrug.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename())
					.getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);

		}
		mPhotoView.setImageDrawable(b);
	}

	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}

	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
		} else if (requestCode == REQUEST_PHOTO) {
			// create a new Photo object and attach it to the drug
			String filename = data
					.getStringExtra(DrugCameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				Photo p = new Photo(filename);
				mDrug.setPhoto(p);
				showPhoto();
			}
		} else if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();
			String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
			Cursor c = getActivity().getContentResolver().query(contactUri,
					queryFields, null, null, null);

			if (c.getCount() == 0) {
				c.close();
				return;
			}

		}
	}

	@Override
	public void onPause() {
		super.onPause();
		DrugBottle.get(getActivity()).saveDrugs();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(getActivity());
			mDrug.mCreated = true;

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
