package br.ufc.artgallery.repository;

import br.ufc.artgallery.exception.ExposicaoJaCadastradaException;
import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.model.Exposicao;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public class RepositorioExposicao  implements IRepositorioExposicao{
    private final Vector<Exposicao> exposicoes;

    public RepositorioExposicao() {
        this.exposicoes = new Vector<>();
    }

    @Override
    public void cadastrar(Exposicao exposicao) throws ExposicaoJaCadastradaException {
        if (exposicoes.contains(exposicao)) {
            throw new ExposicaoJaCadastradaException("Exposicao '" + exposicao.getNome() + "' já está cadastrada.");
        }
        exposicoes.add(exposicao);
    }

    @Override
    public Vector<Exposicao> listar() {
        return exposicoes;
    }

    @Override
    public Exposicao buscar(String nome) {
        return exposicoes.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
//        for (Exposicao e : exposicoes) {
//            if (e.getNome().equalsIgnoreCase(nome)) {
//                return e;
//            }
//        }
//        return null;
    }

    @Override
    public void publicarObra(Exposicao exposicao, Obra obra) {
        exposicao.adicionarObra(obra);
    }

    // no pdf nao vi nada falando sobre nao poder mostrar pinturas desativadas em exposicao
    // na duvida nao fazer
//    @Override
//    public void atualizar(Exposicao exposicao) {
//    }

//    @Override
//    public void remover(String titulo) {
//    }
}
