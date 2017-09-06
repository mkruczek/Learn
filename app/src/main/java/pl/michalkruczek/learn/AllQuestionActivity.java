package pl.michalkruczek.learn;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import pl.michalkruczek.learn.db.Category;
import pl.michalkruczek.learn.db.DaoMaster;
import pl.michalkruczek.learn.db.DaoSession;
import pl.michalkruczek.learn.db.Question;
import pl.michalkruczek.learn.db.QuestionDao;

public class AllQuestionActivity extends AppCompatActivity {

    //TODO find way to properly show all question, when they numbers rise.

    private Context context;

    private DaoSession daoSession;
    private QuestionDao questionDao;

    private List<Question> questionList;
    private List<Category> categoryList;

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private AllQuestionAdapter allQuestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_question_activity);

        context = AllQuestionActivity.this;

        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = dbHelper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();

        questionList = questionDao.queryBuilder().list();


        recyclerView = (RecyclerView) findViewById(R.id.all_question_RV);
        llm = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(llm);
        allQuestionAdapter = new AllQuestionAdapter(context, questionList, questionDao);
        recyclerView.setAdapter(allQuestionAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
