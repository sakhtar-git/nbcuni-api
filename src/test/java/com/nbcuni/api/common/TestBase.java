package com.nbcuni.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class TestBase {

    @BeforeClass
    public void beforeClass() {
        RestAssured.baseURI ="https://images-api.nasa.gov/search";
    }

    public HashMap buildQueryParams(String filename) throws IOException {
        String content = null;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("search/" + filename).getFile());

        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Asset> listAsset = new ArrayList<Asset>() ;
        listAsset = Arrays.asList(mapper.readValue(content, Asset[].class));

        Map<String, String> map = new HashMap<String, String>();

        for(Asset asset: listAsset){
            map.put(asset.getParam(), asset.getVal());
        }
        return (HashMap) map;
    }
}
