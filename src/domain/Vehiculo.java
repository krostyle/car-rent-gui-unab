package domain;

public class Vehiculo {
    private String patente;
    private char condicion;

    public Vehiculo(String patente, char condicion) {
        this.patente = patente;
        this.condicion = condicion;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public char getCondicion() {
        return condicion;
    }

    public void setCondicion(char condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return "Vehiculo" + patente + " con condicion " + condicion;
    }
}
