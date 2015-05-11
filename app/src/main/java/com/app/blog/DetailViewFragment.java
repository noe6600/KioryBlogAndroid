package com.app.blog;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.app.blog.database.DataContract;
import com.kiory.blog.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = DetailViewActivity.class.getSimpleName();
    private ShareActionProvider mShareActionProvider;
    private static final int DETAIL_LOADER = 0;
    static final String DETAIL_URI = "URI";
    private Uri mUri;
    private String shareText;

    public DetailViewFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null){
            mUri = arguments.getParcelable(DetailViewFragment.DETAIL_URI);
        }else{
            //When the app is first started on a tablet, it will show the master/detail view
            //without any detail by default, so we will override this by adding our custom action
            //This will show the first post detail
            mUri = DataContract.PostEntry.buildFirstPostUri();
        }

        View rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) { return; }

        String title = data.getString(data.getColumnIndex(DataContract.PostEntry.COLUMN_TITLE));
        String html_content = data.getString(data.getColumnIndex(DataContract.PostEntry.COLUMN_CONTENT));

        getActivity().setTitle(Html.fromHtml(title));

        String header = "<img src=\""+data.getString(data.getColumnIndex(DataContract.PostEntry.COLUMN_HEADER))+"\" style=\"width: 100%;\" />";

        html_content = header+html_content;
        WebView webview = (WebView) getView().findViewById(R.id.detail_webView);
        webview.loadDataWithBaseURL("",html_content,"", "utf-8", "");

        shareText = data.getString(data.getColumnIndex(DataContract.PostEntry.COLUMN_TITLE))+" "+data.getString(data.getColumnIndex(DataContract.PostEntry.COLUMN_URL))+" #TecnoMarqueting.com";
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_view, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareForecastIntent());
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        return shareIntent;
    }


}
