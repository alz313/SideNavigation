package ua.in.out.sidenavigation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    public static final String ARG_IMAGE_URL = "image_url";

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.im_display_image);
        String url = getArguments().getString(ARG_IMAGE_URL);

        Picasso.with(getActivity())
                .load(url)
                .noFade()
                .into(imageView);

        getActivity().setTitle(getString(R.string.image));

        return rootView;
    }

}
