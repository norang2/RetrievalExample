package com.nor.document;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class InvertedIndex {

    HashMap<String, List<TokenInfo>> indexMap;

    Integer docTotalCount;




}
