package com.edisvrtagicipia.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class APanel extends JFrame {
    private JPanel PPanel;
    private JTextField ImeProizvoda;
    private JTextField CijenaProizvoda;
    private JTextField StanjeProizvoda;
    private JButton izbrisiBtn;
    private JButton sacuvajBtn;
    private JButton editBtn;
    private JTextField piD;
    private JButton pretraziBtn;
    private JButton odjavaBtn;
    private JButton zatvoriBtn;
    private JLabel ImgPro;
    private JButton pretraziSlikeBtn;
    //Osnovno za dodavanje slika
    private ImageIcon format = null;
    String path = null;
    //Sve za konekciju
    Connection conn;
    PreparedStatement ps;
    public APanel() {
        Konekcija();
        add(PPanel);
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Button za spremanje podataka u bazu u tabelu proizvodi
        sacuvajBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (ImeProizvoda.getText().isBlank() || CijenaProizvoda.getText().isBlank())
                {
                    JOptionPane.showMessageDialog(null, "Unesite potrebne podatke!");
                }
                else
                {
                    String imep, cijenap;
                    imep = ImeProizvoda.getText();
                    cijenap = CijenaProizvoda.getText();
                    File f = new File(path);
                    try
                    {
                        InputStream ist = new FileInputStream(f);
                        ps = conn.prepareStatement("insert into proizvodi(ImPro,CPro,SlikaP)values (?,?,?)");
                        ps.setString(1, imep);
                        ps.setString(2, cijenap);
                        ps.setBlob(3,ist);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Proizvod dodan u skladište!");
                        ImeProizvoda.setText("");
                        CijenaProizvoda.setText("");
                        ImgPro.setIcon(null);
                    }
                    catch (Exception ex)
                    {

                    }
                }
            }
        });
        //Button za pretragu proizvoda po ID iz tabele proizvodi
        pretraziBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (piD.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Unesite ID proizvoda!");
                } else {
                    try {
                        String idp = piD.getText();
                        ps = conn.prepareStatement("select ImPro,CPro,SlikaP from proizvodi where IdPro = ?");
                        ps.setString(1, idp);
                        ResultSet rst = ps.executeQuery();

                        if (rst.next() == true) {
                            String imeproi = rst.getString(1);
                            String cijenaproi = rst.getString(2);
                            ImeProizvoda.setText(imeproi);
                            CijenaProizvoda.setText(cijenaproi);
                            byte [] slikadat = rst.getBytes("SlikaP");
                            format = new ImageIcon(slikadat);
                            Image sm = format.getImage();
                            Image img2 = sm.getScaledInstance(64,64,Image.SCALE_SMOOTH);
                            ImageIcon image = new ImageIcon(img2);
                            ImgPro.setIcon(image);
                        } else {
                            ImeProizvoda.setText("");
                            CijenaProizvoda.setText("");
                            piD.setText("");
                            ImgPro.setIcon(null);
                            JOptionPane.showMessageDialog(null, "Pogrešan ID proizvoda!");
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        });
        //Edit button za izmjenu atributa proizvoda u tabeli proizvodi
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (piD.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Unesite ID proizvoda!");
                } else {
                    String idpp, imep, cijenap;
                    imep = ImeProizvoda.getText();
                    cijenap = CijenaProizvoda.getText();
                    idpp = piD.getText();
                    File fa = new File(path);
                    try {
                        InputStream ists = new FileInputStream(fa);
                        ps = conn.prepareStatement("update proizvodi set ImPro = ?, CPro = ?, SlikaP = ? where IdPro = ?");
                        ps.setString(1, imep);
                        ps.setString(2, cijenap);
                        ps.setBlob(3, ists);
                        ps.setString(4, idpp);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Izmjena uspješno izvršena!");
                        ImeProizvoda.setText("");
                        CijenaProizvoda.setText("");
                        piD.setText("");
                        ImgPro.setIcon(null);
                    }
                    catch (Exception ex)
                    {

                    }
                }
            }
        });
        //Button za brisanje proizvoda iz baze po ID
        izbrisiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (piD.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Unesite ID proizvoda!");
                } else {
                    String izid;
                    izid = piD.getText();
                    try {
                        ps = conn.prepareStatement("delete from proizvodi where IdPro = ?");
                        ps.setString(1, izid);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Proizvod uklonjen iz skladišta!");
                        ImeProizvoda.setText("");
                        CijenaProizvoda.setText("");
                        StanjeProizvoda.setText("");
                        piD.setText("");

                    } catch (Exception ex) {

                    }
                }
            }
        });
        //Button za zatvaranje aplikacije
        zatvoriBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImeProizvoda.setText("");
                CijenaProizvoda.setText("");
                piD.setText("");
                System.exit(1);
            }
        });
        //Button za odjavu iz aplikacije i vracanje na login formu
        odjavaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImeProizvoda.setText("");
                CijenaProizvoda.setText("");
                piD.setText("");
                LoginP logp = new LoginP();
                logp.setVisible(true);
                logp.setLocationRelativeTo(null);
                dispose();
            }
        });
        //Button za pretragu slika koje se dodaju u bazu
        pretraziSlikeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG JPG AND JPEG","png","jpg","jpeg");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    path = selectedFile.getAbsolutePath();
                    ImageIcon imic = new ImageIcon(path);
                    Image img = imic.getImage().getScaledInstance(64,64, Image.SCALE_SMOOTH);
                    ImgPro.setIcon(new ImageIcon(img));
                }
            }
        });
    }
    //Konekcija na bazu
    public void Konekcija()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/projekat","root","");
        }
        catch(Exception ex)
        {

        }
    }
}
