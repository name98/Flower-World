package com.flowerworld.fragments;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flowerworld.MainActivity;
import com.flowerworld.R;

public class Router {
    private FragmentManager fragmentManager;
    public Router(final FragmentManager fragmentManager) {
        this.fragmentManager=fragmentManager;
    }
    private final static String MAIN_FRAGMENT_TAG = "main_fragment";
    private final static int CONTAINER = R.id.activity_fragment_contaner;
    final static String PROGRESS_FRAGMENT_TAG = "progress_fragment";
    private final static String CREATE_COMMENT_FRAGMENT_TAG = "create_comment_fragment";
    private final static String SHOP_FRAGMENT_TAG = "shop_fragment";
    private final static String PRODUCT_FRAGMENT_TAG = "product_fragment";
    private final static String ORDERS_FRAGMENT_TAG = "orders_fragment";
    final static String ABOUT_ORDER_FRAGMENT_TAG = "about_orders_fragment";
    public final static String CHARTER_FRAGMENT_TAG = "charter_fragment";



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addFragment(String tag, String data){
        switch (tag){
            case "test":
                TestFragment testFragment = new TestFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner,testFragment,tag)
                        .addToBackStack(null)
                        .commit();
                break;

            case "shopPage":
                ShopFragment shopFragment = new ShopFragment();

                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner,shopFragment,tag)
                        .addToBackStack(null)
                        .commit();
                break;
            case "gridFragment":
                GridFragment gridFragment = GridFragment.newInstance(data);
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner,gridFragment,tag)
                        .addToBackStack(null)
                        .commit();
                break;

            case "createCommentFragment":
            CreateCommentFragment createCommentFragment= new CreateCommentFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_fragment_contaner,createCommentFragment,tag)
                    .addToBackStack(null)
                    .commit();
                break;

        }
    }
    public void addFragment(String tag){
        switch (tag){
            case "mainFragment":
                MainFragment mainFragment = new MainFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner,mainFragment,tag)
                        .commit();
                break;
            case "loginFragment":
                LoginFragment loginFragment= new LoginFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.activity_fragment_contaner,loginFragment,tag)
                        .commit();
                break;

        }
    }
    public static void addCharterFragment (Context context, int id){
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        CharterFragment charterFragment = CharterFragment.newInstance(id);
        fragmentManager.beginTransaction()
                .add(R.id.activity_fragment_contaner,charterFragment, CHARTER_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
    public void remove(String tag){
        if(isAdded(tag)){
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(tag))
                    .disallowAddToBackStack()
                    .commit();
            fragmentManager.popBackStack();
        }
    }
    private boolean isAdded(String tag){
        if(fragmentManager.findFragmentByTag(tag)==null)
            return false;
        else return true;

    }

    
    public void reload(String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        fragmentManager.beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    public static void addMainFragment(Context context){
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
        assert fragment != null;
        manager.beginTransaction()
                .remove(fragment)
                .disallowAddToBackStack()
                .commit();
        manager.popBackStack();
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
}
