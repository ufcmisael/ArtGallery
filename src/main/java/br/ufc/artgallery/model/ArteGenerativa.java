package br.ufc.artgallery.model;

public class ArteGenerativa extends Obra {
    private final String algoritmo;
    private final long seed;

    public ArteGenerativa(String titulo, String autor, String algoritmo, long seed) {
        super(titulo, autor);
        this.algoritmo = algoritmo;
        this.seed = seed;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public String exibirDetalhes() {
        return String.format(
                "===============================" +
                        "\nTítulo: " + this.getTitulo() +
                        "\nAutor: " + this.getAutor() +
                        "\nTipo: " + "Arte Generativa" +
                        "\nAlgoritmo: " + this.getAlgoritmo() +
                        "\nSeed: " + this.getSeed() +
                        "\n==============================="
        );
    }

    @Override
    public String toString() {
        return  exibirDetalhes();
    }
}
