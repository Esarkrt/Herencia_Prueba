
public class Banco {
    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;
    public void agregarCuenta(CuentaBancaria c) {
    if (cantidadCuentas >= cuentas.length) {
        throw new IllegalStateException("El banco está lleno");
    }
    try {
        buscarPorNumero(c.getNumero());
        throw new IllegalArgumentException("El número de cuenta ya existe");
    } catch (IllegalArgumentException e) {
        if (e.getMessage().equals("El número de cuenta ya existe")) {
            throw e;
        }
    }
    cuentas[cantidadCuentas] = c;
    cantidadCuentas++;
}

private int buscarRecursivo(int numero, int indice) {
    if (indice >= cantidadCuentas) {
        throw new IllegalArgumentException("Cuenta no encontrada");
    }
    if (cuentas[indice].getNumero() == numero) {
        return indice;
    }
    return buscarRecursivo(numero, indice + 1);
}

public CuentaBancaria buscarPorNumero(int numero) {
    int indice = buscarRecursivo(numero, 0);
    return cuentas[indice];
}

private void hacerPasada(int i, int n) {
    if (i >= n) {
        return;
    }
    if (cuentas[i].getSaldo() > cuentas[i + 1].getSaldo()) {
        CuentaBancaria temp = cuentas[i];
        cuentas[i] = cuentas[i + 1];
        cuentas[i + 1] = temp;
    }
    hacerPasada(i + 1, n);
}

private void ordenarRecursivo(int n) {
    if (n <= 1) {
        return;
    }
    hacerPasada(0, n - 1);
    ordenarRecursivo(n - 1);
}

public void ordenarPorSaldo() {
    ordenarRecursivo(cantidadCuentas);
}

public void transferir(int numeroOrigen, int numeroDestino, double monto) {
    if (numeroOrigen == numeroDestino) {
        throw new IllegalArgumentException("La cuenta de origen y destino no pueden ser la misma");
    }
    CuentaBancaria origen = buscarPorNumero(numeroOrigen);
    CuentaBancaria destino = buscarPorNumero(numeroDestino);

    boolean retiroExitoso = origen.retirar(monto);
    if (!retiroExitoso) {
        throw new IllegalStateException("Fondos insuficientes para realizar la transferencia");
    }

    try {
        destino.depositar(monto);
    } catch (RuntimeException e) {
        origen.Reintegrar(monto);
        throw e;
    }
}

private void aplicarInteresesRecursivo(int indice) {
    if (indice >= cantidadCuentas) {
        return;
    }
    try {
        cuentas[indice].registrarIntereses();
    } catch (Exception e) {
        System.out.println("Error al aplicar intereses en cuenta " + cuentas[indice].getNumero() + ": " + e.getMessage());
    }
    aplicarInteresesRecursivo(indice + 1);
}

public void aplicarInteresesGlobal() {
    aplicarInteresesRecursivo(0);
}

public void girarChequeCuenta(int numero, double monto) {
    CuentaBancaria c = buscarPorNumero(numero);
    if (c instanceof CuentaCheques) {
        ((CuentaCheques) c).girarCheque(monto);
    } else {
        throw new IllegalArgumentException("La cuenta no es de tipo Cheques");
    }
}

private double sumarRecursivo(int indice) {
    if (indice >= cantidadCuentas) {
        return 0.0;
    }
    return cuentas[indice].getSaldo() + sumarRecursivo(indice + 1);
}
private void imprimirCuentasRecursivo(int indice) {
    if (indice >= cantidadCuentas) {
        return;
    }
    cuentas[indice].quienSoy();
    System.out.println(cuentas[indice].toString());
    imprimirCuentasRecursivo(indice + 1);
}
public void imprimirHistorialCuenta(int numero) {
    CuentaBancaria c = buscarPorNumero(numero);
    c.imprimirHistorial();
}


public double saldoTotalDelBanco() {
    return sumarRecursivo(0);
}
public void generarReporte() {
    ordenarPorSaldo();
    imprimirCuentasRecursivo(0);
}
}
