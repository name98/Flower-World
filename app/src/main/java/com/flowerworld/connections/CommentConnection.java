package com.flowerworld.connections;

import android.os.Message;

import com.flowerworld.database.DataBase;
import com.flowerworld.interfaces.CommentFragmentDataInterface;
import com.flowerworld.interfaces.FragmentSetDataInterface;
import com.flowerworld.items.CommentItem;

public class CommentConnection {
    private CommentFragmentDataInterface parent;

    public void setParent(CommentFragmentDataInterface parent) {
        this.parent = parent;
    }

    public void bind(String idProduct, String idUser) {
        createThreadToComment(idProduct, idUser);
    }

    private boolean isMy(String idProduct, String idUser) {
        return DataBase.isCommented(idProduct, idUser);
    }

    private void createThreadToComment(final String idProduct, final String idUser) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isMy(idProduct, idUser)) {
                    CommentItem comment = DataBase.getCommentByIdAndUserId(idProduct, idUser);
                    Message msg = Message.obtain();
                    msg.obj = comment;
                    parent.sendMessageForGetHandler(msg);
                } else {
                    CommentItem comment = new CommentItem();
                    comment.setMy(false);
                    Message msg = Message.obtain();
                    msg.obj = comment;
                    parent.sendMessageForGetHandler(msg);
                }
            }
        });
        thread.start();
    }

    public void unBind(CommentItem comment, String idProduct, String idUser, int oldRating) {
        createThreadForSendData(comment, idProduct, idUser, oldRating);
    }

    private void createThreadForSendData(final CommentItem comment, final String idProduct, final String idUser, final int oldRating) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (comment.isMy()) {
                    boolean isSuccsess = DataBase.upDateComment(comment, idProduct, idUser, oldRating);
                    Message msg = Message.obtain();
                    msg.obj = isSuccsess;
                    parent.sendMessageForSendHandler(msg);
                } else {
                    boolean isSuccsess = DataBase.insertComment(comment, idProduct, idUser);
                    Message msg = Message.obtain();
                    msg.obj = isSuccsess;
                    parent.sendMessageForSendHandler(msg);
                }
            }
        });
        thread.start();
    }
}
