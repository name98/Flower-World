package com.flowerworld.fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.DataBaseHelper;

public class Router {
    private FragmentManager fragmentManager;

    public Router(final FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public final static String MAIN_FRAGMENT_TAG = "main_fragment";
    private final static int CONTAINER = R.id.activity_fragment_contaner;
    public final static String PROGRESS_FRAGMENT_TAG = "progress_fragment";
    public final static String CREATE_COMMENT_FRAGMENT_TAG = "create_comment_fragment";
    public final static String SHOP_FRAGMENT_TAG = "shop_fragment";
    public final static String PRODUCT_FRAGMENT_TAG = "product_fragment";
    public final static String ORDERS_FRAGMENT_TAG = "orders_fragment";
    public final static String ABOUT_ORDER_FRAGMENT_TAG = "about_orders_fragment";
    public final static String CHARTER_FRAGMENT_TAG = "charter_fragment";
    public final static String GRID_FRAGMENT_TAG = "grid_fragment";
    public final static String LOGIN_FRAGMENT_TAG = "login_fragment";
    public final static String SEARCH_FRAGMENT_EDIT_MODE = "search_fragment_edit_mode";
    public final static String GRID_SEARCHES_FRAGMENT_TAG = "grid_searches_fragment";
    public final static String SIGN_IN_FRAGMENT_TAG = "sign_in_fragment";
    public final static String WELCOME_FRAGMENT_TAG = "welcome_fragment";
    public final static String FOLLOW_PRODUCTS_TAG = "follow_products";
    public final static String COMMENTED_PRODUCTS_TAG = "commented_products";
    public final static String ABOUT_APP_TAG = "about_app_fragment";


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addFragment(String tag, String data) {
        switch (tag) {
            case "test":
                TestFragment testFragment = new TestFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner, testFragment, tag)
                        .addToBackStack(null)
                        .commit();
                break;

            case "shopPage":
                ShopFragment shopFragment = new ShopFragment();

                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner, shopFragment, tag)
                        .addToBackStack(null)
                        .commit();
                break;

            case "createCommentFragment":
                CreateCommentFragment createCommentFragment = new CreateCommentFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner, createCommentFragment, tag)
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    public void addFragment(String tag) {
        switch (tag) {
            case "mainFragment":
                MainFragment mainFragment = new MainFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner, mainFragment, tag)
                        .commit();
                break;
            case "loginFragment":
                LoginFragment loginFragment = new LoginFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner, loginFragment, tag)
                        .commit();
                break;

        }
    }

    public static void addCharterFragment(Context context, int id) {
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        CharterFragment charterFragment = CharterFragment.newInstance(id);
        fragmentManager.beginTransaction()
                .add(R.id.activity_fragment_contaner, charterFragment, CHARTER_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addMainFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        MainFragment mainFragment = MainFragment.newInstance();
        manager.beginTransaction()
                .add(CONTAINER, mainFragment, MAIN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addFlowerFragment(Context context, int id) {

        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        FlowerFragment flowerFragment = FlowerFragment.newInstance(id);
        manager.beginTransaction()
                .add(CONTAINER, flowerFragment, PRODUCT_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addProgressFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        ProgressFragment progressFragment = ProgressFragment.newInstance();
        manager.beginTransaction()
                .add(CONTAINER, progressFragment, PROGRESS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void removeProgressFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(PROGRESS_FRAGMENT_TAG);
        assert fragment != null;
        manager.beginTransaction()
                .remove(fragment)
                .disallowAddToBackStack()
                .commit();
        manager.popBackStack();
    }

    public static void removeFragmentByTag(Context context, String tag) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.beginTransaction()
                    .remove(fragment)
                    .disallowAddToBackStack()
                    .commit();
            manager.popBackStack();
        }
    }

    public static void addCreateCommentFragment(Context context, int idProduct) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        CreateCommentFragment createCommentFragment = CreateCommentFragment.newInstance(idProduct);
        manager.beginTransaction()
                .add(CONTAINER, createCommentFragment, CREATE_COMMENT_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addShopFragment(Context context, String shopName) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        ShopFragment shopFragment = ShopFragment.newInstance(shopName);
        manager.beginTransaction()
                .add(CONTAINER, shopFragment, SHOP_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void reloadProductFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(PRODUCT_FRAGMENT_TAG);
        manager.beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    public static void removeCreateCommentFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(CREATE_COMMENT_FRAGMENT_TAG);
        manager.beginTransaction()
                .remove(fragment)
                .disallowAddToBackStack()
                .commit();
        manager.popBackStack();
    }

    public static void addOrderFragment(Context context, boolean isCompleted) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        OrdersFragment ordersFragment = OrdersFragment.newInstance(isCompleted);
        manager.beginTransaction()
                .add(CONTAINER, ordersFragment, ORDERS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addAboutOrderFragment(Context context, String orderId) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        AboutOrderFragment aboutOrderFragment = AboutOrderFragment.newInstance(orderId);
        manager.beginTransaction()
                .add(CONTAINER, aboutOrderFragment, ABOUT_ORDER_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addGridFragment(Context context, String ids, String title) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        GridFragment gridFragment = GridFragment.newInstance(ids, title);
        manager.beginTransaction()
                .add(CONTAINER, gridFragment, GRID_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addLoginFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        final LoginFragment loginFragment = LoginFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, loginFragment, LOGIN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void start(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String count = "SELECT count(*) FROM " + DataBaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int bool = cursor.getInt(0);
        if (bool > 0) {
            addMainFragment(context);
        } else addWelcomeFragment(context);
    }

    static void removeAllFragments(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        for (Fragment fragment : manager.getFragments()) {
            manager.beginTransaction().remove(fragment).commit();
            manager.popBackStack();
        }
        start(context);
    }

    public static void addSearchFragmentEditMode(Context context, String tag) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        SearchFragmentEditMode searchFragmentEditMode = SearchFragmentEditMode.newInstance(tag);
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, searchFragmentEditMode, SEARCH_FRAGMENT_EDIT_MODE)
                .addToBackStack(null)
                .commit();
    }

    public static void addSignInFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        SignInFragment signInFragment = SignInFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, signInFragment, SIGN_IN_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

    }

    public static void addWelcomeFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, welcomeFragment, WELCOME_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    public static void addFollowFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        FollowFragment followFragment = FollowFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, followFragment, FOLLOW_PRODUCTS_TAG)
                .addToBackStack(null)
                .commit();

    }

    public static void addCommentedFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        CommentedFlowersFragment commentedFlowersFragment = CommentedFlowersFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, commentedFlowersFragment, COMMENTED_PRODUCTS_TAG)
                .addToBackStack(null)
                .commit();

    }

    public static void addAboutAppFragment(Context context) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        AboutAppFragment aboutAppFragment = AboutAppFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.activity_fragment_contaner, aboutAppFragment, ABOUT_APP_TAG)
                .addToBackStack(null)
                .commit();

    }

    public static Fragment getFragmentByTag(Context context, String tag) {
        FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
        return manager.findFragmentByTag(tag);
    }


}
