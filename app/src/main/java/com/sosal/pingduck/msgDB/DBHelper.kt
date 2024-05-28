package com.sosal.pingduck.msgDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class DBHelper(context: Context?, name: String?,
               factory : SQLiteDatabase.CursorFactory?,
               version: Int) : SQLiteOpenHelper(context, name, factory, version) {


    override fun onCreate(db: SQLiteDatabase) {
        val sql : String = "CREATE TABLE if not exists msgtable (" +
                "_id integer primary key autoincrement, " +
                "msgtarget text, " +
                "msgpinktime text," +
                "msgcreatetime datetime," +
                "generatedmsg text);"

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists msgtable"

        db.execSQL(sql)
        onCreate(db)
    }

    fun createMsg(db:SQLiteDatabase,msg : MsgDTO){
        if(msg.isGenerated()){
            val msgtarget : String = msg.getMsgTarget()
            val msgpinktime : String = msg.getMsgPinkTime()
            val msgcreatetime : String = msg.getMsgCreateTime()
            val msgcreatedatas : String = msg.getGeneratedMsg()

            val sql : String = "INSERT into msgtable (" +
                    "msgtarget,msgpinktime,msgcreatetime,generatedmsg)" +
                    "VALUES" +
                    "(msgtarget,msgpinktime,msgcreatetime,msgcreatedatas);"

            db.execSQL(sql)
        }

    }

    fun getMsgById(db:SQLiteDatabase,id:Int) : MsgDTO {
        val db = this.readableDatabase
        val selection = "_id = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor : Cursor = db.query("msgtable",null,selection,selectionArgs,null,null,null)
        val msgAns : MsgDTO = MsgDTO(cursor.getString(cursor.getColumnIndexOrThrow("msgtarget")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinktime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgcreatetime")),
            )

        msgAns.generateMsg(cursor.getString(cursor.getColumnIndexOrThrow("generatedmsg")))

        return msgAns

    }


    /**
     * 생성된 핑계 메세지 정보를 조회
     * @return MsgDTO
     */
    //fun getMsgList() : MutableList<MsgDTO> {

    //}

}