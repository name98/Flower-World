package com.flowerworld.connections;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.common.logging.FLog;
import com.flowerworld.fragments.GridFragment;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.scripts.Scripts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GridFragmentHelper {

    private static String IDS;
    private static Handler GRID_HANDLER;
    boolean static boolean IS_EMPTY = false;

    private String ids;
    private ArrayList<FlowerItem> items = new ArrayList<>();

    public GridFragmentHelper(String ids){
        this.ids=ids;
        start();
    }
    private void start(){
        ArrayList<String> strings = Methods.strParser(ids, " ");
        for (int i=0;i<strings.size();i++){
            try {
                items.add(new FlowerItemHelper(strings.get(i)).getFlowerItem());
            }
            catch (Exception e){
                Log.d("GFH: ", e.toString());
            }
        }
    }

    public ArrayList<FlowerItem> getItems() {
        return items;
    }

    public static void bind(String ids){
        IDS = ids;

    }

    private static void start(final ArrayList<String> itemsId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = Message.obtain();
                    msg.obj = getFlowerItemsArray(itemsId);
                    msg.setTarget(GRID_HANDLER);
                    GRID_HANDLER.sendMessage(msg);
                } catch (Exception e) {
                    Log.d("GFUI2: ", e.toString());
                }
            }
        });
        thread.start();
    }

    private static ArrayList<FlowerItem> getFlowerItemsArray(ArrayList<String> itemsId) {
        ArrayList<FlowerItem> resultItems = new ArrayList<>();
        for(int i = 0; i<itemsId.size(); i++){
            resultItems.add(getProduct(itemsId.get(i)));
        }
        return resultItems;
    }

    private static FlowerItem getProduct(String id){
        JSONArray itemArray = new DataMethod().fromScript(Scripts.flowerItem(id));
        try {
            JSONObject itemObject = itemArray.getJSONObject(0);
            FlowerItem item = new FlowerItem();
            item.setId(Integer.valueOf(id));
            item.setImageUrl(Methods.strParser(
                    itemObject.getString("картинки")," "
            ).get(0));
            item.setPrice(itemObject.getString("цена"));
            item.setRating(itemObject.getString("рейтинг"));
            item.setName(itemObject.getString("название"));
            return item;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initGridHandler {
        GRID_HANDLER = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        }
    }
}
