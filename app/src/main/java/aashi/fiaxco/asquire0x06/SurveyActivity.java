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
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Arrays;
import java.util.HashMap;

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
		fragmentTransaction.add(mQuestionsLL.getId(), questionFragment).commit();

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

				int[] nqs = intent.getIntArrayExtra(QuestionFragment.NQ_PARAM);
				assert nqs != null;
				addQuestionFragment(questionHashMap.get("" + nqs[0]));
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager.getInstance(this)
				.unregisterReceiver(mQuestionAnsweredBR);
	}
}