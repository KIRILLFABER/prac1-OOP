package org.example;

import com.google.gson.Gson;

import java.io.FileReader;

public class Parser {

    public Root parse(){

        Gson gson = new Gson();
        Root root = null;

        try(FileReader reader = new FileReader("file.json")){
            root = gson.fromJson(reader, Root.class);
            return root;
        }catch (Exception e){
            System.out.println("ERROR " + e.toString());
        }

        return null;
    }
}
