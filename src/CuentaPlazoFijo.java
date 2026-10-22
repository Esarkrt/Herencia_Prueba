
import java.util.Calendar;


public class CuentaPlazoFijo extends CuentaBancaria{
    private Calendar findePlazo;
    private double interesesAcumulados;
    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
    super(numero, cliente, moneda, TipoCuenta.PLAZO);
    this.interesesAcumulados = 0.0;
}

public void setPlazo(int anio, int mes, int dia) {
    try {
        Calendar cal = Calendar.getInstance();
        cal.setLenient(false);
        cal.set(anio, mes - 1, dia);
        cal.getTime();
        this.findePlazo = cal;
    } catch (Exception e) {
        throw new IllegalArgumentException("Fecha de plazo no válida");
    }
}

public boolean estaVencida() {
    if (findePlazo == null) {
        return false;
    }
    Calendar hoy = Calendar.getInstance();
    return !hoy.before(findePlazo);
}

public boolean retirar(double monto) {
    if (monto <= 0) {
        throw new IllegalArgumentException("El monto debe ser mayor a cero");
    }
    if (!estaVencida()) {
        if (monto > interesesAcumulados) {
            return false;
        }
        interesesAcumulados -= monto;
        registrarMovimiento("RETIRO_INTERESES -" + monto + " -> intereses: " + interesesAcumulados);
        return true;
    } else {
        if (interesesAcumulados > 0) {
            saldo += interesesAcumulados;
            interesesAcumulados = 0.0;
        }
        return super.retirar(monto);
    }
}

public void registrarIntereses() {
    double interes = saldo * tipo.tasa();
    if (!estaVencida()) {
        interesesAcumulados += interes;
        registrarMovimiento("ACUMULAR_INTERESES +" + interes + " -> intereses: " + interesesAcumulados);
    } else {
        saldo += interes;
        registrarMovimiento("INTERESES +" + interes + " -> saldo: " + saldo);
    }
}

public void reintegrar(double monto) {
    if (!estaVencida()) {
        interesesAcumulados += monto;
        registrarMovimiento("REVERSA_INTERESES +" + monto + " -> intereses: " + interesesAcumulados);
    } else {
        super.Reintegrar(monto);
    }
}

public String toString() {
    String vencimientoStr = (findePlazo != null) ? findePlazo.getTime().toString() : "No definido";
    return super.toString() + ", Vencimiento: " + vencimientoStr + ", Intereses Acumulados: " + interesesAcumulados + ", Vencida: " + estaVencida();
}
public void quienSoy() {
    System.out.println("Soy una CuentaPlazoFijo");
}
}
