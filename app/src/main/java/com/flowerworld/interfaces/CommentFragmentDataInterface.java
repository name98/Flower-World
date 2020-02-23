package com.flowerworld.interfaces;

import android.os.Message;

public interface CommentFragmentDataInterface {
    void sendMessageForGetHandler(Message msg);
    void sendMessageForSendHandler(Message msg);
}
