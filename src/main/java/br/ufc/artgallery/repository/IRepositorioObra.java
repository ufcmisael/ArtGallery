package br.ufc.artgallery.repository;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public interface IRepositorioObra {
    public void cadastrar(Obra obra) throws ObraJaCadastradaException;
    public Obra buscar(String titulo);
    public void atualizar(Obra obra) throws ObraNaoEncontradaException;
    public void remover(String titulo);
    public Vector<Obra> listar();
}
