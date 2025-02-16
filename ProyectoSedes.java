//librerias para escaner y para revolver personas 
import java.util.Random;
import java.util.Scanner;

//paso 1: creacion de las clases (fuera del main)

//clase edificio 

class Sede {
    //atributos privados
    private String nombre;
    private int filas;
    private int columnas;
    private Object[][] cubiculos;
    private String jefe;
    //usar atributos privados en public
    public Sede (String nombre, int filas, int columnas, String jefe) {
        this.nombre=nombre;
        this.filas=filas;
        this.columnas=columnas;
        this.cubiculos=new Object[filas][columnas];
        this.jefe=jefe;
    }
    //deficion de los metodos

    //metodo para solicitar oficina 
    public void solicitarOficina(int fila, int columna, Object valor) {
        if (fila>=0 && fila<filas && columna>=0 && columna<columnas) {
            cubiculos[fila][columna]=valor;
            System.out.println("Oficina asignada");
        } else {
            System.out.println("Posicion no encontrada");
        }
    }

    //metodo para mostrar jefe
    public void mostrarJefe() {
        System.out.println("El jefe de la sede es: "+jefe);
    }

    //metodo para mostrar mostrar oficinas 
    public void mostrarOficinas(int fila, int columna) {
        System.out.println("\nOficinas alrededor para (" +fila+ "," +columna+ "):");

        //arriba: 
        if (fila - 1 <0) System.out.println("Arriba: Pared arriba");
        else System.out.println("Arriba: "+(cubiculos[fila-1][columna] != null ? cubiculos[fila-1][columna] : "Vacio"));

        //abajo
        if (fila + 1 >= filas) System.out.println("Abajo: Piso abajo");
        else System.out.println("Abajo: "+(cubiculos[fila+1][columna] != null ? cubiculos[fila+1][columna] : "Vacio"));

        //izquierda
        if (columna - 1 <0) System.out.println("Izquierda: Pared izquierda");
        else System.out.println("Izquierda: "+(cubiculos[fila][columna-1] != null ? cubiculos[fila][columna-1] : "Vacio"));

        //derecha
        if (columna + 1 >= columnas) System.out.println("Derecha: Pared derecha");
        else System.out.println("Derecha: "+(cubiculos[fila][columna+1] != null ? cubiculos[fila][columna+1] : "Vacio"));
    }
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}

// main 

public class ProyectoSedes {

    //creacion de variables para scanner y para generar numero aleatorio
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    
    //creacion de objeto random
    static Object RNG() {
        if (rand.nextBoolean()) {
        return rand.nextInt(100);   
    } else {
        String [] nombres = {"Cesar","Juan","Maria","Luis","Pedro","Miguel","Sofia","Ivan","Sergio","Javier"};
        return nombres[rand.nextInt(nombres.length)];
    }
}

//public static para consola
public static void main(String[] args) {
    //objeto sede 1
    Sede sede1 = new Sede("Sede 1", 4, 6, "Cesar");
    //objeto sede 2
    Sede sede2 = new Sede("Sede 2", 5, 4, "Juan");
    //variable para escoger sede
    Sede sedeActual=null;
    System.out.println("***Seleccione sede***");
    System.out.println("1. Sede 1 (4x6)");
    System.out.println("2. Sede 2 (5x4)");
    System.out.print("Opcion: ");
    int opcion = sc.nextInt();
    sc.nextLine();
    //menu switch para escoger sede
    switch (opcion) {
        case 1: sedeActual = sede1; break;
        case 2: sedeActual = sede2; break;
        default:
            System.out.println("Opcion invalida");
            return;
    }

    //menu switch para opciones y ciclo while mientras se haya seleccionado 1 o 2

    while (true) {
        System.out.println("***Menu***");
        System.out.println("1. Solicitar oficina");
        System.out.println("2. Mostrar jefe");
        System.out.println("3. Mostrar oficinas alrededor");
        System.out.println("4. Salir");
        System.out.print("Opcion: ");
        int nro = sc.nextInt();
        sc.nextLine(); 

    switch(nro) {
        //caso 1: solicitar oficina
        case 1: 
            System.out.print("Ingrese la fila: ");
            int fila = sc.nextInt();
            System.out.print("Ingrese la columna: ");
            int columna = sc.nextInt();
            sc.nextLine();
            //llenar sede manual o automatico
            System.out.println("***Como desea llenar la sede?***");
            System.out.println("1. Llenar sede manualmente");
            System.out.println("2. Llenar sede automaticamente");
            int tipo=sc.nextInt();
            sc.nextLine();

            Object valor=null;
            if (tipo==1) {
                System.out.print("Ingrese el valor (numero o nombre): ");
                String input = sc.nextLine();
                try {
                    valor = Integer.parseInt(input);
                    //conventir el string a int
                } catch (NumberFormatException e) {
                    valor=input;
                    }
                }else {
                    valor=RNG();
                }
                sedeActual.solicitarOficina(fila, columna, valor);
                break;
        case 2:
            sedeActual.mostrarJefe();
            break;
        case 3:
            System.out.print("Ingrese la fila: ");
            int f = sc.nextInt();
            System.out.print("Ingrese la columna: ");
            int c = sc.nextInt();
            if (f>=0 && f<sedeActual.getFilas() && c>=0 && c<sedeActual.getColumnas()) {
                sedeActual.mostrarOficinas(f, c);
            } else {
                System.out.println("Posicion no encontrada");
            }
            break;
        case 4:
            System.out.println("Saliendo...");
            return;
        default:
            System.out.println("Opcion invalida");
        }
        }
        }
        }