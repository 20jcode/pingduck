package com.sosal.pingduck

import com.sosal.pingduck.msgDB.MsgDTO

class MsgGenerator {

    lateinit var msg : String
    fun generate(msgDTO: MsgDTO) : String {
        if (!msgDTO.isGenerated()){
            msg = "${msgDTO.getMsgTarget()}, ${msgDTO.getMsgPinkTime()}약속에 대해" +
                    " ${msgDTO.getMsgPinkWhy()} 때문에 힘들 것 같다."
            return msg
        } else {
            throw IllegalAccessException("이미 메세지가 generate 되었습니다.")
        }
    }
}