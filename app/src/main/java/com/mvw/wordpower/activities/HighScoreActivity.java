package com.mvw.wordpower.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mvw.wordpower.R;

import java.util.List;

import common.Constants;
import models.Art;
import models.Geography;
import models.History;
import models.Science;
import models.Sports;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initNestedScrollView();
    }


    private void initNestedScrollView(){

        LinearLayout containerView = (LinearLayout)findViewById(R.id.nestedscrollviewcontainer);
        LayoutInflater inflater = getLayoutInflater();

        int length = Constants.QUIZ_MENU_LIST.length;

        for(int index=0; index<length; index++){

            String tagName = Constants.QUIZ_MENU_LIST[index];
            LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.high_score_element, containerView, true);
            LinearLayout subLayout = (LinearLayout)layout.getChildAt(index);
            TextView title = (TextView)subLayout.getChildAt(0);
            //Log.v("layout",subLayout.getChildAt(1).toString());
            title.setText(tagName);
            setSectionScores(index,subLayout);

        }
    }

    private void setSectionScores(int index,LinearLayout layout){

        switch(index){
            // Geography
            case 0:
                List<Integer> geographyScores = Geography.getInstance().getScores();
                setScores(geographyScores,layout);
                break;
            // Science
            case 1:
                List<Integer> scienceScores = Science.getInstance().getScores();
                setScores(scienceScores,layout);
                break;
            // Art
            case 2:
                List<Integer> artScores = Art.getInstance().getScores();
                setScores(artScores,layout);
                break;
            // History
            case 3:
                List<Integer> historyScores = History.getInstance().getScores();
                setScores(historyScores,layout);
                break;
            case 4:
                List<Integer> sportsScores = Sports.getInstance().getScores();
                setScores(sportsScores,layout);
                break;

        }

    }


    private void setScores(List<Integer> scores, LinearLayout layout){

        for(int i=1; i<=5; i++){
            LinearLayout subLayout = (LinearLayout)layout.getChildAt(i);
            TextView textView = (TextView)subLayout.getChildAt(1);
            int score = scores.get(i-1);
            if(score > -1)
                textView.setText(scores.get(i - 1).toString());
        }
    }


}
