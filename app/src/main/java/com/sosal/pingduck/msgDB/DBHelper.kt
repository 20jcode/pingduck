package com.sosal.pingduck.msgDB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDateTime

/*

DB 제어와 관련된 소스코드

getReadableDatabase() : 읽기 전용 DB 객체 반환

getWriteableDatabase() : 쓰기 전용

onCreate() : 데이터베이스 생성 시 작업 구현

onOpen() : 생성된 데이터베이스 오픈 시 작업 구현

onUpgrade() : 데이터베이스 수정 시 작업 구현

 */

class DBHelper(context: Context?, name: String?="msgdb",
               factory : SQLiteDatabase.CursorFactory?=null,
               version: Int=3) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql : String = "CREATE TABLE if not exists msgtable (" +
                "id integer primary key autoincrement, " +
                "msgtarget text, " +
                "msgpinktime text," +
                "msgcreatetime text," +
                "msgpinkwhy text," +
                "generatedmsg text);"

        db.execSQL(sql)
        Log.d("DB","create table ok")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql : String = "DROP TABLE if exists msgtable"
        db.execSQL(sql)
        onCreate(db)
    }

    fun createMsg(msg : MsgDTO){
        val db : SQLiteDatabase = this.writableDatabase

        if(msg.isGenerated()){

            //Table 이름 : value를 가지는 ContentValues 객체 생성
            val contentValue = ContentValues()
            contentValue.put("msgtarget",msg.getMsgTarget())
            contentValue.put("msgpinktime",msg.getMsgPinkTime())
            contentValue.put("msgcreatetime",msg.getMsgCreateTime())
            contentValue.put("msgpinkwhy",msg.getMsgPinkWhy())
            contentValue.put("generatedmsg",msg.getGeneratedMsg())

            //insert 구문 실행
            val ansValue = db.insert("msgtable",null,contentValue)
            if(ansValue>-1){
                Log.d("DB","createMsg, col : ${ansValue}")
            } else {
                //db insert가 실패하였을 경우 ansValue는 -1을 가진다.
                Log.e("DB","createMsg error")
            }
        }
        db.close()


    }

    fun getMsgById(id:Int) : MsgDTO {
        val db = this.readableDatabase
        val selection = "id = ?"
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
        Log.d("DB","getMsgById")
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
        Log.d("DB","getMsgList")
        return rList
    }

    private fun makeCursorToMsgDTO(cursor: Cursor) : MsgDTO {
        val msgAns: MsgDTO = MsgDTO(
            cursor.getString(cursor.getColumnIndexOrThrow("msgtarget")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinktime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgcreatetime")),
            cursor.getString(cursor.getColumnIndexOrThrow("msgpinkwhy"))
        )
        msgAns.setId(cursor.getColumnIndexOrThrow("id"))
        msgAns.generateMsg(cursor.getString(cursor.getColumnIndexOrThrow("generatedmsg")))
        Log.d("DB","id : ${msgAns.getId()}, data : ${msgAns.getGeneratedMsg()}")
        return msgAns
    }

}