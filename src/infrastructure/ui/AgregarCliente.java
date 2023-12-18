package infrastructure.ui;

import domain.Cliente;
import infrastructure.persitence.GestorClientes;

import javax.swing.*;
import java.awt.event.*;

public class AgregarCliente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cedulaTxtField;
    private JTextField nombreTxtField;
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private ButtonGroup vigenteButtonGroup;
    private GestorClientes gestorClientes;

    public AgregarCliente(GestorClientes gestorClientes) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        vigenteButtonGroup = new ButtonGroup();
        vigenteButtonGroup.add(siRadioButton);
        vigenteButtonGroup.add(noRadioButton);

        this.gestorClientes = gestorClientes;

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if(!validarEntadaDeDatos()) {
            return;
        }
        String cedula = cedulaTxtField.getText();
        String nombre = nombreTxtField.getText();
        boolean vigente = siRadioButton.isSelected();
        try {
            if (gestorClientes.validarCliente(cedula)) {
                JOptionPane.showMessageDialog(this, "Cliente ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente nuevoCliente = new Cliente(cedula, nombre, vigente);
            gestorClientes.agregarCliente(nuevoCliente);
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente", "Cliente agregado", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean validarEntadaDeDatos () {
        if (cedulaTxtField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cedula", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (nombreTxtField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!siRadioButton.isSelected() && !noRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una opcion", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
