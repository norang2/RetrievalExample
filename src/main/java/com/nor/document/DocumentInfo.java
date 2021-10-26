package com.nor.document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DocumentInfo {

    /* 문서 번호 */
    private String docId;

    /* 문서 내용 */
    private String[] contents;

    /* 토큰 전체 카운트 */
    private long tokenTotalCount;

    /* 문서별 토큰 빈도수 */
    private Map<String,Integer> tokenMap;
}
