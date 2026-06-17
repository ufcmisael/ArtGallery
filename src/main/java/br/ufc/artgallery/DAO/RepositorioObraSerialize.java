package br.ufc.artgallery.DAO;

import br.ufc.artgallery.exception.ObraJaCadastradaException;
import br.ufc.artgallery.model.Obra;
import br.ufc.artgallery.repository.IRepositorioObra;

import java.io.*;
import java.util.Vector;

public class RepositorioObraSerialize implements IRepositorioObra {

    private static final String ARQUIVO = "data/obras.dat";
    private Vector<Obra> obras;

    public RepositorioObraSerialize() {
        this.obras = carregar();
//        remover2("sonatine","asdfsad");
    }

    @Override
    public void cadastrar(Obra obra) throws ObraJaCadastradaException {
        if (obras.contains(obra)) {
            throw new ObraJaCadastradaException("A obra '" + obra.getTitulo() + "' já está cadastrada!");
        }
        obras.add(obra);
        salvar(); // persiste imediatamente após cada alteração
    }

    @Override
    public Obra buscar(String titulo) {
        return obras.stream()
                .filter(o -> o.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void atualizar(Obra obra) {
        int index = obras.indexOf(obra);
        if (index != -1) {
            obras.set(index, obra);
            salvar();
        }
    }

    @Override
    public void remover(String titulo) {
        obras.stream()
                .filter(o -> o.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .ifPresent(o -> o.setAtiva(false));
        salvar();
    }

    // criei maneualmente pra remover de fato umas obras teste do banco
    public void remover2(String titulo, String autor) {
        obras.stream()
                .filter(o -> o.getTitulo().equalsIgnoreCase(titulo) && o.getAutor().equalsIgnoreCase(autor))
                .findFirst()
                .ifPresent(obras::remove);
        salvar();
    }

    @Override
    public Vector<Obra> listar() {
        return obras;
    }

    private void salvar() {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO))) {
            out.writeObject(obras);
        } catch (IOException e) {
            System.err.println("Erro ao salvar obras: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Vector<Obra> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new Vector<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(ARQUIVO))) {
            return (Vector<Obra>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar obras: " + e.getMessage());
            return new Vector<>();
        }
    }
}