package com.mvw.wordpower.activities;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mvw.wordpower.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import adapters.QuizSyncAdapter;
import adapters.RecyclerAdapter;
import common.Common;
import common.Constants;
import fragments.QuizFragment.OnQuizFragmentInteractionListener;
import listeners.RecyclerItemClickListener;
import models.Art;
import models.Geography;
import models.History;
import models.QuizSection;
import models.Science;
import models.Sports;
import services.QuizJobService;
import singletons.ParseInitializerSingleton;

public class MenuActivity extends AppCompatActivity
                          implements OnQuizFragmentInteractionListener{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initParse();
        initToolbar();
        initFAB();
        initRecyclerView();
        initScores();


        // TODO: IllegalStateException occurs here
        if(Build.VERSION.SDK_INT < 22)
            QuizSyncAdapter.initializeSyncAdapter(this);

        else if(Build.VERSION.SDK_INT >= 22)
            setupJob();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onQuizFragmentInteraction(Uri uri) {

    }

    @Override
    public void onQuizFragmentNextButton(boolean isCorrect) {

    }

    @Override
    public void onQuizFragmentGiveUpButton() {

    }

    private void initParse(){
        ParseInitializerSingleton.getInstance(getApplicationContext());
        /* this is to test if data is retrived from database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Geography");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for(ParseObject object : scoreList){
                        Log.v("question",object.getString(Constants.QUESTION));
                        Log.v("answer",object.getString(Constants.ANSWER));
                        Log.v("options",object.getJSONArray(Constants.OPTIONS).toString());
                        Log.v("trivia",object.getString(Constants.TRIVIA));
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/
    }

    private void initToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    private void initFAB(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initRecyclerView(){

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);

                        switch (position) {
                            // geography
                            case 0:
                                intent.putExtra(Constants.QUIZ_CATEGORY, getString(R.string.geography));
                                break;
                            // science
                            case 1:
                                intent.putExtra(Constants.QUIZ_CATEGORY, getString(R.string.science));
                                break;
                            //art
                            case 2:
                                intent.putExtra(Constants.QUIZ_CATEGORY, getString(R.string.art));
                                break;
                            // history
                            case 3:
                                intent.putExtra(Constants.QUIZ_CATEGORY, getString(R.string.history));
                                break;
                            // sports
                            case 4:
                                intent.putExtra(Constants.QUIZ_CATEGORY, getString(R.string.sports));
                                break;
                        }
                        startActivity(intent);
                    }
                })
        );

    }

    private void setupJob() {
        mJobScheduler = (JobScheduler)getSystemService(Context.JOB_SCHEDULER_SERVICE);
        //set an initial delay with a Handler so that the data loading by the JobScheduler does not clash with the loading inside the Fragment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //schedule the job after the delay has been elapsed
                buildJob();
            }
        }, 10000);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void buildJob() {

        ComponentName serviceName = new ComponentName(this, QuizJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(Constants.JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(true)
                .build();
        int result = mJobScheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) Log.d("TAG", "Job scheduled successfully!");
    }

    private void initScores(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.preference_key), Context.MODE_PRIVATE);

                    // length of these arrays should match
                    // and values MUST correspond to each other
                    String[] sectionKeys = getApplicationContext().
                                           getResources().
                                           getStringArray(R.array.quiz_keys);

                    QuizSection[] quizSections = {Geography.getInstance(),
                                                  Science.getInstance(),
                                                  Art.getInstance(),
                                                  History.getInstance(),
                                                  Sports.getInstance()};

                    // length must be the same as sectionKeys.
                    int length = quizSections.length;
                    if(sectionKeys.length == quizSections.length){

                        for(int index =0; index <length; index++){
                            Common.initPreference(preferences,
                                                  sectionKeys[index],
                                                  quizSections[index]);
                        }
                    }

                }
                catch(NullPointerException exception){

                }
                catch(Exception exception){

                    exception.printStackTrace();
                }

            }
        }).start();
    }



}
