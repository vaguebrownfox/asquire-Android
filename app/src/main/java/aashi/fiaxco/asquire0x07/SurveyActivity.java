package aashi.fiaxco.asquire0x07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Objects;

import aashi.fiaxco.asquire0x07.data.Question;
import aashi.fiaxco.asquire0x07.data.Questions;
import aashi.fiaxco.asquire0x07.fragments.QuestionFragment;
import aashi.fiaxco.asquire0x07.fragments.UserInfoFragment;

public class SurveyActivity extends AppCompatActivity {

	public static final String QUESTION_ANSWERED_BROADCAST = "receive_broadcast_asquire";
	public static final String USER_INFO_BROADCAST = "receive_broadcast_user_info_asquire";

	private static HashMap<String, Question> questionHashMap = Questions.getQuestionHashMap();
	private LinearLayout mQuestionsLL;
	private ScrollView mQuestionsScrollV;

	private String mUserID;
	private String mName;
	private String mAge;
	private String mGender;
	private String mHeight;
	private String mWeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey);

		// get user id from user id activity
		Intent i = getIntent();
		mUserID = i.getStringExtra(UserIDActivity.USER_ID);

		// registering broadcast receiver on activity create
		IntentFilter bcIF = new IntentFilter();
		bcIF.addAction(QUESTION_ANSWERED_BROADCAST);
		bcIF.addAction(USER_INFO_BROADCAST);
		LocalBroadcastManager.getInstance(this)
				.registerReceiver(mQuestionAnsweredBR, bcIF);

		// container views
		mQuestionsLL = findViewById(R.id.survey_questions_ll);
		mQuestionsScrollV = findViewById(R.id.survey_questions_scrollV);


	}

	public void addQuestionFragment(Question question) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment questionFragment = QuestionFragment.newInstance(question);
		fragmentTransaction.add(mQuestionsLL.getId(), questionFragment).commit();

		scrollToBottom();
	}

	private final BroadcastReceiver mQuestionAnsweredBR = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (QUESTION_ANSWERED_BROADCAST.equals(action)) {

				// get all extras from broadcast intent
				int questionNo = intent.getIntExtra(QuestionFragment.QNO_PARAM, -1);
				String answer = intent.getStringExtra(QuestionFragment.ANSWER);
				int[] nextQuestion = intent.getIntArrayExtra(QuestionFragment.NQ_PARAM);

				// set answer to question
				setAnswer(questionNo, answer);

				// logic to render next question
				assert nextQuestion != null;
				if (nextQuestion[0] != -1) {
					if (!isAnswered(questionNo)) {
						setAnswered(questionNo);
						if (nextQuestion.length == 1 || getOptions(questionNo)[0].equals(answer)) {
							Question q = getQuestionObj(nextQuestion[0]);
							addQuestionFragment(q);
						} else if (nextQuestion[1] != -1) {
							Question q = getQuestionObj(nextQuestion[1]);
							addQuestionFragment(q);
						} else {
							addProceedButton();
						}
					}
				} else {
					addProceedButton();
				}

			} else if (USER_INFO_BROADCAST.equals(action)) {
//				mUserID
				mName = intent.getStringExtra(UserInfoFragment.NAME_PARAM);
				mAge = intent.getStringExtra(UserInfoFragment.AGE_PARAM);
				mGender = intent.getStringExtra(UserInfoFragment.GENDER_PARAM);
				mHeight = intent.getStringExtra(UserInfoFragment.HEIGHT_PARAM);
				mWeight = intent.getStringExtra(UserInfoFragment.WEIGHT_PARAM);

				addQuestionFragment(getQuestionObj(1));
			}
		}


	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// unregistering broadcast receiver
		LocalBroadcastManager.getInstance(this)
				.unregisterReceiver(mQuestionAnsweredBR);
	}


	// helper methods

	private Question getQuestionObj(int qno) {
		return questionHashMap.get("" + qno);
	}

	private String[] getOptions(int qno) {
		return Objects.requireNonNull(questionHashMap.get("" + qno)).getOptions();
	}

	private void setAnswer(int qno, String answer) {
		Objects.requireNonNull(questionHashMap.get("" + qno)).setAnswer(answer);
	}


	private boolean isAnswered(int qno) {
		return Objects.requireNonNull(questionHashMap.get("" + qno)).isAnswered();
	}

	private void setAnswered(int qno) {
		Objects.requireNonNull(questionHashMap.get("" + qno)).setAnswered(true);
	}

	private void addProceedButton() {
		Button done = new Button(getApplicationContext());
		done.setId(View.generateViewId());
		done.setText(R.string.proceed_button_text);
		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				StringBuilder c = new StringBuilder();
				c.append("Name: ").append(mName).append("\n")
						.append("Age: ").append(mAge).append("\n")
						.append("Gender: ").append(mGender).append("\n")
						.append("Height: ").append(mHeight).append("\n")
						.append("Weight: ").append(mWeight).append("\n");
				for (int i = 1; i <= questionHashMap.size(); i++) {
					Question qns = getQuestionObj(i);
					Log.d("Survey Activity", "question: " + qns.getqNo() + ". " + qns.getQuestion());
					Log.d("Survey Activity", "answer: " + qns.getAnswer());
					c.append("question: ").append(qns.getqNo()).append(". ").append(qns.getQuestion()).append("\n")
							.append("answer: ").append(qns.getAnswer()).append("\n");
				}

				File file = new File(getCacheDir(), mUserID + ".txt");
				try {
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
					outputStreamWriter.write(String.valueOf(c));
					outputStreamWriter.flush();
					outputStreamWriter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

				Intent recordTaskIntent = new Intent(SurveyActivity.this, RecordTaskActivity.class);
				recordTaskIntent.putExtra(UserIDActivity.USER_ID, mUserID);
				startActivity(recordTaskIntent);
			}
		});
		mQuestionsLL.addView(done);
		scrollToBottom();
	}

	private void scrollToBottom() {
		mQuestionsScrollV
				.postDelayed(new Runnable() {
					@Override
					public void run() {
						mQuestionsScrollV.fullScroll(View.FOCUS_DOWN);
					}
				}, 200);
	}
}