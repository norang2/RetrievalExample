package com.nor.index;

import com.nor.document.DataInfo;
import com.nor.document.DocumentInfo;
import com.nor.document.InvertedIndex;
import com.nor.document.TokenInfo;
import java.util.*;

public class IndexWriter {

    /**
     *
     * @param dataList
     * 수집 데이터로 역색인표 만들기.
     * 1.create ForwardIndex 2.create InvertedIndex
     */
    public InvertedIndex indexWriter(List<DataInfo> dataList){

        List<DocumentInfo> docInfo = makeForwardIndex(dataList);
        InvertedIndex invertedIndex = makeInvertedIndex(docInfo);
        return invertedIndex;

    }

    /**
     * Document 생성 .
     * @param dataList
     * @return
     */
    private List<DocumentInfo> makeForwardIndex(List<DataInfo> dataList){

        List<DocumentInfo> docInfo = new ArrayList<>();
        for(DataInfo data : dataList){
            DocumentInfo documentInfo = new DocumentInfo();
            HashMap<String, Integer> tokenMap = new HashMap<>();
            String[] dataArr = preProcessing(data.getDataContent()).trim().split("\\s+");
            documentInfo.setContents(dataArr);
            documentInfo.setTokenTotalCount(Arrays.stream(dataArr).distinct().count());
            documentInfo.setDocId(data.getDataId());

            for(int i=0; i<documentInfo.getContents().length; i++){

                String key = documentInfo.getContents()[i];
                if(tokenMap.containsKey(key)){
                    tokenMap.put(key, tokenMap.get(key) + 1);
                }else {
                    tokenMap.put(key,1);
                }
            }
            documentInfo.setTokenMap(tokenMap);
            docInfo.add(documentInfo);

        }

        return docInfo;
    }

    /**
     * 역색인표 만들기.
     * @param docInfo
     * @return
     */
    private InvertedIndex makeInvertedIndex(List<DocumentInfo> docInfo){

        HashMap<String,List<TokenInfo>> tokenMap = new HashMap<>();
        for(DocumentInfo doc : docInfo){
            for (Map.Entry<String, Integer> entrySet : doc.getTokenMap().entrySet()) {
                TokenInfo token = new TokenInfo();
                String key = entrySet.getKey();
                token.setFrequency(entrySet.getValue());
                token.setDocId(doc.getDocId());
                token.setDocInfo(doc);
                List<TokenInfo> list = new ArrayList<>();
                if(tokenMap.containsKey(key)){
                    list = tokenMap.get(key);
                    list.add(token);
                    tokenMap.put(key,list);
                }else {
                    list.add(token);
                    tokenMap.put(key,list);
                }
            }

        }
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.setIndexMap(tokenMap);
        invertedIndex.setDocTotalCount(docInfo.size());
        return invertedIndex;
    }
    /**
     *
     * @param data
     * data 전처리 ( 특수문자 제거 , 대소문자 치환 )
     */
    private String preProcessing(String data){

        String returnData = data.replaceAll("[^\\uAC00-\\uD7A30-9a-zA-Z]"," ").toLowerCase();

        return returnData;

    }
}
