package com.edisvrtagicipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginP extends JFrame {
    private JTextField UName;
    private JTextField UPass;
    private JButton loginBtn;
    private JPanel loginpanel;
    private JLabel tekstL;
    Connection con;
    public static String accus;

    public LoginP() {
        KonekcijaLogin();
        add(loginpanel);
        setSize(360, 330);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Login button
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accrole;
                if (!UName.getText().isBlank() && !UPass.getText().isBlank())
                {
                    String logDB = "SELECT count(*) FROM korisnici WHERE ImeKor = '" + UName.getText() + "' AND Lozinka ='" + UPass.getText() + "'";
                    try
                    {
                        Statement state = con.createStatement();
                        ResultSet rss = state.executeQuery(logDB);
                        while (rss.next())
                        {
                            if (rss.getInt(1) == 1)
                            {
                                PreparedStatement states;
                                states = con.prepareStatement("select Uloga from korisnici where ImeKor = '" + UName.getText() + "' and Lozinka ='" + UPass.getText() + "'");
                                ResultSet rets = states.executeQuery();
                                if (rets.next() == true)
                                {
                                        String pozkor = rets.getString(1);
                                        accrole = pozkor;
                                        if (accrole.equals("menadzer"))
                                        {
                                            APanel apa = new APanel();
                                            apa.setVisible(true);
                                            apa.setLocationRelativeTo(null);
                                            dispose();
                                        }
                                        else if(accrole.equals("radnik"))
                                        {
                                            accus = UName.getText();
                                            UPanel upaa = new UPanel();
                                            upaa.setVisible(true);
                                            upaa.setLocationRelativeTo(null);
                                            dispose();
                                        }
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Taj korisnik ne postoji u bazi.");
                                UName.setText("");
                                UPass.setText("");
                            }
                        }
                    }
                    catch (Exception ex)
                    {

                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Unesite potrebne podatke!");
                }
            }
        });
    }
    //Konekcija na bazu za login
    public void KonekcijaLogin() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/projekat", "root", "");
        } catch (Exception ex) {

        }
    }
}

