package aashi.fiaxco.asquire0x06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import aashi.fiaxco.asquire0x06.data.Question;
import aashi.fiaxco.asquire0x06.data.Questions;

public class SurveyActivity extends AppCompatActivity {

	public static String QUESTION_ANSWERED_BROADCAST = "900021";
	private static HashMap<String, Question> questionHashMap = Questions.getQuestionHashMap();
	LinearLayout mQuestionsLL;
	ScrollView mQuestionsScrollV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		LocalBroadcastManager.getInstance(this)
				.registerReceiver(mQuestionAnsweredBR,
						new IntentFilter(QUESTION_ANSWERED_BROADCAST));

		mQuestionsLL = findViewById(R.id.main_questions_ll);
		mQuestionsScrollV = findViewById(R.id.main_questions_scrollV);

		addQuestionFragment(questionHashMap.get("1"));
	}

	public void addQuestionFragment(Question question) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		Fragment questionFragment = QuestionFragment.newInstance(question);
		fragmentTransaction.add(mQuestionsLL.getId(), questionFragment).addToBackStack(null).commit();

		scrollToBottom();
	}

	private void scrollToBottom() {
		mQuestionsScrollV.postDelayed(new Runnable() {
			@Override
			public void run() {
				mQuestionsScrollV.fullScroll(View.FOCUS_DOWN);
			}
		}, 200);
	}

	private final BroadcastReceiver mQuestionAnsweredBR = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (QUESTION_ANSWERED_BROADCAST.equals(action)) {
				Log.d("Survey Activity", "onReceive: Broadcast Receiver Qno: " + intent.getIntExtra(QuestionFragment.QNO_PARAM, -1));
				Log.d("Survey Activity", "onReceive: Broadcast Receiver Ans: " + intent.getStringExtra(QuestionFragment.ANSWER));
				Log.d("Survey Activity", "onReceive: Broadcast Receiver next Q: " + Arrays.toString(intent.getIntArrayExtra(QuestionFragment.NQ_PARAM)));

				int questionNo = intent.getIntExtra(QuestionFragment.QNO_PARAM, -1);
				String answer = intent.getStringExtra(QuestionFragment.ANSWER);
				int[] nextQuestion = intent.getIntArrayExtra(QuestionFragment.NQ_PARAM);

				String[] options = Objects.requireNonNull(questionHashMap.get("" + questionNo)).getOptions();

				Objects.requireNonNull(questionHashMap.get("" + questionNo)).setAnswer(answer);

				// check for last question
				assert nextQuestion != null;
				if (nextQuestion[0] != -1) {
					if (!Objects.requireNonNull(questionHashMap.get("" + questionNo)).isAnswered()) { // check if question is previously answered
						Objects.requireNonNull(questionHashMap.get("" + questionNo)).setAnswered(true); // set current question as answered
						// deciding next question | sub-questions based on radio button index (1st)
						if (nextQuestion.length == 1) { // check for sub question
							addQuestionFragment(questionHashMap.get("" + nextQuestion[0]));
						} else {
							assert answer != null;
							if (answer.equals(options[0])) { // check for text based input (no options)
								addQuestionFragment(questionHashMap.get("" + nextQuestion[0]));
							} else if (nextQuestion[1] != -1) { // check for last question
								addQuestionFragment(questionHashMap.get("" + nextQuestion[1]));
							} else {
								addProceedButton(); // add proceed button at last
							}
						}

					}
				} else {
					Objects.requireNonNull(questionHashMap.get("" + questionNo)).setAnswered(true);
					addProceedButton(); // add proceed button at last
				}

			}
		}
	};

	private void addProceedButton() {
		Button done = new Button(getApplicationContext());
		done.setText(R.string.proceed_button);

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				HashMap<String, Question> q = questionHashMap;
				String a = Objects.requireNonNull(q.get("1")).getAnswer();
				Log.d("Survey Activity", "onClick: first answer: "+a );
				// TODO: Get all answers and find a way to store it;

			}
		});
		mQuestionsLL.addView(done);
		scrollToBottom();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager.getInstance(this)
				.unregisterReceiver(mQuestionAnsweredBR);
	}
}