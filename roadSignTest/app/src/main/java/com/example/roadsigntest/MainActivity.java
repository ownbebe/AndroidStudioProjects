package com.example.roadsigntest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ImageView signImage;
    private TextView questionText;
    private RadioGroup answerGroup;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private RadioButton answer5;
    private Button nextButton;
    private ArrayList<String> signNames = new ArrayList<>();
    private ArrayList<String> shuffledSignNames = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String stop = "Stop sign";
    private String railroad = "Railroad Crossing";
    private String rocks = "Falling rocks ahead";
    private String traffic_sign = "Traffic signal ahead";
    private String yield = "Yield to traffic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signImage = findViewById(R.id.sign_image);
        questionText = findViewById(R.id.question_text);
        answerGroup = findViewById(R.id.answer_group);
        answer1 = findViewById(R.id.answer_1);
        answer2 = findViewById(R.id.answer_2);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);
        answer5 = findViewById(R.id.answer_5);
        nextButton = findViewById(R.id.next_button);

        signNames.add("railroad");
        signNames.add("rocks");
        signNames.add("stop");
        signNames.add("traffic_sign");
        signNames.add("yield");

        shuffledSignNames = new ArrayList<>(signNames);
        Collections.shuffle(shuffledSignNames);

        setQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < shuffledSignNames.size()) {
                    setQuestion(currentQuestionIndex);
                } else {
                    showScore();
                }
            }
        });
    }

    private void setQuestion(int index) {
        String signName = shuffledSignNames.get(index);
        questionText.setText("What is the name of this sign?");

        int imageResource = getResources().getIdentifier(signName.toLowerCase(), "drawable", getPackageName());
        signImage.setImageResource(imageResource);

        answer1.setText(stop);
        answer2.setText(railroad);
        answer3.setText(rocks);
        answer4.setText(traffic_sign);
        answer5.setText(yield);

        answerGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = answerGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return; // No answer selected
        }
        RadioButton selectedButton = findViewById(selectedId);
        String selectedAnswer = selectedButton.getText().toString();
        if(selectedAnswer == stop){
            selectedAnswer = "stop";
        }

        else if(selectedAnswer == railroad){
            selectedAnswer = "railroad";
        }

        else if(selectedAnswer == rocks){
            selectedAnswer = "rocks";
        }

        else if(selectedAnswer == traffic_sign){
            selectedAnswer = "traffic_sign";
        }

        else if(selectedAnswer == yield){
            selectedAnswer = "yield";
        }
        String correctAnswer = shuffledSignNames.get(currentQuestionIndex);
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
        }
    }

    private void showScore() {
        // Show score as a percentage
        int numQuestions = shuffledSignNames.size();
        double percentage = ((double) score / numQuestions) * 100;
        String scoreMessage = "You got " + score + " out of " + numQuestions + " correct (" + String.format("%.1f", percentage) + "%)";
        questionText.setText(scoreMessage);
        signImage.setImageResource(R.drawable.check);
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);
        answer5.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
    }
}
