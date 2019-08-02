package com.example.notreaddit;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

public class ArticlesAdapter extends PagerAdapter {
    private List<Article> articleList;
    private LayoutInflater layoutInflater;
    private Context context;

    ArticlesAdapter(Context context, List<Article> dataObjectList){
        this.layoutInflater = LayoutInflater.from(context);
        this.articleList = dataObjectList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = this.layoutInflater.inflate(R.layout.article_layout, container, false);

        final Article article = articleList.get(position);

        if(article != null) {
            ((TextView) view.findViewById(R.id.title)).setText(article.getTitle());
//            ((TextView) view.findViewById(R.id.content)).setText(article.getContent());
//            ((TextView) view.findViewById(R.id.timestamp)).setText(new Date(article.getTimestamp()).toString());
        }
         view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d("ADAPTER","CLICKED");
                 View customView = layoutInflater.inflate(R.layout.article_popup,null);
                 ((TextView)customView.findViewById(R.id.article_title)).setText(article.getTitle());
                 ((TextView)customView.findViewById(R.id.article_content)).setText(article.getContent());
                 ((TextView)customView.findViewById(R.id.article_timestamp)).setText(new Date(article.getTimestamp()*1000).toString());
                 Glide.with(customView.getContext())
                         .load(article.getImageUrl())
                         .apply(new RequestOptions().placeholder(R.drawable.loading).transform(new RoundedCorners(50)))
                         .into((ImageView) customView.findViewById(R.id.article_image));
                 PopupWindow p = new PopupWindow(customView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                 p.setElevation(20);
                 p.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);
                 p.setFocusable(true);
                 p.update();
             }
         });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
