package pl.michalkruczek.learn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.database.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.michalkruczek.learn.db.DaoMaster;
import pl.michalkruczek.learn.db.DaoSession;
import pl.michalkruczek.learn.db.Question;
import pl.michalkruczek.learn.db.QuestionDao;

public class RepeatActivity extends AppCompatActivity {

    private Context context;

    private DaoSession daoSession;
    private QuestionDao questionDao;
    private List<Question> allQuestions;
    private List<Question> todayRepeat;

    private TextView repeat_question;
    private TextView repeat_answer;
    private TextView repeat_description;

    public static int numberOfQuestion;

    private Button repeat_buttonShowAnswer;
    private Button repeat_buttonGoodAnswer;
    private Button repeat_buttonWrongAnswer;
    private Button repeat_buttonDetailsQuestion;
    private Button repeat_buttonNextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        context = RepeatActivity.this;

        numberOfQuestion = 0;

        repeat_question = (TextView) findViewById(R.id.repeat_question);
        repeat_answer = (TextView) findViewById(R.id.repeat_answer);
        repeat_description = (TextView) findViewById(R.id.repeat_description);

        repeat_buttonShowAnswer = (Button) findViewById(R.id.repeat_buttonShowAnswer);
        repeat_buttonGoodAnswer = (Button) findViewById(R.id.repeat_buttonGoodAnswer);
        repeat_buttonWrongAnswer = (Button) findViewById(R.id.repeat_buttonWrongAnswer);
        repeat_buttonDetailsQuestion = (Button) findViewById(R.id.repeat_buttonDetailsQuestion);
        repeat_buttonNextQuestion = (Button) findViewById(R.id.repeat_buttonNextQuestion);

        DaoMaster.DevOpenHelper helperDB = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = helperDB.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();
        allQuestions = questionDao.queryBuilder().list();
        todayRepeat = new ArrayList<>();

        for (Question question : allQuestions) {
            Date today = new Date();

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String questionString = df.format(question.getNextRepeat());
            String todayString = df.format(today);

            if (questionString.equals(todayString)) {
                todayRepeat.add(question);
            }
        }


        repeat_question.setText(todayRepeat.get(numberOfQuestion).getQuestion());


        repeat_buttonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();

                repeat_buttonGoodAnswer.setVisibility(View.INVISIBLE);
                repeat_buttonWrongAnswer.setVisibility(View.INVISIBLE);
                repeat_buttonGoodAnswer.setEnabled(true);
                repeat_buttonWrongAnswer.setEnabled(true);
                repeat_buttonDetailsQuestion.setVisibility(View.INVISIBLE);
                repeat_buttonNextQuestion.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void showAnswer(View view) {
        repeat_answer.setText(todayRepeat.get(numberOfQuestion).getAnswer());
        repeat_description.setText(todayRepeat.get(numberOfQuestion).getDescribe());

        repeat_buttonGoodAnswer.setVisibility(View.VISIBLE);
        repeat_buttonWrongAnswer.setVisibility(View.VISIBLE);
    }

    public void goodAnswer(View view) {

        Question question = todayRepeat.get(numberOfQuestion);

        if (question.getLevel() > 5) {
            Toast.makeText(context, "Nice!! Question on max Level", Toast.LENGTH_SHORT).show();
            question.makeHistory();
        } else {
            Toast.makeText(context, "Rise know level +1", Toast.LENGTH_SHORT).show();
            question.setLevel(question.getLevel() + 1);
            question.makeHistory();

        }

        question.setDateOfNextRepeat();
        questionDao.update(question);

        repeat_buttonGoodAnswer.setEnabled(false);
        repeat_buttonWrongAnswer.setEnabled(false);
        repeat_buttonDetailsQuestion.setVisibility(View.VISIBLE);
        repeat_buttonNextQuestion.setVisibility(View.VISIBLE);
    }

    public void wrongAnswer(View view) {

        Question question = todayRepeat.get(numberOfQuestion);

        if (question.getLevel() < 1) {
            Toast.makeText(context, "Still on bottom level ", Toast.LENGTH_SHORT).show();
            question.makeHistory();
        } else {
            Toast.makeText(context, "Wrong, know level -1", Toast.LENGTH_SHORT).show();
            question.setLevel(question.getLevel() - 1);
            question.makeHistory();
        }

        question.setDateOfNextRepeat();
        questionDao.update(question);

        repeat_buttonGoodAnswer.setEnabled(false);
        repeat_buttonWrongAnswer.setEnabled(false);
        repeat_buttonDetailsQuestion.setVisibility(View.VISIBLE);
        repeat_buttonNextQuestion.setVisibility(View.VISIBLE);
    }


    public void nextQuestion() {
        if (numberOfQuestion < todayRepeat.size() - 1) {
            numberOfQuestion += 1;
            repeat_question.setText(todayRepeat.get(numberOfQuestion).getQuestion());
            repeat_answer.setText("");
            repeat_description.setText("");
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("Congratulation, today You finish all repeats!")
                    .setPositiveButton("END", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    }).show();
        }
    }

    public void detailsQuestion(View view) {

        final Question question = todayRepeat.get(numberOfQuestion);

        View updateLayoutAlertDialog = View.inflate(context, R.layout.all_question_onclick_rv, null);

        final EditText update_question = (EditText) updateLayoutAlertDialog.findViewById(R.id.update_question);
        final EditText update_answer = (EditText) updateLayoutAlertDialog.findViewById(R.id.update_answer);
        final EditText update_description = (EditText) updateLayoutAlertDialog.findViewById(R.id.update_description);
        final TextView nextDate = (TextView) updateLayoutAlertDialog.findViewById(R.id.nextDate);
        final TextView update_actuallyLevel = (TextView) updateLayoutAlertDialog.findViewById(R.id.update_actuallyLevel);
        LinearLayout ll = (LinearLayout) updateLayoutAlertDialog.findViewById(R.id.ll);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.makeChart(context);
            }
        });

        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String questionString = "  " + df.format(question.getNextRepeat());
        nextDate.setText(questionString);

        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout ll = new LinearLayout(context);
                final DatePicker datePicker = new DatePicker(context);
                ll.addView(datePicker);

                AlertDialog ad = new AlertDialog.Builder(context)
                        .setView(ll)
                        .setTitle("Chose Date")
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int yer = (datePicker.getYear()-1900);
                                int month = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date newDate = new Date(yer, month, day);

                                question.setNextRepeat(newDate);
                                questionDao.update(question);

                                String msgToNextDate = "  " + df.format(new Date());
                                nextDate.setText(msgToNextDate);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });

        update_question.setText(question.getQuestion());
        update_answer.setText(question.getAnswer());
        update_description.setText(question.getDescribe());
        String actuallyLevelString = "Actually level for this question is " + question.getLevel() + ".";
        update_actuallyLevel.setText(actuallyLevelString);

        AlertDialog onClickAlertdialog = new AlertDialog.Builder(context)
                .setView(updateLayoutAlertDialog)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Question updateQuestion = question;

                        updateQuestion.setQuestion(update_question.getText().toString());
                        updateQuestion.setAnswer(update_answer.getText().toString());
                        updateQuestion.setDescribe(update_description.getText().toString());

                        questionDao.update(updateQuestion);

                        repeat_question.setText(updateQuestion.getQuestion());
                        repeat_answer.setText(updateQuestion.getAnswer());
                        repeat_description.setText(updateQuestion.getDescribe());
                    }
                })
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog deleteQuestion = new AlertDialog.Builder(context)
                                .setTitle("You want delete question.")
                                .setMessage("If You do this, you lose all history of this question, continue?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        questionDao.delete(question);
                                        nextQuestion();
                                        repeat_buttonGoodAnswer.setVisibility(View.INVISIBLE);
                                        repeat_buttonWrongAnswer.setVisibility(View.INVISIBLE);
                                        repeat_buttonGoodAnswer.setEnabled(true);
                                        repeat_buttonWrongAnswer.setEnabled(true);
                                        repeat_buttonDetailsQuestion.setVisibility(View.INVISIBLE);
                                        repeat_buttonNextQuestion.setVisibility(View.INVISIBLE);

                                    }
                                }).show();
                    }
                })
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
