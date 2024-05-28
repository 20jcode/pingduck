package com.sosal.pingduck.msgDB

import android.content.Context
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
                "generatedmsg text);";

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists msgtable"

        db.execSQL(sql)
        onCreate(db)
    }

    fun createMsg(msg : MsgDTO){
        val msgtarget : String = msg.getMsgTarget()
        val msgpinktime : String = msg.getMsgPinkTime()
        val msgcreatetime : LocalDateTime = msg.getMsgCreateTime()

        val sql : String = "insert into msgtable (" +
                "msgtarget,msgpinktime,msgcreatetime,generatedmsg)" +
                ""
    }
    /*
    fun getMsgById(id:Int) : MsgDTO {

    }


     */

    /**
     * 생성된 핑계 메세지 정보를 조회
     * @return MsgDTO
     */
    //fun getMsgList() : MutableList<MsgDTO> {

    //}

}