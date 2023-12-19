package infrastructure.ui.utils;

import domain.CuotaArriendo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CuotasTableModel extends AbstractTableModel {
    private final String[] columnNames =new String[] {"N° Cuota", "Valor Cuota", "Estado"};
    private final ArrayList<CuotaArriendo> cuotas;

    public CuotasTableModel(ArrayList<CuotaArriendo> cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public int getRowCount() {
        return cuotas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CuotaArriendo cuota = cuotas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cuota.getNumeroCuota();
            case 1:
                return cuota.getValorCuota();
            case 2:
                return cuota.isPagada() ? "Pagada" : "Pendiente";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    public void actualizarTabla(ArrayList<CuotaArriendo> cuotas){
        this.cuotas.clear();
        this.cuotas.addAll(cuotas);
        fireTableDataChanged();
    }

    public void limpiarTabla(){
        this.cuotas.clear();
        fireTableDataChanged();
    }
}
