package com.example.outilcuisson;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements AjouterFragment.EcouteurAjout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 gestionnairePagination = findViewById(R.id.activity_main_viewpager);
        TabLayout gestionnaireOnglet = findViewById(R.id.tab_layout);

        gestionnairePagination.setAdapter(new AdaptateurPage(this));

        String[] titreOnglet = {
                getString(R.string.tab_afficher),
                getString(R.string.tab_ajouter)
        };

        new TabLayoutMediator(gestionnaireOnglet, gestionnairePagination,
                (tab, position) -> tab.setText(titreOnglet[position])
        ).attach();
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
        AfficherFragment fragmentAModifier =
            (AfficherFragment) getSupportFragmentManager().findFragmentByTag(
                "f0");

        if (fragmentAModifier != null) {
            fragmentAModifier.addCuisson(cuisson);
        }
    }
}