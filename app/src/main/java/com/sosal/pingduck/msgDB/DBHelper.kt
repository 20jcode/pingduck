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
                "msgcreatetime text," +
                "msgpinkwhy text," +
                "generatedmsg text);"

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists msgtable"
        db.execSQL(sql)
        onCreate(db)
    }

    fun createMsg(msg : MsgDTO){
        val db : SQLiteDatabase = this.writableDatabase
        val msgData: String = "${msg.getMsgTarget()}, ${msg.getMsgPinkTime()}에 ${msg.getMsgPinkWhy()} 이유로 못간다."
        msg.generateMsg(msgData)

        if(msg.isGenerated()){
            val msgtarget : String = msg.getMsgTarget()
            val msgpinktime : String = msg.getMsgPinkTime()
            val msgcreatetime : String = msg.getMsgCreateTime()
            val msgpinkwhy : String = msg.getMsgPinkWhy()
            val msgcreatedatas : String = msg.getGeneratedMsg()


            val sql : String = "INSERT into msgtable (" +
                    "msgtarget,msgpinktime,msgcreatetime,msgpinkwhy,generatedmsg)" +
                    "VALUES" +
                    "(msgtarget,msgpinktime,msgcreatetime,msgpinkwhy,msgcreatedatas);"

            db.execSQL(sql)
        }
        db.close()

    }

    fun getMsgById(id:Int) : MsgDTO {
        val db = this.readableDatabase
        val selection = "_id = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor : Cursor = db.query("msgtable",null,selection,selectionArgs,null,null,null)
        val msgAns : MsgDTO = MsgDTO(cursor.getString(
            cursor.getColumnIndexOrThrow("msgtarget")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinktime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgcreatetime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinkwhy"))
        )

        msgAns.generateMsg(cursor.getString(cursor.getColumnIndexOrThrow("generatedmsg")))
        cursor.close()
        db.close()
        return msgAns

    }


    /**
     * 생성된 핑계 메세지 정보를 조회
     * @return MsgDTO
     */
    fun getMsgList() : MutableList<MsgDTO> {

        val rList : MutableList<MsgDTO> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM msgtable"
        val cur : Cursor = db.rawQuery(query,null)

        if(cur.moveToFirst()){
            do {
                val msgDto = makeCursorToMsgDTO(cur)
                rList.add(msgDto)
            } while (cur.moveToNext())
        }

        cur.close()
        db.close()
        return rList
    }

    private fun makeCursorToMsgDTO(cursor: Cursor) : MsgDTO{
        val msgAns : MsgDTO = MsgDTO(cursor.getString(cursor.getColumnIndexOrThrow("msgtarget")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinktime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgcreatetime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinkwhy"))
        )

        msgAns.generateMsg(cursor.getString(cursor.getColumnIndexOrThrow("generatedmsg")))

        return msgAns

        val msgList: MutableList<MsgDTO> = mutableListOf()
        val db = this.readableDatabase
        val cursor: Cursor = db.query("msgtable", null, null, null, null, null, null)

        if(cursor.moveToFirst()) {
            do {
                val msgTarget = cursor.getString(cursor.getColumnIndexOrThrow("msgtarget"))
                val msgPinkTime = cursor.getString(cursor.getColumnIndexOrThrow("msgpinktime"))
                val msgCreateTime = cursor.getString(cursor.getColumnIndexOrThrow("msgcreatetime"))
                val msgPinkWhy = cursor.getString(cursor.getColumnIndexOrThrow("msgpinkwhy"))
                //val generatedMsg = cursor.getString(cursor.getColumnIndexOrThrow("generatedmsg"))
                val msgData: String = "${msgTarget}, ${msgPinkTime}에 ${msgPinkWhy} 이유로 못간다."

                val msgDTO = MsgDTO(msgTarget, msgPinkTime, msgCreateTime, msgPinkWhy)
                msgDTO.generateMsg(msgData)

                msgList.add(msgDTO)
            } while(cursor.moveToNext())
        }
        cursor.close()
        return msgList

    }

}