package com.caotinging.java8action.GUI;

import javax.swing.*;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @create: 2019/7/31
 */
public class demo222 {
    private JCheckBox nanCheckBox;
    private JPanel panel1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("demo222");
        frame.setContentPane(new demo222().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
