package infrastructure.persitence;

import domain.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class GestorVehiculos {
    private List<Vehiculo> listaVehiculos;

    public GestorVehiculos() {
        this.listaVehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.add(vehiculo);
    }

    public List<Vehiculo> obtenerListaVehiculos() {
        return listaVehiculos;
    }

    public boolean validarVehiculo(String patente) {
        for (Vehiculo vehiculo : listaVehiculos) {
            if (vehiculo.getPatente().equals(patente)) {
                return true;
            }
        }
        return false;
    }
}
