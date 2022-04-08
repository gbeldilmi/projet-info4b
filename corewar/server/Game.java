package corewar.server;

import java.util.ArrayList;

import corewar.mars.Mars;
import corewar.mars.Warrior;
import corewar.utils.API;

public class Game implements Runnable {
    private Server server = null;
    private Mars mars = null;
    private ArrayList<ClientHandler> clientHandlers = null;
    private Warrior[] warriors = null;
    private int maxPlayers = -1;
    
    public Game(Server server, ClientHandler clientHandler, int maxPlayers) {
        this.server = server;
        this.clientHandlers = new ArrayList<>();
        this.clientHandlers.add(clientHandler);
        this.maxPlayers = maxPlayers;
        this.warriors = initWarriors();
    }

    //  Gère l'exécution d'une partie
    @Override
    public void run() {
        while (!isFull() || !isAllWarriorUploaded()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("la partie " + clientHandlers.get(0).gameId + " a commence !");
        this.mars = new Mars(this.warriors);
        this.mars.start();
        try {
            this.mars.join();
            this.setClassement();
            this.sendClassement();
            System.out.println("la partie " + clientHandlers.get(0).gameId + " est terminee !");
            this.server.removeGame(this.clientHandlers.get(0).gameId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //  Retourne le classement de la partie
    public String[] getClassement() {
        int length = this.warriors.length;
        String[] warriorNames = new String[length];

        for (int i = 0; i < length; i++)
            warriorNames[i] = this.warriors[i].getName();
        return warriorNames;
    }

    //  Trie les warriors du premier au dernier
    private void setClassement() {
        int length = this.warriors.length;
        Warrior swap;

        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (this.warriors[i].getRank() > this.warriors[j].getRank()) {
                    swap = this.warriors[i];
                    this.warriors[i] = this.warriors[j];
                    this.warriors[j] = swap;
                }  
            }
        }
    }

    //  Envoi le classement de la partie à chaque joueurs
    public void sendClassement() {
        String str = "";
        int i = 1;

        for (Warrior warrior : this.warriors) {
            str += i + " - " + this.clientHandlers.get(warrior.getId()).clientUsername + " (" + warrior.getName() + ")" + API.SEPARATOR;
            i++;
        }
        str = str.substring(0, str.length() - 1);
        for (ClientHandler clientHandler : this.clientHandlers) {
            clientHandler.send(str);
        }
    }

    //  Initialise un tableau de Warrior de taille maxPlayers à null
    private Warrior[] initWarriors() {
        Warrior[] warriors = new Warrior[this.maxPlayers];

        for (int i = 0; i < warriors.length; i++)
            warriors[i] = null;
        return warriors;
    }

    //  Retourne true si la partie est complète
    public boolean isFull() {
        return this.clientHandlers.size() == this.maxPlayers;
    }

    //  Retourne true si tous les Warrior ont été uploadés
    public boolean isAllWarriorUploaded() {
        for (int i = 0; i < this.warriors.length; i++) {
            if (this.warriors[i] == null)
                return false;
        }
        return true;
    }

    //  Retourne l'index du client dont l'username correspond
    public int getClientId(String username) {
        int length = this.clientHandlers.size();

        for (int i = 0; i < length; i++) {
            if (this.clientHandlers.get(i).clientUsername.equals(username))
                return i;
        }
        return -1;
    }
    
    //  Ajoute un nouveau warrior à la partie
    public synchronized void addWarrior(ClientHandler clientHandler, Warrior warrior) {
        int i = 0;

        while (i < this.warriors.length && this.warriors[i] != null)
            i++;
        if (this.warriors[i] == null)
            this.warriors[i] = warrior;
        clientHandler.warriorId = i;
    }

    //  Ajoute un nouveau client à la partie
    public synchronized void addClient(ClientHandler clientHandler) {
        this.clientHandlers.add(clientHandler);
    }

    //  Supprime un client de la partie
    public synchronized void removeClient(ClientHandler clientHandler) {
        if (clientHandler.warriorId == -1) {
            clientHandler.gameId = -1;
            this.clientHandlers.remove(clientHandler);
        }
        else {
            if (!this.isAllWarriorUploaded()) {
                this.warriors[clientHandler.warriorId] = null;
                clientHandler.gameId = -1;
                clientHandler.warriorId = -1;
                this.clientHandlers.remove(clientHandler);
            }
        }
    }

    //  Retourne une représentation de la partie (de sa capacité)
    @Override
    public String toString() {
        return this.clientHandlers.size() + "/" + this.maxPlayers;
    }
}
