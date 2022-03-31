package corewar.server;

import java.util.ArrayList;

import corewar.utils.FileOperation;

public class ClassementHandler implements Runnable {
    private String path;
    private ArrayList<Thread> games;
    private boolean running = true;

    public ClassementHandler(String path) {
        this.path = path;
        games = new ArrayList<>();
    }

    public void run() {
        while (this.running) {
            try {
                Thread.sleep(1000);   
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i) != null && !games.get(i).isAlive()) {
                    System.out.println("Partie terminee, update classement");
                    games.set(i, null);
                }
            }
        }
    }

    public void addGame(Thread game) {
        System.out.println("Game ajoutee");
        this.games.add(game);
    }

    public void close() {
        this.running = false;
    }
}
