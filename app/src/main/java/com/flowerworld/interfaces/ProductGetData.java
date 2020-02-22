package com.flowerworld.interfaces;

import android.os.Message;

public interface ProductGetData {
    void sendMessageForSetProduct(Message msg);
    void sendMessageForSetComment(Message msg);
}
