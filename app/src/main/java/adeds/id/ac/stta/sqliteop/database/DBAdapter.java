package adeds.id.ac.stta.sqliteop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by multimedia 6 on 4/4/2018.
 */

public class DBAdapter {
    DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertData(String uname, String pass) {
        Log.e("getIns", uname+ " "+ pass);

        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME_COL, uname);
        contentValues.put(DBHelper.PASS_COL, pass);
        long id_ins =0;
        try {

             id_ins = dbb.insert(dbHelper.TB_NAME, null, contentValues);
        }catch (SQLException e){
           dbHelper.onkrit(dbb);
            id_ins = dbb.insert(dbHelper.TB_NAME, null, contentValues);
            Log.e("errInsAdapter", e.getMessage().toString());}
        dbb.close();
            return id_ins;
    }

    public String getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] kolom = {DBHelper.UID, DBHelper.NAME_COL, DBHelper.PASS_COL};
        Cursor cursor = db.query(DBHelper.TB_NAME, kolom, null, null, null, null, DBHelper.NAME_COL, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
           int cid = cursor.getInt(cursor.getColumnIndex(DBHelper.UID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL));
            String pass = cursor.getString(cursor.getColumnIndex(DBHelper.PASS_COL));
            buffer.append(cid+"."+name + " " + " password " + pass + "\n");
        }
        return buffer.toString();
    }

    public int delete(String uname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] where = {uname};
        int count = db.delete(DBHelper.TB_NAME, DBHelper.NAME_COL + " = ?", where);

        return count;
    }

    public int update(String old, String newName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME_COL, newName);
        String[] where = {old};

        int count = db.update(DBHelper.TB_NAME, contentValues, DBHelper.NAME_COL + " = ?", where);
        return count;
    }


    static class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "MaDB";
        private static final String TB_NAME = "MaTB";
        private static final int DB_VER = 1;
        private static final String UID = "_id";
        private static final String NAME_COL = "NAMECOL";
        private static final String PASS_COL = "PASSCOL";
        private Context context;

        private static final String CREATE_TB
                = "CREATE TABLE " + TB_NAME + "(" +
                UID + " integer primary key AUTOINCREMENT, " +
                NAME_COL + " TEXT, " +
                PASS_COL + " TEXT)";
        private static final String DROP_TB = "DROP TABLE IF EXIST " + TB_NAME;


        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VER);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TB);

            } catch (Exception e) {
                Message.message(context, "error on Create" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "On Upgrade");
                db.execSQL(DROP_TB);
            //    onCreate(db);
                onkrit(db);
            } catch (Exception e) {
                Message.message(context, "error upgrade :" + e);
            }
        }

        public void onkrit(SQLiteDatabase db) {
            onCreate(db);
        }



    }
}
