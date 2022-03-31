package corewar.server;

import java.util.ArrayList;

import corewar.mars.Warrior;
import corewar.utils.API;
import corewar.mars.Mars;

public class Game implements Runnable { 
    private Mars mars;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // index 0 == host
    private Warrior[] warriors;
    private int maxPlayers;
    private boolean started;

    public Game(ClientHandler host, int maxPlayers) {
        clientHandlers.add(host);
        this.maxPlayers = maxPlayers;
        this.warriors = initWarriors();
        this.started = false;
    }

    public void run() {
        System.out.println("Running");
        while (!isFull() || !isAllWarriorUploaded()) {
            System.out.println("Wait game ready");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!this.started && isFull() && isAllWarriorUploaded()) {
            System.out.println("Init");
            this.mars = new Mars(this.warriors);
            this.mars.start();
            this.started = true;
        }
        if (this.started) {
            System.out.println("Wait end");
            try {
                this.mars.join();
                System.out.println(this.mars);
                for (Warrior warrior : warriors) {
                    System.out.println(this.clientHandlers.get(warrior.getId()).getClientUsername() + " (" + warrior.getName() + ") : " + warrior.getRank());
                }
                for (ClientHandler clientHandler : this.clientHandlers) {
                    sendGameResult(clientHandler);
                    System.out.println("coucou je suis là et j'en ai plein le cul");
                }
                this.started = false;
                this.warriors = null;
                this. clientHandlers = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*
        System.out.println(isFull() + " " + this.warriors.length + " " + this.maxPlayers);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isFull() && this.warriors.length != this.maxPlayers) {
            try {
                System.out.println(this.gameCapacity() + " " + this.warriors.length);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (!this.started && isFull() && this.warriors.length == this.maxPlayers) {
            this.mars = new Mars(this.warriors);
            this.mars.start();
            System.out.println("Game started...");
            this.started = true;
        } else if (started) {
            try {
                this.mars.join();
                System.out.println("fin de la partie !");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }

    private void sendGameResult(ClientHandler clientHandler) {
        Warrior swap;
        String str = API.MSG;

        for (int i = 0; i < warriors.length; i++) {
            for (int j = i; j < warriors.length; j++) {
                if (warriors[i].getRank() > warriors[j].getRank()) {
                    swap = warriors[i];
                    warriors[i] = warriors[j];
                    warriors[j] = swap;
                }  
            }
        }
        for (Warrior warrior : warriors)
            str += API.SEP + warrior.getRank() + " : "  + this.clientHandlers.get(warrior.getId()).getClientUsername() + " (" + warrior.getName() + ")";
        clientHandler.send(str);
    }

    private Warrior[] initWarriors() {
        Warrior[] warriors = new Warrior[this.maxPlayers];

        for (int i = 0; i < warriors.length; i++)
            warriors[i] = null;
        return  warriors;
    }

    public String gameCapacity() {
        return this.clientHandlers.size() + "/" + this.maxPlayers;
    }

    public boolean isFull() {
        return this.clientHandlers.size() == this.maxPlayers;
    }

    public boolean isAllWarriorUploaded() {
        boolean uploaded = true;

        for (int i = 0; i < this.warriors.length; i++) {
            if (this.warriors[i] == null)
                uploaded = false;
        }
        if (uploaded)
            return this.warriors.length == this.clientHandlers.size();
        return uploaded;
    }

    public int getClientId(String username) {
        for (int i = 0; i < clientHandlers.size(); i++) {
            if (clientHandlers.get(i).getClientUsername().equals(username))
                return i;
        }
        return -1;
    }

    //  Voir si juste
    public synchronized void addWarrior(Warrior warrior) {
        int i = 0;

        while (i < this.warriors.length && this.warriors[i] != null)
            i++;
        if (this.warriors[i] == null)
            this.warriors[i] = warrior;
    }

    public synchronized boolean addClient(ClientHandler clientHandler) {
        if (this.clientHandlers.size() == this.maxPlayers)
            return false;
        this.clientHandlers.add(clientHandler);
        return true;
    }


}
