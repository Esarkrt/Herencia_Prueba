
import java.util.Calendar;


public class CuentaAhorro extends CuentaBancaria{
    private Calendar ultimoUso;
    public CuentaAhorro(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda);
        this.tipo = TipoCuenta.AHORRO;
        this.ultimoUso = Calendar.getInstance();
    }
    private boolean estaActiva() {
        if (ultimoUso == null) return false;
        
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.MONTH, -6);
        return ultimoUso.after(limite);
    }
    @Override
    public boolean retirar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta esta inactiva");
        }
        boolean exito = super.retirar(monto);
        if (exito) {
            ultimoUso = Calendar.getInstance();
        }
        return exito;
    }
    @Override
    public void depositar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta esta inactiva");
        }
        super.depositar(monto);
        ultimoUso = Calendar.getInstance();
    }
    @Override
    public void quienSoy() {
        System.out.println("Soy una Cuenta de Ahorro");
    }

    @Override
    public String toString() {
        return super.toString() + " | Ultimo uso: " + (ultimoUso != null ? ultimoUso.getTime() : "Sin uso");
    }
}

