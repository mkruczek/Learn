package pl.michalkruczek.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    List<Question> todayRepeat;




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
        todayRepeat = new ArrayList<>();



        for (Question word : allQuestions) {
            word.setDateOfNextRepeat();
        }


        for (Question word : allQuestions) {
            Date today = new Date();

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            String wordString = df.format(word.getNextRepeat());
            String todayString = df.format(today);

            if(wordString.equals(todayString)){
                todayRepeat.add(word);
            }
        }

        for (Question word : todayRepeat) {
            System.out.println(word);
        }
    }
}
