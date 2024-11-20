package com.example.quizers2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button startQuizButton, nextButton, tryAgainButton;
    private EditText nickname;
    private TextView questionText, resultText;
    private RadioGroup answerGroup;
    private RadioButton answerA, answerB;

    private int currentQuestion = 0;
    private int score = 0;
    private String userNickname;

    private final String[] questions = {
            "Co jest potrzebne, aby wyświetlić listę w RecyclerView?",
            "Jak zażądać uprawnień w czasie działania aplikacji?",
            "Której metody użyć, aby zwalniać zasoby w aktywności?",
            "Która biblioteka umożliwia łatwe zapytania do API?",
            "Jak zarządzać bazą SQLite w Androidzie?",
            "Co oznacza termin \"JVM\" w kontekście języka Java?"
    };

    private final String[][] answers = {
            {"Adapter i LayoutManager", "Tylko ListView"},
            {"ActivityCompat.requestPermissions()", "startActivity()"},
            {"onPause()", "onStart()"},
            {"Retrofit", "Picasso"},
            {"Rozszerzając klasę SQLiteOpenHelper", "Używając SharedPreferences"},
            {"Jest to maszyna wirtualna, która uruchamia kod Java niezależnie od systemu operacyjnego","Jest to biblioteka do obsługi wątków w Javie."}
    };

    private final int[] correctAnswers = {0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        startQuizButton = findViewById(R.id.startQuizButton);
        nickname = findViewById(R.id.nickname);
        questionText = findViewById(R.id.questionText);
        answerGroup = findViewById(R.id.answerGroup);
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        nextButton = findViewById(R.id.nextButton);
        resultText = findViewById(R.id.resultText);
        tryAgainButton = findViewById(R.id.tryAgainButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startQuizButton.setOnClickListener(v -> {
            userNickname = nickname.getText().toString();
            findViewById(R.id.loginLayout).setVisibility(View.GONE);
            findViewById(R.id.quizLayout).setVisibility(View.VISIBLE);
            loadQuestion();
        });

        nextButton.setOnClickListener(v -> {
            int selectedAnswer = answerGroup.indexOfChild(findViewById(answerGroup.getCheckedRadioButtonId()));
            if (selectedAnswer == correctAnswers[currentQuestion]) {
                score++;
            }
            currentQuestion++;
            if (currentQuestion < questions.length) {
                loadQuestion();
            } else {
                showResult();
            }
        });

        tryAgainButton.setOnClickListener(v -> {
            currentQuestion = 0;
            score = 0;
            resultText.setVisibility(View.GONE);
            tryAgainButton.setVisibility(View.GONE);
            questionText.setVisibility(View.GONE);
            answerGroup.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            findViewById(R.id.loginLayout).setVisibility(View.VISIBLE);
        });
    }

    private void loadQuestion() {
        questionText.setText(questions[currentQuestion]);
        answerA.setText(answers[currentQuestion][0]);
        answerB.setText(answers[currentQuestion][1]);
        answerGroup.clearCheck();
    }

    private void showResult() {
        questionText.setVisibility(View.GONE);
        answerGroup.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        resultText.setVisibility(View.VISIBLE);
        resultText.setText("Twój wynik: " + score + " / " + questions.length + "\nGratulacje, " + userNickname + "!");
        tryAgainButton.setVisibility(View.VISIBLE);
    }
}