package game;

import game.gui.StartMenu;

import javax.swing.*;

public class Game implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }
}
