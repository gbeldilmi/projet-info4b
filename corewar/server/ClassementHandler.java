package corewar.server;

import java.util.ArrayList;

public class ClassementHandler implements Runnable {
    private String path;
    private ArrayList<Game> games = null;
    private ArrayList<Thread> threads = null;
    private boolean run = true;

    public ClassementHandler(String path) {
        this.path = path;
        this.games = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public void run() {
        while (this.run) {
            try {
                Thread.sleep(1000); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < games.size(); i++) {
                if (this.games.get(i) != null && this.threads.get(i) != null && !this.threads.get(i).isAlive()) {
                    this.display(this.games.get(i).getClassement()); // temporaire
                    this.games.set(i, null);
                    this.threads.set(i, null);
                }
            }
        }
    }

    public void addGame(Game game, Thread thread) {
        this.games.add(game);
        this.threads.add(thread);
    }

    public void close() {
        this.run = false;
    }

    //  temporaire
    private void display(String[] classement) {
        for (int i = 0; i < classement.length; i++)
            System.out.println(classement[i]);
    }
}
