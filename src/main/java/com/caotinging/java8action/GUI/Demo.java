package com.caotinging.java8action.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @create: 2019/7/31
 */
public class Demo {
    private JTextField name;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JTextField idcard;
    private JRadioButton manRadio;
    private JRadioButton womanRadio;
    private JComboBox comboBox1;
    private JTextField deviceInfoField;
    private JTextArea videoField;
    private JTextField leftEyeImage;
    private JTextField rightEyeImage;
    private JButton captureButton;
    private JButton downButton;
    private JTextField recordField;
    private JLabel rightLabel;
    private JLabel leftLabel;
    private JLabel videoLabel;
    private JLabel deviceListLabel;
    private JLabel iaCardLabel;
    private JLabel sexLabel;
    private JLabel nameLabel;
    private JPanel panel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo");
        frame.setContentPane(new Demo().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
