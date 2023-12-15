/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PsnTrophies;

/**
 *
 * @author Josue Gavidia
 */
public class HashTable {

    Entry inicio = null;
    int size = 0;

    public void add(String username, long pos) {

        if (search(username) != -1) {
            //Agrega el primero cuando la lista esta vacia
            if (inicio == null) {
                inicio = new Entry(pos, username);;
                size++;
            }
            //Agrega al final (?)
            Entry temp = inicio;
            while (temp.next != null) {
                temp = temp.next;

            }
            temp = new Entry(pos, username);
            size++;
        }
    }

    public long search(String username) {
        Entry temp = inicio;
        long pos = 0;
        while (temp != null) {
            if (temp.username.equals(username)) {
                return pos;
            }
            pos++;
            temp = temp.next;
        }
        return -1;
    }

    public void remove(String username) {
        if (inicio.username.equals(username)) {
            inicio = inicio.next;
            size--;
        }
        
        Entry og = inicio;
        Entry neo = inicio.next;
        
        while(neo!=null){
            if(neo.username.equals(username)){
                og.next = neo.next;
                size--;
            }
            og = neo;
            neo = neo.next;
        }

    }

}
