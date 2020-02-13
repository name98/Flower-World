package com.flowerworld.connections;

import com.flowerworld.items.CharterItem;
import com.flowerworld.models.scripts.Scripts;

public class CharterFragmentHelper {
    private CharterItem item;

    public CharterFragmentHelper(CharterItem item) {
        this.item = item;
        start();
    }
    private void start(){
        DataMethod.fromScriptInsert(Scripts.sendOrder(item));
    }
}
