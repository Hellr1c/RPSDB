package alvaro.mt.midterm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "history.db";
    public static final String DB_TABLE = "history";
    public static final String DB_PLAYER = "Player";
    public static final String DB_CPU = "CPU";
    public static final String DB_WIN = "Winner";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE history " +
                        "(id INTEGER PRIMARY KEY," +
                        "Player TEXT," +
                        "CPU TEXT," +
                        "Winner TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
    }

    public boolean insertHistory(String player, String cpu, String winner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Player", player);
        values.put("CPU", cpu);
        values.put("Winner", winner);

        db.insert("history", null, values);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM history WHERE id = " + String.valueOf(id), null);
        return res;
    }


    @SuppressLint("Range")
    public ArrayList<String> getFullHistory() {
        ArrayList<String> items = new ArrayList<String>();
        ArrayList<Integer> player = new ArrayList<Integer>();
        ArrayList<Integer> cpu = new ArrayList<Integer>();
        ArrayList<Integer> results = new ArrayList<Integer>();

        SQLiteDatabase conn = this.getReadableDatabase();
        Cursor rs = conn.rawQuery("SELECT * FROM history", null);
        rs.moveToFirst();

        while (!rs.isAfterLast()) {
            player.add(rs.getInt(rs.getColumnIndex(DB_PLAYER)));
            cpu.add(rs.getInt(rs.getColumnIndex(DB_CPU)));
            results.add(rs.getInt(rs.getColumnIndex(DB_WIN)));

            items.add("Player: " + rs.getString(1) + " CPU: " + rs.getString(2) + " Winner: " + rs.getString(3));
            rs.moveToNext();
        }

        return items;
    }

    public int getWins () {
        int wins = 0;
        ArrayList<Integer> results = new ArrayList<Integer>();
        SQLiteDatabase conn = this.getReadableDatabase();
        Cursor rs = conn.rawQuery("SELECT * FROM history WHERE Winner = 'Player'", null);
        rs.moveToFirst();

        while (!rs.isAfterLast()) {
            wins++;
            rs.moveToNext();
        }
        return wins;
    }

    public int getLoss () {
        int loss = 0;
        ArrayList<Integer> results = new ArrayList<Integer>();
        SQLiteDatabase conn = this.getReadableDatabase();
        Cursor rs = conn.rawQuery("SELECT * FROM history WHERE Winner = 'CPU'", null);
        rs.moveToFirst();

        while (!rs.isAfterLast()) {
            loss++;
            rs.moveToNext();
        }
        return loss;
    }
}