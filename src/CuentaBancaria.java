

import java.util.Calendar;


public class CuentaBancaria {
    protected int numero;
    protected double saldo;
    protected String cliente, moneda;
    protected TipoCuenta tipo;
    protected Calendar apertura;

    protected String[] historial = new String[50];
    protected int cantiMovimientos = 0;

    public CuentaBancaria(int numero, String cliente, String moneda) {
        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.saldo = 0;
        this.apertura = Calendar.getInstance();
        this.tipo = TipoCuenta.CHEQUES;
    }
    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo) {
    this.numero = numero;
    this.cliente = cliente;
    this.moneda = moneda;
    this.saldo = 0.0;
    this.apertura = Calendar.getInstance();
    this.tipo = tipo;
}

    public String getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public Calendar getApertura() {
        return apertura;
    }

    public String getMoneda() {
        return moneda;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        saldo += monto;
        registrarMovimiento("Depositado: +" + monto + " saldo: " + saldo);
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero");
        }

        if (monto > saldo) {
            return false;
        }

        saldo -= monto;
        registrarMovimiento("RETIRO -" + monto + " -> saldo: " + saldo);
        return true;
    }

    public void Reintegrar(double monto) {
        saldo += monto;
        registrarMovimiento("REVERSA +" + monto + " -> saldo: " + saldo);
    }

    public void registrarIntereses() {
        if (tipo != null) {
            double ganancia = saldo * tipo.tasa();
            if (ganancia > 0) {
                saldo += ganancia;
                registrarMovimiento("INTERESES +" + ganancia + " -> saldo: " + saldo);
            }
        }
    }

    protected void registrarMovimiento(String linea) {
        try {
            if (cantiMovimientos >= historial.length) {
                throw new IllegalStateException("El historial de movimientos se ha llenado");
            }
            historial[cantiMovimientos] = linea;
            cantiMovimientos++;
        } catch (IllegalStateException e) {
            System.out.println("Alerta: " + e.getMessage());
        }
    }

    public void imprimirHistorial() {
        System.out.println("--- Historial de Movimientos (" + numero + ") ---");
        for (int i = 0; i < cantiMovimientos; i++) {
            System.out.println(historial[i]);
        }
    }

    @Override
    public String toString() {
        return "NO.Cuenta #" + numero + " | Cliente: " + cliente + 
               " | Saldo: " + saldo + " " + moneda + 
               " | Tipo: " + (tipo != null ? tipo.getNombre() : "N/A") + 
               " | Apertura: " + apertura.getTime();
    }

    public void quienSoy() {
        System.out.println("CuentaBancaria(clase base)");
    }
}
