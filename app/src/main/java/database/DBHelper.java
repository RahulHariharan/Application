package database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rahul on 11-02-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    // mandatory constructor for this class
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    // Constructor to use to initialize database
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION,null);
    }

    private static final String DATABASE_NAME ="QUIZ_DATABASE.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT NOT NULL";
    private static final String INTEGER_TYPE = " INTEGER NOT NULL";
    private static final String REAL_TYPE = " REAL NOT NULL";
    private static final String BLOB_TYPE = " BLOB NOT NULL";

    private static final String COMMA_SEPARATOR = " ,";

    private static final String CREATE_GEOGRAPHY_TABLE =
            "CREATE TABLE " +
                    DBContract.GeographyEntry.TABLE_NAME + " (" +
                    DBContract.GeographyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
                    DBContract.GeographyEntry.QUESTION + TEXT_TYPE + COMMA_SEPARATOR +
                    DBContract.GeographyEntry.OPTIONS + BLOB_TYPE + COMMA_SEPARATOR +
                    DBContract.GeographyEntry.TRIVIA + TEXT_TYPE + COMMA_SEPARATOR +
                    DBContract.GeographyEntry.ANSWER + TEXT_TYPE + " )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GEOGRAPHY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DBContract.GeographyEntry.TABLE_NAME);
        onCreate(db);
    }
}
