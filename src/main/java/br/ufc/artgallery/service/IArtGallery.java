package br.ufc.artgallery.service;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Avaliacao;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public interface IArtGallery {

    void publicarObra(Obra obra) throws ObraJaCadastradaException;
    void removerObra(String titulo) throws ObraNaoEncontradaException;
    void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException;
    Vector<Obra> listarObras();
    Vector<Obra> buscarPorAutor(String autor);
    Vector<Obra> topObras();
    Vector<Obra> obrasExpostas(String nomeExposicao);

}
