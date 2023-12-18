package infrastructure.persitence;

import domain.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private List<Cliente> listaClientes;

    public GestorClientes() {
        this.listaClientes = new ArrayList<>();
    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public List<Cliente> obtenerListaClientes() {
        return listaClientes;
    }
    public boolean validarCliente(String cedula) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }
}
