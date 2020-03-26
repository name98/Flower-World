package com.flowerworld.models.scripts;

import com.flowerworld.items.CharterItem;
import com.flowerworld.items.RatingItem;
import com.flowerworld.models.UserData;

public class Scripts {
    public static final String ALL_MINI_SHOPS = "SELECT название, логотип FROM `магазины`";

    public static String getProductByIdScript(String id) {
        return "SELECT\n" +
                "    a2.один, a2.два, a2.три, a2.четыре, a2.пять, картинки, g.название, цена\n" +
                "FROM\n" +
                "    `товары` as `g`\n" +

                "LEFT JOIN\n" +
                "    `рейтинг` as `a2` ON (`a2`.`id` = `g`.`рейтинг`) WHERE g.ID=" + id + ";";

    }

    public static String flowerItemByShop(String id) {
        return "SELECT\n" +
                "        g.ID, a2.один, a2.два, a2.три, a2.четыре, a2.пять, картинки, g.название, цена\n" +
                "    FROM\n" +
                "        `товары` as `g`\n" +
                "    LEFT JOIN\n" +
                "        `магазины` as `a1` ON (`a1`.`id` = `g`.`магазин`)\n" +
                "    LEFT JOIN\n" +
                "        `рейтинг` as `a2` ON (`a2`.`id` = `g`.`рейтинг`) WHERE a1.название='" + id + "';";

    }

    public static String allData(String table) {
        return "SELECT * FROM " + table + ";";
    }

    public static String fullData(String id) {
        return "SELECT g.ID, g.название, g.цена, g.картинки, \n" +
                "a1.название `магазин`, a1.логотип, a2.один, \n" +
                "a2.два, a2.три, a2.четыре, a2.пять, a2.рейтинг,\n" +
                "a3.длина, a3.ширина, a3.описание, a3.состав\n" +
                "FROM\n" +
                "`товары` as `g` \n" +
                "LEFT JOIN `магазины` as `a1` ON (`a1`.`id` = `g`.`магазин`)\n" +
                "LEFT JOIN `рейтинг` as `a2` ON (`a2`.`id` = `g`.`рейтинг`) \n" +
                "LEFT JOIN `параметры` as `a3` ON (`a3`.`id` = `g`.`параметр`) \n" +
                "WHERE g.ID = " + id + ";";
    }

    public static String fullDataShop(String id) {
        return "SELECT\n" +
                "        *\n" +
                "    FROM\n" +
                "        `магазины` as `g`\n" +
                "    LEFT JOIN\n" +
                "        `доставки` as `a1` ON (`a1`.`id` = `g`.`условия_доставки`)\n" +
                "    WHERE g.название='" + id + "';";
    }

    public static String auth(String email, String pass) {
        return "SELECT * FROM `пользователи` where почта='" + email + "' and пароль='" + pass + "';";
    }

    public static String sendOrder(CharterItem item) {
        return "INSERT INTO `заказы` (`id`, `товар`, `пользователь`, `адрес`, `Получатель`, `время`, `число`, `статус`) VALUES (NULL, '" + item.getIdProduct() +
                "', '" + item.getIdUser() + "', '" + item.getAddress() + "', '" + item.getReceiver() + "', '" + item.getTime() + "', '" + item.getDate() + "', '" + item.getState() + "');";

    }

    public static String ordersById(String id) {
        return "SELECT g.id, g.число, g.статус, a1.картинки, \n" +
                "    a1.название\n" +
                "    FROM\n" +
                "    `заказы` as `g` \n" +
                "    LEFT JOIN `товары` as `a1` ON (`a1`.`id` = `g`.`товар`)    \n" +
                "    WHERE g.пользователь = " + id + " AND g.статус!='доставлено';";
    }

    public static String ordersByIdC(String id) {
        return "SELECT g.id, g.число, g.статус, a1.картинки, \n" +
                "    a1.название\n" +
                "    FROM\n" +
                "    `заказы` as `g` \n" +
                "    LEFT JOIN `товары` as `a1` ON (`a1`.`id` = `g`.`товар`)    \n" +
                "    WHERE g.пользователь = " + id + " AND g.статус='доставлено';";
    }

    public static String aboutOrderById(String id) {
        return "SELECT g.время, g.число, g.статус, g.товар, a1.цена, g.Получатель \n" +
                "                    FROM\n" +
                "                    `заказы` as `g` \n" +
                "                    LEFT JOIN `товары` as `a1` ON (`a1`.`id` = `g`.`товар`)    \n" +
                "                   WHERE g.id = " + id + ";";
    }

    public static String getCommentScript(String productId) {
        return "SELECT g.ID, g.оценка, g.отзыв, a1.ФИО, g.id_пользователь, g.день\n" +
                "FROM `отзывы` as `g` \n" +
                "                LEFT JOIN `пользователи` as `a1` ON (`a1`.`id` = `g`.`id_пользователь`)\n" +
                "                WHERE id_товар = " + productId + ";";
    }

    public static String getCommentScriptByIdProductAndIdUser(String idProduct, String idUSer) {
        return "SELECT *FROM `отзывы` WHERE id_товар = " + idProduct + " and id_пользователь = " + idUSer + ";";
    }

    public static String upDateCommentScript(String rate, String comment, String date, String productId, String userID) {
        return "UPDATE `отзывы` SET `оценка` = '"
                + rate + "', `отзыв` = '" + comment + "', `день` = '"
                + date + "' WHERE `отзывы`.`id_товар` = "
                + productId + " AND `отзывы`.`id_пользователь` = " + userID + ";";
    }

    public static String addDateCommentScript(String rate, String comment, String date, String productId, String userID) {
        return "INSERT INTO `отзывы` (`ID`, `id_товар`, `id_пользователь`, " +
                "`оценка`, `отзыв`, `день`) VALUES (NULL, '" + productId + "', '" + userID + "', '" + rate + "', '" +
                "" + comment + "', '" + date + "');";
    }

    public static String addDateCommentScript(String rate, String date, String productId, String userID) {
        return "INSERT INTO `отзывы` (`ID`, `id_товар`, `id_пользователь`, " +
                "`оценка`, `отзыв`, `день`) VALUES (NULL, '" + productId + "', '" + userID + "', '" + rate + "', '" +
                "NULL', '" + date + "');";
    }

    public static String getRatingById(String ratingId) {
        return "SELECT * FROM `рейтинг` WHERE ID = " + ratingId + ";";
    }

    public static String getRatingIdByProductId(String productId) {
        return "SELECT рейтинг FROM `товары`where ID = " + productId + ";";
    }

    public static String upDateRatingById(String ratingId, RatingItem ratingItem) {
        return "UPDATE `рейтинг` SET " +
                "`один` = '" + ratingItem.getOne() + "', " +
                "`два` = '" + ratingItem.getTwo() + "', " +
                "`три` = '" + ratingItem.getTree() + "', " +
                "`четыре` = '" + ratingItem.getFour() + "', " +
                "`пять` = '" + ratingItem.getFive() + "' " +
                "WHERE `рейтинг`.`ID` = " + ratingId + ";";
    }

    public static String getTags() {
        return "SELECT тэг FROM `тэги` GROUP by тэг;";
    }

    public static String getLikeProductsByName(String productName) {
        return "SELECT DISTINCT LCASE(t.название) as название, t.ID FROM товары as t" +
                " where название LIKE '%" + productName + "%' or ID in " +
                "(SELECT товар from тэги as a WHERE a.тэг LIKE '%" + productName + "%') LIMIT 10;";
    }

    public static String getLikeTagByName(String tagName) {
        return "SELECT DISTINCT LCASE(t.тэг) as тэг, t.id FROM тэги as t where t.тэг LIKE '%" + tagName + "%' GROUP by (t.тэг) LIMIT 4;";
    }

    public static String getProductsItemByTag(String tag) {
        return "SELECT DISTINCT a2.один, a2.два, a2.три, a2.четыре, a2.пять, t.картинки, t.название, t.цена, t.ID FROM товары as t LEFT JOIN\n" +
                "              `рейтинг` as `a2` ON (`a2`.`id` = `t`.`рейтинг`)\n" +
                "               where t.ID in\n" +
                "               (SELECT товар from тэги as a WHERE a.тэг = '" + tag + "');";
    }

    public static String insertUser(UserData userData) {
        return "INSERT INTO `пользователи` (`ID`, `ФИО`, `пароль`, `почта`, `телефон`) " +
                "VALUES (NULL, '" +
                userData.getFullName() + "', '" +
                userData.getPassword() + "', '" +
                userData.getEmail() + "', '" +
                userData.getPhone() + "');";
    }

    public static String checkEmail(String email) {
        return "SELECT * FROM `пользователи` WHERE `пользователи`.`почта` = '" +
                email + "';";
    }
}
