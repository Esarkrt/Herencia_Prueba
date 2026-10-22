
public class CuentaCheques extends CuentaBancaria {
    private double limiteSobregiro;

    public CuentaCheques(int numero, String cliente, String moneda, double limiteSobregiro) {
        super(numero, cliente, moneda);
        this.tipo = TipoCuenta.CHEQUES;
        if (limiteSobregiro < 0) {
            throw new IllegalArgumentException("El límite de sobregiro no puede ser negativo");
        }
        this.limiteSobregiro = limiteSobregiro;
    }

    public void girarCheque(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del cheque debe ser mayor a cero");
        }
        if (monto > saldo + limiteSobregiro) {
            throw new IllegalStateException("Fondos insuficientes para girar el cheque");
        }
        saldo -= monto;
        registrarMovimiento("GIRAR_CHEQUE -" + monto + " -> saldo: " + saldo);
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero");
        }
        if (monto > (saldo + limiteSobregiro)) {
            return false;
        }
        saldo -= monto;
        registrarMovimiento("RETIRO -" + monto + " -> saldo: " + saldo);
        return true;
    }

    

    @Override
    public String toString() {
        double disponible = saldo + limiteSobregiro;
        return super.toString() + " | Sobregiro Limite: " + limiteSobregiro + " | Disponible total: " + disponible;
    }
    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaCheques");
    }
}


