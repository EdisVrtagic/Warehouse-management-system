package com.edisvrtagicipia.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NarPanel extends JFrame {
    private JTextField brNar;
    private JPanel panelNar;
    public JTextField brRad;
    private JTextField ProSearch;
    private JButton pretragaBtn;
    private JButton nazadBtn;
    private JButton odustaniBtn;
    private JButton potvrdiBtn;
    private JLabel cijenaPro;
    private JLabel SlikaPro;
    private JLabel nazivPro;
    private JTextField kolBox;
    private JLabel kolLabel;
    //Osnovnje stvari za sliku
    private ImageIcon format = null;
    //Osnovno za konekciju
    Connection conet;
    PreparedStatement pstate;
    //Staticni string za dohvatanje imena profila
    public static String imeacc;

    public NarPanel()
    {
        Konekt();
        KorisnikData();
        add(panelNar);
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Buttoni koji su sakriveni na formi sve do njihovog koristenja
        kolLabel.setVisible(false);
        kolBox.setVisible(false);
        potvrdiBtn.setVisible(false);
        //Button za pretragu proizvoda iz baze po ID
        pretragaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ProSearch.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Unesite ID proizvoda!");
                } else {
                    try {
                        String idp = ProSearch.getText();
                        pstate = conet.prepareStatement("select ImPro,CPro,SlikaP from proizvodi where IdPro = ?");
                        pstate.setString(1, idp);
                        ResultSet rst = pstate.executeQuery();
                        if (rst.next() == true) {
                            String imeproizvoda = rst.getString(1);
                            String cijenaproizvoda = rst.getString(2);
                            byte [] slikadat = rst.getBytes("SlikaP");
                            format = new ImageIcon(slikadat);
                            Image sm = format.getImage();
                            Image img2 = sm.getScaledInstance(64,64,Image.SCALE_SMOOTH);
                            ImageIcon image = new ImageIcon(img2);
                            SlikaPro.setIcon(image);
                            nazivPro.setText(imeproizvoda);
                            cijenaPro.setText(cijenaproizvoda);
                            kolLabel.setVisible(true);
                            kolBox.setVisible(true);
                            potvrdiBtn.setVisible(true);
                        } else {
                            ProSearch.setText("");
                            nazivPro.setText("");
                            cijenaPro.setText("");
                            SlikaPro.setIcon(null);
                            potvrdiBtn.setVisible(false);
                            JOptionPane.showMessageDialog(null, "Pogrešan ID proizvoda!");
                        }
                    } catch (Exception ex) {

                    }
                }
            }
        });
        //Button za odustajanje od narudzbe
        odustaniBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brNar.setText("");
                ProSearch.setText("");
                nazivPro.setText("");
                cijenaPro.setText("");
                SlikaPro.setIcon(null);
                kolLabel.setVisible(false);
                kolBox.setVisible(false);
                potvrdiBtn.setVisible(false);
            }
        });
        //Button za potvrdjivanje narudzbe
        potvrdiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kolBox.getText().isBlank() || brNar.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Unesite količinu i broj narudžbe!");
                } else {
                    int ogran;
                    ogran = Integer.parseInt(kolBox.getText());
                    if (ogran < 1 || ogran > 1000) {
                        JOptionPane.showMessageDialog(null, "Količina ne može biti manja od 1 i veća od 1000!");
                        kolBox.setText("");
                    } else {
                        String idkori, idnar,imekoris, proime, procijena, prokol;
                        idkori = brRad.getText();
                        idnar = brNar.getText();
                        imekoris = LoginP.accus;
                        proime = nazivPro.getText();
                        procijena = cijenaPro.getText();
                        prokol = kolBox.getText();
                        try
                        {
                            pstate = conet.prepareStatement("insert into historija(IDKor,IDNar,KorPro,ImePro,CijenaPro,KolPro)values (?,?,?,?,?,?)");
                            pstate.setString(1, idkori);
                            pstate.setString(2, idnar);
                            pstate.setString(3, imekoris);
                            pstate.setString(4, proime);
                            pstate.setString(5, procijena);
                            pstate.setString(6, prokol);
                            pstate.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Proizvod uspješno naručen!");
                            brNar.setText("");
                            ProSearch.setText("");
                            nazivPro.setText("");
                            cijenaPro.setText("");
                            SlikaPro.setIcon(null);
                            kolLabel.setVisible(false);
                            kolBox.setVisible(false);
                            potvrdiBtn.setVisible(false);
                        }
                        catch (SQLException ex)
                        {
                            JOptionPane.showMessageDialog(null,"Broj narudžbe već postoji!");
                        }
                    }
                }
            }
        });
        //Button za vracanje na pocetnu formu za radnike
        nazadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UPanel ua = new UPanel();
                ua.setVisible(true);
                ua.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    //Funkcija koja vrsi provjeru ID-a radnika na osnovu imena radnika i smjesta ga u textField brRad
    public void KorisnikData()
    {
        try
        {
            PreparedStatement stas;
            stas = conet.prepareStatement("select IdKor from korisnici where ImeKor = '" + LoginP.accus + "'");
            ResultSet rets = stas.executeQuery();
            if (rets.next() == true)
            {
                brRad.setText(rets.getString(1));
            }
        }
        catch (Exception ex)
        {

        }
    }
    //Konekcija na bazu
    public void Konekt() {
        try {
            conet = DriverManager.getConnection("jdbc:mysql://localhost/projekat", "root", "");
        } catch (Exception ex) {

        }
    }
}
