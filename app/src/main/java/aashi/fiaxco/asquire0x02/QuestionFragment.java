package aashi.fiaxco.asquire0x02;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import aashi.fiaxco.asquire0x02.data.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
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
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionTV = rootView.findViewById(R.id.fragment_question);
        RadioGroup optionsRG = rootView.findViewById(R.id.fragment_radioGroup);

        questionTV.setText(mQuestion);

        for (String mOption : mOptions) {
            RadioButton optionRB = new RadioButton(getContext());
            optionRB.setId(View.generateViewId());
            optionRB.setText(mOption);
            optionRB.setOnClickListener(this);
            optionsRG.addView(optionRB);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        RadioButton option = (RadioButton) view;

        Toast.makeText(getContext(), "Selected: " + option.getText(), Toast.LENGTH_SHORT).show();

    }
}