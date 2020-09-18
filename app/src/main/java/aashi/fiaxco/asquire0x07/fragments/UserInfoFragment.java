package aashi.fiaxco.asquire0x07.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import aashi.fiaxco.asquire0x07.R;
import aashi.fiaxco.asquire0x07.SurveyActivity;


public class UserInfoFragment extends Fragment {

	// the fragment initialization parameters
	public static final String NAME_PARAM = "paramName";
	public static final String AGE_PARAM = "paramAge";
	public static final String GENDER_PARAM = "paramGender";
	public static final String HEIGHT_PARAM = "paramHeight";
	public static final String WEIGHT_PARAM = "paramWeight";

	// parameters
	private String mName;
	private String mAge;
	private String mGender;
	private String mHeight;
	private String mWeight;
	private Context mContext;

	public UserInfoFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootUsrInfoFragment = inflater.inflate(R.layout.fragment_user_info, container, false);

		final EditText nameET = rootUsrInfoFragment.findViewById(R.id.edit_profile_name);
		final EditText ageET = rootUsrInfoFragment.findViewById(R.id.edit_profile_age);
		final EditText heightET = rootUsrInfoFragment.findViewById(R.id.edit_profile_height);
		final EditText weightET = rootUsrInfoFragment.findViewById(R.id.edit_profile_weight);
		final Spinner genderSPN = setupSpinner(rootUsrInfoFragment);
		final Button nextBtn = rootUsrInfoFragment.findViewById(R.id.userInfo_fragment_button);
		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mName = nameET.getText().toString();
				mAge = ageET.getText().toString();
				mHeight = heightET.getText().toString();
				mWeight = weightET.getText().toString();

				if (!checkForEmptyText()) {

					// disable inputs
					nameET.setEnabled(false);
					ageET.setEnabled(false);
					heightET.setEnabled(false);
					weightET.setEnabled(false);
					genderSPN.setEnabled(false);
					nextBtn.setEnabled(false);

					// send broadcast to survey active
					sendBroadcast();
				} else
					Toast.makeText(mContext, "Fill all the fields", Toast.LENGTH_SHORT).show();
			}
		});

		return rootUsrInfoFragment;
	}

	private void sendBroadcast() {
		Intent usrInfoIntent = new Intent(SurveyActivity.USER_INFO_BROADCAST);
		usrInfoIntent.putExtra(NAME_PARAM, mName);
		usrInfoIntent.putExtra(AGE_PARAM, mAge);
		usrInfoIntent.putExtra(GENDER_PARAM, mGender);
		usrInfoIntent.putExtra(HEIGHT_PARAM, mHeight);
		usrInfoIntent.putExtra(WEIGHT_PARAM, mWeight);

		LocalBroadcastManager.getInstance(mContext).sendBroadcast(usrInfoIntent);

	}

	// setup gender spinner
	private Spinner setupSpinner(View rootView) {

		Spinner genderSpinner = rootView.findViewById(R.id.spinner_gender);
		ArrayAdapter<CharSequence> genderSpinnerAdapter = ArrayAdapter.createFromResource(mContext,
				R.array.array_gender_options, android.R.layout.simple_spinner_item);

		genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		genderSpinner.setAdapter(genderSpinnerAdapter);

		genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				mGender = (String) parent.getItemAtPosition(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				mGender = "Other";
			}
		});

		return genderSpinner;
	}

	// returns true if empty
	private boolean checkForEmptyText() {
		return TextUtils.isEmpty(mName) ||
				TextUtils.isEmpty(mAge) ||
				TextUtils.isEmpty(mHeight) ||
				TextUtils.isEmpty(mWeight);
	}
}