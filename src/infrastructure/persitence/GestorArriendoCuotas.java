package infrastructure.persitence;

import domain.ArriendoCuota;

import java.util.ArrayList;
import java.util.List;

public class GestorArriendoCuotas {
    private List<ArriendoCuota> listaArriendoCuotas;

    public GestorArriendoCuotas() {
        this.listaArriendoCuotas = new ArrayList<>();
    }

    public void agregarArriendoCuota(ArriendoCuota arriendoCuota){
        listaArriendoCuotas.add(arriendoCuota);
    }

    public List<ArriendoCuota> obtenerListaArriendoCuotas(){
        return listaArriendoCuotas;
    }
}
