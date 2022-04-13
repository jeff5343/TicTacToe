package com.hong.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {
    
    private JFrame frame = new JFrame("Tic Tac Toe");
    private int width = 500;
    private int height = 500;

    public Frame() {
        setFrameSettings();
        JPanel panel = new JPanel();
    }

    private void setFrameSettings() {
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
        frame.isActive();
    }

}
