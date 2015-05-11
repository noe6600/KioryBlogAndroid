package com.app.blog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kiory.blog.R;


public class DetailViewActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailViewFragment.DETAIL_URI, getIntent().getData());

            DetailViewFragment fragment = new DetailViewFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.post_detail_container, fragment)
                    .commit();
        }
    }

}
