package aashi.fiaxco.asquire0x02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Objects;

import aashi.fiaxco.asquire0x02.data.Question;
import aashi.fiaxco.asquire0x02.data.Questions;

public class MainActivity extends AppCompatActivity {

    LinearLayout mQuestionsLL;

    public static HashMap<String, Question> allQuestions = Questions.getQuestionHashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuestionsLL = findViewById(R.id.main_linearLayout);

//        Question q1 = allQuestions.get("1");
//        Question q2 = allQuestions.get("2");
//
//        assert q1 != null;
//        addQuestionFragment(q1);
//        assert q2 != null;
//        addQuestionFragment(q2);

        for (int i=1; i<=allQuestions.size(); i++) {
            Question question = Objects.requireNonNull(allQuestions.get("" + i));
            addQuestionFragment(question, i);
        }

    }

    public void addQuestionFragment(Question question, int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment questionFragment = QuestionFragment.newInstance(index + ". " + question.getQuestion(), question.getOptions());
        fragmentTransaction.add(mQuestionsLL.getId(), questionFragment).commit();
    }

}