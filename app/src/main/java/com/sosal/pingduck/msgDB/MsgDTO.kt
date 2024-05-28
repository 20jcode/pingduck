package com.sosal.pingduck.msgDB


/**
 * 메세지 데이터를 담고 있는 Object
 *
 * msgTarget : String
 *
 * msgPinkTime : String
 *
 * msgCreateTime : String
 * 
 * isGenerate : String
 */
class MsgDTO(msgTarget:String,msgPinkTime:String,msgCreateTime:String) {

    /**
     * 핑계 메세지 전송 대상
     */
    private var msgTarget : String = msgTarget;

    /**
     * 핑계 댈 시간
     */
    private var msgPinkTime : String = msgPinkTime;

    /**
     * 메세지 생성or수정 시간
     */
    private var msgCreateTime : String = msgCreateTime;

    /**
     * 메세지가 평문으로 생성되었는가?
     */
    private var isGenerate : Boolean = false;

    private lateinit var generatedMsg : String;
    fun getMsgTarget():String{
        return msgTarget;
    }

    fun getMsgPinkTime():String{
        return msgPinkTime
    }

    fun getMsgCreateTime():String{
        return msgCreateTime
    }

    fun isGenerated() : Boolean {
        return isGenerate
    }

    fun generateMsg(msgData : String) {
        if(!isGenerate){
            generatedMsg = msgData;
        }
    }

}