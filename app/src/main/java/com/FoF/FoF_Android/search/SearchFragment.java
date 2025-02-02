package com.FoF.FoF_Android.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    TabLayout tabLayout;
    NonSwipeViewPager viewPager;
    RetrofitApi api;
    TokenManager gettoken;
    Button searchBt;
    ImageButton searchIb;
    Fragment hashFragment;
    View view;

    int tagIdx[] = new int[5];
    String tagName[] = new String[5];
    int searchCnt[] = new int[5];
    List<HashTag.Data.TagList> taglist = new ArrayList<>();
    HashTagAdapter mAdapter;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_nestedscroll, container, false);
        getHashTag(api, view);
        searchBt = view.findViewById(R.id.searchEdit);
        searchIb = view.findViewById(R.id.searchIb);

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new SearchHashFragment()).addToBackStack(null).commit();
            }
        });

        tabLayout = (TabLayout)view.findViewById(R.id.searchTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("감정"));
        tabLayout.addTab(tabLayout.newTab().setText("동물"));
        tabLayout.addTab(tabLayout.newTab().setText("만화"));
        tabLayout.addTab(tabLayout.newTab().setText("클립"));
        tabLayout.addTab(tabLayout.newTab().setText("텍스트"));
        tabLayout.addTab(tabLayout.newTab().setText("인물"));
        tabLayout.addTab(tabLayout.newTab().setText("이모티콘"));

        viewPager = (NonSwipeViewPager)view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager()));
        viewPager.setSaveEnabled(false);
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        return view;
    }

    public void getHashTag(RetrofitApi api, View view){
        String token = gettoken.checklogin(getContext());
        api.getTag(token).enqueue(new Callback<HashTag>() {
            @Override
            public void onResponse(Call<HashTag> call, Response<HashTag> response) {
                HashTag tag = response.body();
                for(int i=0; i<tag.getData().getTagList().size(); i++){
                    tagIdx[i] = tag.getData().getTagList().get(i).getTagIdx();
                    tagName[i] = tag.getData().getTagList().get(i).getTagName();
                    searchCnt[i] = tag.getData().getTagList().get(i).getSearchCnt();
                }
                taglist = tag.getData().getTagList();
                RecyclerView mRecyclerView = view.findViewById(R.id.hashtag_recycler);
                LinearLayoutManager mLinearLayoutmanager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLinearLayoutmanager);
                mAdapter = new HashTagAdapter(taglist, getContext(), api, token);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new HashTagAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        HashTag.Data.TagList item = mAdapter.getItem(position);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, HashClickFragment.newInstance(item.getTagIdx(), item.getTagName())).addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<HashTag> call, Throwable t) {

            }
        });
    }
}