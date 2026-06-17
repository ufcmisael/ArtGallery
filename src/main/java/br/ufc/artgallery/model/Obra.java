package br.ufc.artgallery.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

public abstract class Obra implements Comparable<Obra>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String titulo;
    private final String autor;
    private boolean ativa;
    private final Vector<Avaliacao> avaliacoes;

    public Obra(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.ativa = true;
        this.avaliacoes = new Vector<>();
    }

    public Vector<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao){
        if(avaliacao != null) {
            this.avaliacoes.add(avaliacao);
        }
    }

    public double mediaAvaliacoes(){
        double media = 0;

        if(avaliacoes.isEmpty()){ return media; }

        for(Avaliacao avaliacao : avaliacoes){
            media += avaliacao.getNota();
        }
        media = media/avaliacoes.size();
        return media;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Obra obra = (Obra) o;
        boolean t = (this.titulo != null && this.titulo.equalsIgnoreCase(obra.titulo));
        boolean a = (this.autor != null && this.autor.equalsIgnoreCase(obra.autor));

        return t && a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, autor);
    }

    @Override
    public int compareTo(@NotNull Obra o) {
        return Double.compare(o.mediaAvaliacoes(), this.mediaAvaliacoes());
    }

    public abstract String exibirDetalhes();
}
