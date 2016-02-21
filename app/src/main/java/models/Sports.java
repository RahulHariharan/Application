package models;

import java.util.List;

/**
 * Created by rahul on 20-02-2016.
 */
public class Sports extends QuizSection {

    private static Sports mSports;

    // create private constructor to make it Singleton
    private Sports(){};

    // return synchronized version of science object.
    public static synchronized Sports getInstance(){

        if(mSports == null) {
            mSports = new Sports();
        }
        return mSports;
    }
}
