package com.flowerworld.connections;

import android.util.Log;

import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.links.UrlLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DataMethod {

    public JSONArray fromLinkAllData(String link) throws JSONException {
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + "";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject flowerFromID(String id){
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + id;
            URL url = new URL("http://flowerworld03.000webhostapp.com/OpenFlowerByID.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray.getJSONObject(0);



        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<String> strParser(String str,String delimiter){
        String tempArray[]=str.split(delimiter);
        ArrayList<String> tempArrayList=new ArrayList<>();
        for (String s:tempArray){
            tempArrayList.add(s);
        }
        return tempArrayList;
    }

    public JSONArray newsData() throws JSONException {
        return fromLinkAllData(UrlLinks.NEWS);
    }

    public ArrayList<NewsItem> newsItemArrayList(JSONArray newsData) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        for (int i = 0; i < newsData.length(); i++) {
            JSONObject newData = newsData.getJSONObject(i);
            result.add(new NewsItem(newData.getString("Картинка"),
                    newData.getString("Название"), newData.getString("ids")));
        }
        return result;
    }

    public JSONObject ratingById(String id){
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + id;
            URL url = new URL("http://flowerworld03.000webhostapp.com/GetRatingsById.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray.getJSONObject(0);



        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject shopByName(String name){
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + name;
            URL url = new URL("http://flowerworld03.000webhostapp.com/ShopByName.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray.getJSONObject(0);



        } catch (Exception e) {
            return null;
        }
    }

    public JSONArray flowersByShopName(String name){
        try {
            String data = URLEncoder.encode("id", "UTF-8") + "=" + name;
            URL url = new URL("http://flowerworld03.000webhostapp.com/FlowersByShopName.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            return new JSONArray(sb.toString());




        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<FlowerItem> fromJsArrToMyItem(JSONArray js){
        ArrayList<FlowerItem> items=new ArrayList<>();
        try {
            for(int i=0;i<js.length();i++){
                String images = js.getJSONObject(i).getString("Картинки");
                ArrayList<String> image = strParser(images, " ");
                FlowerItem item = new FlowerItem(js.getJSONObject(i).getString("Название"),
                        image.get(0),js.getJSONObject(i).getString("Рейтинг"),
                        js.getJSONObject(i).getString("Цена"),
                        js.getJSONObject(i).getInt("ID"));
                items.add(item);
            }
            return items;

        }
        catch (Exception e){
            Log.d("except", e.toString());
            return null;
        }

    }

    public JSONArray fromScript(String script){

        try {

            String data = URLEncoder.encode("script", "UTF-8") + "=" + URLEncoder.encode(script, "UTF-8");
            URL url = new URL(UrlLinks.FROM_SCRIPT);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray;
        } catch (Exception e) {

            return null;
        }

    }
    public static void fromScriptInsert(String script){
        try {
            String data = URLEncoder.encode("script", "UTF-8") + "=" + URLEncoder.encode(script, "UTF-8");
            URL url = new URL(UrlLinks.FROM_SCRIPT_Insert);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));



        } catch (Exception e) {


        }
    }

    public static JSONArray fromScript1(String script){

        try {

            String data = URLEncoder.encode("script", "UTF-8") + "=" + URLEncoder.encode(script, "UTF-8");
            URL url = new URL(UrlLinks.FROM_SCRIPT);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;


            while ((line = reader.readLine()) != null) {
                sb.append(line);

                break;
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            return jsonArray;
        } catch (Exception e) {

            return null;
        }

    }
}
