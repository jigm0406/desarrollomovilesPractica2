package mx.unam.jigm.practica2.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Mario on 28/06/2016.
 */
public class MySqliteHelper extends SQLiteOpenHelper
{
    private final static String DATABASE_NAME = "appStore";
    private final static int DATABASE_VERSION=1;
    public static final String TABLE_NAME = "item_app";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_ITEM_APP = "nameapp";
    public static final String COLUMN_ITEM_DEPLOY ="namedeploy";
    public static final String COLUMN_ITEM_DETAIL ="detailapp";
    public static final String COLUMN_ITEM_RESOURCE = "resource_id";
    public static final String COLUMN_ITEM_INSTALLUPDATE = "installupdate";

    private static final String CREATE_TABLE="create table "+TABLE_NAME+
            "("+COLUMN_ID+" integer primary key autoincrement,"+
            COLUMN_ITEM_APP+" text not null,"+
            COLUMN_ITEM_DEPLOY+" text not null,"+
            COLUMN_ITEM_DETAIL+" text not null,"+
            COLUMN_ITEM_RESOURCE+" integer not null,"+
            COLUMN_ITEM_INSTALLUPDATE+" integer not null)";

private static final String DELETE_TABLE=" FROM "+TABLE_NAME;

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DELETE_TABLE);
        db.execSQL(CREATE_TABLE);
    }
}
