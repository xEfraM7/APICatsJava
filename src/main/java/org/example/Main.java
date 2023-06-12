package org.example;

import org.example.service.CatsService;

import javax.swing.*;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        int option_menu = -1;
        String[] buttons = {"1. Ver Gatos", "2. Salir"};
        do {
            //menu principal
            String option = (String) JOptionPane.showInputDialog
                    (null,
                            "Gatitos Java",
                            "Menu Principal",
                            JOptionPane.INFORMATION_MESSAGE,
                            null, buttons, buttons[0]);

            //validamos que opcion selecciona el usuario

            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])){
                    option_menu = i;
                }
            }

            switch (option_menu){
                case 0:
                    CatsService.showCats();
                    break;
                default:
                    break;
            }

        } while (option_menu != 1);
    }
}