package br.ufc.artgallery.model;

import java.util.Vector;

public class Exposicao {
    private final String nome;
    private final Vector<Obra> obras;

    public Exposicao(String nome) {
        this.nome = nome;
        this.obras = new Vector<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarObra(Obra obra) {
        this.obras.add(obra);
    }

    public Vector<Obra> listarObras() {
        return this.obras;
    }
}
