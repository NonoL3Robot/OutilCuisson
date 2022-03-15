package com.example.outilcuisson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AjouterFragment.EcouteurAjout {

    /**
     * Le nom du fichier de sauvegarde
     */
    private static final String NOM_FICHIER = "cuisson.txt";

    /**
     * Tag utilisé dans les messages de log. Les messages de log sont
     * affichés en cas
     * de problème lors de l'accès au fichier
     */
    private static final String TAG = "Cuisson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 gestionnairePagination = findViewById(
            R.id.activity_main_viewpager);
        TabLayout gestionnaireOnglet = findViewById(R.id.tab_layout);

        gestionnairePagination.setAdapter(new AdaptateurPage(this));

        String[] titreOnglet = {
            getString(R.string.tab_afficher), getString(R.string.tab_ajouter)
        };

        new TabLayoutMediator(gestionnaireOnglet, gestionnairePagination,
                              (tab, position) -> tab.setText(
                                  titreOnglet[position])).attach();
    }

    @Override
    protected void onStart() {
        super.onStart();
        readFromFile();
        System.out.println("onResume : " + AfficherFragment.cuissonAffichees);
    }

    @Override
    protected void onDestroy() {
        System.out.println(
            "Avant write : " + AfficherFragment.cuissonAffichees);
        writeToFile();
        System.out.println(
            "Après write : " + AfficherFragment.cuissonAffichees);
        super.onDestroy();
    }

    private void writeToFile() {
        try {
            File path = getFilesDir();
            File file = new File(path, NOM_FICHIER);

            if (!path.exists()) {
                path.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(AfficherFragment.cuissonAffichees);

            oos.close();
            fos.close();
            System.out.println("Ecriture réalisée");

        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    private void readFromFile() {
        try {
            File path = getFilesDir();
            File file = new File(path, NOM_FICHIER);

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            AfficherFragment.cuissonAffichees
                = (ArrayList<String>) ois.readObject();

            ois.close();
            fis.close();
            System.out.println("Lecture réalisée");

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
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
        new AlertDialog.Builder(this).setTitle(R.string.alert_title_aide)
                                     .setMessage(R.string.alert_content_help)
                                     .setNeutralButton(
                                         R.string.alert_neutral_button, null)
                                     .show();
    }

    public void reinitData() {
        new AlertDialog.Builder(this).setTitle(R.string.alert_title_reinit)
                                     .setMessage(R.string.alert_content_reinit)
                                     .setNeutralButton(
                                         R.string.alert_neutral_button, null)
                                     .setPositiveButton(R.string.btn_valider,
                                                        (dialogInterface, i) -> {
                                                            AfficherFragment.adapterCuissons
                                                                .clear();
                                                            AfficherFragment.adapterCuissons
                                                                .notifyDataSetChanged();
                                                        })
                                     .show();
    }

    @Override
    public void recevoirCuisson(Cuisson cuisson) {
        AfficherFragment fragmentAModifier
            = (AfficherFragment) getSupportFragmentManager().findFragmentByTag(
            "f0");

        if (fragmentAModifier != null) {
            fragmentAModifier.addCuisson(cuisson);
        }
    }
}