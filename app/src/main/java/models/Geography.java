package models;

import java.util.List;

/**
 * Created by rahul on 20-02-2016.
 */
public class Geography extends QuizSection {

    private static Geography mGeography;
    // create private constructor to make it Singleton
    private Geography(){};

    // return synchronized version of science object.
    public static synchronized Geography getInstance(){

        if(mGeography == null) {
            mGeography = new Geography();
        }
        return mGeography;
    }
}
