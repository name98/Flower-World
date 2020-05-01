package com.flowerworld.database;

import android.util.Log;

import com.flowerworld.connections.DataMethod;
import com.flowerworld.items.AboutOrderItem;
import com.flowerworld.items.CharterItem;
import com.flowerworld.items.CommentItem;
import com.flowerworld.items.FlowerImagesItem;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.items.FullProductItem;
import com.flowerworld.items.FullShopItem;
import com.flowerworld.items.Item;
import com.flowerworld.items.NewsItem;
import com.flowerworld.items.OrderItem;
import com.flowerworld.items.RatingItem;
import com.flowerworld.items.SearchItem;
import com.flowerworld.items.ShopItem;
import com.flowerworld.items.UserItem;
import com.flowerworld.links.UrlLinks;
import com.flowerworld.methods.Methods;
import com.flowerworld.models.UserData;
import com.flowerworld.models.scripts.Scripts;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataBase {
    static JSONArray getJSONArrayByScript(String script) {
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
            line = reader.readLine();
            sb.append(line);
            return new JSONArray(sb.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static void insertFromScript(String script) {
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
            StringBuilder sb = new StringBuilder();
            String line;
            line = reader.readLine();
            sb.append(line);
            System.out.println(sb.toString() + "ksjdksjdkjksjdkf");


        } catch (Exception ignored) {
            System.out.println(ignored.toString() + "errrrrrrr");

        }
    }

    public static FlowerItem getProductById(String id) {
        System.out.println(id + "this is");
        JSONArray array = getJSONArrayByScript(Scripts.getProductByIdScript(id));
        FlowerItem product = new FlowerItem();
        try {
            assert array != null;
            JSONObject object = array.getJSONObject(0);
            product.setName(object.getString("название"));
            product.setImageUrl(Methods.strParser(object.getString("картинки"), " ").get(0));
            product.setPrice(object.getString("цена"));
            product.setId(Integer.valueOf(id));
            RatingItem ratingItem = new RatingItem();
            ratingItem.setOne(object.getInt("один"));
            ratingItem.setTwo(object.getInt("два"));
            ratingItem.setTree(object.getInt("три"));
            ratingItem.setFour(object.getInt("четыре"));
            ratingItem.setFive(object.getInt("пять"));
            product.setRating(String.valueOf(ratingItem.getGeneral()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }


    public static FlowerItem getProductById(String id, String userId) {
        System.out.println(id + "this is");
        JSONArray array = getJSONArrayByScript(Scripts.getProductByIdScript(id, userId));
        FlowerItem product = new FlowerItem();
        try {
            assert array != null;
            JSONObject object = array.getJSONObject(0);
            product.setName(object.getString("название"));
            product.setImageUrl(Methods.strParser(object.getString("картинки"), " ").get(0));
            product.setPrice(object.getString("цена"));
            product.setId(Integer.valueOf(id));
            RatingItem ratingItem = new RatingItem();
            ratingItem.setOne(object.getInt("один"));
            ratingItem.setTwo(object.getInt("два"));
            ratingItem.setTree(object.getInt("три"));
            ratingItem.setFour(object.getInt("четыре"));
            ratingItem.setFive(object.getInt("пять"));
            int follow = object.getInt("follow");
            if (follow==0)
                product.setFollow(false);
            else product.setFollow(true);
            product.setRating(String.valueOf(ratingItem.getGeneral()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static ArrayList<NewsItem> getNewsArray() {
        JSONArray array = getJSONArrayByScript(Scripts.allData("news"));
        ArrayList<NewsItem> news = new ArrayList<>();
        try {
            assert array != null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                news.add(new NewsItem(jsonObject.getString("Картинка"),
                        jsonObject.getString("Название"),
                        jsonObject.getString("ids")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return news;
    }

    public static ArrayList<Item> getCategoriesArray() {
        JSONArray array = getJSONArrayByScript(Scripts.allData("Categories"));
        ArrayList<Item> categories = new ArrayList<>();
        try {
            assert array != null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String s = jsonObject.getString("goods");
                ArrayList<String> ids = Methods.strParser(s, " ");
                categories.add(new Item(array.getJSONObject(i).getString("categories"), ids));
            }
        } catch (Exception e) {
            Log.d("HomeFragmentConstr1: ", e.toString());
        }
        return categories;
    }

    public static ArrayList<ShopItem> getShopsArray() {
        JSONArray array = getJSONArrayByScript(Scripts.ALL_MINI_SHOPS);
        ArrayList<ShopItem> shops = new ArrayList<>();
        try {
            assert array != null;
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                shops.add(new ShopItem(jsonObject.getString("логотип"),
                        jsonObject.getString("название")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shops;
    }

    public static FullProductItem getFullProduct(int id, String userId) {
        FullProductItem product = new FullProductItem();
        try {
            JSONObject object = Objects.requireNonNull(getJSONArrayByScript(Scripts.fullData(String.valueOf(id), userId))).getJSONObject(0);
            product.setName(object.getString("название"));
            product.setShopLogo(object.getString("логотип"));
            product.setShopName(object.getString("магазин"));
            product.setSizeH(object.getString("ширина"));
            product.setSizeL(object.getString("длина"));
            product.setAnnotation(object.getString("описание"));
            product.setCompound(object.getString("состав"));
            ArrayList<String> temp = Methods.strParser(object.getString("картинки"), " ");
            ArrayList<FlowerImagesItem> productImages = new ArrayList<>();
            for (int i = 0; i < temp.size(); i++) {
                productImages.add(new FlowerImagesItem(temp.get(i)));
            }
            product.setItems(productImages);
            ArrayList<Integer> productRates = new ArrayList<>();
            RatingItem ratingItem = new RatingItem();

            ratingItem.setOne(object.getInt("один"));
            ratingItem.setTwo(object.getInt("два"));
            ratingItem.setTree(object.getInt("три"));
            ratingItem.setFour(object.getInt("четыре"));
            ratingItem.setFive(object.getInt("пять"));
            productRates.add(ratingItem.getOne());
            productRates.add(ratingItem.getTwo());
            productRates.add(ratingItem.getTree());
            productRates.add(ratingItem.getFour());
            productRates.add(ratingItem.getFive());
            product.setNumberOfRates(productRates);
            product.setSumRate(ratingItem.getGeneralFormated());
            product.setPrice(object.getString("цена"));
            product.setId(String.valueOf(id));
            product.setNumRaters(String.valueOf(ratingItem.getNumberOfRates()));
            boolean isFollow = object.getString("follow").equals("1");
            product.setFollow(isFollow);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static ArrayList<CommentItem> getCommentsByProductId(String id, String myId) {
        ArrayList<CommentItem> comments = new ArrayList<>();
        JSONArray array = getJSONArrayByScript(Scripts.getCommentScript(id));
        for (int i = 0; i < array.length(); i++) {
            try {

                JSONObject object = array.getJSONObject(i);
                CommentItem comment = new CommentItem();
                comment.setAuthor(object.getString("ФИО"));
                comment.setComment(object.getString("отзыв"));
                comment.setDate(object.getString("день"));
                comment.setRate(object.getInt("оценка"));
                comment.setId(object.getString("ID"));
                String usersId = object.getString("id_пользователь");
                comment.setMy(usersId.equals(myId));
                System.out.println(object.getString("id_пользователь") + " : " + id);
                comments.add(comment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }

    public static FullShopItem getShopByName(String name, String userId) {
        FullShopItem shop = new FullShopItem();
        ArrayList<FlowerItem> products = new ArrayList<>();
        try {
            JSONObject jsonObject = getJSONArrayByScript(Scripts.fullDataShop(name)).getJSONObject(0);
            String logo = jsonObject.getString("логотип");

            String address = jsonObject.getString("адрес") + "\n" +
                    jsonObject.getString("телефон") + "\n" +
                    jsonObject.getString("почта");
            String delivery = jsonObject.getString("тип") + "\n" +
                    jsonObject.getString("внутри_МКАДа") + "\n" +
                    jsonObject.getString("за_МКАДом");
            String annotantion = jsonObject.getString("описание");
            JSONArray array = getJSONArrayByScript(Scripts.flowerItemByShop(name, userId));
            System.out.println(array.length() + "length");
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = array.getJSONObject(i);
                String nameProduct = temp.getString("название");
                String images = Methods.strParser(temp.getString("картинки"), " ").get(0);
                String price = temp.getString("цена");
                int productId = temp.getInt("ID");
                RatingItem ratingItem = new RatingItem();
                ratingItem.setOne(temp.getInt("один"));
                ratingItem.setTwo(temp.getInt("два"));
                ratingItem.setTree(temp.getInt("три"));
                ratingItem.setFour(temp.getInt("четыре"));
                int fol;
                fol = temp.getInt("follow");
                boolean follow;
                follow = fol != 0;
                ratingItem.setFive(temp.getInt("пять"));
                products.add(new FlowerItem(nameProduct, images, String.valueOf(ratingItem.getGeneral()), price, productId, follow));
            }
            shop.setAddress(address);
            shop.setAnnotantion(annotantion);
            shop.setDelivery(delivery);
            shop.setLogo(logo);
            System.out.println(logo);
            shop.setId(name);
            shop.setItems(products);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shop;
    }

    public static boolean isCommented(String id, String userId) {
        JSONArray array = getJSONArrayByScript(Scripts.getCommentScriptByIdProductAndIdUser(id, userId));
        return array.length() != 0;
    }

    public static CommentItem getCommentByIdAndUserId(String id, String userId) {
        JSONArray array = getJSONArrayByScript(Scripts.getCommentScriptByIdProductAndIdUser(id, userId));
        System.out.println(id + " : " + userId);
        CommentItem comment = new CommentItem();
        try {
            JSONObject object = array.getJSONObject(0);
            comment.setComment(object.getString("отзыв"));
            comment.setDate(object.getString("день"));
            comment.setRate(object.getInt("оценка"));
            comment.setId(object.getString("ID"));
            comment.setMy(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public static boolean upDateComment(CommentItem comment, String productId, String userID, int oldRate) {
        String ratingId = getRatingIdByProductId(productId);
        RatingItem ratingItem = getRatingByRatingId(ratingId);
        ratingItem.minus(oldRate);
        System.out.println("oldComment " + oldRate);
        ratingItem.plus(comment.getRate());
        insertFromScript(Scripts.upDateCommentScript(String.valueOf(comment.getRate()), comment.getComment(),
                comment.getDate(), productId, userID));
        insertFromScript(Scripts.upDateRatingById(ratingId, ratingItem));
        return true;
    }

    public static boolean insertComment(CommentItem commentItem, String productId, String userID) {
        if (commentItem.getComment() != null) {
            insertFromScript(Scripts.addDateCommentScript(String.valueOf(commentItem.getRate()), commentItem.getComment(),
                    commentItem.getDate(), productId, userID));
            String ratingId = getRatingIdByProductId(productId);
            RatingItem ratingItem = getRatingByRatingId(ratingId);
            System.out.println("finish " + commentItem.getRate());
            ratingItem.plus(commentItem.getRate());
            insertFromScript(Scripts.upDateRatingById(ratingId, ratingItem));
        } else
            insertFromScript(Scripts.addDateCommentScript(String.valueOf(commentItem.getRate()), commentItem.getDate(), productId, userID));
        return true;
    }

    public static ArrayList<OrderItem> getOrdersByUserId(boolean isCompleted, String idUser) {
        JSONArray js;
        ArrayList<OrderItem> orders = new ArrayList<>();
        if (isCompleted)
            js = new DataMethod().fromScript(Scripts.ordersByIdC(idUser));
        else
            js = new DataMethod().fromScript(Scripts.ordersById(idUser));
        for (int i = 0; i < js.length(); i++) {
            try {
                String s = js.getJSONObject(i).getString("картинки");
                JSONObject object = js.getJSONObject(i);
                orders.add(new OrderItem(object.getString("id"),
                        object.getString("название"),
                        Methods.strParser(s, " ").get(0),
                        object.getString("число"),
                        object.getString("статус")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    public static AboutOrderItem getOrderInformation(String idOrder) {
        AboutOrderItem aboutOrder = new AboutOrderItem();
        JSONArray orderValues = getJSONArrayByScript(Scripts.aboutOrderById(idOrder));
        try {
            JSONObject object = orderValues.getJSONObject(0);
            aboutOrder.setCost(object.getString("цена"));
            aboutOrder.setDate(object.getString("число") + " в " +
                    object.getString("время"));
            aboutOrder.setProductId(object.getString("товар"));
            aboutOrder.setReceiver(object.getString("Получатель"));
            aboutOrder.setState(object.getString("статус"));
            aboutOrder.setProduct(getProductById(aboutOrder.getProductId()));
            aboutOrder.setOrderId(idOrder);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aboutOrder;
    }

    public static RatingItem getRatingByRatingId(String ratingId) {
        RatingItem ratingItem = new RatingItem();
        JSONArray array = getJSONArrayByScript(Scripts.getRatingById(ratingId));
        try {
            JSONObject object = array.getJSONObject(0);
            ratingItem.setOne(object.getInt("один"));
            ratingItem.setTwo(object.getInt("два"));
            ratingItem.setTree(object.getInt("три"));
            ratingItem.setFour(object.getInt("четыре"));
            ratingItem.setFive(object.getInt("пять"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ratingItem;
    }

    public static String getRatingIdByProductId(String productId) {
        String ratingId = "";
        JSONArray array = getJSONArrayByScript(Scripts.getRatingIdByProductId(productId));
        try {
            JSONObject object = array.getJSONObject(0);
            ratingId = object.getString("рейтинг");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ratingId;
    }

    public static UserItem getUserByEmailAndPassword(String email, String password) {
        JSONArray array = getJSONArrayByScript(Scripts.auth(email, password));
        if (array == null) {
            return null;
        } else {
            UserItem user = new UserItem();
            try {
                JSONObject object = array.getJSONObject(0);
                user.setUserPassword(object.getString("пароль"));
                user.setUserName(object.getString("ФИО"));
                user.setUserId(object.getInt("ID"));
                user.setUserEmail(object.getString("почта"));
                user.setTelephone(object.getString("телефон"));
                return user;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static ArrayList<String> getPopularSearches() {
        JSONArray array = getJSONArrayByScript(Scripts.getTags());
        ArrayList<String> temp = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                temp.add(object.getString("тэг"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static ArrayList<SearchItem> getSearches(String text) {
        ArrayList<SearchItem> temp = new ArrayList<>();
        JSONArray array1 = getJSONArrayByScript(Scripts.getLikeTagByName(text));
        try {
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object = array1.getJSONObject(i);
                SearchItem item = new SearchItem();
                item.setId(object.getInt("id"));
                item.setType("tag");
                item.setText(object.getString("тэг"));
                temp.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray array = getJSONArrayByScript(Scripts.getLikeProductsByName(text));
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                SearchItem item = new SearchItem();
                item.setId(object.getInt("ID"));
                item.setType("product");
                item.setText(object.getString("название"));
                temp.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static ArrayList<FlowerItem> getProductsByTag(String text, String userId) {
        ArrayList<FlowerItem> temp = new ArrayList<>();
        JSONArray array = getJSONArrayByScript(Scripts.getProductsItemByTag(text, userId));

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                FlowerItem product = new FlowerItem();
                product.setName(object.getString("название"));
                product.setImageUrl(Methods.strParser(object.getString("картинки"), " ").get(0));
                product.setPrice(object.getString("цена"));
                product.setId(object.getInt("ID"));
                RatingItem ratingItem = new RatingItem();
                ratingItem.setOne(object.getInt("один"));
                ratingItem.setTwo(object.getInt("два"));
                ratingItem.setTree(object.getInt("три"));
                ratingItem.setFour(object.getInt("четыре"));
                ratingItem.setFive(object.getInt("пять"));
                product.setRating(String.valueOf(ratingItem.getGeneral()));
                int fol = object.getInt("follow");
                boolean follow = fol != 0;
                product.setFollow(follow);
                temp.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;

    }

    public static boolean insertUserData(UserData userData) {
        JSONArray array = getJSONArrayByScript(Scripts.checkEmail(userData.getEmail()));
        if (array == null || array.length() == 0) {
            insertFromScript(Scripts.insertUser(userData));
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, String> getSettingsData(String email) {
        Map<String, String> result = new HashMap<>();
        JSONArray activeCountArray = getJSONArrayByScript(Scripts.getActiveOrdersCount(email));
        JSONArray completedCountArray = getJSONArrayByScript(Scripts.getCompletedOrdersCount(email));
        JSONArray followCountArray = getJSONArrayByScript(Scripts.getFollowOrdersCount(email));
        JSONArray commentedCountArray = getJSONArrayByScript(Scripts.getCommentedOrdersCount(email));
        try {
            if (activeCountArray != null && activeCountArray.length() != 0) {
                String count = activeCountArray.getJSONObject(0).getString("active_orders");
                result.put("active_count", count);
            } else
                result.put("active_count", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (completedCountArray != null && completedCountArray.length() != 0) {
                String count = completedCountArray.getJSONObject(0).getString("completed_orders");
                result.put("completed_count", count);
            } else
                result.put("completed_count", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (followCountArray != null && followCountArray.length() != 0) {
                String count = followCountArray.getJSONObject(0).getString("follow_orders");
                result.put("follow_count", count);
            } else
                result.put("follow_count", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (commentedCountArray != null && commentedCountArray.length() != 0) {
                String count = commentedCountArray.getJSONObject(0).getString("commented_orders");
                result.put("commented_count", count);
            } else
                result.put("commented_count", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static ArrayList<FlowerItem> getItemsByUserFollow(String userId) {
        JSONArray itemArray = getJSONArrayByScript(Scripts.getItemsByUserFollow(userId));
        ArrayList<FlowerItem> items = new ArrayList<>();
        try {
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject object = itemArray.getJSONObject(i);
                FlowerItem product = new FlowerItem();
                product.setName(object.getString("название"));
                product.setImageUrl(Methods.strParser(object.getString("картинки"), " ").get(0));
                product.setPrice(object.getString("цена"));
                product.setId(object.getInt("ID"));
                RatingItem ratingItem = new RatingItem();
                ratingItem.setOne(object.getInt("один"));
                ratingItem.setTwo(object.getInt("два"));
                ratingItem.setTree(object.getInt("три"));
                ratingItem.setFour(object.getInt("четыре"));
                ratingItem.setFive(object.getInt("пять"));
                product.setRating(String.valueOf(ratingItem.getGeneral()));
                product.setFollow(true);
                items.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;

    }

    public static void insertCharter(CharterItem charterItem) {
        insertFromScript(Scripts.sendOrder(charterItem));
        System.out.println(Scripts.sendOrder(charterItem));
    }

    public static ArrayList<FlowerItem> getItemsByCommented(String userId) {
        JSONArray array = getJSONArrayByScript(Scripts.getItemsByUserCommented(userId));
        ArrayList<FlowerItem> products = new ArrayList<>();
        try {
            System.out.println(array.length() + "length");
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = array.getJSONObject(i);
                String nameProduct = temp.getString("название");
                String images = Methods.strParser(temp.getString("картинки"), " ").get(0);
                String price = temp.getString("цена");
                int productId = temp.getInt("ID");
                RatingItem ratingItem = new RatingItem();
                ratingItem.setOne(temp.getInt("один"));
                ratingItem.setTwo(temp.getInt("два"));
                ratingItem.setTree(temp.getInt("три"));
                ratingItem.setFour(temp.getInt("четыре"));
                ratingItem.setFive(temp.getInt("пять"));
                int fol = temp.getInt("follow");
                boolean follow = fol != 0;

                products.add(new FlowerItem(nameProduct, images, String.valueOf(ratingItem.getGeneral()), price, productId, follow));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }
}
