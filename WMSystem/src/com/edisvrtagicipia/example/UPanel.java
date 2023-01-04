package com.edisvrtagicipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UPanel extends JFrame {
    private JPanel UPPanel;
    private JButton proizvodiBtn;
    private JButton narudzbaBtn;
    private JButton stareNarudzbeBtn;
    private JButton odjaBtn;
    private JButton zatvoBtn;

    public UPanel()
    {
        add(UPPanel);
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Button za otvaranje nove forme liste proizvoda
        proizvodiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LPanel lpa = new LPanel();
                lpa.setVisible(true);
                lpa.setLocationRelativeTo(null);
                dispose();
            }
        });
        //Button za zatvaranje aplikacije
        zatvoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        //Button za odjavu sa aplikacije i vracanje na login formu
        odjaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginP lop = new LoginP();
                lop.setVisible(true);
                lop.setLocationRelativeTo(null);
                dispose();
            }
        });
        //Button za otvaranje nove forme za narudzbu proizvoda
        narudzbaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NarPanel nap = new NarPanel();
                nap.setVisible(true);
                nap.setLocationRelativeTo(null);
                dispose();
            }
        });
        //Button za otvaranje nove forme za listu starih narudzbi
        stareNarudzbeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HPanel hpa = new HPanel();
                hpa.setVisible(true);
                hpa.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
}
