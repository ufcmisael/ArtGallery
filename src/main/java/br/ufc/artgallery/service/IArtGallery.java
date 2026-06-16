package br.ufc.artgallery.service;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Avaliacao;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public interface IArtGallery {

    public void publicarObra(Obra obra) throws ObraJaCadastradaException;
    public void removerObra(String titulo) throws ObraNaoEncontradaException;
    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException;
    public Vector<Obra> listarObras();
    public Vector<Obra> buscarPorAutor(String autor);
    public Vector<Obra> topObras();
    public  Vector<Obra> obrasExpostas(String nomeExposicao);

}
