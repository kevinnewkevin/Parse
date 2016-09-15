package mikhail.akushko.test.balinasoft.test;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {

    ListView listdesctipthionProduct;
    ImageView imageViewProductDescrishion;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;

    String uri;
    String detal[];

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);


        // получаем данные из  СategoryFragment
        Bundle bundle = getArguments();
        detal = bundle.getStringArray("detal");
        uri = bundle.getString("image");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_description, container, false);

        // находим элементы
        listdesctipthionProduct = (ListView) view.findViewById(R.id.listViewProductDescrishion);
        imageViewProductDescrishion = (ImageView) view.findViewById(R.id.imageViewProductDescrishion);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarDescripshion);
        progressBar.setVisibility(View.VISIBLE);

        Picasso.with(getActivity()).load(uri).into(imageViewProductDescrishion, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {

            }
        });
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, detal);
        listdesctipthionProduct.setAdapter(adapter);


        return view;
    }


}
