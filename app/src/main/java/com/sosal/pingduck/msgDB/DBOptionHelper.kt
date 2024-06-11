package com.sosal.pingduck.msgDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

//메세지 생성 target 등에 대한 관리 DB

//msgTarget
//msgPinkTime
//msgPinkWhy

enum class OptionTag{
    MSGTARGET,MSGPINKTIME,MSGPINKWHY
}

class DBOptionHelper(context: Context?, name: String?="optiondb",
                     factory : SQLiteDatabase.CursorFactory?=null,
                     version: Int=1) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql : String = "CREATE TABLE if not exists optiontable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "optionname text," +
                "optiontag INTEGER);"

        db.execSQL(sql)
        /*
        //default setting
        createOption("교수님",OptionTag.MSGTARGET)
        createOption("선배",OptionTag.MSGTARGET)
        createOption("후배",OptionTag.MSGTARGET)
        createOption("내일",OptionTag.MSGPINKTIME)
        createOption("다음생",OptionTag.MSGPINKTIME)
        createOption("아픔",OptionTag.MSGPINKWHY)
        createOption("금붕어산책",OptionTag.MSGPINKWHY)


         */
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists optiontable"
        db.execSQL(sql)
        onCreate(db)
    }

    fun createOption(text : String, tag : OptionTag){
        val db :SQLiteDatabase = this.writableDatabase

        val contentValue = ContentValues()
        contentValue.put("optionname",text)
        contentValue.put("optiontag",tag.ordinal)

        val ansValue = db.insert("optiontable",null,contentValue)

        if(ansValue>-1){
            Log.d("DB","옵션 생성 : ${text}")
            db.close()
            return
        } else {
            Log.e("DB","옵션 생성 error : ${text}")
            db.close()
            return
        }
    }

    /**
     * 메세지 대상, 메세지 핑계 시간,핑계 사유 등의 옵션에 대한 정보를 가져온다.
     *
     * @param 옵션 태그 명 - type : OptionTag
     *
     */
    fun getOption(tag : OptionTag) : MutableList<String>{
        val db : SQLiteDatabase = this.readableDatabase

        val selection = "optiontag = ?"
        val selectionArgs = arrayOf(tag.ordinal.toString())
        val cur : Cursor = db.query("optiontable",null,selection,selectionArgs,null,null,null)

        val rList : MutableList<String> = mutableListOf()

        if(cur.count>0){
            cur.moveToFirst()
            do{
                rList.add(cur.getString(cur.getColumnIndexOrThrow("optionname")))

            }while(cur.moveToNext())
        } else {
            Log.e("DB","조회된 옵션 데이터가 없습니다.")
        }


        cur.close()
        db.close()

        return rList

    }

    fun delOption(text: String){
        val db = this.writableDatabase
        val whereClause = "optionname = ?"
        val whereArgs = arrayOf(text)
        db.delete("optiontable",whereClause,whereArgs)
        db.close()
        Log.d("DB","delete ${text}")
    }

}
