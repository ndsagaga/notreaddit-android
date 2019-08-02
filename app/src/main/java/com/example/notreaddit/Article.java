package com.example.notreaddit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class Article {
    private int id;
    private String title,content,imageUrl,readMoreUrl;
    private long timestamp;
    private String[] trees;
    private ArrayList<String> nodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getTrees() {
        return trees;
    }

    public void setTrees(String[] trees) {
        this.trees = trees;
    }

    public static Article deserialize(JSONObject obj) throws JSONException {
        Article article = new Article();

        article.setId(obj.getInt("id"));
        article.setTitle(obj.getString("title"));
        article.setContent(obj.getString("content"));
        article.setImageUrl(obj.getString("imageUrl"));
        article.setReadMoreUrl(obj.getString("readMoreUrl"));
        article.setTimestamp(obj.getLong("timestamp"));

        JSONArray arr = obj.getJSONArray("trees");
        String[] trees = new String[arr.length()];

        for (int i=0;i<arr.length();i++)
            trees[i] = arr.getString(i);

        article.setTrees(trees);

        return article;
    }

    public ArrayList<String> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<String> nodes) {
        this.nodes = nodes;
    }

    public String getReadMoreUrl() {
        return readMoreUrl;
    }

    public void setReadMoreUrl(String readMoreUrl) {
        this.readMoreUrl = readMoreUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
