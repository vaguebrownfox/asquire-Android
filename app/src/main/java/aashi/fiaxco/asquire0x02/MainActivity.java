package aashi.fiaxco.asquire0x02;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;

import aashi.fiaxco.asquire0x02.data.Question;
import aashi.fiaxco.asquire0x02.data.Questions;

public class MainActivity extends AppCompatActivity {

    LinearLayout mQuestionsLL;

    public static HashMap<String, Question> allQuestions = Questions.getQuestionHashMap();
    public static int questionIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionsLL = findViewById(R.id.main_linearLayout);

        Question q1 = allQuestions.get("1");
//        Question q2 = allQuestions.get("2");

        assert q1 != null;
        addQuestionFragment(q1);

//        for (int i=1; i<=allQuestions.size(); i++) {
//            Question question = Objects.requireNonNull(allQuestions.get("" + i));
//            addQuestionFragment(question);
//        }

    }

    public void addQuestionFragment(Question question) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment questionFragment = QuestionFragment.newInstance(question);
        fragmentTransaction.add(mQuestionsLL.getId(), questionFragment).commit();

        questionIndex++;
    }

}