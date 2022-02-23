package com.example.outilcuisson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CuissonAdapter extends ArrayAdapter<Cuisson> {

    private int identifiantVueItem;


    public CuissonAdapter(@NonNull Context context, int vueItem, List<Cuisson> lesCuissons) {
        super(context, vueItem,lesCuissons);
        this.identifiantVueItem = vueItem;
    }

    public View getView(int position, View uneVue, ViewGroup parent){
        Cuisson unPlat = getItem(position);
        LinearLayout vueItemListe;

        if (uneVue == null) {

            /*
             * la vue décrivant chaque item de la liste n'est pas encore créée
             * Il faut désérialiser le layout correspondant à cette vue.
             */
            LayoutInflater outil;
            outil = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vueItemListe = (LinearLayout) outil.inflate(identifiantVueItem,
                    parent, false);

        } else {
            vueItemListe = (LinearLayout) uneVue;
        }

        // on accède aux 2 widgets présents sur la vue
        TextView vuePlat = vueItemListe.findViewById(R.id.item_plat);
        TextView vueDuree = vueItemListe.findViewById(R.id.item_duree);
        TextView vueDegres = vueItemListe.findViewById(R.id.item_degres);

        // on place dans les 2 widgets les valeurs de l'item à afficher
        vuePlat.setText(unPlat.getPlat());
        vueDuree.setText(unPlat.getHeure());
        vueDegres.setText(Integer.toString(unPlat.getDegree()));
        return vueItemListe;
    }
}

