package pl.michalkruczek.learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        TextView infoTV = (TextView) findViewById(R.id.info);

        String info = "When You add a question and answer, application will be ask you for properly answer. " +
                "If you always know correct, application will be repeat this question less and when you wrong, " +
                "repeats will be more frequent.\n" +
                "Every question has a \"level known\" and this is way how often single question will be asked.\n" +
                "Level 0 - next day,\n" +
                "Level 1 - 2 days,\n" +
                "Level 2 - 5 days,\n" +
                "Level 3 - 23 days,\n" +
                "Level 4 - 30 days,\n" +
                "Level 5 - 60 days,\n" +
                "Level 6 - 90 days.\n " +
                "On details You can see graph with progress of learning. You cen also update and delete question. " +
                "If one day You forget repeat, you will be obliged make up on the next day.\n" +
                "If You want to have good score, read more about mnemonic and use description.";

        infoTV.setText(info);
    }
}
