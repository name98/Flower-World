package com.flowerworld.models.scripts;

import com.flowerworld.items.CharterItem;

public class Scripts {
    public static final String ALL_MINI_SHOPS = "SELECT название, логотип FROM `магазины`";

    public static String getProductByIdScript(String id) {
        return "SELECT\n" +
                "    a2.рейтинг, картинки, g.название, цена\n" +
                "FROM\n" +
                "    `товары` as `g`\n" +

                "LEFT JOIN\n" +
                "    `рейтинг` as `a2` ON (`a2`.`id` = `g`.`рейтинг`) WHERE g.ID=" + id + ";";

    }

    public static String flowerItemByShop(String id) {
        return "SELECT\n" +
                "        g.ID, a2.рейтинг, картинки, g.название, цена\n" +
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
    public static String addDateCommentScript(String rate, String comment, String date, String productId, String userID){
        return "INSERT INTO `отзывы` (`ID`, `id_товар`, `id_пользователь`, " +
                "`оценка`, `отзыв`, `день`) VALUES (NULL, '"+productId+"', '"+userID+"', '"+rate+"', '" +
                ""+comment+"', '"+date+"');";
    }
    public static String addDateCommentScript(String rate, String date, String productId, String userID){
        return "INSERT INTO `отзывы` (`ID`, `id_товар`, `id_пользователь`, " +
                "`оценка`, `отзыв`, `день`) VALUES (NULL, '"+productId+"', '"+userID+"', '"+rate+"', '" +
                "NULL', '"+date+"');";
    }

}
