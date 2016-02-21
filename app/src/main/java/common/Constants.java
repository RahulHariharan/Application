package common;

/**
 * Created by rahul on 19-01-2016.
 */
public class Constants {

    public static String[] QUIZ_MENU_LIST = {"Geography",
                                             "Science",
                                             "Art",
                                             "History",
                                             "Sports"};


    // Tag names for fragments
    public static String QUIZ_FRAGMENT_TAG = "QUIZ_FRAGMENT_TAG";
    public static String SCORE_FRAGMENT_TAG = "SCORE_FRAGMENT_TAG";

    public static final int TOTAL_QUESTION_COUNT = 5;
    public static final int PROGRESS_BAR_MAX = 4000; // representing milliseconds

    // keys corresponding to Parse database
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String OPTIONS = "options";
    public static final String TRIVIA = "trivia";


    // JOB IDs
    public static final int JOB_ID = 100;

    // for JSONObject score keys
    public static final String FIRST_SCORE = "firstscore";
    public static final String SECOND_SCORE = "secondscore";
    public static final String THIRD_SCORE = "thirdscore";
    public static final String FOURTH_SCORE = "fourthscore";
    public static final String FIFTH_SCORE = "fifthscore";

    // key to pass and retrieve quiz_category
    public static final String QUIZ_CATEGORY = "quiz_cateogory";

}
