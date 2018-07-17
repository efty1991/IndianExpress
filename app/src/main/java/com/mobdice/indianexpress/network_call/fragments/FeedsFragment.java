package com.mobdice.indianexpress.network_call.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mobdice.indianexpress.R;
import com.mobdice.indianexpress.network_call.OwnVolleyResponse;
import com.mobdice.indianexpress.network_call.RequestStandard;
import com.mobdice.indianexpress.network_call.FeedsRVAdapter;
import com.mobdice.indianexpress.network_call.database.AppDatabase;
import com.mobdice.indianexpress.network_call.database.BookMarked;
import com.mobdice.indianexpress.network_call.database.BookMarkedDAO;
import com.mobdice.indianexpress.network_call.database.PagingData;
import com.mobdice.indianexpress.network_call.database.PagingDataDAO;
import com.mobdice.indianexpress.network_call.pojo.CustomizedItem;
import com.mobdice.indianexpress.network_call.pojo.Feeds;
import com.mobdice.indianexpress.network_call.pojo.Item;
import com.mobdice.indianexpress.network_call.pojo.StackOverflowXmlParser;
import com.mobdice.indianexpress.network_call.util.Util;
import com.mobdice.indianexpress.network_call.web_view.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedsFragment extends Fragment {

    public FeedsFragment() {
    }

    public static FeedsFragment newInstance() {
        FeedsFragment fragment = new FeedsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    HandlerThread dataBaseThread;
    Handler mainThreadHandler;
    Handler databaseThreadHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseThread = new HandlerThread("DataBaseThread");
        dataBaseThread.start();
        databaseThreadHandler = new Handler(dataBaseThread.getLooper());
        mainThreadHandler = new Handler(Looper.getMainLooper());
        if (getArguments() != null) {
        }
    }

    final String URL = "https://indianexpress.com/feedapi/section/latest/";
    @BindView(R.id.list_view)
    RecyclerView feedList_listRV;
    @BindView(R.id.progress_parent)
    RelativeLayout progress_parent;
    @BindView(R.id.swipe_refresh_feed)
    SwipeRefreshLayout swipeRefreshLayout;

    private void showSnackBar(String str) {
        Snackbar.make(rootView, str, Snackbar.LENGTH_SHORT).show();
    }

    public void showProgressBar(boolean isShow) {
        if (isShow)
            progress_parent.setVisibility(View.VISIBLE);
        else
            progress_parent.setVisibility(View.GONE);
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_feeds, container, false);
        ButterKnife.bind(this, rootView);
        initFirstPageRequest();
        return rootView;
    }


    private void initFirstPageRequest() {
        pageCount = 0;
        RequestStandard.requestStringObject(getContext(), URL + "1", new OwnVolleyResponseClass(1));
    }

    class OwnVolleyResponseClass implements OwnVolleyResponse {
        private int pageNo;

        OwnVolleyResponseClass(int pageNo) {
            this.pageNo = pageNo;
        }

        @Override
        public void response(boolean success, Object response, String error) {
            if (success) {
                try {
                    String data = (String) response;
                    StackOverflowXmlParser stackOverflowXmlParser = new StackOverflowXmlParser();
//                        stackOverflowXmlParser.parse(stackOverflowXmlParser.stringToIS(data));
                    Gson gson = new Gson();
                    Feeds feeds = gson.fromJson(stackOverflowXmlParser.xmlToString(data), Feeds.class);
                    Log.e("DataPrinted:", "" + feeds);
                    final ArrayList<CustomizedItem> customizedItems = getCustomizedItems(feeds.getRss().getChannel().getItem(), pageNo);
                    progress_parent.setVisibility(View.GONE);
                    if (pageNo == 1)
                        if (!isRecyclerSet)
                            initRecyclerView(customizedItems);
                        else {
                            setSwipeRefresh();
                            feedAdapter.clearAllData();
                            incrementPage();
                            addMoreLoadedElement(customizedItems);
                        }
                    else {
                        incrementPage();
                        addMoreLoadedElement(customizedItems);
                    }
                    checkForBookMarked(customizedItems, pageNo == 1);
                    databaseThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            PagingDataDAO pagingDataDAO = AppDatabase.getDatabase(getContext().getApplicationContext()).pagingDataDAO();
                            pagingDataDAO.deletePageData(pageNo);
                            pagingDataDAO.insertPageData(new PagingData(pageNo, new Gson().toJson(customizedItems), "" + System.currentTimeMillis()));
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    progress_parent.setVisibility(View.GONE);
                    showSnackBar("Sorry error occurred in loading feeds...");
                }
            } else {
                databaseThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        PagingDataDAO pagingDataDAO = AppDatabase.getDatabase(getContext().getApplicationContext()).pagingDataDAO();
                        PagingData data = pagingDataDAO.getData(pageNo);
                        if (data != null) {
                            final ArrayList<CustomizedItem> customizedItems = new ArrayList<>();
                            Gson gson = new Gson();
                            JsonArray jsonArray = gson.fromJson(data.getPageData(), JsonArray.class);
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JsonElement jsonElement = jsonArray.get(i);
                                CustomizedItem customizedItem = gson.fromJson(jsonElement, CustomizedItem.class);
                                customizedItems.add(customizedItem);
                            }
                            Log.e("FROM_DATA_BASE:" + pageNo, customizedItems.toString());
                            mainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (pageNo == 1) {
                                        if (!isRecyclerSet)
                                            initRecyclerView(customizedItems);
                                        else {
                                            setSwipeRefresh();
                                            feedAdapter.clearAllData();
                                            incrementPage();
                                            addMoreLoadedElement(customizedItems);
                                        }
                                    } else {
                                        incrementPage();
                                        addMoreLoadedElement(customizedItems);
                                    }
                                    checkForBookMarked(customizedItems, pageNo == 1);
                                }
                            });

                        } else {
                            mainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    isAlreadyFetching = false;
                                    progress_parent.setVisibility(View.GONE);
                                    showSnackBar("Sorry error occurred in loading feeds...");
                                }
                            });
                        }
                    }
                });

            }
        }
    }

    HashMap<String, Item> allBookMarked;

    public void checkForBookMarked(final ArrayList<CustomizedItem> customizedItems, final boolean isFirstPage) {
        allBookMarked = new HashMap<>();
        final Gson gson = new Gson();
        databaseThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                BookMarkedDAO bookMarkedDAO = AppDatabase.getDatabase(getContext().getApplicationContext()).bookMarkedDAO();
                List<BookMarked> all = bookMarkedDAO.getAll();
                for (int i = 0; all != null && i < all.size(); i++) {
                    BookMarked bookMarked = all.get(i);
                    Item item = gson.fromJson(bookMarked.getItemData(), Item.class);
                    allBookMarked.put(bookMarked.getPostId(), item);
                    Log.e("\nDATA", "" + bookMarked.toString());
                }
                for (int i = 0; i < customizedItems.size(); i++) {
                    if (i == 1 && isFirstPage) {
                        Item item1 = allBookMarked.get(customizedItems.get(i).getAllItems().get(0).getPostid());
                        if (item1 != null)
                            customizedItems.get(i).getAllItems().get(0).setBookMarked(item1.isBookMarked());

                        Item item2 = allBookMarked.get(customizedItems.get(i).getAllItems().get(1).getPostid());
                        if (item2 != null)
                            customizedItems.get(i).getAllItems().get(1).setBookMarked(item2.isBookMarked());
                    } else {
                        Item item = allBookMarked.get(customizedItems.get(i).getItem().getPostid());
                        if (item != null)
                            customizedItems.get(i).getItem().setBookMarked(item.isBookMarked());
                    }
                }
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyAdapterAllData();
                    }
                });

            }
        });
    }


    public ArrayList<CustomizedItem> getCustomizedItems(ArrayList<Item> items, int pageNo) {
        ArrayList<CustomizedItem> customizedItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (pageNo == 1)
                switch (i) {
                    case 0:
                        customizedItems.add(new CustomizedItem(FeedsRVAdapter.VIEW_TYPE_LARGE, items.get(i)));
                        break;
                    case 1:
                        ArrayList<Item> items1 = new ArrayList<>();
                        items1.add(items.get(i));
                        customizedItems.add(new CustomizedItem(FeedsRVAdapter.VIEW_TYPE_TWO_IN_ONE, items1));
                        break;
                    case 2:
                        customizedItems.get(1).getAllItems().add(items.get(i));
                        break;
                    default:
                        customizedItems.add(new CustomizedItem(FeedsRVAdapter.VIEW_TYPE_NORMAL, items.get(i)));
                        break;
                }
            else
                customizedItems.add(new CustomizedItem(FeedsRVAdapter.VIEW_TYPE_NORMAL, items.get(i)));
            Log.e("POST_ID", "" + items.get(i).getPostid());
        }
        return customizedItems;
    }


    private boolean isScrolling = false;
    private int currentItems, totalItems, scrolledOutItems;
    private FeedsRVAdapter feedAdapter;
    private boolean isRecyclerSet = false;

    public void initRecyclerView(ArrayList<CustomizedItem> feeds) {
        isRecyclerSet = true;
        feedList_listRV.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        feedList_listRV.setLayoutManager(mLayoutManager);
        feedList_listRV.setItemAnimator(new DefaultItemAnimator());
//        feedList_listRV.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        feedAdapter = new FeedsRVAdapter(feeds, this);
        feedList_listRV.setAdapter(feedAdapter);
        ++pageCount;
        feedList_listRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrolledOutItems = mLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrolledOutItems) == totalItems) {
                    loadMore();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        initFirstPageRequest();
                        Log.i("SWIPE_REFRESH", "onRefresh called from SwipeRefreshLayout");
                    }
                }
        );

    }

    public void setSwipeRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void addMoreLoadedElement(ArrayList<CustomizedItem> customizedItems) {
        feedAdapter.addMoreData(customizedItems);
    }

    private final int MAX_PAGE_COUNT = 5;
    private int pageCount = 0;

    private void incrementPage() {
        ++pageCount;
        isAlreadyFetching = false;
        showProgressBar(false);
    }

    private boolean isAlreadyFetching = false;

    private void loadMore() {
        if (!isAlreadyFetching /*&& pageCount <= MAX_PAGE_COUNT*/) {
            showProgressBar(true);
            isScrolling = false;
            isAlreadyFetching = true;
            RequestStandard.requestStringObject(getContext(), URL + (pageCount + 1), new OwnVolleyResponseClass(pageCount + 1));
            Log.e("LoadMoreCalled", "CALLED:" + (pageCount + 1));
        }
    }

    private void notifyAdapter(int position) {
        feedAdapter.notifyItemChanged(position);
    }

    private void notifyAdapterAllData() {
        feedAdapter.notifyDataSetChanged();
    }

    public void openWebViewWithThisLink(Item item, Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("URL", "" + item.getLink());
        intent.putExtra("TITTLE", "" + item.getTitle());
        intent.putExtra("DATA", "" + item.getContentEncoded());
        context.startActivity(intent);
    }

    public void bookMarkThisItem(final Item item, final int position, int subPosition, Context context) {
        if (item.isBookMarked())
            showSnackBar("You have already bookmarked this article.");
        else
            databaseThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        item.setBookMarked(true);
                        BookMarkedDAO bookMarkedDAO = AppDatabase.getDatabase(getContext().getApplicationContext()).bookMarkedDAO();
                        long insertedId = bookMarkedDAO.insertBookMarked(new BookMarked(item.getPostid(), new Gson().toJson(item), "" + System.currentTimeMillis()));
                        if (insertedId > 0)
                            mainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyAdapter(position);
                                }
                            });
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                showSnackBar("You just saved this article...");
                            }
                        });
                        Log.e("INSERTED:ROW", "" + insertedId);
                    } catch (SQLiteConstraintException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
    }

    public void shareThisItem(Item item, Context context) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        String shareSubject = Html.fromHtml(item.getTitle()).toString();
        String shareBody = Html.fromHtml(item.getDescription() + "<br>" + item.getLink()).toString();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSubject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "The Indian Express"));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
