package com.flowerworld.connections;

import com.flowerworld.items.CommentItem;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsGetter {
    private ArrayList<CommentItem> commentItems;
    private String productId;
    private String userId;

    public CommentsGetter(String productId, String userId) {
        this.productId = productId;
        this.userId =userId;
    }

    public void initComment(){
        commentItems = new ArrayList<>();
        JSONArray commentArray = new DataMethod().fromScript(Scripts.getCommentScript(productId));
        for (int i=0;i<commentArray.length();i++){
            try {
                addItem(commentArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addItem(JSONObject commentObject){
        try {
            commentItems.add(new CommentItem(commentObject.getString("отзыв"),
                    cutName(commentObject.getString("ФИО")),commentObject.getString("день"),
                    commentObject.getInt("оценка"),isMy(commentObject.getString("id_пользователь")), commentObject.getString("ID")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String cutName(String fullName){
        String[] parsedName = fullName.split(" ");
        if(parsedName.length>1)
            return parsedName[1];
        else return parsedName[0];
    }
    private boolean isMy(String id){
        return userId.equals(id);
    }

    public ArrayList<CommentItem> getCommentItems() {
        return commentItems;
    }
}
