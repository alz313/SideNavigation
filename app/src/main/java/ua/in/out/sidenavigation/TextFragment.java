package ua.in.out.sidenavigation;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {
    public static final String ARG_TEXT = "text";

    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text, container, false);

        String text = getArguments().getString(ARG_TEXT);

        ((TextView) rootView.findViewById(R.id.tv_display_text)).setText(text);
        getActivity().setTitle(getString(R.string.text));
        return rootView;
    }

}
