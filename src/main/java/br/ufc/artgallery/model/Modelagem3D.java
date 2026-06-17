package br.ufc.artgallery.model;

import br.ufc.artgallery.exception.PoligonoInvalidoException;

public class Modelagem3D extends Obra {
    private final int numeroPoligonos;
    private final String engine;

    public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine) throws PoligonoInvalidoException {

        super(titulo, autor);
        if (numeroPoligonos < 0) {
            throw new PoligonoInvalidoException("A quantidade de polígonos tem que ser um valor inteiro positivo");
        }
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }

    public int getNumeroPoligonos() {
        return numeroPoligonos;
    }

    public String getEngine() { return engine; }

    @Override
    public String exibirDetalhes() {
        return String.format(
//                "===============================" +
                        "\nTítulo: " + this.getTitulo() +
                        "\nAutor: " + this.getAutor() +
                        "\nTipo: " + "Modelagem 3D" +
                        "\nPolígonos: " + this.getNumeroPoligonos() +
                        "\nEngine: " + this.getEngine() // +
//                        "\n==============================="
        );
    }

    @Override
    public String toString() {
        return  exibirDetalhes();
    }
}
