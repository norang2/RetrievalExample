package com.nor;

import com.nor.crawling.DataCrawling;
import com.nor.document.DataInfo;
import com.nor.document.InvertedIndex;
import com.nor.index.IndexWriter;
import com.nor.search.DataSearch;

import java.io.IOException;
import java.util.List;

public class Application {

    public static void main(String arg[]) throws IOException {
        /**
         * 1. 수집 : Txt 파일 읽어오기
         * 2. 색인 : 역색인표 만들기
         * 3. 검색 : 랭킹순 문서리스트 출력
         */
        //StopWatch stopWatch = new StopWatch();
        DataCrawling crawling = new DataCrawling();
        IndexWriter indexWriter = new IndexWriter();
        DataSearch dataSearch = new DataSearch();
        String searchKeyword = "queries ranking ";
        System.out.println("검색어 >  "+searchKeyword);
        long indexStart = System.currentTimeMillis();
        List<DataInfo> dataList = crawling.fileReader();
        InvertedIndex invertedIndex = indexWriter.indexWriter(dataList);
        long indexEnd = System.currentTimeMillis();
        System.out.println("색인 수행시간 : " + (indexEnd-indexStart) + "ms");
        long searchStart = System.currentTimeMillis();
        dataSearch.search(invertedIndex,searchKeyword);
        long searchEnd = System.currentTimeMillis();
        System.out.println("검색 수행시간 : " + (searchEnd-searchStart) + "ms");


    }
}
