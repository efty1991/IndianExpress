package com.mobdice.indianexpress.network_call;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobdice.indianexpress.R;
import com.mobdice.indianexpress.network_call.fragments.BookMarkFragment;
import com.mobdice.indianexpress.network_call.fragments.FeedsFragment;
import com.mobdice.indianexpress.network_call.pojo.CustomizedItem;
import com.mobdice.indianexpress.network_call.pojo.Item;
import com.mobdice.indianexpress.network_call.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookMarkedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Item> feeds;
    BookMarkFragment bookMarkFragment;
    public static final int VIEW_TYPE_LARGE = 1;
    public static final int VIEW_TYPE_TWO_IN_ONE = 2;
    public static final int VIEW_TYPE_NORMAL = 3;

    public void clearAllData() {
        feeds.clear();
    }

    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        public TextView tittle;
        @BindView(R.id.description)
        public TextView description;
        @BindView(R.id.image_picture)
        public ImageView picture;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public BookMarkedAdapter(ArrayList<Item> feeds, BookMarkFragment bookMarkFragment) {
        this.feeds = feeds;
        this.bookMarkFragment = bookMarkFragment;
    }

    LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        try {
            inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case VIEW_TYPE_NORMAL:
                default:
                    View view = inflater.inflate(R.layout.bookmarked_item, parent, false);
                    final NormalViewHolder normalHolder = new NormalViewHolder(view);
                    View.OnClickListener onClickListener2 = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.user_item_parent:
                                    if (bookMarkFragment != null)
                                        bookMarkFragment.openWebViewWithThisLink(feeds.get(normalHolder.getAdapterPosition()), view.getContext());
                                    break;
                            }
                        }
                    };
                    view.setOnClickListener(onClickListener2);
                    return normalHolder;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_NORMAL:
                default:
                    NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                    normalViewHolder.tittle.setText(Html.fromHtml(feeds.get(position).getTitle()));
                    normalViewHolder.description.setText(Html.fromHtml(feeds.get(position).getDescription()));
                    ImageLoading.loadImage(feeds.get(position).getMedialThumbnail().getUrl(), normalViewHolder.picture, normalViewHolder.picture.getContext());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

}
