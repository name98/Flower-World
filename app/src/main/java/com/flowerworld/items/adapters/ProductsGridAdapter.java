package com.flowerworld.items.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flowerworld.MainActivity;
import com.flowerworld.R;
import com.flowerworld.connections.FollowButton;
import com.flowerworld.fragments.ChildRouter;
import com.flowerworld.fragments.MainFragment;
import com.flowerworld.fragments.Router;
import com.flowerworld.fragments.SettingsFragment;
import com.flowerworld.items.FlowerItem;
import com.flowerworld.methods.Methods;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class ProductsGridAdapter extends RecyclerView.Adapter<ProductsGridAdapter.FlowerItemHolder> {

    private ArrayList<FlowerItem> items = new ArrayList<>();
    private String userId;
    private boolean isFollowPage = false;

    public void setItems(ArrayList<FlowerItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setFollowPage(boolean followPage) {
        isFollowPage = followPage;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    @Override
    public FlowerItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flower_item, parent, false);
        return new FlowerItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerItemHolder holder, int position) {
        FlowerItem item = items.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FlowerItemHolder extends RecyclerView.ViewHolder {

        FlowerItemHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(FlowerItem flowerItem, int position) {
            setNameProduct(flowerItem.getName());
            setPriceProduct(flowerItem.getPrice());
            setRatingProduct(Float.valueOf(flowerItem.getRating()));
            setPaneListener(flowerItem.getId());
            setProductImage(flowerItem.getImageUrl());
            setFollowListener(flowerItem.isFollow(), String.valueOf(flowerItem.getId()), position);
        }

        private void setNameProduct(String nameProduct) {
            TextView productNameTextView = itemView.findViewById(R.id.flowerItemProductNameTextView);
            productNameTextView.setText(nameProduct);
        }

        private void setPriceProduct(String priceProduct) {
            TextView priceProductPriceTextView = itemView.findViewById(R.id.flowerItemProductPriceTextView);
            priceProductPriceTextView.setText(Methods.formatRuble(priceProduct));
        }

        private void setRatingProduct(float ratingProduct) {
            RatingBar ratingProductRatingBar = itemView.findViewById(R.id.flowerItemRatingBar);
            ratingProductRatingBar.setRating(ratingProduct);
        }

        private void setPaneListener(final int idProduct) {
            ConstraintLayout mainPaneConstraintLayout = itemView.findViewById(R.id.flowerItemConstraintLayout);
            mainPaneConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.addFlowerFragment(itemView.getContext(), idProduct);
                }
            });
        }

        private void setFollowListener(boolean isFollow, final String productId, final int position) {
            System.out.println("follow_listener: " + isFollow);
            LikeButton follow = itemView.findViewById(R.id.flower_item_follow_like_button);
            follow.setLiked(isFollow);
            follow.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    FollowButton.setLike(userId, productId);
                    reloadValues();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    FollowButton.setUnLike(userId, productId);
                    reloadValues();
                    if (isFollowPage) {
                        System.out.println("position " + position);
                        items.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, items.size());

                    }
                }
            });

        }

        private void setProductImage(String urlImage){
            SimpleDraweeView productImageSimpleDraweeView = itemView.findViewById(R.id.flowerItemSimpleDraweeView);
            productImageSimpleDraweeView.setImageURI(Uri.parse(urlImage));
        }

        private void reloadValues() {
            MainFragment parentFragment = (MainFragment) Router.getFragmentByTag(itemView.getContext(), Router.MAIN_FRAGMENT_TAG);
            if (parentFragment != null)
                ((SettingsFragment) ChildRouter.getFragmentByTag(parentFragment, ChildRouter.SETTINGS_FRAGMENT_TAG)).reloadValues();
        }
    }
}
