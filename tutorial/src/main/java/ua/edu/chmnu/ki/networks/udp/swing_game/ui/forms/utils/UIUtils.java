package ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms.utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UIUtils {
    private UIUtils() {
    }

    public static JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    public static JButton createButton(String text) {
        return createButton(text, null);
    }

    public static JTextField createTextField(String defaultText, ActionListener listener) {
        JTextField textField = new JTextField(defaultText);
        textField.addActionListener(listener);
        return textField;
    }

    public static JTextField createTextField(String defaultText) {
        return createTextField(defaultText, null);
    }

    public static <T> JComboBox<T> createComboBox(T[] items, ActionListener listener) {
        JComboBox<T> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(items));
        comboBox.addActionListener(listener);
        return comboBox;
    }

    public static <T> JComboBox<T> createComboBox(T[] items) {
        return createComboBox(items, null);
    }

    public static <T> JComboBox<T> createComboBox(ComboBoxModel<T> model, ActionListener listener) {
        JComboBox<T> comboBox = new JComboBox<>();
        comboBox.setModel(model);
        comboBox.addActionListener(listener);
        return comboBox;
    }

    public static <T> JComboBox<T> createComboBox(ComboBoxModel<T> model) {
        return createComboBox(model, null);
    }
}
