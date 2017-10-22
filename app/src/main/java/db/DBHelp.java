package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 13.10.2017.
 */

public class DBHelp extends SQLiteOpenHelper {

    private static String DB_PATH;

    public DBHelp(Context context) {
        // конструктор суперкласса
        super(context, "members", null, 10);
        DB_PATH = context.getDatabasePath("members").getAbsolutePath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table Members ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "surname text not null,"
                + "age integer not null,"
                + "type text not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table Members ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "surname text not null"
                + "age integer not null"
                + "type text not null" + ");");
    }
}

