package com.nor.crawling;

import com.nor.document.DataInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataCrawling {

    /**
     * data 폴더 하위 파일 읽어오기.
     */
     public List<DataInfo> fileReader() throws IOException {
         File dir = new File("/Users/work/tmp/data");
         File files[] = dir.listFiles();

         List<DataInfo> dataList = new ArrayList<>();
         for (int i = 0; i < files.length; i++) {
             StringBuilder stringBuilder  = new StringBuilder();
             BufferedReader reader = new BufferedReader(new FileReader(files[i]));
             String line;
             while ((line = reader.readLine()) != null) {
                 stringBuilder.append(line).append('\n');
             }
             DataInfo data = new DataInfo();
             data.setDataContent(stringBuilder.toString());
             data.setDataId(files[i].getName());
             dataList.add(data);
         }
         return dataList;
     }

}
