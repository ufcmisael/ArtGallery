package br.ufc.artgallery.model;

import br.ufc.artgallery.exception.NotaInvalidaException;

public class Avaliacao {
    private String usuario;

    public Avaliacao(String usuario, int nota, String comentario) throws NotaInvalidaException {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota inválida: " + nota + "deveria estar entre 0 e 10.");
        }
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) throws NotaInvalidaException {
        if (nota >= 0 && nota <= 10) {
            this.nota = nota;
        }
        else {
            throw new NotaInvalidaException("Nota inválida");
        }
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    private int nota;
    private String comentario;
}
