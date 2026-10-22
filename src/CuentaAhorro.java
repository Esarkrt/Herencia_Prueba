
import java.util.Calendar;


public class CuentaAhorro extends CuentaBancaria{
    private Calendar UltimaModif;
    private int retiros=0;
    private static final int MAX_RETIROS_MES=3;
    public CuentaAhorro(int numero, String cliente) {
        super(numero,cliente,0.03);
        this.UltimaModif=Calendar.getInstance();
        
    }
    public boolean status(){
        Calendar fechaActual=Calendar.getInstance();
        fechaActual.add(Calendar.MONTH,-6);
        return UltimaModif.after(fechaActual);
    }
    public void aplicarIntereses(){
        if(status()){
            double interes=saldo*tasa;
            depositar(interes);
            Historial.add("Se ha registrado interes: +"+interes);
            
        }
    }
    public void retiro(int monto){
        if(status() && (retiros<MAX_RETIROS_MES)){
            retirar(monto);
        }
    }
}
