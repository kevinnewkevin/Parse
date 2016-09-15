package mikhail.akushko.test.balinasoft.test;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * fragment для отображения контактов и карты
 */
public class ContactFragment extends Fragment implements OnMapReadyCallback {

    MapView gMapView;
    GoogleMap gMap = null;

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    // массив для ListView
    String[] telNumber = {" Velcom: +375 44 536453", " MTC: +375 44 535653"};

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact, container, false);

        // находим Элементы
        final ListView listContact = (ListView) view.findViewById(R.id.listViewContact);

        gMapView = (MapView) view.findViewById(R.id.map);
        gMapView.onCreate(savedInstanceState);
        gMapView.onResume();
        gMapView.getMapAsync(this);


        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, telNumber);
        listContact.setAdapter(adapter);


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // ставим тип карты стандартный
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // добавляем маркеры на карту
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.90347853, 27.56499767)).title("1"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.90636074, 27.52684593)).title("1"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.94537747, 27.63670921)).title("1"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(53.92375094, 27.67567635)).title("1"));

        // указываем координаты для камеры
        LatLng Minsk = new LatLng(53.90347853, 27.56499767);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Minsk, 11));
        //добавляем кнопку MyLocation  на карту
        gMap.setMyLocationEnabled(true);

    }
}
