package com.app.blog;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.blog.database.DataContract;
import com.app.blog.service.DataSyncAdapter;
import com.kiory.blog.R;

/**
 * A placeholder fragment containing a simple view.
 */
public  class PostFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = PostFragment.class.getSimpleName();
    private static final int POSTS_LOADER = 0;
    private PostsAdapter postsAdapter;
    private ListView postsListView;
    private int listPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";

    public interface Callbacks {
        public void onItemSelected(Uri postUri);
    }

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        postsAdapter = new PostsAdapter(getActivity(), null, 0);
        postsListView = (ListView) rootView.findViewById(R.id.listview_posts);
        postsListView.setAdapter(postsAdapter);

        postsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
            if (cursor != null) {
                ((Callbacks)getActivity()).onItemSelected(DataContract.PostEntry.buildPostUri(cursor.getInt(cursor.getColumnIndex(DataContract.PostEntry.COLUMN_ID))));
            }
            listPosition = position;
            }
        });

        if ( (savedInstanceState != null) && (savedInstanceState.containsKey(SELECTED_KEY)) ){
            listPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        updateNews();

        return rootView;
    }

    protected void updateNews() {
        DataSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(POSTS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        if(listPosition != ListView.INVALID_POSITION){
            bundle.putInt(SELECTED_KEY, listPosition);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new CursorLoader(getActivity(),
                DataContract.PostEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data.getCount()<=0){
            //mostrarError();
        }
        else{
            postsAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        postsAdapter.swapCursor(null);
    }
}
