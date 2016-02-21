package models;

import java.util.List;

/**
 * Created by rahul on 20-02-2016.
 */
public class History extends QuizSection {

    private static History mHistory;

    // create private constructor to make it Singleton
    private History(){};

    // return synchronized version of science object.
    public static synchronized History getInstance(){

        if(mHistory == null) {
            mHistory = new History();
        }
        return mHistory;
    }
}
