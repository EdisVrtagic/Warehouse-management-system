package com.edisvrtagicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LPanel extends JFrame {
    private JPanel LpPanel;
    private JButton nazadBtn;
    private JButton izBtn;
    private JTable tabela;
    //Osnovno za konekciju
    Connection cone;
    Statement st;

    public LPanel() {
        ProKonekcija();
        add(LpPanel);
        setSize(700, 485);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Ucitavanje tabele sa proizvodima
        LoadTable();
        //Button za izlaz iz aplikacije
        izBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        //Button za vracanje na prethodnu pocetnu formu za radnike
        nazadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UPanel upa = new UPanel();
                upa.setVisible(true);
                upa.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    //Tabela liste proizvoda koji se ucitavaju iz baze
    public void LoadTable()
    {
        try
        {
            String[] naslov = {"ID", "Proizvod", "Cijena", "Slika"};
            String query = "select * from proizvodi";
            DefaultTableModel model = new DefaultTableModel(null, naslov)
            {
                //Nadjacavanje klase kako bi mogao konvertovati sliku iz baze u tabelu
                @Override
                public Class<?> getColumnClass(int column)
                {
                    if (column == 3) return ImageIcon.class;
                    return Object.class;
                }
            };
            st = cone.createStatement();
            ResultSet rs = st.executeQuery(query);
            Object[] objekat = new Object[4];
            while (rs.next())
            {
                objekat[0] = rs.getInt("IdPro");
                objekat[1] = rs.getString("ImPro");
                objekat[2] = rs.getString("CPro");
                objekat[3] = new ImageIcon(rs.getBytes("SlikaP"));
                model.addRow(objekat);
            }
            tabela.setModel(model);
            tabela.getTableHeader().setReorderingAllowed(false);
        }
        catch (Exception ex)
        {

        }
    }
    //Konekcija na bazu
    public void ProKonekcija()
    {
        try
        {
            cone = DriverManager.getConnection("jdbc:mysql://localhost/projekat", "root", "");
        }
        catch (Exception ex)
        {

        }
    }
}
