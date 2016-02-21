package models;

import java.util.List;

/**
 * Created by rahul on 20-02-2016.
 */
public class Art extends QuizSection{

    private static Art mArt;

    // create private constructor to make it Singleton
    private Art(){};

    // return synchronized version of science object.
    public static synchronized Art getInstance(){

        if(mArt == null) {
            mArt = new Art();
        }
        return mArt;
    }
}
