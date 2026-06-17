package br.ufc.artgallery.model;

import br.ufc.artgallery.exception.NotaInvalidaException;

import java.io.Serial;
import java.io.Serializable;

public class Avaliacao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String usuario;
    private int nota;
    private String comentario;

    public Avaliacao(String usuario, int nota, String comentario) throws NotaInvalidaException {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException("Nota inválida: " + nota + ". O valor deveria estar entre 0 e 10.");
        }
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

}
