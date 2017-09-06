package pl.michalkruczek.learn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.greendao.database.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.michalkruczek.learn.db.DaoMaster;
import pl.michalkruczek.learn.db.DaoSession;
import pl.michalkruczek.learn.db.Question;
import pl.michalkruczek.learn.db.QuestionDao;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private DaoSession daoSession;
    private QuestionDao questionDao;
    private List<Question> questionList;

    private TextView main_numberOfAllQuestion;
    private TextView main_numberOfTodayQuestion;
    private ImageButton main_repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        DaoMaster.DevOpenHelper helperDB = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = helperDB.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();
        questionList = questionDao.queryBuilder().list();

        for (Question question : questionList) {
            question.checkMissesOutRepeat();
            questionDao.update(question);
        }

        main_numberOfAllQuestion = (TextView) findViewById(R.id.main_numberOfAllQuestion);
        main_numberOfTodayQuestion = (TextView) findViewById(R.id.main_numberOfTodayQuestion);
        main_repeat = (ImageButton) findViewById(R.id.main_repeat);

        String numberOfAllQuestionString = "In Your Base You have " + questionList.size() + " questions.";

        main_numberOfAllQuestion.setText(numberOfAllQuestionString);

        int numberOfTodayQuestionInt = 0;

        for (Question question : questionList) {
            Date today = new Date();

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String questionString = df.format(question.getNextRepeat());
            String todayString = df.format(today);

            if (questionString.equals(todayString)) {
                numberOfTodayQuestionInt++;
            }
        }

        String numberOfTodayQuestionString = "";

        if (numberOfTodayQuestionInt == 0) {
            numberOfTodayQuestionString = "Today... You have holiday :D, zero questions to repeat.";
            main_repeat.setEnabled(false);
        } else if(numberOfTodayQuestionInt == 1){
            numberOfTodayQuestionString = " Today You have only one question to repeat.";
        } else {
            numberOfTodayQuestionString = "Today you have " + numberOfTodayQuestionInt + " questions to repeat.";
        }

        main_numberOfTodayQuestion.setText(numberOfTodayQuestionString);

    }

    public void mainTodayRepeat(View view) {
        Intent intent = new Intent(context, RepeatActivity.class);
        context.startActivity(intent);
    }

    public void mainAddQuestion(View view) {
        Intent intent = new Intent(context, AddQuestionActivity.class);
        context.startActivity(intent);
    }

    public void mainShowDb(View view) {
        Intent intent = new Intent(context, AllQuestionActivity.class);
        context.startActivity(intent);
    }

    public void mainInfo(View view) {
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);


    }
}
