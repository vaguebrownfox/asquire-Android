package aashi.fiaxco.asquire0x06;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import aashi.fiaxco.asquire0x06.data.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	public static final String QUES_PARAM = "param1";
	public static final String OPTN_PARAM = "param2";
	public static final String QNO_PARAM = "param3";
	public static final String NQ_PARAM = "param4";

	public static final String RADIO_IDX = "radio_index";
	public static final String ANSWER = "answer212";



	// TODO: Rename and change types of parameters
	private String mQuestion;
	private String[] mOptions;
	private int[] mNQns;
	private int mQno;

	private Context mContext;

	public QuestionFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
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

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View rootView = inflater.inflate(R.layout.fragment_question, container, false);


		TextView fragmentQuestionTV = rootView.findViewById(R.id.fragment_question_tv);
		RadioGroup fragmentOptionsRG = rootView.findViewById(R.id.fragment_options_rg);
		LinearLayout fragmentOptionLL = rootView.findViewById(R.id.fragment_option_ll);
		final EditText fragmentOptionET = rootView.findViewById(R.id.fragment_option_et);
		final Button fragmentOptionButton = rootView.findViewById(R.id.fragment_option_bt);

		fragmentQuestionTV.setText(mQuestion);
		if (mOptions.length > 0) {
			fragmentOptionsRG.setVisibility(View.VISIBLE);
			fragmentOptionLL.setVisibility(View.GONE);

			for (String mOption : mOptions) {
				RadioButton optionRB = new RadioButton(getContext());
				optionRB.setId(View.generateViewId());
				optionRB.setText(mOption);
				optionRB.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Small);
				optionRB.setTextColor(getResources().getColor(R.color.textLight));

				fragmentOptionsRG.addView(optionRB);
			}


			fragmentOptionsRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup radioGroup, int i) {
					RadioButton radioButton = rootView.findViewById(radioGroup.getCheckedRadioButtonId());
					String answer = radioButton.getText().toString();
					sendBroadCast(i, answer);
				}
			});
		} else {
			fragmentOptionsRG.setVisibility(View.GONE);
			fragmentOptionLL.setVisibility(View.VISIBLE);

			fragmentOptionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String answer = fragmentOptionET.getText().toString();
					sendBroadCast(0, answer);
				}
			});
		}


		return rootView;
	}

	private void sendBroadCast(int radioBtIndex, String answer) {
		Intent optionSelectIntent = new Intent(SurveyActivity.QUESTION_ANSWERED_BROADCAST);
		optionSelectIntent.putExtra(QNO_PARAM, mQno);
		optionSelectIntent.putExtra(RADIO_IDX, radioBtIndex);
		optionSelectIntent.putExtra(ANSWER, answer);
		optionSelectIntent.putExtra(NQ_PARAM, mNQns);

		LocalBroadcastManager.getInstance(mContext).sendBroadcast(optionSelectIntent);
	}

}