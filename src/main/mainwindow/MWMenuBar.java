package main.mainwindow;

import javax.swing.*;

import main.cardtypemanager.CardTypeManagerWindow;

public class MWMenuBar extends JMenuBar {
    private JMenu menu, submenu;

    private JMenuItem menuItem;
    public MWMenuBar(MainWindow mw) {
        super();
        menu = new JMenu("File");
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> mw.save());
        menu.add(menuItem);
        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(e -> mw.load());
        menu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(e -> mw.dispose());
        menu.add(menuItem);
        add(menu);

        menu = new JMenu("Edit");
        add(menu);

        menu = new JMenu("Tools");
        menuItem = new JMenuItem("Manage card types");
        menuItem.addActionListener(e -> new CardTypeManagerWindow(mw));
        menu.add(menuItem);
        menuItem = new JMenuItem("Card count");
        menuItem.addActionListener(e -> mw.cardCount());
        menu.add(menuItem);
        menuItem = new JMenuItem("Delete unused media");
        menuItem.addActionListener(e -> mw.deleteUnusedMedia());
        menu.add(menuItem);
        add(menu);


        menu = new JMenu("Help");
        add(menu);
    }
}
