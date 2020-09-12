package aashi.fiaxco.asquire0x02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import aashi.fiaxco.asquire0x02.data.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters
    private static final String QUESTION = "question";
    private static final String OPTIONS = "options";

    private String mQuestion;
    private String[] mOptions;


    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(QUESTION, question.getQuestion());
        args.putStringArray(OPTIONS, question.getOptions());
        fragment.setArguments(args);
        return fragment;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(QUESTION);
            mOptions = getArguments().getStringArray(OPTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionTV = mRootView.findViewById(R.id.fragment_question);
        RadioGroup optionsRG = mRootView.findViewById(R.id.fragment_radioGroup);

        questionTV.setText(mQuestion);

        for (String mOption : mOptions) {
            RadioButton optionRB = new RadioButton(getContext());
            optionRB.setId(View.generateViewId());
            optionRB.setText(mOption);
            optionRB.setOnClickListener(this);
            optionsRG.addView(optionRB);
        }

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        RadioButton option = (RadioButton) view;

        Toast.makeText(getContext(), "Selected: " + option.getText(), Toast.LENGTH_SHORT).show();

        if (MainActivity.questionIndex <= 28) {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Question question = MainActivity.allQuestions.get("" + MainActivity.questionIndex++);

            Fragment questionFragment =
                    QuestionFragment.newInstance(Objects.requireNonNull(question));

            fragmentTransaction.add(R.id.main_linearLayout, questionFragment).commit();
        }


    }
}