package com.czjy.chaozhi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.czjy.chaozhi.model.bean.DataLibraryBean;
import com.czjy.chaozhi.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class DataLibraryDao {
    private DBHelper helper;

    public DataLibraryDao(Context context) {
        this.helper = new DBHelper(context);
    }

    /*
     * 插入资料
     */
    public void insert(DataLibraryBean dataLibraryBean) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(
                "insert into tab_datalibrary values(?,?,?,?)",
                new Object[] { dataLibraryBean.getFile_id(), dataLibraryBean.getFile_name(), dataLibraryBean.getFile(),
                        dataLibraryBean.getFile_localurl()});
        db.close();
    }

    /*
     * 查找资料
     */
    public DataLibraryBean select(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tab_datalibrary where file_id=?",
                new String[] { id + "" });

        DataLibraryBean dataLibraryBean = null;
        if (cursor.moveToNext()) {
            int file_id = cursor.getInt(cursor.getColumnIndex("file_id"));
            String file_name = cursor.getString(cursor.getColumnIndex("file_name"));
            String file = cursor.getString(cursor.getColumnIndex("file"));
            String file_localurl = cursor.getString(cursor.getColumnIndex("file_localurl"));
            dataLibraryBean = new DataLibraryBean(file_id, file_name, file, file_localurl);
        }
        cursor.close();
        db.close();
        return dataLibraryBean;
    }

    /*
     * 删除资料
     */
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from tab_datalibrary where file_id = ?", new Object[] { id });
        db.close();
    }

    /*
     * 获取资料列表
     */
    public ArrayList<DataLibraryBean> getAllData() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("tab_datalibrary", null, null, null, null, null, null);

        cursor.moveToFirst();
        // 生成动态数组，加入数据
        ArrayList<DataLibraryBean> listItem = new ArrayList<DataLibraryBean>();
        for (int i = 0; i < cursor.getCount(); i++) {
            int file_id = cursor.getInt(cursor.getColumnIndex("file_id"));
            String file_name = cursor.getString(cursor.getColumnIndex("file_name"));
            String file = cursor.getString(cursor.getColumnIndex("file"));
            String file_localurl = cursor.getString(cursor.getColumnIndex("file_localurl"));

            if (Utils.fileIsExists(file_localurl)) { //文件存在
                DataLibraryBean dataLibraryBean = new DataLibraryBean(file_id, file_name, file, file_localurl);
                listItem.add(dataLibraryBean);
            } else {
                //如果数据库中的文件已经被删除，则把数据库中的记录也删除
                delete(file_id);
            }

            cursor.moveToNext();
        }

        return listItem;
    }
}
