package domain;

public abstract class Arriendo {

    private int numArriendo;
    private String fehcArriendo;

    private int diasArriendo;

    private Cliente cliente;

    private Vehiculo vehiculo;

    public Arriendo(int numArriendo, String fehcArriendo, int diasArriendo, Cliente cliente, Vehiculo vehiculo) {
        this.numArriendo = numArriendo;
        this.fehcArriendo = fehcArriendo;
        this.diasArriendo = diasArriendo;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
    }

    //Sobrecarga de constructores solo por el diseño que se muesta en el el diagrama de clases
    public Arriendo(int numArriendo, String fehcArriendo, int diasArriendo) {
        this.numArriendo = numArriendo;
        this.fehcArriendo = fehcArriendo;
        this.diasArriendo = diasArriendo;
    }

    public int getNumArriendo() {
        return numArriendo;
    }

    public void setNumArriendo(int numArriendo) {
        this.numArriendo = numArriendo;
    }

    public String getFehcArriendo() {
        return fehcArriendo;
    }

    public void setFehcArriendo(String fehcArriendo) {
        this.fehcArriendo = fehcArriendo;
    }

    public int getDiasArriendo() {
        return diasArriendo;
    }

    public void setDiasArriendo(int diasArriendo) {
        this.diasArriendo = diasArriendo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }



    public int obtenerMontoAPagar(int precioDia) {
        return precioDia * this.diasArriendo;
    }

    public boolean evaluarArriendo() {
        return this.cliente.isVigente() && this.vehiculo.getCondicion() == 'D';
    }

    @Override
    public String toString() {
        return "Arriendo{" +
                "numArriendo=" + numArriendo +
                ", fehcArriendo='" + fehcArriendo + '\'' +
                ", diasArriendo=" + diasArriendo +
                ", cliente=" + cliente +
                ", vehiculo=" + vehiculo +
                '}';
    }
}
