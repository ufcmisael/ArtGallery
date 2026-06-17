package br.ufc.artgallery.DAO;

import br.ufc.artgallery.exception.ExposicaoJaCadastradaException;
import br.ufc.artgallery.model.Exposicao;
import br.ufc.artgallery.model.Obra;
import br.ufc.artgallery.repository.IRepositorioExposicao;

import java.io.*;
import java.util.Vector;

/**
 * Implementação de IRepositorioExposicao com persistência via serialização Java.
 *
 * Funciona exatamente igual ao RepositorioObraSerializado:
 * - Serializa o Vector<Exposicao> inteiro num arquivo .dat separado
 * - Cada Exposicao contém internamente um Vector<Obra> — tudo é serializado junto
 * - As obras dentro das exposições são as mesmas referências do repositório de obras?
 *   NÃO — na serialização cada objeto é copiado. Por isso ao carregar, as obras
 *   dentro das exposições são objetos separados dos do repositório de obras.
 *   Isso é aceitável para esse trabalho.
 */
public class RepositorioExposicaoSerialize implements IRepositorioExposicao {

    private static final String ARQUIVO = "data/exposicoes.dat";
    private Vector<Exposicao> exposicoes;

    public RepositorioExposicaoSerialize() {
        this.exposicoes = carregar();
//        remover2("j");
    }

    @Override
    public void cadastrar(Exposicao exposicao) throws ExposicaoJaCadastradaException {
        if (exposicoes.contains(exposicao)) {
            throw new ExposicaoJaCadastradaException("Exposicao '" + exposicao.getNome() + "' já está cadastrada.");
        }
       exposicoes.add(exposicao);
       salvar();
    }

    @Override
    public Exposicao buscar(String nome) {
        return exposicoes.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void publicarObra(Exposicao exposicao, Obra obra) {
        exposicao.adicionarObra(obra);
        salvar();
    }

    public void remover2(String nome) {
        exposicoes.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .ifPresent(exposicoes::remove);
    }

    @Override
    public Vector<Exposicao> listar() {
        return exposicoes;
    }

    private void salvar() {
        try (ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(ARQUIVO))) {
            out.writeObject(exposicoes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar exposições: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Vector<Exposicao> carregar() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            return new Vector<>();
        }
        try (ObjectInputStream in = new ObjectInputStream( new FileInputStream(ARQUIVO))) {
            return (Vector<Exposicao>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar exposições: " + e.getMessage());
            return new Vector<>();
        }
    }
}