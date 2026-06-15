package br.ufc.artgallery.model;

import java.util.Vector;

public class Exposicao {
    private String nome;
    private Vector<Obra> obras;

    public void adicionar(Obra obra) {
        this.obras.add(obra);
    }

    public Vector<Obra> listarObras() {
        return this.obras;
    }
}
