package models;

import java.util.List;

/**
 * Created by rahul on 20-02-2016.
 */

public class Science extends QuizSection{

    private static Science mScience;

    // create private constructor to make it Singleton
    private Science(){};

    // return synchronized version of science object.
    public static synchronized Science getInstance(){

        if(mScience == null) {
            mScience = new Science();
        }
        return mScience;
    }
}
