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
 * generatedMsg : String
 */
class MsgDTO(msgTarget:String,msgPinkTime:String,msgCreateTime:String,msgPinkWhy:String) {

    /**
     * db pk, 디비에 반영되어있지 않은 경우 -1
     */
    private var id : Int = -1
    /**
     * 핑계 메세지 전송 대상
     *
     * 교수님 선배 후배 친구 동료
     */
    private var msgTarget : String = msgTarget;

    /**
     * 핑계 댈 시간
     *
     * 오늘 내일 다음주 etc
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

    /**
     * 생성된 메세지 내용
     *
     * 교수님 내일 아프다. 못간다요
     */
    private lateinit var generatedMsg : String;

    /**
     * 핑계 내용 : 아파서, 그냥, etc
     */
    private var msgPinkWhy : String = msgPinkWhy;

    /**
     * @return 핑계댈 대상
     */
    fun getMsgTarget():String{
        return msgTarget;
    }

    /**
     * @return 핑계댈 약속 시간
     */
    fun getMsgPinkTime():String{
        return msgPinkTime
    }

    /**
     * @return 메세지 생성 시간
     */
    fun getMsgCreateTime():String{
        return msgCreateTime
    }

    /**
     * @return 메세지가 생성 되었는가?
     */
    fun isGenerated() : Boolean {
        return isGenerate
    }

    /**
     * @return 핑계 내용 : 아파서, 그냥, 금붕어 산책
     */
    fun getMsgPinkWhy():String {
        return msgPinkWhy
    }

    /**
     * @param msgData 생성된 메세지
     */
    fun generateMsg(msgData : String) {
        if(!isGenerate){
            isGenerate = true
            generatedMsg = msgData;
        }
    }

    /**
     * @return 생성된 메세지 내용
     */
    fun getGeneratedMsg() : String {
        if(isGenerate){
            return generatedMsg
        } else {
            throw IllegalArgumentException("생성된 메세지가 없습니다.")
        }

    }
    fun setId(id:Int) {
        if(id>0){
            this.id = id
        }

    }
    fun getId() : Int {
        return id
    }

}