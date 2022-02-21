/*
 * AjouterFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

public class AjouterFragment extends Fragment {

    EditText inputPlat;
    TimePicker inputDuree;
    EditText inputTemperature;

    public AjouterFragment() {
    }

    public static AjouterFragment newInstance() {
        return new AjouterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ajouter_fragment, container, false);
    }

    public void actionBtnEffacer(View view) {
    }

    public void actionBtnAjouter(View view) {
    }

}
