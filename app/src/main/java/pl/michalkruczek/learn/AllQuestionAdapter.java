package pl.michalkruczek.learn;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.michalkruczek.learn.db.Question;
import pl.michalkruczek.learn.db.QuestionDao;


/**
 * Created by mikr on 30/08/17.
 */

public class AllQuestionAdapter extends RecyclerView.Adapter<AllQuestionAdapter.SingleQuestionView> {

    private Context context;
    private List<Question> questionList;
    private QuestionDao questionDao;

    private static String DATE;

    public AllQuestionAdapter(Context context, List<Question> questionList, QuestionDao questionDao) {
        this.context = context;
        this.questionList = questionList;
        this.questionDao = questionDao;
    }

    @Override
    public SingleQuestionView onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_question_layout, parent, false);

        return new SingleQuestionView(row);
    }

    @Override
    public void onBindViewHolder(SingleQuestionView holder, final int position) {

        final Question question = questionList.get(position);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String categoryString = "Category: " + question.getCategory().getValue();
        holder.single_question_layout_category.setText(categoryString);

        final String questionString = question.getQuestion();
        holder.single_question_layout_question.setText(questionString);

        String actuallyLevelString = "Actually level: " + question.getLevel();
        holder.single_question_layout_level.setText(actuallyLevelString);

        String nextRepeatString = "Next repeat: " + df.format(question.getNextRepeat());
        holder.single_question_layout_nextRepeat.setText(nextRepeatString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                notifyDataSetChanged();
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
                                                questionList.remove(position);
                                                notifyDataSetChanged();

                                            }
                                        }).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class SingleQuestionView extends RecyclerView.ViewHolder {

        TextView single_question_layout_category;
        TextView single_question_layout_question;
        TextView single_question_layout_level;
        TextView single_question_layout_nextRepeat;

        public SingleQuestionView(View itemView) {
            super(itemView);

            single_question_layout_category = (TextView) itemView.findViewById(R.id.single_question_layout_category);
            single_question_layout_question = (TextView) itemView.findViewById(R.id.single_question_layout_question);
            single_question_layout_level = (TextView) itemView.findViewById(R.id.single_question_layout_level);
            single_question_layout_nextRepeat = (TextView) itemView.findViewById(R.id.single_question_layout_nextRepeat);
        }
    }
}
