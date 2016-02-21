package common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mvw.wordpower.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import models.Art;
import models.Geography;
import models.History;
import models.QuizSection;
import models.Science;
import models.Sports;

/**
 * Created by rahul on 21-02-2016.
 */
public class Common {

    public static List<Integer> getScores(String scores){

        List<Integer> list = new ArrayList<Integer>();
        try {

            JSONObject jsonObject = new JSONObject(scores);
            // 5 represents the number of scores to parse
            if (jsonObject != null && jsonObject.length() == 5) {

                int firstScore = jsonObject.getInt(Constants.FIRST_SCORE);
                int secondScore = jsonObject.getInt(Constants.SECOND_SCORE);
                int thirdScore = jsonObject.getInt(Constants.THIRD_SCORE);
                int fourthScore = jsonObject.getInt(Constants.FOURTH_SCORE);
                int fifthScore = jsonObject.getInt(Constants.FIFTH_SCORE);

                // add scores to list
                list.add(firstScore);
                list.add(secondScore);
                list.add(thirdScore);
                list.add(fourthScore);
                list.add(fifthScore);

            } else
                return null;

        }catch(JSONException exception){
            // TODO: do something here
        }

        return list;
    }

    // returns scores stored in List<String> as JSON to store in SharedPreferences
    public static JSONObject jsonScores(List<Integer> list){

        JSONObject jsonObject = new JSONObject();
        try {
            // 5 represents the number of scores to save
            if (list != null && list.size() == 5) {

                jsonObject.put(Constants.FIRST_SCORE, list.get(0));
                jsonObject.put(Constants.SECOND_SCORE, list.get(1));
                jsonObject.put(Constants.THIRD_SCORE, list.get(2));
                jsonObject.put(Constants.FOURTH_SCORE, list.get(3));
                jsonObject.put(Constants.FIFTH_SCORE, list.get(4));
            }
        }catch(JSONException exception){
            // TODO: DO SOMETHING HERE
        }

        return jsonObject;
    }

    public static JSONObject setScore(String category,int score,SharedPreferences preferences){

        String previousScores = preferences.getString(category,null);

        if(previousScores != null){

            List<Integer> scoreList = getScores(previousScores);
            Collections.sort(scoreList);

            int length = scoreList.size();
            int index = -1;
            for(int i=0; i<length; i++){

                if(score >= scoreList.get(i)){
                    index = i;
                    break;
                }
            }

            if(index != -1){
                // add new score to indexed position
                scoreList.add(index,score);
                // remove the last score as the length of the list
                // has increased to 6 as a result of the previous operation
                if(scoreList.size() > 0)
                scoreList.remove(scoreList.size()-1);
            }

            JSONObject object = jsonScores(scoreList);

            return object;

        }
        else{
            // TODO: do something if scores are null
            return null;
        }
    }

    // init sports scores when app is installed for the first time
    public static void initPreference(SharedPreferences preferences,
                                      String sectionKey,
                                      QuizSection quizSection){

        if(preferences == null){
            throw new NullPointerException();
        }
        else {
            if (preferences.contains(sectionKey)){

                String sectionScores = preferences.getString(sectionKey, null);
                if(sectionScores != null){

                    List<Integer> list = Common.getScores(sectionScores);

                    if(quizSection  instanceof Geography){
                        Geography geographyObject = Geography.getInstance();
                        geographyObject.setScores(list);
                    }
                    else if(quizSection instanceof Science){
                        Science scienceObject = Science.getInstance();
                        scienceObject.setScores(list);
                    }
                    else if(quizSection instanceof Art){
                        Art artObject = Art.getInstance();
                        artObject.setScores(list);
                    }
                    else if(quizSection instanceof History){
                        History historyObject = History.getInstance();
                        historyObject.setScores(list);
                    }
                    else if(quizSection instanceof Sports) {
                        Sports sportsObject = Sports.getInstance();
                        sportsObject.setScores(list);
                    }
                }
                else{
                    // TODO: do something here
                }
            } else {

                String scores = getInitialScores().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(sectionKey,scores);
                editor.apply();

                if(quizSection  instanceof Geography){
                    Geography geographyObject = Geography.getInstance();
                    geographyObject.setScores(getScores(scores));
                }
                else if(quizSection instanceof Science){
                    Science scienceObject = Science.getInstance();
                    scienceObject.setScores(getScores(scores));
                }
                else if(quizSection instanceof Art){
                    Art artObject = Art.getInstance();
                    artObject.setScores(getScores(scores));
                }
                else if(quizSection instanceof History){
                    History historyObject = History.getInstance();
                    historyObject.setScores(getScores(scores));
                }
                else if(quizSection instanceof Sports) {
                    Sports sportsObject = Sports.getInstance();
                    sportsObject.setScores(getScores(scores));
                }

            }
        }
    }

    // helper method for initialization
    private static JSONObject getInitialScores(){

        try{
            JSONObject jobject = new JSONObject();
            jobject.put(Constants.FIRST_SCORE,-1);
            jobject.put(Constants.SECOND_SCORE,-1);
            jobject.put(Constants.THIRD_SCORE,-1);
            jobject.put(Constants.FOURTH_SCORE,-1);
            jobject.put(Constants.FIFTH_SCORE,-1);

            return jobject;

        }catch(JSONException exception){

            return null;
        }
    }
}
