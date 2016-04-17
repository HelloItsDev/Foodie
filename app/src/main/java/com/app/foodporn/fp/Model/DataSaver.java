package com.app.foodporn.fp.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by louis on 09/04/2016.
 */

public class DataSaver {

    //une map qui contient les recettes préférées de l'utilisateur
    ArrayList<Recipe> listFavoris = new ArrayList<>();
    int index ;
    private static String FILENAME = "saveFavoris";
    private static String FILENAME_INDEX = "saveIndex";


    //constructeur par defaut

    //chargement des paramtres depuis la persistance
    public DataSaver(Context ctx){
        //retrouver les favoris sauvegardés qqpart
        FileInputStream in = null;
        try
        {
            File saveFavoris = new File(ctx.getFilesDir()  + "/" + FILENAME);
            FileInputStream fis = new FileInputStream(saveFavoris);


            ObjectInputStream ois;
            ois = new ObjectInputStream(fis);
            listFavoris = (ArrayList) ois.readObject();
            ois.close();
            fis.close();

            File saveIndex = new File(ctx.getFilesDir()  + "/" + FILENAME_INDEX);
            FileInputStream fis2 = new FileInputStream(saveIndex);

            ObjectInputStream ois2;
            ois2= new ObjectInputStream(fis2);
            index = ois2.readInt();
            ois2.close();
            fis2.close();

            Log.i("index recup: ", index + "");

        }catch(IOException ioe){
            ioe.printStackTrace();
            index = 1;
            return;
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }

        Log.i("INFO : ", listFavoris.toString());
    }

    public ArrayList<Recipe> getListFavoris() {
        return listFavoris;
    }

    //ajout d'une recette a la liste des favoris --juste un setter pour le moment
    public void addFavoriteRecipes(Recipe recette, Context ctx) throws FileNotFoundException {

        //ajout a la liste
        listFavoris.add(recette);
        //save de la liste dans le localstorage

        try{
            File saveFavoris = new File(ctx.getFilesDir()  + "/" + FILENAME);
            FileOutputStream fos= new FileOutputStream(saveFavoris);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(listFavoris);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public void setIndex(Context ctx, int index){
        this.index = index;
        try{
            File saveIndex = new File(ctx.getFilesDir()  + "/" + FILENAME_INDEX);
            FileOutputStream fos= new FileOutputStream(saveIndex);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeInt(index);
            oos.close();
            fos.close();

            Log.i("index save: ", index + "");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public int getIndex() {
        return index;
    }

    //DEBUG
    public String toString(){
        return listFavoris.toString();
    }

    //permet de sauvegarder une image de recette sur la carte SD de l'appareil
    public void saveToSD() throws IOException {
        URL url = new URL ("file://some/path/anImage.png");
        InputStream input = url.openStream();
        try {
            //The sdcard directory e.g. '/sdcard' can be used directly, or
            //more safely abstracted with getExternalStorageDirectory()
            File storagePath = Environment.getExternalStorageDirectory();
            OutputStream output = new FileOutputStream(storagePath + "/myImage.png");
            try {

                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                output.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            input.close();
        }
    }

}

