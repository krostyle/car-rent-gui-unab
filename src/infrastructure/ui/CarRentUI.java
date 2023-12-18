package infrastructure.ui;

import domain.Cliente;
import domain.Vehiculo;
import infrastructure.persitence.GestorClientes;
import infrastructure.persitence.GestorVehiculos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarRentUI extends JFrame {

    private JPanel MainPanel;
    private JComboBox clientesCb;
    private JComboBox vehiculosCb;
    private JButton ingresarClientesButton;
    private JTable cuotasTbl;
    private JTextField fechaArriendoTxt;
    private JTextField cantidadDiasTxt;
    private JTextField montoAPagarTxt;
    private JTextField precioDiaTxt;
    private JButton ingresarVehiculosButton;
    private JTextField cantidadCuotasTxt;
    private JButton guardarArriendoButton;
    private JButton salirButton;
    private JButton pagarCuotaButton;
    private GestorClientes gestorClientes = new GestorClientes();
    private GestorVehiculos gestorVehiculos = new GestorVehiculos();
    private DefaultComboBoxModel clientesCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel vehiculosCbModel = new DefaultComboBoxModel();

    public CarRentUI() {
        setContentPane(MainPanel);
        setTitle("Car Rent");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        clientesCb.setModel(clientesCbModel);
        vehiculosCb.setModel(vehiculosCbModel);
        primeraCargaComboBoxCliente();
        primeraCargaComboBoxVehiculo();

        ingresarClientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarCliente agregarClienteDialog = new AgregarCliente(gestorClientes);
                agregarClienteDialog.pack();
                agregarClienteDialog.setLocationRelativeTo(null);
                agregarClienteDialog.setVisible(true);
                actualizarComboBoxClientes();
            }
        });

        ingresarVehiculosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarVehiculo agregarVehiculoDialog = new AgregarVehiculo(gestorVehiculos);
                agregarVehiculoDialog.pack();
                agregarVehiculoDialog.setLocationRelativeTo(null);
                agregarVehiculoDialog.setVisible(true);
                actualizarComboBoxVehiculos();
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void actualizarComboBoxClientes() {
        clientesCbModel.removeAllElements();
        primeraCargaComboBoxCliente();
        List<Cliente> listaClientes = gestorClientes.obtenerListaClientes();
        for (Cliente cliente : listaClientes) {
            clientesCbModel.addElement(cliente);
        }
    }

    private void actualizarComboBoxVehiculos() {
        vehiculosCbModel.removeAllElements();
        primeraCargaComboBoxVehiculo();
        List<Vehiculo> listaVehiculos = gestorVehiculos.obtenerListaVehiculos();
        for (Vehiculo vehiculo : listaVehiculos) {
            vehiculosCbModel.addElement(vehiculo);
        }
    }

    private void primeraCargaComboBoxCliente(){
        clientesCbModel.addElement("Seleccione un cliente");

    }

    private void primeraCargaComboBoxVehiculo(){
        vehiculosCbModel.addElement("Seleccione un vehiculo");
    }
}
