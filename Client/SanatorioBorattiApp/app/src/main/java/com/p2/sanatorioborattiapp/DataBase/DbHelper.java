package com.p2.sanatorioborattiapp.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.p2.sanatorioborattiapp.Entities.User;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String LONG_TYPE = " LONG";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + DbHelperContract.UserEntry.TABLE_NAME + " (" +
                    DbHelperContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    DbHelperContract.UserEntry.USER_ID + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.PASSWORD + TEXT_TYPE + COMMA_SEP +
                    DbHelperContract.UserEntry.IS_LOGGED + INT_TYPE +
                    " );";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + DbHelperContract.UserEntry.TABLE_NAME + ";";

    // If you change the database schema, you must increment the database version.

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SanatorioBoratti.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        if(newVersion > oldVersion) {
            db.execSQL(SQL_DELETE_ENTRIES_USER);
            onCreate(db);
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion);
    }


    public long insertUser(User user){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Si ya existe, lo actualizo. sino lo crea.
        User oldUser = this.getUserByUserId(user.getUserId());
        if(oldUser.getId() != 0) {
            if(oldUser.getIsLogged()== 0 && user.getIsLogged() > 0){
                //El usuario se esta logueando
                clearSessionData(db);
            }

            user.setId(oldUser.getId());
            this.updateUser(user);
            return user.getId();
        }

        if(user.getIsLogged() > 0){
            clearSessionData(db);
        }

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.UserEntry.USER_ID, user.getUserId());
        values.put(DbHelperContract.UserEntry.FIRST_NAME, user.getFirstName());
        values.put(DbHelperContract.UserEntry.LAST_NAME, user.getLastName());
        values.put(DbHelperContract.UserEntry.PASSWORD, user.getPassword());
        values.put(DbHelperContract.UserEntry.IS_LOGGED, user.getIsLogged());



        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DbHelperContract.UserEntry.TABLE_NAME,
                DbHelperContract.UserEntry._ID,
                values);

        return newRowId;
    }

    private void clearSessionData(SQLiteDatabase db) {
        db.execSQL("Update "+ DbHelperContract.UserEntry.TABLE_NAME + " SET " + DbHelperContract.UserEntry.IS_LOGGED
                + " = 0");
    }


    public void updateUser(User user){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbHelperContract.UserEntry.USER_ID, user.getUserId());
        values.put(DbHelperContract.UserEntry.FIRST_NAME, user.getFirstName());
        values.put(DbHelperContract.UserEntry.LAST_NAME, user.getLastName());
        values.put(DbHelperContract.UserEntry.PASSWORD, user.getPassword());
        values.put(DbHelperContract.UserEntry.IS_LOGGED, user.getIsLogged());


        // Define 'where' part of query.
        String selection = DbHelperContract.UserEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(user.getId()) };

        db.update(DbHelperContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);

    }


    public User getUserByUserId(int userId){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DbHelperContract.UserEntry._ID,
                DbHelperContract.UserEntry.USER_ID,
                DbHelperContract.UserEntry.FIRST_NAME,
                DbHelperContract.UserEntry.LAST_NAME,
                DbHelperContract.UserEntry.PASSWORD,
                DbHelperContract.UserEntry.IS_LOGGED,

        };

        String selection = DbHelperContract.UserEntry.USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor c = db.query(
                DbHelperContract.UserEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if (c != null && c.moveToFirst()) {

            while (c.isAfterLast() == false) {
                long id = c.getLong(c.getColumnIndex(DbHelperContract.UserEntry._ID));
                String firstName = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.FIRST_NAME));
                String lastName = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.LAST_NAME));
                String password = c.getString(c.getColumnIndex(DbHelperContract.UserEntry.PASSWORD));
                int isLogged = c.getInt(c.getColumnIndex(DbHelperContract.UserEntry.IS_LOGGED));

                User user = new User(id, userId, firstName, lastName, password);
                user.setIsLogged(isLogged);
                return user;
            }
        }
        //Si no está devuelvo uno default.
        return new User();
    }
}
