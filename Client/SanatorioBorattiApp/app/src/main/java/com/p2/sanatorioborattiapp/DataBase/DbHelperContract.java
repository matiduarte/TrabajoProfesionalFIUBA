package com.p2.sanatorioborattiapp.DataBase;

import android.provider.BaseColumns;

public final class DbHelperContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DbHelperContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String USER_ID = "userId";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PASSWORD = "password";
        public static final String IS_LOGGED = "isLogged";

    }
}