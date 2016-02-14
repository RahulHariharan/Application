package com.mvw.wordpower.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.mvw.wordpower.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import adapters.QuizSyncAdapter;
import adapters.RecyclerAdapter;
import common.Constants;
import fragments.QuizFragment.OnQuizFragmentInteractionListener;
import singletons.ParseInitializerSingleton;

public class MenuActivity extends AppCompatActivity
                          implements OnQuizFragmentInteractionListener{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initParse();
        initToolbar();
        initFAB();
        initRecyclerView();

        QuizSyncAdapter.initializeSyncAdapter(this);
        
        // to be removed
        //Intent intent = new Intent(this,ContentActivity.class);
        //startActivity(intent);
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

    }
}
