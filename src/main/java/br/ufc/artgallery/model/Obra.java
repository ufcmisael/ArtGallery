package br.ufc.artgallery.model;

import java.util.Vector;

public abstract class Obra {
    private String titulo;
    private String autor;
    private boolean ativa;
    private Vector<Avaliacao> avaliacoes;

    public Obra(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.ativa = true;
        this.avaliacoes = new Vector<>();
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
        try {
            this.avaliacoes.add(avaliacao);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public double mediaAvaliacoes(){
        double media = 0;
        for(Avaliacao avaliacao : avaliacoes){
            media += avaliacao.getNota();
        }
        media = media/avaliacoes.size();
        return media;
    }

    public abstract String exibirDetalhes();

}
