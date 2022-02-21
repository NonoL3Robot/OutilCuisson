/*
 * AdaptateurPage.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdaptateurPage extends FragmentStateAdapter {

    private static final int NB_FRAGMENT = 2;

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
