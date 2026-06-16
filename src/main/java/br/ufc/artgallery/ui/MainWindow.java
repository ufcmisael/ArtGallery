package br.ufc.artgallery.ui;

import br.ufc.artgallery.repository.RepositorioObra;
import br.ufc.artgallery.service.ArtGallery;

import javax.swing.*;

public class MainWindow extends JFrame {
    private final ArtGallery artGallery;

    // icon da janela
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JTabbedPane tabbedPane;
    private JPanel obrasPanel;
    private JPanel avaliacoesPanel;
    private JPanel exposicoesPanel;

    public MainWindow () {
        artGallery = new ArtGallery(new RepositorioObra());

        this.setTitle("Art Gallery");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500,500);

        ImageIcon icon = new ImageIcon("src/main/resources/icon.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new java.awt.Color(22, 130, 200));

        this.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true); // obriga desenhar o cabeçalho, necesario p hyprland
        new MainWindow();
        System.out.println("Art Gallery");
    }

}
