package main;

import com.formdev.flatlaf.FlatDarkLaf;
import javafx.application.Platform;
import main.mainwindow.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", "javafx.application.Preloader");
        try {
            Platform.startup(() -> {
            });
        }
        catch (IllegalStateException e) {
            // ignore
        }
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        new MainWindow();
        System.out.println("New main window created");
    }
}