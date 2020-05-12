package ua.edu.chmnu.ki.networks.udp.swing_game.ui;

import ua.edu.chmnu.ki.networks.udp.swing_game.models.Gamer;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.GamerInfo;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.UUID;

public class LoginDialog extends JDialog {
    private static final String DEFAULT_TITLE = "Gamer info";
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 180;

    private LayoutManager layoutManager;
    private GamerInfo gamerInfo = new GamerInfo();
    private ShapeType[] shapeTypes = new ShapeType[]{ShapeType.CIRCLE, ShapeType.ELLIPSE, ShapeType.DIAMOND, ShapeType.RECTANGLE, ShapeType.SQUARE};
    private DefaultComboBoxModel<ShapeType> shapeModel;
    private JTextField nikName;
    public LoginDialog() {
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setTitle(DEFAULT_TITLE);
        shapeModel = new DefaultComboBoxModel<>(shapeTypes);
        layoutManager = new GridLayout(3,1);
        setLayout(this.layoutManager);
        add(nikName = createTextField("Name"));
        add(createShapeList(shapeModel));
        add(createButton("Enter", (e) -> {
            Gamer gamer = new Gamer(UUID.randomUUID(), nikName.getText());
            gamerInfo.setGamer(gamer);
            gamerInfo.setDimension(new Dimension(20, 20));
            gamerInfo.setShapeType((ShapeType)shapeModel.getSelectedItem());
            LoginDialog.this.setVisible(false);
        }));
    }

    private JButton createButton(String caption, ActionListener listener) {
        JButton button = new JButton(caption);
        button.addActionListener(listener);
        return button;
    }

    private JTextField createTextField(String defaultText) {
        JTextField textField = new JTextField(defaultText);
        return textField;
    }

    private JComboBox createShapeList(ComboBoxModel<?> comboBoxModel) {
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(comboBoxModel);
        return comboBox;
    }

    public GamerInfo getGamerInfo() {
        return gamerInfo;
    }
}
