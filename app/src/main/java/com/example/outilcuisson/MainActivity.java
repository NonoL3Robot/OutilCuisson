package com.example.outilcuisson;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    /**
     * Le nom du fichier de sauvegarde
     */
    private static final String NOM_FICHIER = "cuisson.txt";

    private ArrayList<Cuisson> listeCuisson;

    private ViewPager2 gestionnairePagination;

    public boolean modeEdition = false;
    public Cuisson cuissonAEditer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestionnairePagination = findViewById(
            R.id.activity_main_viewpager);
        TabLayout gestionnaireOnglet = findViewById(R.id.tab_layout);

        gestionnairePagination.setAdapter(new AdaptateurPage(this));

        String[] titreOnglet = {
            getString(R.string.tab_afficher), getString(R.string.tab_ajouter)
        };

        new TabLayoutMediator(gestionnaireOnglet, gestionnairePagination,
                              (tab, position) -> tab.setText(
                                  titreOnglet[position])).attach();

        /* Crée la liste des cuissons */
        listeCuisson = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerFichier();
    }

    @Override
    protected void onPause() {
        sauvegarderFichier();
        super.onPause();
    }

    /**
     * Ajoute une cuisson a la liste des cuissons
     *
     * @param cuisson Objet cuisson a ajouter dans la liste
     */
    public void addCuisson(Cuisson cuisson) throws CuissonDejaExistanteException {
        if (estDansCuisson(cuisson)) throw new CuissonDejaExistanteException();

        listeCuisson.add(cuisson);

        /* Test affichage du contennu de la liste des cuisson */
        System.out.println(Arrays.asList(listeCuisson));
    }

    public ArrayList<Cuisson> getListeCuisson() {
        return listeCuisson;
    }

    private boolean estDansCuisson(Cuisson aTester) {
        for (Cuisson cuisson : listeCuisson) {
            if (cuisson.getPlat().equals(aTester.getPlat())) return true;
        }
        return false;
    }

    public void editerCuisson(int index) {
        changeFragment(1);
        modeEdition = true;
        cuissonAEditer = listeCuisson.get(index);
    }


    private void chargerFichier() {
        try {


            FileInputStream fis = openFileInput(NOM_FICHIER);
            ObjectInputStream ois = new ObjectInputStream(fis);

            listeCuisson = (ArrayList<Cuisson>) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sauvegarderFichier() {
        try {
            FileOutputStream fos = openFileOutput(NOM_FICHIER, MODE_PRIVATE);
            ObjectOutputStream oos =  new ObjectOutputStream(fos);

            oos.writeObject(listeCuisson);

            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Crée le menu d'options en le désérialisant à partir du fichier
     * menu_options.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Réalise l'action souhaité en fonction de l'item du menu selectionné */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aideOptions:
                afficherAide();
                break;
            case R.id.reinitOptions:
                reinitData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void afficherAide() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_aide)
                .setMessage(R.string.alert_content_help)
                .setNeutralButton(R.string.alert_neutral_button, null)
                .show();
    }

    public void reinitData() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_reinit)
                .setMessage(R.string.alert_content_reinit)
                .setNeutralButton(R.string.alert_neutral_button, null)
                .setPositiveButton(R.string.btn_valider, (dialogInterface, i) -> {
                    listeCuisson.clear();
                    AfficherFragment.adapterCuissons.clear();
                    AfficherFragment.adapterCuissons.notifyDataSetChanged();})
                .show();
    }

    public void changeFragment(int pos) {
        gestionnairePagination.setCurrentItem(pos);
    }
}