
import java.util.Scanner;


public class main {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Banco banco = new Banco();
    int opcion = 0;
    while (opcion != 9) {
        try {
            System.out.println("\n--- BANCO FINANZAS DEL VALLE ---");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Depositar en una cuenta");
            System.out.println("3. Retirar en una cuenta");
            System.out.println("4. Transferir entre cuentas");
            System.out.println("5. Aplicar intereses a todas las cuentas");
            System.out.println("6. Ver reporte y saldo total");
            System.out.println("7. Ver historial de movimientos");
            System.out.println("8. Girar cheque");
            System.out.println("9. Salir");
            System.out.print("Elija una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.print("Ingrese tipo de cuenta (AHORRO, CHEQUES, PLAZO): ");
                String tipoStr = sc.nextLine().trim().toUpperCase();
                System.out.print("Ingrese número de cuenta: ");
                int num = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese nombre del cliente: ");
                String cliente = sc.nextLine();
                System.out.print("Ingrese moneda: ");
                String moneda = sc.nextLine();

                if (tipoStr.equals("AHORRO")) {
                    CuentaAhorro ca = new CuentaAhorro(num, cliente, moneda);
                    banco.agregarCuenta(ca);
                    System.out.println("Cuenta de Ahorro creada.");
                } else if (tipoStr.equals("CHEQUES")) {
                    System.out.print("Ingrese límite de sobregiro: ");
                    double sobregiro = Double.parseDouble(sc.nextLine());
                    CuentaCheques cc = new CuentaCheques(num, cliente, moneda, sobregiro);
                    banco.agregarCuenta(cc);
                    System.out.println("Cuenta de Cheques creada.");
                } else if (tipoStr.equals("PLAZO")) {
                    CuentaPlazoFijo cpf = new CuentaPlazoFijo(num, cliente, moneda);
                    System.out.print("Ingrese año de vencimiento: ");
                    int anio = Integer.parseInt(sc.nextLine());
                    System.out.print("Ingrese mes de vencimiento (1-12): ");
                    int mes = Integer.parseInt(sc.nextLine());
                    System.out.print("Ingrese día de vencimiento: ");
                    int dia = Integer.parseInt(sc.nextLine());
                    cpf.setPlazo(anio, mes, dia);
                    banco.agregarCuenta(cpf);
                    System.out.println("Cuenta de Plazo Fijo creada.");
                } else {
                    throw new IllegalArgumentException("Tipo de cuenta inválido");
                }
            } else if (opcion == 2) {
                System.out.print("Ingrese número de cuenta: ");
                int num = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese monto a depositar: ");
                double monto = Double.parseDouble(sc.nextLine());
                CuentaBancaria c = banco.buscarPorNumero(num);
                c.depositar(monto);
                System.out.println("Depósito realizado con éxito.");
            } else if (opcion == 3) {
                System.out.print("Ingrese número de cuenta: ");
                int num = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese monto a retirar: ");
                double monto = Double.parseDouble(sc.nextLine());
                CuentaBancaria c = banco.buscarPorNumero(num);
                boolean res = c.retirar(monto);
                if (res) {
                    System.out.println("Retiro realizado con éxito.");
                } else {
                    System.out.println("No se pudo realizar el retiro por fondos insuficientes.");
                }
            } else if (opcion == 4) {
                System.out.print("Ingrese número de cuenta origen: ");
                int origen = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese número de cuenta destino: ");
                int destino = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese monto a transferir: ");
                double monto = Double.parseDouble(sc.nextLine());
                banco.transferir(origen, destino, monto);
                System.out.println("Transferencia realizada con éxito.");
            } else if (opcion == 5) {
                banco.aplicarInteresesGlobal();
                System.out.println("Intereses aplicados a las cuentas.");
            } else if (opcion == 6) {
                System.out.println("\n--- REPORTE DEL BANCO ---");
                banco.generarReporte();
                System.out.println("Saldo total del banco: " + banco.saldoTotalDelBanco());
            } else if (opcion == 7) {
                System.out.print("Ingrese número de cuenta: ");
                int num = Integer.parseInt(sc.nextLine());
                System.out.println("\n--- HISTORIAL DE MOVIMIENTOS ---");
                banco.imprimirHistorialCuenta(num);
            } else if (opcion == 8) {
                System.out.print("Ingrese número de cuenta de cheques: ");
                int num = Integer.parseInt(sc.nextLine());
                System.out.print("Ingrese monto del cheque: ");
                double monto = Double.parseDouble(sc.nextLine());
                banco.girarChequeCuenta(num, monto);
                System.out.println("Cheque girado con éxito.");
            } else if (opcion == 9) {
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error capturado: " + e.getMessage());
        }
    }
    sc.close();
    }
}
