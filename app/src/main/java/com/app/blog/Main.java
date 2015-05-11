package com.app.blog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kiory.blog.R;


public class Main extends ActionBarActivity implements PostFragment.Callbacks{
    private static final String DETAILVIEWFRAGMENT_TAG = "DVGTAG";
    private final String LOG_TAG = Main.class.getSimpleName();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            if (findViewById(R.id.post_detail_container) != null){
                mTwoPane = true;

                if (savedInstanceState == null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.post_detail_container, new DetailViewFragment(), DETAILVIEWFRAGMENT_TAG)
                            .commit();

                }
            }else{
                mTwoPane = false;
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        PostFragment placeholderFragment = (PostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_post);
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailViewFragment.DETAIL_URI, contentUri);

            DetailViewFragment fragment = new DetailViewFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.post_detail_container, fragment, DETAILVIEWFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailViewActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }

    }

