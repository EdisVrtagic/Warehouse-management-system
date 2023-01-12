package com.edisvrtagicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HPanel extends JFrame
{
    private JPanel HistPanel;
    private JTable histTbl;
    private JButton nazadButton;
    private JButton izlazButton;
    //Osnovno za konekciju
    Connection conect;
    Statement sts;

    public HPanel()
    {
        HistKonekcija();
        add(HistPanel);
        setSize(700, 485);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Ucitavanje starih narudzbi radnika
        LoadHist();
        //Button za izlaz iz aplikacije
        izlazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        //Button za vracanje na pocetnu formu za radnike
        nazadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UPanel pa = new UPanel();
                pa.setVisible(true);
                pa.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    //Funkcija koja vrsi ucitavanje starih narudzbi u tabelu od radnika
    public void LoadHist()
    {
        try
        {
            String[] header = {"Profil ID", "Narudzba ID", "Radnik", "Proizvod", "Cijena", "Kolicina"};
            DefaultTableModel models = new DefaultTableModel(null, header);
            PreparedStatement sta;
            sta = conect.prepareStatement("select * from historija where KorPro = '" + LoginP.accus + "'");
            ResultSet res = sta.executeQuery();
            Object[] objekat = new Object[6];
            while(res.next())
            {
                objekat[0] = res.getInt("IDKor");
                objekat[1] = res.getInt("IDNar");
                objekat[2] = res.getString("KorPro");
                objekat[3] = res.getString("ImePro");
                objekat[4] = res.getInt("CijenaPro");
                objekat[5] = res.getInt("KolPro");
                models.addRow(objekat);
            }
            histTbl.setModel(models);
            histTbl.getTableHeader().setReorderingAllowed(false);
        }
        catch (SQLException ex)
        {

        }
    }
    //Koneckija na bazu
    public void HistKonekcija()
    {
        try
        {
            conect = DriverManager.getConnection("jdbc:mysql://localhost/projekat", "root", "");
        }
        catch (Exception ex)
        {

        }
    }
}
