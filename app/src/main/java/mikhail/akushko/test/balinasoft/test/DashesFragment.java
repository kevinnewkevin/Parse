package mikhail.akushko.test.balinasoft.test;


import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;



import java.io.StringReader;

import java.util.ArrayList;


public class DashesFragment extends Fragment {

    String[] detal;

    DashesListAdapter dashesListAdapter;
    ListView listView;
    Integer recieveInfo;
    ProgressBar progressBar;

    String name = null;
    String price = null;
    String desc = null;
    String ves = null;

    String text = null;
    int categoryId = 0;
    String urlForImage;
    boolean chek = false;
    private final static String urlAddress = "http://ufa.farfor.ru/getyml/?key=ukAXxeJYZN";
    OkHttpClient client = new OkHttpClient();
    Handler handler = new Handler();
    ArrayList<Dashes> dashes = new ArrayList<Dashes>();

    String titels;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // получаем данные из  СategoryFragment
        XmlPars xmlPars = new XmlPars();
        Bundle bundle = getArguments();

        recieveInfo = bundle.getInt("number");


        xmlPars.execute(recieveInfo);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, null);
        // находим Элементы

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        listView = (ListView) v.findViewById(R.id.listDashes);

        progressBar.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // сохраняем в массив, при нажатии на элемент все данные которые должны отобразиться в ProductDescriptionFragment
                detal = new String[]{dashesListAdapter.getProduct(i).name.toString(), dashesListAdapter.getProduct(i).price.toString(), "Описание: " + "\n" + dashesListAdapter.getProduct(i).description.toString(),
                        dashesListAdapter.getProduct(i).ves.toString()};

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                Bundle bundle = new Bundle();
                //передаем параметры в  другой фрагмент
                bundle.putStringArray("detal", detal);
                bundle.putString("image", dashesListAdapter.getProduct(i).uriImage.toString());

                productDescriptionFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.ContainerFragment, productDescriptionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    class XmlPars extends AsyncTask<Integer, Void, ArrayList<Dashes>> {


        @Override
        // генерируем данные для адаптера
        protected ArrayList<Dashes> doInBackground(Integer... integers) {


            try {
                final String xml = get(urlAddress);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        parse(xml);
                    }
                });
            } catch (IOException e) {

            }


            return dashes;


        }

        private String get(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected void onPostExecute(ArrayList<Dashes> dashes) {

            super.onPostExecute(dashes);

// проверяем загрузилось ли в адаптер хоть что то из  интернета
            if (dashes.size() != 0) {

                progressBar.setVisibility(View.INVISIBLE);
                dashesListAdapter = new DashesListAdapter(getActivity(), dashes);
                listView.setAdapter(dashesListAdapter);
            } else {

                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Проверьте интернет соединение", Toast.LENGTH_LONG).show();


            }


        }


    }


    public void parse(String xml) {
        try {
            XmlPullParser parser = prepareXpp(xml);

            dashes.clear();
            int eventType = parser.getEventType();
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    parser.getName();

                } else if (eventType == XmlPullParser.START_TAG) {


                    if (parser.getName().equalsIgnoreCase("param")) {


                        if (parser.getAttributeValue(0).equalsIgnoreCase("Вес")) {
                            chek = true;

                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equalsIgnoreCase("name")) {
                        name = text;

                    } else if (parser.getName().equalsIgnoreCase("price")) {
                        price = text;
                    } else if (parser.getName().equalsIgnoreCase("description")) {
                        desc = text;
                    } else if (parser.getName().equalsIgnoreCase("picture")) {

                        urlForImage = text;


                    }
                    if (parser.getName().equalsIgnoreCase("categoryId")) {
                        categoryId = Integer.parseInt(text);
                    }

                    if (parser.getName().equalsIgnoreCase("param")) {


                        if (chek) {

                            ves = text;
                            chek = false;
                            if (categoryId == recieveInfo) {


                                dashes.add(new Dashes(name, "цена: " + price, urlForImage, desc, "Вес: " + ves, urlForImage));


                            }

                        }


                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    text = parser.getText();
                }

                eventType = parser.next();
            }
        } catch (Exception e) {

        }
    }


    private XmlPullParser prepareXpp(String rss) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(rss));
        return xpp;
    }
}






