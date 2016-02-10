package com.mvw.wordpower.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.mvw.wordpower.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import common.Constants;
import fragments.QuizFragment;
import fragments.ScoreFragment;

public class ContentActivity extends AppCompatActivity
                             implements QuizFragment.OnQuizFragmentInteractionListener,
                                        ScoreFragment.OnScoreFragmentInteractionListener{

    int mQuestionCount;
    int mScoreCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        initializeData();
        initializeToolbar();
        addQuizFragment();
        queryQuestions();
    }

    private void initializeData(){

        this.mQuestionCount = 1;
        this.mScoreCount = 0;
    }

    private void initializeToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addQuizFragment(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        QuizFragment fragment = QuizFragment.newInstance(mQuestionCount);
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.add(R.id.container, fragment, Constants.QUIZ_FRAGMENT_TAG);
        transaction.commit();

        manager = null;
        transaction = null;
    }

    private void replaceQuizFragment(){

        mQuestionCount++;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        QuizFragment oldFragment = (QuizFragment)manager.findFragmentByTag(Constants.QUIZ_FRAGMENT_TAG);
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.remove(oldFragment);

        QuizFragment newFragment = QuizFragment.newInstance(mQuestionCount);

        transaction.add(R.id.container, newFragment, Constants.QUIZ_FRAGMENT_TAG);
        transaction.commit();

        manager = null;
        transaction = null;
    }

    @Override
    public void onQuizFragmentInteraction(Uri uri) {
        // replace QuizFragment with ScoreFragment

    }

    @Override
    public void onQuizFragmentNextButton(boolean isCorrect) {

        if(isCorrect) mScoreCount++;
        replaceQuizFragment();
    }

    @Override
    public void onQuizFragmentGiveUpButton() {

        showScoreFragment();
    }

    @Override
    public void onScoreFragmentInteraction(Uri uri) {

    }

    private void showScoreFragment(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // I don't trust the replace function so I remove and add manually these fragments
        QuizFragment quizFragment  = (QuizFragment)manager.findFragmentByTag(Constants.QUIZ_FRAGMENT_TAG);
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        transaction.remove(quizFragment);
        ScoreFragment scoreFragment = ScoreFragment.newInstance(this.mScoreCount);
        transaction.add(R.id.container,scoreFragment,Constants.SCORE_FRAGMENT_TAG);
        transaction.commit();
    }

    private void queryQuestions(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Geography");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.d("question",Integer.toString(list.size()));

                    for(ParseObject object : list){
                        try {
                            Log.v("question", object.getString("question"));
                            Log.v("options", object.getJSONArray("options").toString());
                            Log.v("answer", object.getString("answer"));
                            Log.v("trivia", object.getString("trivia"));
                        }
                        catch(Exception exception){
                            exception.printStackTrace();
                        }
                    }
                } else {
                    Log.d("score2", "Error: " + e.getMessage());
                }
            }
        });
    }
}
