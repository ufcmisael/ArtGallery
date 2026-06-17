package br.ufc.artgallery.repository;

import br.ufc.artgallery.exception.ExposicaoJaCadastradaException;
import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.model.Exposicao;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public interface IRepositorioExposicao {
    void cadastrar(Exposicao exposicao) throws ExposicaoJaCadastradaException;
    Exposicao buscar(String titulo);
//    void atualizar(Exposicao exposicao);
//    void remover(String titulo);
    void publicarObra(Exposicao exposicao, Obra obra);
    Vector<Exposicao> listar();
}
