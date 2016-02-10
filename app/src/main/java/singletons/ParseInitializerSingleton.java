package singletons;

import android.content.Context;

import com.parse.Parse;

/**
 * Created by rahul on 10-02-2016.
 */
public class ParseInitializerSingleton {

    private static ParseInitializerSingleton mParseInitializer;

    // make constructor private
    private ParseInitializerSingleton(Context context){
        Parse.initialize(context);
    }

    // synchronize ParseInitializerSingleton
    public static synchronized ParseInitializerSingleton getInstance(Context context){

        if(mParseInitializer == null)
            mParseInitializer = new ParseInitializerSingleton(context);
        return mParseInitializer;
    }
}
