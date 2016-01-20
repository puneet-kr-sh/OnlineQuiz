package com.rapidsofttechnologies.myproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResultFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int totalQues, correctQues,wrongQues, attemptedQues;
        totalQues = getArguments().getInt("TOTAL_QUESTIONS");
        correctQues = getArguments().getInt("CORRECT_QUESTIONS");
        wrongQues = getArguments().getInt("WRONG_QUESTIONS");
        attemptedQues = correctQues+wrongQues;
        View resultView = inflater.inflate(R.layout.fragment_result,container,false);
        TextView totalQuesNoView = (TextView)resultView.findViewById(R.id.total_ques_no);
        totalQuesNoView.setText(""+totalQues);
        TextView answeredQuesNoView = (TextView)resultView.findViewById(R.id.answered_ques_no);
        answeredQuesNoView.setText(""+attemptedQues);
        TextView correctQuesNoView = (TextView)resultView.findViewById(R.id.correct_ques_no);
        correctQuesNoView.setText(""+correctQues);
        TextView wrongQuesNoView = (TextView)resultView.findViewById(R.id.wrong_ques_no);
        wrongQuesNoView.setText(""+wrongQues);

        Button exitButton = (Button)resultView.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        Button restartButton = (Button)resultView.findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onRestart();
            }
        });


        return resultView;
    }
}
