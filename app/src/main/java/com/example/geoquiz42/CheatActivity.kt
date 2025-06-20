package com.example.geoquiz42

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var  versionTextView: TextView

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(com.example.geoquiz.R.layout.activity_cheat)

        versionTextView = findViewById(com.example.geoquiz.R.id.version_text_view)

        // Получаем версию Android
        val androidVersion = "Android Version: ${Build.VERSION.RELEASE} (API Level: ${Build.VERSION.SDK_INT})"

        // Отображаем версию в TextView
        versionTextView.text = androidVersion


        // Получаем ответ из интента
        answerIsTrue = intent.getBooleanExtra(com.example.geoquiz.EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(com.example.geoquiz.R.id.answer_text_view)
        showAnswerButton = findViewById(com.example.geoquiz.R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            // Показываем ответ пользователю
            val answerText = when {
                answerIsTrue ->
                    com.example.geoquiz.R.string.true_button
                else -> com.example.geoquiz.R.string.false_button
            }
            answerTextView.setText(answerText)

            // Устанавливаем результат, что ответ был показан
            setAnswerShownResult(true)
        }
    }

         fun setAnswerShownResult(isAnswerShown: Boolean) {
            val data = Intent().apply {
                putExtra(com.example.geoquiz.EXTRA_ANSWER_SHOWN, isAnswerShown)
            }
             // Устанавливаем результат для передачи обратно в MainActivity
            setResult(RESULT_OK, data)
        }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(com.example.geoquiz.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }

        // Метод для проверки, был ли показан ответ
        fun wasAnswerShown(result: Intent?): Boolean {
            return result?.getBooleanExtra(com.example.geoquiz.EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}