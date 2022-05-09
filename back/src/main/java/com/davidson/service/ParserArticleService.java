package com.davidson.service;

import com.google.gson.JsonObject;
import org.apache.commons.io.Charsets;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Service
public class ParserArticleService {

    public JsonObject jsonParser(File file, String nameFolder) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("path", nameFolder);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), Charsets.UTF_8))) {
            int lines = 0;
            String line = "";
            StringBuilder bld = new StringBuilder();
            while ((line = reader.readLine()) != null) {

                switch (lines) {
                    case 1:
                        json.addProperty("title", line.replace("title: ", ""));
                        break;
                    case 2:
                        json.addProperty("date", line.replace("date: ", ""));
                        break;
                    case 3:
                        json.addProperty("author", line.replace("author: ", ""));
                        break;
                    case 0:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        bld.append(line);
                        break;
                    default:
                        bld.append("\n").append(line);
                }
                lines++;
            }
            String markdown = bld.toString();

            markdown = replaceImgUrl(markdown, nameFolder);
            json.addProperty("cover", nameFolder + "/cover.jpg");
            json.addProperty("markdown", markdown);
        }
        return json;
    }

    private String replaceImgUrl(String markdownBase, String nameFolder) {
        String src = "src=\"";
        String newUrl = "http://localhost:8080/api/v1/img" + nameFolder + "/";
        ArrayList<Integer> arrayList = new ArrayList<>();
        StringBuffer q = new StringBuffer(markdownBase);
        for (int index = markdownBase.indexOf(src);
             index >= 0;
             index = markdownBase.indexOf(src, index + 1)) {
            arrayList.add(index + (src).length());
        }
        int prev = 0;
        for (int i = 0; i < arrayList.size(); i++) { //
            q = q.insert(prev + arrayList.get(i), newUrl); //Insert the add string at position (index + (number of times 'add' string appears * length of 'add' string)
            prev = (i + 1) * newUrl.length(); // calculate the next position where to insert the string
        }
        return q.toString();
    }
}
