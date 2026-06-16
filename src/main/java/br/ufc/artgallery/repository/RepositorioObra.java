package br.ufc.artgallery.repository;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.exception.ObraNaoEncontradaException;
import br.ufc.artgallery.model.Obra;

import java.util.Vector;

public class RepositorioObra implements IRepositorioObra {
    private final Vector<Obra> obras;

    public RepositorioObra() {
        this.obras = new Vector<>();
    }

    @Override
    public void cadastrar(Obra obra) throws ObraJaCadastradaException {
        if (obras.contains(obra)) {
            throw new ObraJaCadastradaException("Obra já cadastrada: " + obra.getTitulo());
        }
        obras.add(obra);
//        boolean match = obras.stream() .anyMatch(o -> o.equals(obra));
//        if (match){
//            throw new ObraJaCadastradaException("Esta Obra já está cadastrada: " + obra);
//        }
    }

    @Override
    public Obra buscar(String titulo) {
//        for (Obra o : obras) {
//            if (o.getTitulo().equalsIgnoreCase(titulo)) {
//                return o;
//            }
//        }
//        return null;
        return obras.stream()
                .filter(o -> o.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void atualizar(Obra obra) throws ObraNaoEncontradaException {
        int index = obras.indexOf(obra);
        if (index != -1) {
            obras.set(index, obra);
        }else {
            throw new ObraNaoEncontradaException("Não foi encontrada obra correspondente: " + obra);
        }
    }

    @Override
    public void remover(String titulo) {
//        for (Obra o : obras) {
//            if (o.getTitulo().equalsIgnoreCase(titulo)) {
//                o.setAtiva(false);
//            }
//        }
        obras.stream()
                .filter(o -> o.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .ifPresent(o -> o.setAtiva(false));
    }

    @Override
    public Vector<Obra> listar() {
        return obras;
    }
}
