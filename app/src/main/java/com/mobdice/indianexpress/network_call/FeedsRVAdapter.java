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
import com.mobdice.indianexpress.network_call.fragments.FeedsFragment;
import com.mobdice.indianexpress.network_call.pojo.CustomizedItem;
import com.mobdice.indianexpress.network_call.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CustomizedItem> feeds;
    FeedsFragment feedsFragment;
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
        @BindView(R.id.bookmark_image)
        ImageView bookmark_image;
        @BindView(R.id.share_image)
        ImageView share_image;
        @BindView(R.id.pub_date)
        TextView pub_date;

        public NormalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class TwoInOneViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item1)
        public CardView item1;
        @BindView(R.id.title1)
        public TextView tittle1;
        @BindView(R.id.description1)
        public TextView description1;
        @BindView(R.id.image_picture_1)
        public ImageView picture1;
        @BindView(R.id.bookmark_image_1)
        ImageView bookmark_image_1;
        @BindView(R.id.share_image_1)
        ImageView share_image_1;
        @BindView(R.id.pub_date_1)
        TextView pub_date1;

        @BindView(R.id.item2)
        public CardView item2;
        @BindView(R.id.title2)
        public TextView tittle2;
        @BindView(R.id.description2)
        public TextView description2;
        @BindView(R.id.image_picture_2)
        public ImageView picture2;
        @BindView(R.id.bookmark_image_2)
        ImageView bookmark_image_2;
        @BindView(R.id.share_image_2)
        ImageView share_image_2;
        @BindView(R.id.pub_date_2)
        TextView pub_date2;


        public TwoInOneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class LargeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.heading)
        public TextView heading;
        @BindView(R.id.bullets)
        public LinearLayout bullets;
        @BindView(R.id.image_picture_large)
        public ImageView image_picture_large;
        @BindView(R.id.bookmark_image)
        ImageView bookmark;
        @BindView(R.id.share_image)
        ImageView share;
        @BindView(R.id.pub_date)
        TextView pub_date;

        public LargeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FeedsRVAdapter(ArrayList<CustomizedItem> feeds, FeedsFragment feedsFragment) {
        this.feeds = feeds;
        this.feedsFragment = feedsFragment;
    }

    LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        try {
            inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case VIEW_TYPE_LARGE:
                    View viewLarge = inflater.inflate(R.layout.large_layout_item, parent, false);
                    final LargeViewHolder largeHolder = new LargeViewHolder(viewLarge);

                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.large_layout_parent:
                                    if (feedsFragment != null)
                                        feedsFragment.openWebViewWithThisLink(feeds.get(largeHolder.getAdapterPosition()).getItem(), view.getContext());
                                    break;
                                case R.id.bookmark_image:
                                    if (feedsFragment != null)
                                        feedsFragment.bookMarkThisItem(feeds.get(largeHolder.getAdapterPosition()).getItem(), largeHolder.getAdapterPosition(), -1, view.getContext());
                                    break;
                                case R.id.share_image:
                                    if (feedsFragment != null)
                                        feedsFragment.shareThisItem(feeds.get(largeHolder.getAdapterPosition()).getItem(), view.getContext());
                                    break;
                            }
                        }
                    };
                    viewLarge.setOnClickListener(onClickListener);
                    largeHolder.bookmark.setOnClickListener(onClickListener);
                    largeHolder.share.setOnClickListener(onClickListener);
                    return largeHolder;
                case VIEW_TYPE_TWO_IN_ONE:
                    View twoInOne = inflater.inflate(R.layout.two_in_one_feed, parent, false);
                    final TwoInOneViewHolder twoInOneViewHolder = new TwoInOneViewHolder(twoInOne);

                    View.OnClickListener onClickListener1 = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.item1:
                                    if (feedsFragment != null)
                                        feedsFragment.openWebViewWithThisLink(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(0), view.getContext());
                                    break;
                                case R.id.item2:
                                    if (feedsFragment != null)
                                        feedsFragment.openWebViewWithThisLink(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(1), view.getContext());
                                    break;
                                case R.id.bookmark_image_1:
                                    feedsFragment.bookMarkThisItem(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(0), twoInOneViewHolder.getAdapterPosition(), 0, view.getContext());
                                    break;
                                case R.id.share_image_1:
                                    feedsFragment.shareThisItem(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(0), view.getContext());
                                    break;
                                case R.id.bookmark_image_2:
                                    feedsFragment.bookMarkThisItem(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(1), twoInOneViewHolder.getAdapterPosition(), 1, view.getContext());
                                    break;
                                case R.id.share_image_2:
                                    feedsFragment.shareThisItem(feeds.get(twoInOneViewHolder.getAdapterPosition()).getAllItems().get(1), view.getContext());
                                    break;
                            }
                        }
                    };
                    twoInOneViewHolder.item1.setOnClickListener(onClickListener1);
                    twoInOneViewHolder.item2.setOnClickListener(onClickListener1);
                    twoInOneViewHolder.bookmark_image_1.setOnClickListener(onClickListener1);
                    twoInOneViewHolder.bookmark_image_2.setOnClickListener(onClickListener1);
                    twoInOneViewHolder.share_image_1.setOnClickListener(onClickListener1);
                    twoInOneViewHolder.share_image_2.setOnClickListener(onClickListener1);

                    return twoInOneViewHolder;
                case VIEW_TYPE_NORMAL:
                default:
                    View view = inflater.inflate(R.layout.user_item, parent, false);
                    final NormalViewHolder normalHolder = new NormalViewHolder(view);
                    View.OnClickListener onClickListener2 = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.user_item_parent:
                                    if (feedsFragment != null)
                                        feedsFragment.openWebViewWithThisLink(feeds.get(normalHolder.getAdapterPosition()).getItem(), view.getContext());
                                    break;
                                case R.id.bookmark_image:
                                    if (feedsFragment != null)
                                        feedsFragment.bookMarkThisItem(feeds.get(normalHolder.getAdapterPosition()).getItem(), normalHolder.getAdapterPosition(), -1, view.getContext());
                                    break;
                                case R.id.share_image:
                                    if (feedsFragment != null)
                                        feedsFragment.shareThisItem(feeds.get(normalHolder.getAdapterPosition()).getItem(), view.getContext());
                                    break;
                            }
                        }
                    };
                    view.setOnClickListener(onClickListener2);
                    normalHolder.bookmark_image.setOnClickListener(onClickListener2);
                    normalHolder.share_image.setOnClickListener(onClickListener2);
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
                case VIEW_TYPE_LARGE:
                    LargeViewHolder largeViewHolder = (LargeViewHolder) holder;
                    largeViewHolder.heading.setText(Html.fromHtml(feeds.get(position).getItem().getTitle()));
                    largeViewHolder.pub_date.setText(Util.getSelectedDateFormat(feeds.get(position).getItem().getPubDate()));
                    ImageLoading.loadImage(feeds.get(position).getItem().getMedialThumbnail().getUrl(), largeViewHolder.image_picture_large, largeViewHolder.image_picture_large.getContext());
                    String content = feeds.get(position).getItem().getContentEncoded();
                    largeViewHolder.bullets.removeAllViews();
                    if (content != null && !"".equalsIgnoreCase(content)) {
                        String arr[] = content.split("</p>");
                        for (int i = 0; i < arr.length && i < 5 && arr[i] != null && !"".equalsIgnoreCase(arr[i]) && !arr[i].contains("http://") && !arr[i].contains("https://")&& !arr[i].contains("<script") && !arr[i].contains("hrfe="); i++) {
                            arr[i] = arr[i].replace("<p>", "");
                            arr[i] = arr[i].replace("</p>", "");
                            arr[i] = "<li>" + arr[i] + "</li>";
                            TextView textView = (TextView) inflater.inflate(R.layout.single_bullet_text, null);
                            textView.setText(Html.fromHtml(arr[i]));
                            largeViewHolder.bullets.addView(textView);
                        }
                    }
                    if (feeds.get(position).getItem().isBookMarked())
                        largeViewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_green_24dp);
                    else
                        largeViewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    break;
                case VIEW_TYPE_TWO_IN_ONE:
                    TwoInOneViewHolder twoInOneViewHolder = (TwoInOneViewHolder) holder;
                    twoInOneViewHolder.tittle1.setText(Html.fromHtml(feeds.get(position).getAllItems().get(0).getTitle()));
                    twoInOneViewHolder.description1.setText(Html.fromHtml(feeds.get(position).getAllItems().get(0).getDescription()));
                    twoInOneViewHolder.pub_date1.setText(Util.getSelectedDateFormat(feeds.get(position).getAllItems().get(0).getPubDate()));
                    ImageLoading.loadImage(feeds.get(position).getAllItems().get(0).getMedialThumbnail().getUrl(), twoInOneViewHolder.picture1, twoInOneViewHolder.picture1.getContext());

                    twoInOneViewHolder.tittle2.setText(Html.fromHtml(feeds.get(position).getAllItems().get(1).getTitle()));
                    twoInOneViewHolder.description2.setText(Html.fromHtml(feeds.get(position).getAllItems().get(1).getDescription()));
                    twoInOneViewHolder.pub_date2.setText(Util.getSelectedDateFormat(feeds.get(position).getAllItems().get(1).getPubDate()));
                    ImageLoading.loadImage(feeds.get(position).getAllItems().get(1).getMedialThumbnail().getUrl(), twoInOneViewHolder.picture2, twoInOneViewHolder.picture2.getContext());
                    if (feeds.get(position).getAllItems().get(0).isBookMarked())
                        twoInOneViewHolder.bookmark_image_1.setImageResource(R.drawable.ic_bookmark_green_24dp);
                    else
                        twoInOneViewHolder.bookmark_image_1.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    if (feeds.get(position).getAllItems().get(1).isBookMarked())
                        twoInOneViewHolder.bookmark_image_2.setImageResource(R.drawable.ic_bookmark_green_24dp);
                    else
                        twoInOneViewHolder.bookmark_image_2.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    break;
                case VIEW_TYPE_NORMAL:
                default:
                    NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                    normalViewHolder.tittle.setText(Html.fromHtml(feeds.get(position).getItem().getTitle()));
                    normalViewHolder.description.setText(Html.fromHtml(feeds.get(position).getItem().getDescription()));
                    ImageLoading.loadImage(feeds.get(position).getItem().getMedialThumbnail().getUrl(),normalViewHolder.picture,normalViewHolder.picture.getContext());
                    normalViewHolder.pub_date.setText(Util.getSelectedDateFormat(feeds.get(position).getItem().getPubDate()));
                    if (feeds.get(position).getItem().isBookMarked())
                        normalViewHolder.bookmark_image.setImageResource(R.drawable.ic_bookmark_green_24dp);
                    else
                        normalViewHolder.bookmark_image.setImageResource(R.drawable.ic_bookmark_black_24dp);
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
        return feeds.get(position).getType();
    }


    public void addMoreData(ArrayList<CustomizedItem> arrayList) {
        feeds.addAll(arrayList);
        notifyDataSetChanged();
    }
}
