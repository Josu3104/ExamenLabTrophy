/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PsnTrophies;

/**
 *
 * @author Josue Gavidia
 */
public class Entry {

     String username;
     long pos;
     Entry next = null;

    public Entry(long posicion, String user) {
        pos = posicion;
        username = user;

    }

  
}
