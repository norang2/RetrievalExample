package com.nor.document;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TokenInfo {

    /* 토큰 */
    //private String token;

    private Integer frequency;

    private String docId;

    private DocumentInfo docInfo;

    /* 토큰정보 (문서번호,빈도수) */
    //private Map<String,Integer> tokenMap;

}
