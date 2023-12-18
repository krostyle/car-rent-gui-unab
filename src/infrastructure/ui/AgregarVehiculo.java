package infrastructure.ui;

import domain.Vehiculo;
import infrastructure.persitence.GestorVehiculos;

import javax.swing.*;
import java.awt.event.*;

public class AgregarVehiculo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField patenteTxt;
    private JComboBox condicionCb;
    private GestorVehiculos gestorVehiculos;

    public AgregarVehiculo(GestorVehiculos gestorVehiculos) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        poblarComboBoxCondicion();
        this.gestorVehiculos = gestorVehiculos;


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
    private void poblarComboBoxCondicion(){
        condicionCb.addItem("A");
        condicionCb.addItem("D");
        condicionCb.addItem("M");
    }

    private void onOK() {
        if(!validarEntradaDeDatos()) {
            return;
        }
        String patente = patenteTxt.getText().toUpperCase();
        char condicion = condicionCb.getSelectedItem().toString().charAt(0);

        try {
            if(gestorVehiculos.validarVehiculo(patente)){
                JOptionPane.showMessageDialog(this, "Vehiculo ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Vehiculo nuevoVehiculo = new Vehiculo(patente, condicion);
            gestorVehiculos.agregarVehiculo(nuevoVehiculo);
            JOptionPane.showMessageDialog(this, "Vehiculo agregado correctamente", "Vehiculo agregado", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar vehiculo", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean validarEntradaDeDatos() {
        if (patenteTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una patente", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
