package br.ufc.artgallery.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public class Exposicao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exposicao exposicao = (Exposicao) o;
        return this.getNome() != null && this.getNome().equalsIgnoreCase(exposicao.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNome());
    }

    public Vector<Obra> listarObras() {
        return this.obras;
    }
}
