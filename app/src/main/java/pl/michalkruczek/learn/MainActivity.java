package pl.michalkruczek.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    private Context context;

    private DaoSession daoSession;
    private QuestionDao questionDao;
    List<Question> allQuestions;

    private TextView mainTextView;
    private Button button_todayRepeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        DaoMaster.DevOpenHelper helperDB = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = helperDB.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();
        allQuestions = questionDao.queryBuilder().list();


        mainTextView = (TextView) findViewById(R.id.mainTextView);
        button_todayRepeat = (Button) findViewById(R.id.button_todayRepeat);

        int numberOfAllQuestion = allQuestions.size();

        String text = "w bazie jest " + numberOfAllQuestion + "pytań.\n";

        for (Question question : allQuestions) {
            text += question + "\n";
        }

        mainTextView.setText(text);

    }

    public void todayRepeat(View view) {

        Intent intent = new Intent(context, RepeatActivity.class);
        context.startActivity(intent);

    }
}
