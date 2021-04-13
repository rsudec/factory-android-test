package com.rsudec.factory_android_test.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.rsudec.factory_android_test.R;
import com.rsudec.factory_android_test.model.Article;
import com.rsudec.factory_android_test.adapters.ViewPagerAdapter;
import com.rsudec.factory_android_test.fragments.ArticleDetailFragment;

import java.util.ArrayList;

public class ArticleDetailActivity extends AppCompatActivity {
    private Article selectedArticle;
    private ArrayList<Article> articleList;
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private ActionBar actionBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        selectedArticle = this.getIntent().getExtras().getParcelable("article");
        articleList = this.getIntent().getBundleExtra("articleListBundle").getParcelableArrayList("articleList");

        viewPager = findViewById(R.id.article_detail_viewPager);





        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(vpAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getSupportActionBar().setTitle(articleList.get(position).getTitle());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        for(Article a : articleList){
            Bundle b = new Bundle();
            b.putParcelable("article", a);

            ArticleDetailFragment novi = new ArticleDetailFragment();
            novi.setArguments(b);

            fragments.add(novi);

        }
        vpAdapter.setFragments(fragments);

        int index = 0;
        for(Article a : articleList){
            if(a.getUrl().equals(selectedArticle.getUrl())){
                viewPager.setCurrentItem(articleList.indexOf(a));
                break;
            }

        }

    }


}
