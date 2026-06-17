package br.ufc.artgallery.service;

import br.ufc.artgallery.exception.ExposicaoJaCadastradaException;
import br.ufc.artgallery.exception.ExposicaoNaoEncontrada;
import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Avaliacao;
import br.ufc.artgallery.model.Exposicao;
import br.ufc.artgallery.model.Obra;
import br.ufc.artgallery.repository.IRepositorioExposicao;
import br.ufc.artgallery.repository.IRepositorioObra;

import java.util.Vector;
import java.util.stream.Collectors;

public class ArtGallery implements IArtGallery {
    private final IRepositorioObra repositorio;
    private final IRepositorioExposicao exposicoes;

    public ArtGallery(IRepositorioObra repositorio, IRepositorioExposicao exposicoes) {
        this.repositorio = repositorio;
        this.exposicoes = exposicoes;
    }

    @Override
    public void publicarObra(Obra obra) throws ObraJaCadastradaException {
//        try {
//             repositorio.cadastrar(obra);
//         } catch (Exception e) {
//             System.out.println("Erro: " + e.getMessage());
//         }
        repositorio.cadastrar(obra); // o erro sera tratado na UI
    }

    @Override
    public void removerObra(String titulo) throws ObraNaoEncontradaException {
        Obra obra = repositorio.buscar(titulo);
        if(obra == null){
           throw new ObraNaoEncontradaException("Obra ' "+ titulo + "' não encontrada.");
        }
        if(!obra.isAtiva()){
            throw new ObraNaoEncontradaException("A obra ' "+ titulo + "' já está desativada!");
        }
        repositorio.remover(titulo);
    }

    public void ativarObra(String titulo) throws ObraNaoEncontradaException {
        Obra obra = repositorio.buscar(titulo);
        if(obra == null){
            throw new ObraNaoEncontradaException("Obra ' "+ titulo + "' não encontrada.");
        }
        if(obra.isAtiva()){
            throw new ObraNaoEncontradaException("A obra ' "+ titulo + "' já está ativada!");
        }
        obra.setAtiva(true);
        repositorio.atualizar(obra);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException {
        Obra obra = repositorio.buscar(titulo);
        if(obra == null){
            throw new ObraNaoEncontradaException("Obra ' "+ titulo + "' não encontrada.");
        }
        if(!obra.isAtiva()){
            throw new ObraNaoEncontradaException("A obra ' "+ titulo + "' não está ativada!");
        }
        obra.adicionarAvaliacao(avaliacao);
        repositorio.atualizar(obra);
    }

    @Override
    public Vector<Obra> listarObras() {
//        Vector<Obra> obras = new Vector<>();
//        for (Obra o : repositorio.listar()) {
//            if(o.isAtiva()){
//               obras.add(o);
//            }
//        }
//        return obras;
        return repositorio.listar().stream()
                .filter(Obra::isAtiva)
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public Vector<Obra> buscarPorAutor(String autor) {
//        Vector<Obra> obras = new Vector<>();
//        for (Obra o : repositorio.listar()) {
//            if(o.getAutor().equalsIgnoreCase(autor)){
//               obras.add(o);
//            }
//        }
//        return obras;
        return repositorio.listar().stream()
                .filter(o -> o.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public Vector<Obra> topObras() {
//        vector<obra> obras = repositorio.listar();
//        collections.sort(obras);
//        return obras;

        return repositorio.listar().stream()
                .sorted()
                .collect(Collectors.toCollection(Vector::new));
    }

    public void criarExposicao(String nome) throws ExposicaoJaCadastradaException {
        Exposicao exposicao = new Exposicao(nome);
        exposicoes.cadastrar(exposicao);
    }

    public void publicarObraExposicao(String nomeExposicao, String titulo) throws ObraNaoEncontradaException, ExposicaoNaoEncontrada {
        Obra obra = repositorio.buscar(titulo);
       if (obra == null) {
           throw new ObraNaoEncontradaException("Obra ' "+ titulo + "' não encontrada.");
       }

       Exposicao e = exposicoes.buscar(nomeExposicao);
       if (e == null) {
           throw new ExposicaoNaoEncontrada("Exposição '" + nomeExposicao + "' não encontrada.");
       }

       exposicoes.publicarObra(e,obra);
    }

    public Vector<Exposicao> listarExposicoes() {
        return exposicoes.listar();
    }

    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) {

//        for (Exposicao e : exposicoes) {
//            if (e.getNome().equalsIgnoreCase(nomeExposicao)) {
//                return e.listarObras();
//            }
//        }
//        return new Vector<>();
        return exposicoes.listar().stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeExposicao))
                .findFirst()
                .map(Exposicao::listarObras)
                .orElse(new Vector<>());
    }
}
