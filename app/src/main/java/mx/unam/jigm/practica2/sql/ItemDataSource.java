package mx.unam.jigm.practica2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mx.unam.jigm.practica2.model.ModelItem;

/**
 * Created by Mario on 28/06/2016.
 */
public class ItemDataSource
{
    private final SQLiteDatabase db;

    public ItemDataSource(Context context)
    {
        MySqliteHelper helper = new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }
    public void saveItemList(ModelItem modelItem)
    {
        ContentValues contentValuesAPP = new ContentValues();
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_APP,modelItem.nameapp);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_DEPLOY,modelItem.deployment);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_DETAIL,modelItem.detailapp);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_RESOURCE,modelItem.resourceId);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_INSTALLUPDATE,modelItem.InstalUpdate);
        db.insert(MySqliteHelper.TABLE_NAME,null,contentValuesAPP);
    }
    public void updateItemList(ModelItem modelItem)
    {
        //String sId=Integer.toString(modelItem.id);
        ContentValues contentValuesAPP = new ContentValues();
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_APP,modelItem.nameapp);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_DEPLOY,modelItem.deployment);
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_INSTALLUPDATE,modelItem.InstalUpdate);
        db.update(MySqliteHelper.TABLE_NAME,contentValuesAPP,MySqliteHelper.COLUMN_ID+"='"+modelItem.id+"'",null);
    }
    public void updateUpadet(ModelItem modelItem){
        ContentValues contentValuesAPP = new ContentValues();
        contentValuesAPP.put(MySqliteHelper.COLUMN_ITEM_INSTALLUPDATE,modelItem.InstalUpdate);
        db.update(MySqliteHelper.TABLE_NAME,contentValuesAPP,MySqliteHelper.COLUMN_ID+"='"+modelItem.id+"'",null);
    }
    public void deleteItem(ModelItem modelItem)
    {
        db.delete(MySqliteHelper.TABLE_NAME,MySqliteHelper.COLUMN_ID+"=?",
                new String[]{String.valueOf(modelItem.id)});
    }
    public List<ModelItem> getAllItems()
    {
        List<ModelItem>modelItemList = new ArrayList<>();
        Cursor cursor = db.query(MySqliteHelper.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int id_table = cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ID));
            String appname=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ITEM_APP));
            String deployment=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ITEM_DEPLOY));
            String appdetail=cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ITEM_DETAIL));
            int imageid=cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ITEM_RESOURCE));
            int installupdate=cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.COLUMN_ITEM_INSTALLUPDATE));
            ModelItem modelitem = new ModelItem();
            modelitem.id=id_table;
            modelitem.nameapp=appname;
            modelitem.deployment=deployment;
            modelitem.detailapp=appdetail;
            modelitem.resourceId=imageid;
            modelitem.InstalUpdate=installupdate;
            modelItemList.add(modelitem);
        }
        return modelItemList;
    }
}
