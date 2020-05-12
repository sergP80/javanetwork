package ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms;

import ua.edu.chmnu.ki.networks.udp.swing_game.core.GamerPool;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Gamer;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Position;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.gamers.Size;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.Shape;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.ShapeFactory;
import ua.edu.chmnu.ki.networks.udp.swing_game.models.shapes.ShapeType;
import ua.edu.chmnu.ki.networks.udp.swing_game.ui.forms.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class LoginDialog extends JDialog {
    private static final String DEFAULT_TITLE = "Gamer info";
    private static final int DEFAULT_WIDTH = 320;
    private static final int DEFAULT_HEIGHT = 240;

    private static final Size DEFAULT_SHAPE_SIZE = Size.of(50, 50);

    private final GamerPool gamerPool;
    private Gamer currentGamer = null;

    private DefaultComboBoxModel<ShapeType> shapeModel;
    private JTextField nikName;

    public LoginDialog(final JFrame parent, final GamerPool gamerPool) {
        super(parent, DEFAULT_TITLE, ModalityType.DOCUMENT_MODAL);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationAndSize();
        setTitle(DEFAULT_TITLE);
        this.gamerPool = gamerPool;
        createDialogContent();
    }

    private void setLocationAndSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocation(screenSize.width/2 - DEFAULT_WIDTH/2, screenSize.height/2 - DEFAULT_HEIGHT / 2);
    }

    private void createDialogContent() {
        Container container = getContentPane();
        container.setLayout(new GridLayout(3,1));
        shapeModel = new DefaultComboBoxModel<>(ShapeType.values());

        container.add(nikName = UIUtils.createTextField("Name"));
        container.add(UIUtils.createComboBox(shapeModel));
        container.add(UIUtils.createButton("Enter", (e) -> {
            Shape shape = ShapeFactory.shapeBuilder((ShapeType) shapeModel.getSelectedItem(), DEFAULT_SHAPE_SIZE).apply(Position.zero());
            LoginDialog.this.currentGamer = new Gamer(UUID.randomUUID(), nikName.getText(), shape);
            LoginDialog.this.gamerPool.add(currentGamer);
            LoginDialog.this.setVisible(false);
        }));
    }

    public Gamer getCurrentGamer() {
        return currentGamer;
    }
}
