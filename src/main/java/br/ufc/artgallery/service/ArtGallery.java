package br.ufc.artgallery.service;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Avaliacao;
import br.ufc.artgallery.model.Exposicao;
import br.ufc.artgallery.model.Obra;
import br.ufc.artgallery.repository.IRepositorioObra;

import java.util.Vector;
import java.util.stream.Collectors;

public class ArtGallery implements IArtGallery {
    private final IRepositorioObra repositorio;
    private final Vector<Exposicao> exposicoes;

    public ArtGallery(IRepositorioObra repositorio) {
        this.repositorio = repositorio;
        this.exposicoes = new Vector<>();
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
           throw new ObraNaoEncontradaException("Obra nao encontradaaaa");
        }
        if(!obra.isAtiva()){
            throw new ObraNaoEncontradaException("Obra nao esta ativaaaa");
        }
        repositorio.remover(titulo);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) throws ObraNaoEncontradaException {
        Obra obra = repositorio.buscar(titulo);
        if(obra == null){
            throw new ObraNaoEncontradaException("Obra nao encontradaaaa");
        }
        if(!obra.isAtiva()){
            throw new ObraNaoEncontradaException("Obra nao esta ativaaaa");
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

    public void criarExposicao(String nome) {
        Exposicao exposicao = new Exposicao(nome);
        exposicoes.add(exposicao);
    }

    public boolean publicarObraExposicao(String nomeExposicao, Obra obra) throws ObraNaoEncontradaException {
       if (obra == null) throw new ObraNaoEncontradaException("nao achamo");
        boolean sucesso = false;
        for (Exposicao e : exposicoes) {
            if (e.getNome().equalsIgnoreCase(nomeExposicao)) {
                e.adicionarObra(obra);
                sucesso = true;
            }
        }

        return sucesso;

//        Exposicao ex = exposicoes.stream()
//                .filter(e -> e.getNome().equalsIgnoreCase(nomeExposicao))
//                .findFirst()
//                .orElse(null)
//                .adicionarObra(obra);
//        if (ex != null) {
//            ex.adicionarObra(obra);
//            sucesso = true;
//        }
//        return sucesso;
    }

    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) {
//        for (Exposicao e : exposicoes) {
//            if (e.getNome().equalsIgnoreCase(nomeExposicao)) {
//                return e.listarObras();
//            }
//        }
//        return new Vector<>();
        return exposicoes.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeExposicao))
                .findFirst()
                .map(Exposicao::listarObras)
                .orElse(new Vector<>());
    }
}
