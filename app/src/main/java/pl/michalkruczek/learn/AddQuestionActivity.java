package pl.michalkruczek.learn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.List;

import pl.michalkruczek.learn.db.Category;
import pl.michalkruczek.learn.db.CategoryDao;
import pl.michalkruczek.learn.db.DaoMaster;
import pl.michalkruczek.learn.db.DaoSession;
import pl.michalkruczek.learn.db.Question;
import pl.michalkruczek.learn.db.QuestionDao;

public class AddQuestionActivity extends AppCompatActivity {

    private Context context;

    private DaoSession daoSession;
    private QuestionDao questionDao;
    private CategoryDao categoryDao;
    private List<Category> allCategories;

    private Spinner spinner_category;
    private Button button_settingsCategory;
    private EditText editText_question;
    private EditText editText_answer;
    private EditText editText_description;
    private ImageButton imageButton_addQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        createShortCut();

        context = AddQuestionActivity.this;

        DaoMaster.DevOpenHelper helperDB = new DaoMaster.DevOpenHelper(context, "users.db");
        Database db = helperDB.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        questionDao = daoSession.getQuestionDao();
        categoryDao = daoSession.getCategoryDao();
        allCategories = categoryDao.queryBuilder().list();

        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        refreshSpinner(spinner_category, allCategories);
        button_settingsCategory = (Button) findViewById(R.id.button_settingsCategory);
        editText_question = (EditText) findViewById(R.id.editText_question);
        editText_answer = (EditText) findViewById(R.id.editText_answer);
        editText_description = (EditText) findViewById(R.id.editText_description);
        imageButton_addQuestion = (ImageButton) findViewById(R.id.imageButton_addQuestion);


    }

    public void addQuestion(View view) {
        Question question = new Question();
        question.setQuestion(editText_question.getText().toString());
        question.setAnswer(editText_answer.getText().toString());
        question.setDescribe(editText_description.getText().toString());
        question.setAddDate(new Date());
        question.setNextRepeat(new Date());
        question.setLevel(0);
        question.setCategory((Category) spinner_category.getSelectedItem());

        question.setDateOfNextRepeat();
        questionDao.insert(question);

        cleanEditText(editText_question);
        cleanEditText(editText_answer);
        cleanEditText(editText_description);

    }

    public void addCategory(View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        final View alertDialogView = View.inflate(context, R.layout.category_settings_layout, null);
        alertDialog.setView(alertDialogView);

        final ListView listView = (ListView) alertDialogView.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<Category>(context
                , android.R.layout.simple_spinner_item
                , changeListToTable(categoryDao.queryBuilder().list())));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Really want delete this category?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                Category categoryToDelete = (Category) listView.getItemAtPosition(i);
                                daoSession.delete(categoryToDelete);
                                listView.setAdapter(new ArrayAdapter<Category>(context
                                        , android.R.layout.simple_spinner_item
                                        , changeListToTable(categoryDao.queryBuilder().list())));

                                refreshSpinner(spinner_category, categoryDao.queryBuilder().list());
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                return false;
            }
        });


        final EditText addCategoryEditTextNameNewCategory = (EditText) alertDialogView.findViewById(R.id.addCategoryEditTextNameNewCategory);
        Button button_addCategory = (Button) alertDialogView.findViewById(R.id.button_addCategory);
        button_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNameCategory = addCategoryEditTextNameNewCategory.getText().toString();
                Category category = new Category();
                category.setValue(newNameCategory);
                categoryDao.insert(category);
                listView.setAdapter(new ArrayAdapter<Category>(context
                        , android.R.layout.simple_spinner_item
                        , changeListToTable(categoryDao.queryBuilder().list())));

                Toast.makeText(context, "Category added.", Toast.LENGTH_SHORT).show();

                refreshSpinner(spinner_category, categoryDao.queryBuilder().list());

                cleanEditText(addCategoryEditTextNameNewCategory);
            }
        });

        alertDialog.setView(alertDialogView)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void refreshSpinner(Spinner spinner, List<Category> categories) {
        Category[] tableCategories = changeListToTable(categories);

        ArrayAdapter<Category> spinnerArrayAdapter = new ArrayAdapter<Category>(context, android.R.layout.simple_spinner_item, tableCategories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public static Category[] changeListToTable(List<Category> categories) {
        Category[] tableCategories = new Category[categories.size()];

        for (int i = 0; i < categories.size(); i++) {
            tableCategories[i] = categories.get(i);
        }

        return tableCategories;
    }

    public static void cleanEditText(EditText editText) {
        editText.setText("");
    }

    public void createShortCut(){
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "add to Learn");
        //TODO - change application icon
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), android.R.drawable.arrow_down_float);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), AddQuestionActivity.class));
        sendBroadcast(shortcutintent);
    }
}
