package infrastructure;

import javax.swing.*;

public class CarRentUI extends JFrame {

    private JPanel MainPanel;
    private JButton agregarClienteButton;

    public CarRentUI() {
        setContentPane(MainPanel);
        setTitle("Car Rent");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
