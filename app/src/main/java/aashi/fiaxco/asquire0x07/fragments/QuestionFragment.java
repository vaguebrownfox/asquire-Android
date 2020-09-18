package aashi.fiaxco.asquire0x07.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import aashi.fiaxco.asquire0x07.R;
import aashi.fiaxco.asquire0x07.SurveyActivity;
import aashi.fiaxco.asquire0x07.data.Question;


public class QuestionFragment extends Fragment {

	// the fragment initialization parameters
	public static final String QUES_PARAM = "param1";
	public static final String OPTN_PARAM = "param2";
	public static final String QNO_PARAM = "param3";
	public static final String NQ_PARAM = "param4";
	public static final String ANSWER = "answer212";

	// Types of parameters
	private String mQuestion;
	private String[] mOptions;
	private int[] mNQns;
	private int mQno;
	private Context mContext;

	public QuestionFragment() {
		// Required empty public constructor
	}

	/**
	 * @param question Parameter 1.
	 * @return A new instance of fragment QuestionFragment.
	 */
	public static QuestionFragment newInstance(Question question) {
		QuestionFragment fragment = new QuestionFragment();

		Bundle args = new Bundle();
		args.putString(QUES_PARAM, question.getQuestion());
		args.putStringArray(OPTN_PARAM, question.getOptions());
		args.putInt(QNO_PARAM, question.getqNo());
		args.putIntArray(NQ_PARAM, question.getNextQuestion());
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mQuestion = getArguments().getString(QUES_PARAM);
			mOptions = getArguments().getStringArray(OPTN_PARAM);
			mQno = getArguments().getInt(QNO_PARAM);
			mNQns = getArguments().getIntArray(NQ_PARAM);
		}
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View rootFragmentView = inflater.inflate(R.layout.fragment_question, container, false);

		// All fragment view elements
		TextView fragmentQuestionTV = rootFragmentView.findViewById(R.id.fragment_question_tv);
		RadioGroup fragmentOptionsRG = rootFragmentView.findViewById(R.id.fragment_options_rg);
		LinearLayout fragmentOptionLL = rootFragmentView.findViewById(R.id.fragment_option_ll);
		final EditText fragmentOptionET = rootFragmentView.findViewById(R.id.fragment_option_et);
		final Button fragmentOptionButton = rootFragmentView.findViewById(R.id.fragment_option_bt);

		// 1. set fragment question
		fragmentQuestionTV.setText(mQuestion);

		// 2. inflate either options radio buttons or edit text input
		// [decide based on length of options (>0)]
		if (mOptions.length > 0) {
			// flipping visibilities - RADIO GROUP
			fragmentOptionsRG.setVisibility(View.VISIBLE);
			fragmentOptionLL.setVisibility(View.GONE);

			// 2.1 add radio buttons to radio group
			for (String mOption : mOptions) {
				RadioButton optionRB = new RadioButton(getContext());
				optionRB.setId(View.generateViewId());
				optionRB.setText(mOption);
				optionRB.setTextColor(getResources().getColor(R.color.textLight));

				fragmentOptionsRG.addView(optionRB);
			}

			// set on check listener to radio group
			// on check -> send broadcast with ans -> received in survey activity
			fragmentOptionsRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup radioGroup, int i) {
					RadioButton radioButton =
							rootFragmentView.findViewById(radioGroup.getCheckedRadioButtonId());
					String answer = radioButton.getText().toString();
					sendBroadcast(answer);

					// lock options if it's a branching question
					if (mNQns.length > 1) {
						for (int j = 0; j < radioGroup.getChildCount(); j++) {
							radioGroup.getChildAt(j).setEnabled(false);
						}
					}
				}
			});

		} else {
			// flipping visibilities - EDIT TEXT
			fragmentOptionsRG.setVisibility(View.GONE);
			fragmentOptionLL.setVisibility(View.VISIBLE);

			// button onclick
			// get text from edit text -> send broadcast -> received in survey activity
			fragmentOptionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String answer = fragmentOptionET.getText().toString();
					sendBroadcast(answer);
				}
			});
		}

		return rootFragmentView;
	}

	private void sendBroadcast(String answer) {
		Intent optionSelectIntent = new Intent(SurveyActivity.QUESTION_ANSWERED_BROADCAST);
		optionSelectIntent.putExtra(QNO_PARAM, mQno);
		optionSelectIntent.putExtra(ANSWER, answer);
		optionSelectIntent.putExtra(NQ_PARAM, mNQns);

		LocalBroadcastManager.getInstance(mContext).sendBroadcast(optionSelectIntent);
	}
}