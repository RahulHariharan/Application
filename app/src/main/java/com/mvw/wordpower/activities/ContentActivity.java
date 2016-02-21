package com.mvw.wordpower.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import database.DBContract;
import fragments.QuizFragment;
import fragments.ScoreFragment;

public class ContentActivity extends AppCompatActivity
                             implements QuizFragment.OnQuizFragmentInteractionListener,
                                        ScoreFragment.OnScoreFragmentInteractionListener,
                                        LoaderManager.LoaderCallbacks<Cursor>{

    int mQuestionCount;
    int mScoreCount;
    String mQuizCategory;

    static final int URI_LOADER = 0;

    private static final String[] FORECAST_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            DBContract.GeographyEntry.TABLE_NAME + "." + DBContract.GeographyEntry._ID,
            DBContract.GeographyEntry.QUESTION,
            DBContract.GeographyEntry.OPTIONS,
            DBContract.GeographyEntry.ANSWER,
            DBContract.GeographyEntry.TRIVIA,
    };

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_GEOGRAPHY_ID = 0;
    static final int COL_GEOGRAPHY_QUESTION = 1;
    static final int COL_GEOGRAPHY_OPTIONS = 2;
    static final int COL_GEOGRAPHY_ANSWER = 3;
    static final int COL_GEOGRAPHY_TRIVIA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        initializeData();
        initializeLoader();
        initializeToolbar();
        addQuizFragment();
        queryQuestions();

    }

    private void initializeData(){
        this.mQuestionCount = 1;
        this.mScoreCount = 0;
        // get quiz category
        Intent intent = getIntent();
        if(intent != null){
            mQuizCategory = intent.getStringExtra(Constants.QUIZ_CATEGORY);
        }
    }

    private void initializeLoader(){
        getSupportLoaderManager().initLoader(URI_LOADER,null,this);
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
        ScoreFragment scoreFragment = ScoreFragment.newInstance(this.mScoreCount,this.mQuizCategory);
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

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        switch(loaderId) {

            case URI_LOADER:

                Uri geographyUri = DBContract.GeographyEntry.CONTENT_URI;
                return new CursorLoader(this,
                        geographyUri,
                        FORECAST_COLUMNS,
                        null,
                        null,
                        null);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
