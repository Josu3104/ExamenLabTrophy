/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PsnTrophies;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 *
 * @author Josue Gavidia
 */
public class PSNUsers {

    private RandomAccessFile psn, us;
    private HashTable users;
    long size = 0;

    public PSNUsers() {
        try {
            psn = new RandomAccessFile("psn", "rw");
            us = new RandomAccessFile("users", "rw");

            File a = new File("users");
            a.createNewFile();
            a = new File("psn");
            a.createNewFile();
            //RELOAD
            reloadHashTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadHashTable() throws IOException {
        size = 0;
        us.seek(0);
        users = new HashTable();
        while (us.getFilePointer() < us.length()) {
            String username = us.readUTF();
            int puntosTrofeos = us.readInt();
            int cantTrofeos = us.readInt();
            boolean state = us.readBoolean();

            if (!state) {
                System.out.println("Gonce, reduced to atoms ");
                continue;
            }

            users.add(username, size);
            size++;
            System.out.println(users.size);
        }
    }

    //Formato =  user, acum Puntos por trofeo, cantidad Trofeos, registro (Activo/Inactivo)
    public boolean addUser(String user) throws IOException {
        if (users.search(user) != -1) {
            return false;
        }
        us.seek(us.length());
        us.writeUTF(user);
        us.writeInt(0);
        us.writeBoolean(true);
        return true;
    }

    public String playerInfo(String user) throws IOException {
        String textArea = " ";
        if (users.search(user) != -1) {
            return null;
        }

        us.seek(0);
        while (us.getFilePointer() < us.length()) {
            String username = us.readUTF();
            int puntosTrofeos = us.readInt();
            int cantTrofeos = us.readInt();
            boolean state = us.readBoolean();

            if (!username.equals(user) || !state) {
                continue;
            }

            textArea = "Usuario: " + user + "\n"
                    + "Puntaje Total de trofeos: " + puntosTrofeos + "\n"
                    + "Cantidad Total de trofeos: " + cantTrofeos + "\n"
                    + " * * *Detalle trofeos* * *" + "\n";

            psn.seek(0);
            while (psn.getFilePointer() < psn.length()) {
                String PSNuser = psn.readUTF();
                String trofeoTipo = psn.readUTF();
                String juegoTrofeo = psn.readUTF();
                String nameTrofeo = psn.readUTF();
                long fechaaa = psn.readLong();

                if (PSNuser.equals(user)) {
                    textArea += "Trofeo: " + trofeoTipo + " obtenido en:  " + juegoTrofeo + " Titulo: " + nameTrofeo + " Desbloqueado en:  "
                            + new Date(fechaaa);
                }
            }

        }
        return textArea;

    }

    public boolean addTrophieTo(String username, String trophyGame, String trophyName, Trophy type) throws IOException {
        if (users.search(username) != -1) {
            return false;
        }
        psn.seek(0);
        psn.writeUTF(username);
        psn.writeUTF(trophyGame);
        psn.writeUTF(trophyName);
        psn.writeUTF(type.name());

        Date fecha = new Date();
        psn.writeLong(fecha.getTime());
        System.out.println("fecha test: " + fecha.getTime());

        us.seek(0);
        long pos = 0;
        while (us.getFilePointer() < us.length()) {
            pos = us.getFilePointer();
            String userTemp = us.readUTF();

            if (userTemp.equals(username)) {

                System.out.println("Current troph pts & count");
                int puntosTrofeos = us.readInt();
                int cantTrofeos = us.readInt();
                boolean state = us.readBoolean();

                System.out.println(puntosTrofeos);
                System.out.println(cantTrofeos);
                System.out.println(state);
                if (!state) {
                    return false;
                }

                puntosTrofeos += type.points;
                cantTrofeos++;

                System.out.println("new");
                System.out.println(puntosTrofeos);
                System.out.println(cantTrofeos);

                us.seek(pos);

                us.writeUTF(userTemp);
                us.writeInt(puntosTrofeos);
                us.writeInt(cantTrofeos);
                us.writeBoolean(true);
                us = new RandomAccessFile("users", "rw");
                return true;
            }
        }
        return false;
    }

}
