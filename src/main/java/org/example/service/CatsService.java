package org.example.service;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.example.model.Cats;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatsService {
    public static void showCats() throws IOException {
        //1. vamos a traer los datos de la API
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();

        Response response = client.newCall(request).execute();

        String resultJson = response.body().string();

        //cortar los corchetes

        resultJson = resultJson.substring(1);
        resultJson = resultJson.substring(0, resultJson.length() - 1);

        //crear un objeto de la clase Gson

        Gson gson = new Gson();
        Cats cats = gson.fromJson(resultJson, Cats.class);

        //redimensionar la imagen en caso de necesitar

        Image image = null;

        try {
            URL url = new URL(cats.getUrl());
            image = ImageIO.read(url);

            ImageIcon wallpaperCat = new ImageIcon(image);

            if (wallpaperCat.getIconWidth() > 800) {
                //redimensionamos
                Image wallpaper = wallpaperCat.getImage();
                Image modified = wallpaper.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                wallpaperCat = new ImageIcon(modified);
            }

            String menu = "Opciones: \n"
                    + "1. Ver otra imagen\n"
                    + "2. Favoritos\n"
                    + "3. Volver al menu\n";

            String[] buttons = {"ver otra imagen", "favorito", "volver"};
            String id_cat = cats.getId();
            String option = (String) JOptionPane.showInputDialog(null, menu, id_cat, JOptionPane.INFORMATION_MESSAGE, wallpaperCat, buttons, buttons[0]);

            int select = -1;

            for (int i = 0; i < buttons.length; i++) {
                if (option.equals(buttons[i])) {
                    select = i;
                }
            }

            switch (select) {
                case 0:
                    showCats();
                    break;
                case 1:
                    favoritesCats(cats);
                    break;
                default:
                    break;
            }


        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void favoritesCats(Cats cats) {

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\" : \"" + cats.getId() + "\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cats.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
