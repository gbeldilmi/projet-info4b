package corewar.server;

import java.util.ArrayList;

import corewar.utils.API;
import corewar.utils.FileOperation;
import java.io.File;

public class ClassementHandler implements Runnable {
    private String path;
    private ArrayList<Game> games = null;
    private ArrayList<Thread> threads = null;
    private ArrayList<String> classement = null;
    private boolean run = true;

    public ClassementHandler(String path) {
        this.path = path;
        if (!new File(this.path).exists())
            FileOperation.create(this.path);
        this.classement = FileOperation.read(this.path);
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
                    this.updateClassement(this.games.get(i).getClassement());
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

    private void updateClassement(String[] gameClassement) {
        ArrayList<String> newClassement = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> wins = new ArrayList<>();

        if (!this.classement.get(1).equals("")) {
            for (int i = 1; i < this.classement.size(); i++) {
                names.add(this.classement.get(i).split(API.SEPARATOR)[0]);
                wins.add(Integer.parseInt(this.classement.get(i).split(API.SEPARATOR)[1]));
            }
        }
        for (int i = 0; i < gameClassement.length; i++) {
            if (names.contains(gameClassement[i])) {
                if (i < gameClassement.length - 1)
                    wins.set(names.indexOf(gameClassement[i]), wins.get(names.indexOf(gameClassement[i])) + 1);
            } else {
                names.add(gameClassement[i]);
                if (i < gameClassement.length - 1)
                    wins.add(1);
                else
                    wins.add(0);
            }
        }
        for (int i = 0; i < names.size(); i++) {
            for (int j = i; j < names.size(); j++) {
                if (wins.get(i) < wins.get(j)) {
                    String name = names.get(i);
                    int win = wins.get(i);
                    names.set(i, names.get(j));
                    wins.set(i, wins.get(j));
                    names.set(j, name);
                    wins.set(j, win);
                }
            }
        }
        for (int i = 0; i < names.size(); i++)
            newClassement.add(names.get(i) + API.SEPARATOR + wins.get(i));
        FileOperation.write(this.path, newClassement);
        this.classement = FileOperation.read(this.path);
    }

    public String getClassement() {
        String str = "";

        if (this.classement.get(1).equals(""))
            return null;
        for (int i = 1; i < this.classement.size(); i++)
            str += this.classement.get(i) + API.SEPARATOR;
        System.out.println(str);
        return str.substring(0, str.length() - 1);
    }
}
