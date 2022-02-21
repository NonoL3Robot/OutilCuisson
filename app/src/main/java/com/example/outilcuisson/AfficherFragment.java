/*
 * AfficherFragment.java, 21/02/2022
 * IUT Rodez 2022-2022, INFO2
 * pas de copyright, aucun droits
 */

package com.example.outilcuisson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AfficherFragment extends Fragment {

    /**
     * Liste des cuissons enregistré dans l'application
     */
    public static ArrayList<Cuisson> listeCuisson = new ArrayList<>();

    /**
     * Ajouter une cuisson
     *
     * @param cuisson La cuisson à ajouter
     */
    public static void addCuisson(Cuisson cuisson) {
        listeCuisson.add(cuisson);
        updateSaveFile();
    }

    /**
     * Met a jour le fichier dataCuisson.txt qui sauvegarde la liste des cuissons
     */
    private static void updateSaveFile() {
        // TODO Il faut creer un fichier cuisson.txt si il n'existe pas

//        try {
//            FileOutputStream fos = new FileOutputStream("dataCuisson.txt", false);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(listeCuisson);
//            oos.close();
//            fos.close();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    }

    /**
     * Charge la liste des cuissons contenue dans le fichier dataCuisson.txt
     */
    private static void loadSaveFile() {
        // TODO à décommenter lorsque updateSaveFile() sera terminé

//        try {
//            FileInputStream fis = new FileInputStream("dataCuisson.txt");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            listeCuisson = (ArrayList) ois.readObject();
//
//            ois.close();
//            fis.close();
//        } catch (FileNotFoundException ignored) {
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } catch (ClassNotFoundException c) {
//            System.out.println("Class not found");
//            c.printStackTrace();
//        }
    }

    public AfficherFragment() {
    }

    public static AfficherFragment newInstance() {
        return new AfficherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadSaveFile();
        return inflater.inflate(R.layout.afficher_fragment, container, false);
    }

}
