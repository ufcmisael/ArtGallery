package br.ufc.artgallery.ui;

import br.ufc.artgallery.DAO.RepositorioExposicaoSerialize;
import br.ufc.artgallery.DAO.RepositorioObraSerialize;
import br.ufc.artgallery.exception.*;
import br.ufc.artgallery.model.*;
import br.ufc.artgallery.repository.RepositorioExposicao;
import br.ufc.artgallery.repository.RepositorioObra;
import br.ufc.artgallery.service.ArtGallery;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class JanelaPrincipal extends JFrame {

    private final ArtGallery artGallery;
//    private JTextArea areaResultado; // adicionando imagens na area de resulado
    private JTextPane areaResultado;

    private enum TipoBanco { MEMORIA, ARQUIVO, SQLITE }

    private final String okBrito = "src/main/resources/okBrito.jpg";
    private final String erroBrito = "src/main/resources/erroBrito.jpg";
    private final String hmBrito = "src/main/resources/hmBrito.jpg";
    private final String icon = "src/main/resources/icon.png";
    private final String kitano = "src/main/resources/kitano.jpg";

    public JanelaPrincipal() {
//        artGallery = new ArtGallery(new RepositorioObra(), new RepositorioExposicao());

        TipoBanco tipoBanco = TipoBanco.ARQUIVO;
        artGallery = switch (tipoBanco) {
            case ARQUIVO -> new ArtGallery( new RepositorioObraSerialize(), new RepositorioExposicaoSerialize() );
//            case SQLITE -> {
//                RepositorioObraDAO repObra = new RepositorioObraDAO();
//                yield new ArtGallery(repObra, new RepositorioExposicaoDAO(repObra));
//            }
            case MEMORIA -> new ArtGallery(new RepositorioObra(), new RepositorioExposicao());
            case SQLITE -> null;
        };

        configurarJanela();
        configurarMenu();
        configurarAreaResultado();
    }

    // Configuracao geral
    private void configurarJanela() {
        setTitle("ArtGallery — Galeria de Arte Digital");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        try {
            ImageIcon icone = new ImageIcon(icon);
            setIconImage(icone.getImage());
        } catch (Exception e) {
            System.out.println("Não foi possível encontrar o ícone da janela.");
        }
    }

    private void configurarAreaResultado() {
//        areaResultado = new JTextArea();
        areaResultado = new JTextPane();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultado.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(scroll, BorderLayout.CENTER);

        JLabel rodape = new JLabel("  UFC — Técnicas de Programação I  |  ArtGallery");
        rodape.setFont(new Font("SansSerif", Font.ITALIC, 11));
        rodape.setForeground(new Color(22,33,44));
        add(rodape, BorderLayout.SOUTH);
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(menuObras());
        menuBar.add(menuAvaliacoes());
        menuBar.add(menuConsultas());
        menuBar.add(menuExposicoes());
        menuBar.add(menuKitano());

        setJMenuBar(menuBar);
    }

    // Menu Kitano
    private JMenu menuKitano() {
        JMenu menu = new JMenu("Takeshi");

        JMenuItem kitano = new JMenuItem("Kitano");
        kitano.addActionListener(e -> mostrarKitano() );
        menu.add(kitano);

        return menu;
    }

    private void mostrarKitano() {
        exibirComImagem("tenha um bom dia!", kitano);
    }

    // Menu Obras
    private JMenu menuObras() {
        JMenu menu = new JMenu("Obras");

        JMenuItem cadastrarPintura = new JMenuItem("Cadastrar Pintura Digital");
        JMenuItem cadastrarModelagem = new JMenuItem("Cadastrar Modelagem 3D");
        JMenuItem cadastrarGenerativa = new JMenuItem("Cadastrar Arte Generativa");
        JMenuItem remover = new JMenuItem("Remover Obra");
        JMenuItem ativar = new JMenuItem("Ativar Obra");

        cadastrarPintura.addActionListener(e -> cadastrarPinturaDigital());
        cadastrarModelagem.addActionListener(e -> cadastrarModelagem3D());
        cadastrarGenerativa.addActionListener(e -> cadastrarArteGenerativa());
        remover.addActionListener(e -> removerObra());
        ativar.addActionListener(e -> ativarObra());

        menu.add(cadastrarPintura);
        menu.add(cadastrarModelagem);
        menu.add(cadastrarGenerativa);
        menu.addSeparator();
        menu.add(remover);
        menu.add(ativar);

        return menu;
    }

    // Menu Avaliações
    private JMenu menuAvaliacoes() {
        JMenu menu = new JMenu("Avaliações");

        JMenuItem avaliar = new JMenuItem("Avaliar Obra");
        JMenuItem listar = new JMenuItem("Listar Avaliações duma Obra");
        JMenuItem listarTodos = new JMenuItem("Listar Todas Avaliações");

        avaliar.addActionListener(e -> avaliarObra());
        listarTodos.addActionListener(e -> listarTodasAvaliacoes());
        listar.addActionListener(e -> listarAvaliacoes());

        menu.add(avaliar);
        menu.add(listar);
        menu.add(listarTodos);

        return menu;
    }

    // Menu Consultas
    private JMenu menuConsultas() {
        JMenu menu = new JMenu("Consultas");

        JMenuItem listar = new JMenuItem("Listar Obras Ativas");
        JMenuItem porAutor = new JMenuItem("Listar Obras por Autor");
        JMenuItem top = new JMenuItem("Listar o Ranking das Obras");

        listar.addActionListener(e -> listarObras());
        porAutor.addActionListener(e -> buscarPorAutor());
        top.addActionListener(e -> topObras());

        menu.add(listar);
        menu.add(porAutor);
        menu.add(top);

        return menu;
    }

    // Menu Exposicoes
    private JMenu menuExposicoes() {
        JMenu menu = new JMenu("Exposições");

        JMenuItem criar = new JMenuItem("Criar Exposição");
        JMenuItem adicionarObra = new JMenuItem("Adicionar Obra à Exposição");
        JMenuItem listar= new JMenuItem("Listar Obras de uma Exposição");
        JMenuItem listarE = new JMenuItem("Listar Exposições disponíveis");

        criar.addActionListener(e -> criarExposicao());
        adicionarObra.addActionListener(e -> adicionarObraExposicao());
        listar.addActionListener(e -> listarObrasExposicao());
        listarE.addActionListener(e -> listarExposicoes());

        menu.add(criar);
        menu.add(adicionarObra);
        menu.add(listar);
        menu.add(listarE);

        return menu;
    }

    // Cadastro
    private void cadastrarPinturaDigital() {
        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();
        JTextField resolucao = new JTextField();
        JTextField software = new JTextField();

        Object[] campos = {
                "Título:",titulo,
                "Autor:", autor,
                "Resolução:", resolucao,
                "Software:", software
        };

        int ok = JOptionPane.showConfirmDialog(this, campos, "Cadastrar Pintura Digital", JOptionPane.OK_CANCEL_OPTION);

        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Obra obra = new PinturaDigital( titulo.getText(), autor.getText(), resolucao.getText(), software.getText() );
            artGallery.publicarObra(obra);
            exibirComImagem("Pintura Digital cadastrada:\n" + obra.exibirDetalhes(), okBrito);
        } catch (ResolucaoInvalidaException | ObraJaCadastradaException ex) {
            erro(ex.getMessage());
        }
    }

    private void cadastrarModelagem3D() {
        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();
        JTextField poligonos = new JTextField();
        JTextField engine = new JTextField();

        Object[] campos = {
                "Título:", titulo,
                "Autor:", autor,
                "Polígonos:", poligonos,
                "Engine:", engine
        };

        int ok = JOptionPane.showConfirmDialog(this, campos, "Cadastrar Modelagem 3D", JOptionPane.OK_CANCEL_OPTION);

        if (ok != JOptionPane.OK_OPTION) return;

        try {
            int numPoligonos = Integer.parseInt(poligonos.getText());
            Obra obra = new Modelagem3D( titulo.getText(), autor.getText(), numPoligonos, engine.getText() );
            artGallery.publicarObra(obra);
            exibirComImagem("Modelagem 3D cadastrada:\n" + obra.exibirDetalhes(),okBrito);
        } catch (NumberFormatException ex) {
            erro("Número de polígonos inválido. Digite um valor inteiro positivo.");
        } catch (PoligonoInvalidoException | ObraJaCadastradaException ex) {
            erro(ex.getMessage());
        }
    }

    private void cadastrarArteGenerativa() {
        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();
        JTextField algoritmo = new JTextField();
        JTextField seed = new JTextField();

        Object[] campos = {
                "Título:", titulo,
                "Autor:", autor,
                "Algoritmo:", algoritmo,
                "Seed:", seed
        };

        int ok = JOptionPane.showConfirmDialog(this, campos, "Cadastrar Arte Generativa", JOptionPane.OK_CANCEL_OPTION);

        if (ok != JOptionPane.OK_OPTION) return;

        try {
            long seedVal = Long.parseLong(seed.getText());
            Obra obra = new ArteGenerativa( titulo.getText(), autor.getText(), algoritmo.getText(), seedVal );
            artGallery.publicarObra(obra);
            exibirComImagem("Arte Generativa cadastrada:\n" + obra.exibirDetalhes(),okBrito);
        } catch (NumberFormatException ex) {
            erro("Número de seed inválido. Digite um valor positivo.");
        } catch (ObraJaCadastradaException ex) {
            erro(ex.getMessage());
        }
    }

    private void removerObra() {
        String titulo = JOptionPane.showInputDialog(this, "Título da obra que deseja remover:");
        if (titulo == null || titulo.isBlank()) return;

        try {
            artGallery.removerObra(titulo);
            exibirComImagem("Obra \"" + titulo + "\" removida com sucesso.",hmBrito);
        } catch (ObraNaoEncontradaException ex) {
            erro(ex.getMessage());
        }
    }
    private void ativarObra(){
        String titulo = JOptionPane.showInputDialog(this, "Título da obra que deseja ativar:");
        if (titulo == null || titulo.isBlank()) return;

        try {
            artGallery.ativarObra(titulo);
            exibirComImagem("Obra \"" + titulo + "\" ativada com sucesso.",okBrito);
        } catch (ObraNaoEncontradaException ex) {
            erro(ex.getMessage());
        }
    }

    // Avaliacao
    private void avaliarObra() {
        JTextField titulo = new JTextField();
        JTextField usuario = new JTextField();
        JTextField nota = new JTextField();
        JTextField comentario = new JTextField();

        Object[] campos = {
                "Título da obra:", titulo,
                "Usuário:", usuario,
                "Nota (0-10):",nota,
                "Comentário:", comentario
        };

        int ok = JOptionPane.showConfirmDialog(this, campos, "Avaliar Obra", JOptionPane.OK_CANCEL_OPTION);

        if (ok != JOptionPane.OK_OPTION) return;

        try {
            int notaVal = Integer.parseInt(nota.getText());
            Avaliacao avaliacao = new Avaliacao(usuario.getText(), notaVal, comentario.getText());
            artGallery.avaliarObra(titulo.getText(), avaliacao);
            exibirComImagem("Avaliação registrada com sucesso para \"" + titulo.getText() + "\".",okBrito);
        } catch (NumberFormatException ex) {
            erro("Número de nota inválido. Digite um valor de 0 a 10.");
        } catch (ObraNaoEncontradaException | NotaInvalidaException ex) {
            erro(ex.getMessage());
        }
    }

    // Consultas
    private void listarObras() {
        Vector<Obra> obras = artGallery.listarObras();
        if (obras.isEmpty()) {
            exibirComImagem("Nenhuma obra ativa cadastrada.",erroBrito);
            return;
        }
        StringBuilder sb = new StringBuilder("Obras ativas:\n\n");
        sb.append("—".repeat(80)).append("\n");
        for (Obra o : obras) {
            sb.append(o.exibirDetalhes()).append("\n");
            sb.append("Média de avaliações: ").append(String.format("%.1f", o.mediaAvaliacoes())).append("\n\n");
            sb.append("—".repeat(80)).append("\n");
        }
        exibir(sb.toString());
//        exibirComImagem(sb.toString(), okBrito);
    }

    private void listarAvaliacoes() {
        String titulo = JOptionPane.showInputDialog(this, "Título da obra:");
        if (titulo == null || titulo.isBlank()) return;

        Vector<Avaliacao> avaliacoes = artGallery.listarAvaliacoes(titulo);
        if (avaliacoes.isEmpty()) {
           exibirComImagem("Nenhuma avaliação encontrada para o obra: " + titulo,erroBrito);
           return;
        }

        StringBuilder sb = new StringBuilder("Avaliações de \"" + titulo + "\":\n\n");
        sb.append("—".repeat(80)).append("\n");
        for (Avaliacao a : avaliacoes) {
            sb.append("Usuário: ").append(a.getUsuario());
                    sb.append(" | Nota: ").append(a.getNota());
                    sb.append(" | Comentário: ").append(a.getComentario()).append("\n");
            sb.append("—".repeat(80)).append("\n");
        }
        exibir(sb.toString());
    }

    private void listarTodasAvaliacoes() {
        Vector<Avaliacao> avaliacoes = artGallery.listarAvaliacoes();
        if (avaliacoes.isEmpty()) {
            exibirComImagem("Nenhuma avaliação cadastrada.",erroBrito);
            return;
        }

        Vector<Obra> obras = artGallery.listarObras();
        if (obras.isEmpty()) {
            exibirComImagem("Nenhuma avaliação cadastrada.",erroBrito);
            return;
        }


        StringBuilder sb = new StringBuilder("Avaliações:\n\n");
        sb.append("—".repeat(80)).append("\n");
        for (Obra o : obras) {
            avaliacoes = o.getAvaliacoes();

            for (Avaliacao a : avaliacoes) {
                sb.append("Usuário: ").append(a.getUsuario());
                sb.append(" | Obra: ").append(o.getTitulo());
                sb.append(" | Nota: ").append(a.getNota()).append("\n");
                sb.append("Comentário: ").append(a.getComentario()).append("\n");
                sb.append("—".repeat(80)).append("\n");
            }

        }
            exibir(sb.toString());
    }


    private void buscarPorAutor() {
        String autor = JOptionPane.showInputDialog(this, "Nome do autor:");
        if (autor == null || autor.isBlank()) return;

        Vector<Obra> obras = artGallery.buscarPorAutor(autor);
        if (obras.isEmpty()) {
            exibirComImagem("Nenhuma obra encontrada para o autor: " + autor,erroBrito);
            return;
        }
        StringBuilder sb = new StringBuilder("Obras de " + autor + ":\n\n");
//        sb.append("=".repeat(40)).append("\n");
        sb.append("—".repeat(80)).append("\n");
        for (Obra o : obras) {
            sb.append(o.exibirDetalhes()).append("\n\n");
            sb.append("—".repeat(80)).append("\n");
//            sb.append("=".repeat(40)).append("\n");
        }
        exibir(sb.toString());
//        exibirComImagem(sb.toString(),okBrito);
    }

    private void topObras() {
        Vector<Obra> obras = artGallery.topObras();
        if (obras.isEmpty()) {
            exibirComImagem("Nenhuma obra cadastrada.",erroBrito);
            return;
        }
        StringBuilder sb = new StringBuilder("Top Obras por avaliação:\n\n");
        int pos = 1;
        for (Obra o : obras) {
            sb.append(pos++).append("º — ").append(o.getTitulo())
                    .append(" (").append(o.getAutor()).append(")")
                    .append(" | Média: ").append(String.format("%.2f", o.mediaAvaliacoes()))
                    .append("\n");
        }
        exibir(sb.toString());
//        exibirComImagem(sb.toString(),okBrito);
    }

    private void listarExposicoes() {
        Vector<Exposicao> exposicoes = artGallery.listarExposicoes();
        if (exposicoes.isEmpty()) {
            exibirComImagem("Nenhuma exposição cadastrada.",erroBrito);
            return;
        }
        StringBuilder sb = new StringBuilder("Exposições disponíveis:\n\n");
//        sb.append("=".repeat(40)).append("\n");
        for (Exposicao e : exposicoes) {
            sb.append("— ").append(e.getNome()).append("\n");
//            sb.append("=".repeat(40)).append("\n");
        }
        exibir(sb.toString());
//        exibirComImagem(sb.toString(), okBrito);
    }

    // Exposicoes
    private void criarExposicao() {
        String nome = JOptionPane.showInputDialog(this, "Nome da exposição:");
        if (nome == null || nome.isBlank()) return;

        try {
            artGallery.criarExposicao(nome);
            exibir("Exposição \"" + nome + "\" criada com sucesso.");
        } catch (ExposicaoJaCadastradaException ex) {
             erro(ex.getMessage());
        }
    }

    private void adicionarObraExposicao() {
        JTextField exposicao = new JTextField();
        JTextField titulo    = new JTextField();

        Object[] campos = {
                "Nome da exposição:", exposicao,
                "Título da obra:", titulo
        };

        int ok = JOptionPane.showConfirmDialog(this, campos, "Adicionar Obra à Exposição", JOptionPane.OK_CANCEL_OPTION);

        if (ok != JOptionPane.OK_OPTION) return;

        try {
            artGallery.publicarObraExposicao(exposicao.getText(), titulo.getText());
            exibirComImagem("Obra \"" + titulo.getText() + "\" adicionada à exposição \"" + exposicao.getText() + "\".",okBrito);
        } catch (ExposicaoNaoEncontrada | ObraNaoEncontradaException ex) {
            erro(ex.getMessage());
        }
    }

    private void listarObrasExposicao() {
        String nome = JOptionPane.showInputDialog(this, "Nome da exposição:");
        if (nome == null || nome.isBlank()) return;

        Vector<Obra> obras = artGallery.obrasExpostas(nome);
        if (obras.isEmpty()) {
            exibirComImagem("Nenhuma obra encontrada para a exposição : " + nome,erroBrito);
            return;
        }
        StringBuilder sb = new StringBuilder("Obras da exposição \"" + nome + "\":\n\n");
        sb.append("—".repeat(80)).append("\n");
        for (Obra o : obras) {
            sb.append(o.exibirDetalhes()).append("\n\n");
            sb.append("—".repeat(80)).append("\n");
        }
        exibir(sb.toString());
//        exibirComImagem(sb.toString(),okBrito);
    }

    // Exibicao
    private void exibir(String texto) {
        areaResultado.setText(texto + "\n");
    }

    private void exibirComImagem(String texto, String caminhoImagem) {
        areaResultado.setText(texto + "\n\n"); // Adiciona o texto e pula linha

        // Coloca o cursor no final do texto para inserir a imagem
        areaResultado.setCaretPosition(areaResultado.getDocument().getLength());
//        String caminhoImagem = "src/main/resources/okBrito.jpg";

        try {
            ImageIcon iconeOriginal = new ImageIcon(caminhoImagem);

            // Redimensiona a imagem para nao estourar a tela
            Image img = iconeOriginal.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon iconeRedimensionado = new ImageIcon(img);

            // Insere a imagem no painel de resultados
            areaResultado.insertIcon(iconeRedimensionado);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a imagem: " + caminhoImagem);
        }
    }

    private void erro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Principal
    public static void main(String[] args) {
        // Aplica o tema do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Decoracao da janela para funcionar no Hyprland
        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal window = new JanelaPrincipal();
            window.setVisible(true);
        });
    }
}