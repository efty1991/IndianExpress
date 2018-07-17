package com.mobdice.indianexpress.network_call.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mobdice.indianexpress.R;
import com.mobdice.indianexpress.network_call.BookMarkedAdapter;
import com.mobdice.indianexpress.network_call.FeedsRVAdapter;
import com.mobdice.indianexpress.network_call.database.AppDatabase;
import com.mobdice.indianexpress.network_call.database.BookMarked;
import com.mobdice.indianexpress.network_call.database.BookMarkedDAO;
import com.mobdice.indianexpress.network_call.pojo.Item;
import com.mobdice.indianexpress.network_call.web_view.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookMarkFragment extends Fragment {

    public BookMarkFragment() {
    }

    public static BookMarkFragment newInstance() {
        BookMarkFragment fragment = new BookMarkFragment();
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

    BookMarkedAdapter bookMarkedAdapter;
    View rootView;
    @BindView(R.id.bookmarked_list_view)
    RecyclerView bookmarked_list_view;
    @BindView(R.id.progress_parent)
    RelativeLayout progress_parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_book_mark, container, false);
        ButterKnife.bind(this, rootView);
        getAllBookMarkedList();
        return rootView;
    }

    public void initRecyclerView(ArrayList<Item> feeds) {
        bookmarked_list_view.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        bookmarked_list_view.setLayoutManager(mLayoutManager);
        bookmarked_list_view.setItemAnimator(new DefaultItemAnimator());
        bookMarkedAdapter = new BookMarkedAdapter(feeds, this);
        bookmarked_list_view.setAdapter(bookMarkedAdapter);
        showProgressBar(false);

    }

    private void showSnackBar(String str) {
        Snackbar.make(rootView, str, Snackbar.LENGTH_SHORT).show();
    }

    public void showProgressBar(boolean isShow) {
        if (isShow)
            progress_parent.setVisibility(View.VISIBLE);
        else
            progress_parent.setVisibility(View.GONE);
    }

    public void getAllBookMarkedList() {
        showProgressBar(true);
        final ArrayList<Item> items = new ArrayList<>();
        final Gson gson = new Gson();
        databaseThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                BookMarkedDAO bookMarkedDAO = AppDatabase.getDatabase(getContext().getApplicationContext()).bookMarkedDAO();
                List<BookMarked> all = bookMarkedDAO.getAll();
                for (int i = 0; i < all.size(); i++) {
                    Item item = gson.fromJson(all.get(i).getItemData(), Item.class);
                    items.add(item);
                }
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (items.size() == 0)
                            showSnackBar("Sorry no saved articles found.");
                        else
                            initRecyclerView(items);
                        showProgressBar(false);
                    }
                });
            }
        });
    }


    public void openWebViewWithThisLink(Item item, Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("URL", "" + item.getLink());
        intent.putExtra("TITTLE", "" + item.getTitle());
        intent.putExtra("DATA", "" + item.getContentEncoded());
        context.startActivity(intent);
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
