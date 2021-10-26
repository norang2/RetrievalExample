package com.nor.search;

import com.nor.document.InvertedIndex;
import com.nor.document.TokenInfo;

import java.util.*;
import java.util.stream.Stream;

public class DataSearch {

    public void search(InvertedIndex index, String searchKeyword){
        /*
            1. 키워드 토큰 분리
            2. 토큰 별 index 검색
            3. 검색 결과 별 점수 계산
         */
        String[] tokens = tokenizer(searchKeyword);
        HashMap<String,List<TokenInfo>> indexMap = index.getIndexMap();
        Map<String, Double> resultScore = new HashMap<>();

        for(int i=0; i<tokens.length; i++){
            String token = tokens[i];
            List<TokenInfo> tokenInfos = indexMap.get(token);

            for(TokenInfo ti : tokenInfos){
                // 문서별 토큰빈도수 tokens.frequency / 토큰전체수 tokens.obj.total / 문서 전체수 tokens.total / 토큰 포함 문서수
                Double score = calcScore(ti.getFrequency(),ti.getDocInfo().getTokenTotalCount(),index.getDocTotalCount(),tokenInfos.size());
                if(resultScore.containsKey(ti.getDocId())){
                    resultScore.put(ti.getDocId(),resultScore.get(ti.getDocId())+score);
                }else {
                    resultScore.put(ti.getDocId(),score);
                }
            }
        }
        /* score 내림차순 정렬 */
        List<String> listKeySet = new ArrayList<>(resultScore.keySet());
        Collections.sort(listKeySet, (value1, value2) -> (resultScore.get(value2).compareTo(resultScore.get(value1))));
        for(String key : listKeySet) {
            System.out.println(key + " > " + "score : " + Math.round(resultScore.get(key)*10000)/10000.0);
        }


    }

    /**
     * 스코어 계산 Score = tfidf + weight (소수점 네자리)
     * tf = n/N (frequency / tokenTotal )
     * idf = 1+log(N/n) (docTotal / searchDocCnt)
     * weight = 1.0
     * @param frequency
     * @param tokenTotal
     * @param docTotal
     * @param searchDocCnt
     * @return
     */
    private Double calcScore(int frequency, long tokenTotal, int docTotal, int searchDocCnt){

        Double tf = Double.valueOf(frequency)/Double.valueOf(tokenTotal);
        Double idf = 1.0 + Math.log(docTotal/searchDocCnt);
        Double score = tf*idf + 1.0;
        return Math.round(score*10000)/10000.0;
    }

    private String[] tokenizer(String searchKeyword){

        String[] keywordArr = searchKeyword.replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]"," ").toLowerCase().trim().split("\\s+");

        return keywordArr;
    }

}
