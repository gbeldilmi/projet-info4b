package corewar.server;

import java.util.ArrayList;

import corewar.utils.API;
import corewar.utils.FileOperation;

public class ClassementHandler implements Runnable {
    private String path;
    private ArrayList<Game> games;
    private ArrayList<Thread> gamesThreads;
    private boolean running = true;

    public ClassementHandler(String path) {
        this.path = path;
        games = new ArrayList<>();
        gamesThreads = new ArrayList<>();
    }

    public void run() {
        while (this.running) {
            try {
                Thread.sleep(1000);   
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i) != null && gamesThreads.get(i) != null && !gamesThreads.get(i).isAlive()) {
                    System.out.println("Partie terminee, update classement");
                    updateClassement(games.get(i));
                    games.set(i, null);
                    gamesThreads.set(i, null);
                }
            }
        }
    }

    public void addGame(Game game, Thread thread) {
        System.out.println("Game ajoutee");
        this.games.add(game);
        this.gamesThreads.add(thread);
    }

    public void close() {
        this.running = false;
    }

    private void updateClassement(Game game) {
        ArrayList<String> currentClassement = FileOperation.read(this.path);
        ArrayList<String> currentClassementNames = new ArrayList<>();
        ArrayList<Integer> currentClassementWins = new ArrayList<>();
        ArrayList<String> gameClassement = game.getGameClassement();
        String tmp1;
        int tmp2;

        for (int i = 1; i < currentClassement.size(); i++) {
            currentClassementNames.add(currentClassement.get(i).split(API.SEP)[0]);
            currentClassementWins.add(Integer.parseInt(currentClassement.get(i).split(API.SEP)[1]));
        }
        for (int i = 0; i < gameClassement.size(); i++) {
            tmp2 = currentClassementNames.indexOf(gameClassement.get(i));
            if (tmp2 != -1 && i < gameClassement.size() - 1)
                currentClassementWins.set(tmp2, currentClassementWins.get(tmp2) + 1);
            else {
                currentClassementNames.add(gameClassement.get(i));
                if (i < gameClassement.size() - 1)
                    currentClassementWins.add(1);
                else
                    currentClassementWins.add(0);
            }
        }
        for (int i = 0; i < currentClassementWins.size(); i++) {
            for (int j = 1; j < currentClassementWins.size(); j++) {
                if (currentClassementWins.get(i) < currentClassementWins.get(j)) {
                    tmp1 = currentClassementNames.get(i);
                    tmp2 = currentClassementWins.get(i);
                    currentClassementNames.set(i, currentClassementNames.get(j));
                    currentClassementWins.set(i, currentClassementWins.get(j));
                    currentClassementNames.set(j, tmp1);
                    currentClassementWins.set(j, tmp2);
                } 
            }
        }
        currentClassement = new ArrayList<>();
        for (int i = 0; i < currentClassementNames.size(); i++) {
            currentClassement.add(currentClassementNames.get(i) + API.SEP  + currentClassementWins.get(i));
            System.out.println(currentClassement.get(i));
        }
        FileOperation.write(this.path, currentClassement);
    }
}
