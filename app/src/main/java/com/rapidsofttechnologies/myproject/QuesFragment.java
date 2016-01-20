package com.rapidsofttechnologies.myproject;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

/**
 * Created by AND-18 on 11/16/2015.
 */
public class QuesFragment extends Fragment {
    RadioButton radio_option1,radio_option2,radio_option3,radio_option4,radio_option5;
    String user_Answer, user_Answer_Text,correct_answer_index,correct_Answer_Text;
    int quesNo;
    CountDownTimer countDownTimer;
    Boolean isTimerRunning = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView quesTextView;
        final TextView timerTextView;
        RadioGroup radioGroup; Button nextButton;

        int duration = getArguments().getInt("DURATION");
        String ques = getArguments().getString("QUESTION");
        String option1 = getArguments().getString("OPTION1");
        String option2 = getArguments().getString("OPTION2");
        String option3 = getArguments().getString("OPTION3");
        String option4 = getArguments().getString("OPTION4");
        String option5 = getArguments().getString("OPTION5");
        correct_answer_index = getArguments().getString("CORRECT_ANSWER_INDEX");
        quesNo = getArguments().getInt("QUESTION_NO");

        View quesFragmentView = inflater.inflate(R.layout.fragment_ques, container, false);

        timerTextView = (TextView)quesFragmentView.findViewById(R.id.timer_text_view);

        countDownTimer = new CountDownTimer(duration*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerRunning=true;
                timerTextView.setText(""+millisUntilFinished/1000);
                if(millisUntilFinished<=10000){
                    timerTextView.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
            @Override
            public void onFinish() {
                isTimerRunning=false;
                timerTextView.setText("--");
                Toast.makeText(getActivity()," Time's up!",Toast.LENGTH_SHORT).show();
                updateResult();
            }
        }.start();

        quesTextView = (TextView)quesFragmentView.findViewById(R.id.ques_text_view);
        quesTextView.setText("Question - "+quesNo+" :\n"+ ques);

        radioGroup = (RadioGroup)quesFragmentView.findViewById(R.id.radioGroup);
        radio_option1 =(RadioButton)quesFragmentView.findViewById(R.id.radio_option1);
        radio_option2 =(RadioButton)quesFragmentView.findViewById(R.id.radio_option2);
        radio_option3 =(RadioButton)quesFragmentView.findViewById(R.id.radio_option3);
        radio_option4 =(RadioButton)quesFragmentView.findViewById(R.id.radio_option4);
        radio_option5 =(RadioButton)quesFragmentView.findViewById(R.id.radio_option5);
        radio_option1.setText(option1);
        radio_option2.setText(option2);
        radio_option3.setText(option3);
        radio_option4.setText(option4);
        radio_option5.setText(option5);

        radio_option1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (radio_option1.isChecked()) {
                    user_Answer_Text = radio_option1.getText().toString();
                    user_Answer="Option1";
                }
            }
        });

        radio_option2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(radio_option2.isChecked()){
                    user_Answer_Text=radio_option2.getText().toString();
                    user_Answer="Option2";
                }
            }
        });

        radio_option3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(radio_option3.isChecked()){
                    user_Answer_Text=radio_option3.getText().toString();
                    user_Answer="Option3";
                }
            }
        });

        radio_option4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(radio_option4.isChecked()){
                    user_Answer_Text=radio_option4.getText().toString();
                    user_Answer="Option4";
                }
            }
        });

        radio_option5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(radio_option5.isChecked()){
                    user_Answer_Text=radio_option5.getText().toString();
                    user_Answer="Option5";
                }
            }


        });


        nextButton = (Button)quesFragmentView.findViewById(R.id.next_button);
        if(quesNo==((MainActivity)getActivity()).totalNoOfQuestions)
        {
            nextButton.setText("Submit");
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_Answer==null && isTimerRunning){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder .setMessage("Please Select an answer")
                            .setPositiveButton("OK",null)
                            .create().show();
                }
                else {
                    countDownTimer.cancel();
                    timerTextView.setText("--");
                    updateResult();
                }
            }
        });

        return quesFragmentView;
       }

  /*  @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        if (isTimerRunning) {
                            countDownTimer.cancel();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }*/

    private void showResultFragment() {
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TOTAL_QUESTIONS",((MainActivity)getActivity()).totalNoOfQuestions);
        bundle.putInt("CORRECT_QUESTIONS", ((MainActivity)getActivity()).correctNoOfQuestions);
        bundle.putInt("WRONG_QUESTIONS", ((MainActivity)getActivity()).wrongNoOfQuestions);
        resultFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame, resultFragment).commit();
    }

    private void updateResult() {
        final AlertDialog.Builder builder =new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        moveToNext();
                    }
                })
                .setTitle("RESULT")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToNext();
                    }
                });
        if (user_Answer==null){
            getCorrectAnswerText();
            builder .setMessage(" Not Answered \n Correct Answer ->\n"+correct_answer_index+" : " +correct_Answer_Text)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveToNext();
                        }
                    })
                    .create().show();
            }
        else if(user_Answer.equals(correct_answer_index)){
            ((MainActivity)getActivity()).correctNoOfQuestions++;
            builder .setMessage(" Correct Answer ")
                    .setIcon(R.mipmap.ic_correct)
                    .create().show();
        }
        else {
            ((MainActivity)getActivity()).wrongNoOfQuestions++;
            getCorrectAnswerText();
            builder .setMessage(" Wrong Answer \n Your Answer ->\n"+user_Answer+" : "+user_Answer_Text+"\n\n Correct Answer-> \n"+correct_answer_index+" : "+correct_Answer_Text)
                    .setIcon(R.mipmap.ic_wrong)
                    .create().show();
        }
    }

    private void moveToNext() {
        if(quesNo<((MainActivity)getActivity()).totalNoOfQuestions){
            try {
                ((MainActivity)getActivity()).showQuesFragment();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            showResultFragment();
        }
    }

    private void getCorrectAnswerText() {
        switch (correct_answer_index){
            case "Option1":
                correct_Answer_Text = radio_option1.getText().toString();
                break;

            case "Option2":
                correct_Answer_Text = radio_option2.getText().toString();
                break;

            case "Option3":
                correct_Answer_Text = radio_option3.getText().toString();
                break;

            case "Option4":
                correct_Answer_Text = radio_option4.getText().toString();
                break;

            case "Option5":
                correct_Answer_Text = radio_option5.getText().toString();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
