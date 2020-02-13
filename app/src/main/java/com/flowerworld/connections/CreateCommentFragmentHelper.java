package com.flowerworld.connections;

import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateCommentFragmentHelper {
    private String userId;
    private String productId;
    private String comment;
    private int rate;
    private String date;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void sendData() {
        if(comment!=null)
        DataMethod.fromScriptInsert(Scripts.addDateCommentScript(String.valueOf(rate),
                comment, date, productId, userId));
        else
            DataMethod.fromScriptInsert(Scripts.addDateCommentScript(String.valueOf(rate),
                    comment, date, productId, userId));
    }

    public boolean isCommented() {
        JSONArray array = new JSONArray();
        array = new DataMethod().fromScript(Scripts.getCommentScriptByIdProductAndIdUser(productId, userId));
        if (array.length() == 0)
            return false;
        else {
            initValues(array);
            return true;
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getComment() {
        return comment;
    }

    public int getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    private void initValues(JSONArray data) {
        try {
            JSONObject object = data.getJSONObject(0);
            date = object.getString("день");
            comment = object.getString("отзыв");
            rate = object.getInt("оценка");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void upDateComment() {
        DataMethod.fromScriptInsert(Scripts.upDateCommentScript(String.valueOf(rate),
                comment, date, productId, userId));
    }
}
