package domain;

public class CuotaArriendo {
    private int numeroCuota;
    private int valorCuota;
    private boolean pagada;

    public CuotaArriendo(int numeroCuota, int valorCuota, boolean pagada) {
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.pagada = pagada;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public int getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(int valorCuota) {
        this.valorCuota = valorCuota;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public boolean pagarCuota(){
        if(!isPagada()){
            this.setPagada(true);
            return isPagada();
        }
        return false;
    }

    @Override
    public String toString() {
        return "CuotaArriendo{" +
                "numeroCuota=" + numeroCuota +
                ", valorCuota=" + valorCuota +
                ", pagada=" + pagada +
                '}';
    }
}
