package ua.in.out.sidenavigation;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {
    public static final String ARG_WEB_URL = "web_url";


    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web, container, false);

        String url = getArguments().getString(ARG_WEB_URL);
        WebView webView = (WebView) rootView.findViewById(R.id.wv_url);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);

        getActivity().setTitle(getString(R.string.url));
        return rootView;
    }

}
