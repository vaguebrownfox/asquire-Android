package aashi.fiaxco.asquire0x01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private boolean isFragmentOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.main_button_fragmentControl);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentOpen) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });


    }

    public void displayFragment() {
        QuestionFragment questionFragment = QuestionFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_fragment_question_container, questionFragment).addToBackStack(null).commit();

        mButton.setText("Close");
        isFragmentOpen = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        QuestionFragment questionFragment = (QuestionFragment)  fragmentManager.findFragmentById(R.id.main_fragment_question_container);

        if (questionFragment != null) {
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(questionFragment).commit();
        }

        mButton.setText("Open");
        isFragmentOpen = false;
    }
}