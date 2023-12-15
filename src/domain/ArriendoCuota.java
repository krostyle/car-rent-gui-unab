package domain;

import java.util.ArrayList;

public class ArriendoCuota extends Arriendo{
    private int cantidadCuotas;
    private ArrayList<CuotaArriendo> cuotasAriendo;

    public ArriendoCuota(int numArriendo, String fehcArriendo, int diasArriendo, Cliente cliente, Vehiculo vehiculo, int cantidadCuotas, ArrayList<CuotaArriendo> cuotasAriendo) {
        super(numArriendo, fehcArriendo, diasArriendo, cliente, vehiculo);
        this.cantidadCuotas = cantidadCuotas;
        this.cuotasAriendo = new ArrayList<>();
    }

    public ArriendoCuota(int numArriendo, String fehcArriendo, int diasArriendo, int cantidadCuotas, ArrayList<CuotaArriendo> cuotasAriendo) {
        super(numArriendo, fehcArriendo, diasArriendo);
        this.cantidadCuotas = cantidadCuotas;
        this.cuotasAriendo = new ArrayList<>();
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public ArrayList<CuotaArriendo> getCuotasAriendo() {
        return cuotasAriendo;
    }

    public void setCuotasAriendo(ArrayList<CuotaArriendo> cuotasAriendo) {
        this.cuotasAriendo = cuotasAriendo;
    }

    public ArrayList<CuotaArriendo> generarCuotas(int precioDia) {
        this.cuotasAriendo.clear();
        int montoTotal = this.obtenerMontoAPagar(precioDia);
        int valorCuota = montoTotal / this.cantidadCuotas;

        for (int i = 0; i < this.cantidadCuotas; i++) {
            CuotaArriendo cuota = new CuotaArriendo(i + 1, valorCuota, false);
            this.cuotasAriendo.add(cuota);
        }
        return this.cuotasAriendo;
    }

    public boolean ingresarArriendoConCuota(int precioDia) {
        if (this.evaluarArriendo()) {
            this.getVehiculo().setCondicion('A');
            this.generarCuotas(precioDia);
            return true;
        }
        return false;
    }

    public boolean pagarCuota(int numeroCuota){
        for (CuotaArriendo cuota : cuotasAriendo) {
            if(cuota.getNumeroCuota() == numeroCuota && !cuota.isPagada()){
                return cuota.pagarCuota();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ArriendoCuota{" +
                "cantidadCuotas=" + cantidadCuotas +
                ", cuotasAriendo=" + cuotasAriendo +
                '}';
    }
}
