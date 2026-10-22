
import java.util.ArrayList;
import java.util.Calendar;


public class CuentaBancaria {
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar creacion;
    protected ArrayList<String>Historial;
    
    public CuentaBancaria(int numero,String cliente, double tasa){
        this.cliente=cliente;
        this.numero=numero;
        this.tasa=tasa;
        this.saldo=500;
        this.creacion=Calendar.getInstance();
        this.Historial=new ArrayList<>();
        this.Historial.add("Cuenta creada con saldo inicial de 500Lps");
    }
    public void depositar(double monto){
        try{
            if(monto<=0){
                System.out.println("Insuficiente saldo");
            }else{
                System.out.println("Monto realizado con exito");
                saldo+=monto;
                Historial.add("Deposito +"+monto);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public boolean retirar(double monto){
        try{
            if(monto>0 && saldo>monto){
                saldo-=monto;
                Historial.add("Retiro -"+monto);
                return true; 
            }
        }catch(Exception e){
            
        }
        Historial.add("Intento de retiro fallido: "+monto);
        return false;
    }
    public void mostrarHistorial(){
        System.out.println("*****HISTORIAL DE "+cliente.toUpperCase()+" *************");
        for(String registro:Historial){
            System.out.println(registro);
        }
    }
    public double getSaldo(){
        return saldo;
    }
    public String toString(){
        return String.format("Cuenta #%d |%s|$%.2f",numero,cliente,saldo);
    }
    public void transferir(double monto,CuentaBancaria destino){
        if(this.retirar(monto)){
            destino.depositar(monto);
        this.Historial.add("Transferencia enviada a# "+destino.numero+" : "+monto);
    }else{
            System.out.println("Transferencia fallida");
        }
    }
}
