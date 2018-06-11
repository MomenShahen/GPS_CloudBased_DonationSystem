package momen.shahen.com.gps_cloudbaseddonationsystemproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.Equalizer;
import android.widget.Toast;

/**
 * Created by fci on 11/03/17.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Donation_System";

    private static final String TABLE_NAME = "TABLE_FCI";

    private static final String UID = "id";

    private static final String EMAIL = "email";

    private static final String LOGIN = "login";

    private static final String TYPE = "type";

    private static final int DATABASE_VERSION = 2;
    Context cont;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " +TABLE_NAME +
            "( "+UID+" integer primary key , "+EMAIL+" varchar(255) not null, "+LOGIN+" varchar(2) not null, "+TYPE+" varchar(255) not null);";

    // Database Deletion
    private static final String DATABASE_DROP = "drop table if exists "+TABLE_NAME+";";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);
            db.execSQL("insert into "+TABLE_NAME+" ( "+UID+", "+EMAIL+", "+LOGIN+", "+TYPE+" ) values ( '1', 'e', '0', 't');");
            Toast.makeText(cont,"database created", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't created " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
            Toast.makeText(cont,"database upgraded", Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(cont,"database doesn't upgraded " +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean InsertData (String email, String state , String type )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL,email);
        contentValues.put(LOGIN,state);
        contentValues.put(TYPE,type);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        return result==-1?false:true;
    }

    public Cursor ShowData ()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME+" ;",null);
        return cursor;
    }

    public boolean UpdateData (String id, String email, String state, String type )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID,id);
        contentValues.put(EMAIL,email);
        contentValues.put(LOGIN,state);
        contentValues.put(TYPE,type);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id = "+Integer.parseInt( id ),null);

        return true;
    }

    public boolean UpdateData (String id, String lang, String lat )
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID,id);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id = ?",new String[]{id});

        return true;
    }

    public int DeleteData (String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[] {id});
    }

}
