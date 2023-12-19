package infrastructure.ui;

import domain.ArriendoCuota;
import domain.Cliente;
import domain.CuotaArriendo;
import domain.Vehiculo;
import infrastructure.persitence.GestorArriendoCuotas;
import infrastructure.persitence.GestorClientes;
import infrastructure.persitence.GestorVehiculos;
import infrastructure.ui.utils.CuotasTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class CarRentUI extends JFrame {

    private JPanel MainPanel;
    private JComboBox clientesCb;
    private JComboBox vehiculosCb;
    private JButton ingresarClientesButton;
    private JTable cuotasTbl;
    private JTextField fechaArriendoTxt;
    private JTextField cantidadDiasTxt;
    private JTextField precioDiaTxt;
    private JButton ingresarVehiculosButton;
    private JTextField cantidadCuotasTxt;
    private JButton guardarArriendoButton;
    private JButton salirButton;
    private JButton pagarCuotasButton;
    private JComboBox arriendosCb;
    private JButton habilitarGenraciónDeArriendosButton;
    private JLabel montoAPagarTxt;
    private JLabel montoPagadoTxt;
    private GestorClientes gestorClientes = new GestorClientes();
    private GestorVehiculos gestorVehiculos = new GestorVehiculos();
    private GestorArriendoCuotas gestorArriendoCuotas = new GestorArriendoCuotas();
    private DefaultComboBoxModel clientesCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel vehiculosCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel arriendosCbModel = new DefaultComboBoxModel();
    private CuotasTableModel cuotasTableModel;

    public CarRentUI() {
        setContentPane(MainPanel);
        setTitle("Car Rent");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        clientesCb.setModel(clientesCbModel);
        vehiculosCb.setModel(vehiculosCbModel);
        arriendosCb.setModel(arriendosCbModel);
        primeraCargaComboBoxCliente();
        primeraCargaComboBoxVehiculo();
        primeraCargaComboBoxArriendo();
        cuotasTableModel = new CuotasTableModel(new ArrayList<CuotaArriendo>());
        cuotasTbl.setModel(cuotasTableModel);
        toggleComponents(true);
        pagarCuotasButton.setEnabled(false);
        habilitarGenraciónDeArriendosButton.setEnabled(false);

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
        guardarArriendoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validarEntradaDeDatos()) {
                    return;
                }
                Cliente cliente = (Cliente) clientesCb.getSelectedItem();
                Vehiculo vehiculo = (Vehiculo) vehiculosCb.getSelectedItem();
                String fechaArriendo = fechaArriendoTxt.getText();
                int cantidadDias = Integer.parseInt(cantidadDiasTxt.getText());
                int cantidadCuotas = Integer.parseInt(cantidadCuotasTxt.getText());
                int precioDia = Integer.parseInt(precioDiaTxt.getText());
                int numeroArriendo = obtenerUltimoNumeroArriendo() + 1;
                ArriendoCuota arriendoCuota = new ArriendoCuota(numeroArriendo, fechaArriendo, cantidadDias, cliente, vehiculo, cantidadCuotas);
                if(arriendoCuota.evaluarArriendo()) {
                    gestorArriendoCuotas.agregarArriendoCuota(arriendoCuota);
                    arriendoCuota.ingresarArriendoConCuota(precioDia);
                    limpiarCampos();
                    actualizarComboBoxArriendos();
                    JOptionPane.showMessageDialog(null, "Arriendo guardado correctamente", "Arriendo guardado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No es posible guardar el arriendo, el cliente debe estar vigente y el vehiculo en un estado D", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        arriendosCb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED && arriendosCb.getSelectedIndex() != 0){
                    ArriendoCuota arriendoCuota = (ArriendoCuota) arriendosCb.getSelectedItem();
                    if(arriendoCuota != null) {
                        actualizarTablaCuotas(arriendoCuota.getCuotasAriendo());
                        int montoAPagar = obtenerMontoAPagar(arriendoCuota.getCuotasAriendo());
                        int montoPagado = obtenerMontoPagado(arriendoCuota.getCuotasAriendo());
                        montoAPagarTxt.setText(String.valueOf(montoAPagar));
                        montoPagadoTxt.setText(String.valueOf(montoPagado));
                        toggleComponents(false);
                        pagarCuotasButton.setEnabled(true);
                        habilitarGenraciónDeArriendosButton.setEnabled(true);
                    }
                }
                if(arriendosCb.getSelectedIndex() == 0){
                    toggleComponents(true);
                    pagarCuotasButton.setEnabled(false);
                    habilitarGenraciónDeArriendosButton.setEnabled(false);
                    limpiarTablaCuotas();
                    montoAPagarTxt.setText("");
                    montoPagadoTxt.setText("");
                }
            }
        });
        habilitarGenraciónDeArriendosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleComponents(true);
                pagarCuotasButton.setEnabled(false);
                habilitarGenraciónDeArriendosButton.setEnabled(false);
                arriendosCb.setSelectedIndex(0);
                limpiarTablaCuotas();
                montoAPagarTxt.setText("");
                montoPagadoTxt.setText("");
            }
        });
        pagarCuotasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarCuotas();
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

    private void actualizarComboBoxArriendos() {
        ItemListener[] itemListeners = arriendosCb.getItemListeners();
        for (ItemListener itemListener : itemListeners) {
            arriendosCb.removeItemListener(itemListener);
        }
        arriendosCbModel.removeAllElements();
        primeraCargaComboBoxArriendo();
        List<ArriendoCuota> listaArriendos = gestorArriendoCuotas.obtenerListaArriendoCuotas();
        for (ArriendoCuota arriendoCuota : listaArriendos) {
            arriendosCbModel.addElement(arriendoCuota);
        }
        for (ItemListener itemListener : itemListeners) {
            arriendosCb.addItemListener(itemListener);
        }
    }

    private void primeraCargaComboBoxCliente() {
        clientesCbModel.addElement("Seleccione un cliente");

    }

    private void primeraCargaComboBoxVehiculo() {
        vehiculosCbModel.addElement("Seleccione un vehiculo");
    }
    private void primeraCargaComboBoxArriendo() {
        arriendosCbModel.addElement("Seleccione un arriendo");
    }

    public void actualizarTablaCuotas(ArrayList<CuotaArriendo> cuotas) {
        cuotasTableModel.actualizarTabla(cuotas);
        cuotasTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private boolean validarEntradaDeDatos() {
        if (clientesCb.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (vehiculosCb.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehiculo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (fechaArriendoTxt.getText().isEmpty() || !validarFecha(fechaArriendoTxt.getText())) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una fecha de arriendo valida", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cantidadDiasTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de dias mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            try {
                int cantidadDias = Integer.parseInt(cantidadDiasTxt.getText());
                if (cantidadDias <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de dias mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de dias mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (cantidadCuotasTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de cuotas mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            try {
                int cantidadCuotas = Integer.parseInt(cantidadCuotasTxt.getText());
                if (cantidadCuotas <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de cuotas mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad de cuotas mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (precioDiaTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un precio por dia mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else{
            try {
                int precioDia = Integer.parseInt(precioDiaTxt.getText());
                if (precioDia <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un precio por dia mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un precio por dia mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    //Metodo para obtener el ultimo numero de arriendo registrado
    private int obtenerUltimoNumeroArriendo() {
        int numeroArriendo = 0;
        List<ArriendoCuota> listaArriendos = gestorArriendoCuotas.obtenerListaArriendoCuotas();
        for (ArriendoCuota arriendoCuota : listaArriendos) {
            if (arriendoCuota.getNumArriendo() > numeroArriendo) {
                numeroArriendo = arriendoCuota.getNumArriendo();
            }
        }
        return numeroArriendo;
    }

    //Metodo para validar la fecha en formato dd/mm/yyyy o dd-mm-yyyy
    private boolean validarFecha(String fecha) {
        String[] fechaArray = fecha.split("/");
        if (fechaArray.length != 3) {
            fechaArray = fecha.split("-");
            if (fechaArray.length != 3) {
                return false;
            }
        }
        int dia = Integer.parseInt(fechaArray[0]);
        int mes = Integer.parseInt(fechaArray[1]);
        int anio = Integer.parseInt(fechaArray[2]);
        if (dia < 1 || dia > 31) {
            return false;
        }
        if (mes < 1 || mes > 12) {
            return false;
        }
        if (anio < 1900 || anio > 2100) {
            return false;
        }
        return true;
    }

    private void limpiarCampos(){
        clientesCb.setSelectedIndex(0);
        vehiculosCb.setSelectedIndex(0);
        arriendosCb.setSelectedIndex(0);
        fechaArriendoTxt.setText("");
        cantidadDiasTxt.setText("");
        montoAPagarTxt.setText("");
        precioDiaTxt.setText("");
        cantidadCuotasTxt.setText("");
    }

    private void toggleComponents(boolean toggle){
        clientesCb.setEnabled(toggle);
        vehiculosCb.setEnabled(toggle);
        fechaArriendoTxt.setEnabled(toggle);
        cantidadDiasTxt.setEnabled(toggle);
        montoAPagarTxt.setEnabled(toggle);
        precioDiaTxt.setEnabled(toggle);
        cantidadCuotasTxt.setEnabled(toggle);
        guardarArriendoButton.setEnabled(toggle);
        pagarCuotasButton.setEnabled(toggle);
        ingresarVehiculosButton.setEnabled(toggle);
        ingresarClientesButton.setEnabled(toggle);
    }

    //Metodo para pagar cuotas
    private void pagarCuotas () {
        int[] selectedRows = cuotasTbl.getSelectedRows();
        if(selectedRows.length == 0){
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una cuota para pagar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArriendoCuota arriendoCuota = (ArriendoCuota) arriendosCb.getSelectedItem();
        for (int selectedRow : selectedRows) {
            int numeroCuota = (int) cuotasTbl.getValueAt(selectedRow, 0);
            if(!arriendoCuota.pagarCuota(numeroCuota)){
                JOptionPane.showMessageDialog(this, "No es posible pagar la cuota " + numeroCuota, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        actualizarTablaCuotas(arriendoCuota.getCuotasAriendo());
        int montoPagado = obtenerMontoPagado(arriendoCuota.getCuotasAriendo());
        System.out.println(montoPagado);
        int montoAPagar = obtenerMontoAPagar(arriendoCuota.getCuotasAriendo());
        montoPagadoTxt.setText(String.valueOf(montoPagado));
        montoAPagarTxt.setText(String.valueOf(montoAPagar));
        JOptionPane.showMessageDialog(this, "Cuotas pagadas correctamente", "Cuotas pagadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private int obtenerMontoPagado (ArrayList<CuotaArriendo> cuotas) {
        int montoPagado = 0;
        for (CuotaArriendo cuota : cuotas) {
            if (cuota.isPagada()) {
                montoPagado += cuota.getValorCuota();
            }
        }
        return montoPagado;
    }

    private int obtenerMontoAPagar (ArrayList<CuotaArriendo> cuotas) {
        int montoAPagar = 0;
        for (CuotaArriendo cuota : cuotas) {
            if (!cuota.isPagada()) {
                montoAPagar += cuota.getValorCuota();
            }
        }
        return montoAPagar;
    }

    private void limpiarTablaCuotas(){
        cuotasTableModel.limpiarTabla();
    }
}
