package com.nor.search;

public class Analyzer {
    /**
     *
     * @param data
     * data 전처리 ( 특수문자 제거 , 대소문자 치환 )
     */
    public String preProcessing(String data){

        String returnData = data.replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]"," ").toLowerCase();

        return returnData;

    }
}
