/*
 * AdaptateurPage.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Gestionnaire des différent fragments de l'application
 *
 * @author THIZY Alexandre
 * @author VABRE Lucàs
 * @author VILLENEUVE Noé
 */
public class AdaptateurPage extends FragmentStateAdapter {

    /**
     * Nombre de fragment de l'application
     */
    private static final int NB_FRAGMENT = 2;

    /**
     * Crée une adaptateur de page
     *
     * @param activite le Main Activity de référence
     */
    public AdaptateurPage(FragmentActivity activite) {
        super(activite);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return AfficherFragment.newInstance();
            case 1:
                return AjouterFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NB_FRAGMENT;
    }
}
