package pl.michalkruczek.learn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        context = RepeatActivity.this;

        numberOfQuestion = 0;

        repeat_question = (TextView) findViewById(R.id.repeat_question);
        repeat_answer = (TextView) findViewById(R.id.repeat_answer);
        repeat_description = (TextView) findViewById(R.id.repeat_description);

        DaoMaster.DevOpenHelper helperDB = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = helperDB.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();
        allQuestions = questionDao.queryBuilder().list();
        todayRepeat = new ArrayList<>();

        for (Question word : allQuestions) {
            Date today = new Date();

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String wordString = df.format(word.getNextRepeat());
            String todayString = df.format(today);

            if (wordString.equals(todayString)) {
                todayRepeat.add(word);
            }
        }
//TODO - create Layout, method are good
        todayRepeat.add(new Question(1L, 1L, "1", "1", "1", new Date(), new Date(), 5));
        todayRepeat.add(new Question(2L, 2L, "2", "2", "2", new Date(), new Date(), 4));
        todayRepeat.add(new Question(3L, 3L, "3", "3", "3", new Date(), new Date(), 1));
        todayRepeat.add(new Question(4L, 4L, "4", "4", "4", new Date(), new Date(), 0));

        repeat_question.setText(todayRepeat.get(numberOfQuestion).getQuestion());

    }

    public void ShowAnswer(View view) {
        repeat_answer.setText(todayRepeat.get(numberOfQuestion).getAnswer());
        repeat_description.setText(todayRepeat.get(numberOfQuestion).getDescribe());
    }

    public void GoodAnswer(View view) {
        if (todayRepeat.get(numberOfQuestion).getLevel() > 4) {
            Toast.makeText(context, "Question on max Level", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Rise know level +1", Toast.LENGTH_SHORT).show();
            todayRepeat.get(numberOfQuestion).setLevel(todayRepeat.get(numberOfQuestion).getLevel() + 1);
        }
    }

    public void WrongAnswer(View view) {
        if (todayRepeat.get(numberOfQuestion).getLevel() < 1) {
            Toast.makeText(context, "Still on bottom level ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "hujnia know level -1", Toast.LENGTH_SHORT).show();
            todayRepeat.get(numberOfQuestion).setLevel(todayRepeat.get(numberOfQuestion).getLevel() - 1);
        }
    }

    public void DeleteQuestion(View view) {
    }

    public void NextQuestion(View view) {
        if (numberOfQuestion < todayRepeat.size() - 1) {
            numberOfQuestion += 1;
            repeat_question.setText(todayRepeat.get(numberOfQuestion).getQuestion());
            repeat_answer.setText("");
            repeat_description.setText("");
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage("Gratuluje")
                    .setPositiveButton("END", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                    }).show();
        }
    }
}
