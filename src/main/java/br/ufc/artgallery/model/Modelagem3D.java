package br.ufc.artgallery.model;

public class Modelagem3D extends Obra {
    private int numeroPoligonos;
    private String engine;

    public int getNumeroPoligonos() {
        return numeroPoligonos;
    }

    public String getEngine() {
        return engine;
    }

    public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine) {
        super(titulo, autor);
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }

    @Override
    public String exibirDetalhes() {
        return String.format(
                "===============================" +
                        "\nTítulo: " + this.getTitulo() +
                        "\nAutor: " + this.getAutor() +
                        "\nTipo: " + "Modelagem 3D" +
                        "\nPolígonos: " + this.getNumeroPoligonos() +
                        "\nEngine: " + this.getEngine() +
                        "\n==============================="
        );
    }

    @Override
    public String toString() {
        return  exibirDetalhes();
    }
}
