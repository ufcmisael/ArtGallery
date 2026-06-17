package br.ufc.artgallery.model;

import br.ufc.artgallery.exception.ResolucaoInvalidaException;

public class PinturaDigital extends Obra {
    private final String resolucao;
    private final String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado) throws ResolucaoInvalidaException {
        super(titulo, autor);

        String regex = "(?i)^(144p|240p|360p|480p|sd|720p|hd|1080p|full\\s*hd|fhd|1440p|quad\\s*hd|qhd|2k|2160p|ultra\\s*hd|uhd|4k|4320p|8k)$";
        if (!resolucao.trim().matches(regex)) {
            throw new ResolucaoInvalidaException("Resolução inválida: " + resolucao + ". Use formatos oficiais como 1080p, 4K, FHD ou Full HD.");
        }
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
//                "===============================" +
                "\nTítulo: " + this.getTitulo() +
                "\nAutor: " + this.getAutor() +
                "\nTipo: " + "Pintura Digital" +
                "\nResolução: " + this.getResolucao() +
                "\nSoftware: " + this.getSoftwareUtilizado()
//                        + "\n==============================="
        );
    }

    @Override
    public String toString() {
        return  exibirDetalhes();
    }
}
