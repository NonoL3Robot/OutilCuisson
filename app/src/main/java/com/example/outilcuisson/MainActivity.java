package com.example.outilcuisson;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

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
    protected void onResume() {
        super.onResume();
        printFile("print avant readFromFile()");
        readFromFile(this);

        printFile("print après readFromFile()");
    }

    @Override
    protected void onPause() {
        printFile("print avant writeToFile()");
        writeToFile("test", this);
        printFile("print après writeToFile()");
        super.onPause();
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(NOM_FICHIER);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                context.openFileOutput(NOM_FICHIER, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //    private void writeToFile() {
    //        try {
    //            File path = getFilesDir();
    //            File file = new File(path, NOM_FICHIER);
    //
    //            if (!path.exists()) {
    //                path.mkdirs();
    //            }
    //            FileWriter fw = new FileWriter(file);
    //            BufferedWriter bw = new BufferedWriter(fw);
    //
    //            printFile("print avant écriture");
    //
    //            for (String s : AfficherFragment.cuissonAffichees) {
    //                bw.write(s + "\n");
    //            }
    //
    //            printFile("print après écriture");
    //
    //            fw.close();
    //        } catch (IOException e) {
    //            Log.e(TAG, "File write failed: " + e.toString());
    //        }
    //    }
    //
    //    private void readFromFile() {
    //        try {
    //            File path = getFilesDir();
    //            File file = new File(path, NOM_FICHIER);
    //            FileReader fr = new FileReader(file);
    //            BufferedReader br = new BufferedReader(fr);
    //            String receiveString;
    //
    //            printFile("print avant lecture");
    //
    //            while ((receiveString = br.readLine()) != null) {
    //                AfficherFragment.cuissonAffichees.add(receiveString);
    //            }
    //
    //            printFile("print après lecture");
    //
    //            fr.close();
    //        } catch (FileNotFoundException e) {
    //            Log.e(TAG, "File not found: " + e.toString());
    //        } catch (IOException e) {
    //            Log.e(TAG, "Can not read file: " + e.toString());
    //        }
    //    }

    public void printFile(String intitule) {
        System.out.println(intitule);
        try {
            File path = getFilesDir();
            File file = new File(path, NOM_FICHIER);
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while (line != null) {
                System.out.println(line);
                line = in.readLine();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                // TODO
                break;
            case R.id.reinitOptions:
                // TODO
                break;
        }
        return super.onOptionsItemSelected(item);
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