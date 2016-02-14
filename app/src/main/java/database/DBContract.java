package database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rahul on 11-02-2016.
 */
public class DBContract {

    public DBContract() {}

    // authority of content provider
    public static final String CONTENT_AUTHORITY = "com.mvw.wordpower";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_GEOGRAPHY = "geography";

    /* Inner class that defines the table contents */
    public static final class GeographyEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GEOGRAPHY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GEOGRAPHY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GEOGRAPHY;

        public static final String TABLE_NAME = "GEOGRAPHY";
        public static final String QUESTION = "question";
        public static final String OPTIONS = "options";
        public static final String ANSWER = "answer";
        public static final String TRIVIA = "trivia";

        public static Uri buildGeographyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    

}
