package br.ufc.artgallery.model;

public class PinturaDigital extends Obra {
    private final String resolucao;
    private final String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado) {
        super(titulo, autor);
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    public String getResolucao() {
        return resolucao;
    }

    public String getSoftwareUtilizado() {
        return softwareUtilizado;
    }

    @Override
    public String exibirDetalhes() {
        return String.format(
                "===============================" +
                "\nTítulo: " + this.getTitulo() +
                "\nAutor: " + this.getAutor() +
                "\nTipo: " + "Pintura Digital" +
                "\nResolução: " + this.getResolucao() +
                "\nSoftware: " + this.getSoftwareUtilizado() +
                "\n==============================="
        );
    }

    @Override
    public String toString() {
        return  exibirDetalhes();
    }
}
