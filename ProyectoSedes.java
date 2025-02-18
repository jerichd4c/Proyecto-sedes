//librerias para escaner y para revolver personas 
import java.util.Random;
import java.util.Scanner;

//paso 1: creacion de las clases (fuera del main)

//clase Sede

class Sede {
    //atributos privados
    private String nombre;
    private int filas;
    private int columnas;
    private Object[][] cubiculos;
    private int jefeFila= -1;
    private int jefeColumna= -1;
    private Random rand= new Random();
    //usar atributos privados en public
    public Sede (String nombre, int filas, int columnas) {
        this.nombre=nombre;
        this.filas=filas;
        this.columnas=columnas;
        this.cubiculos=new Object[filas][columnas];
        llenarCubiculos();
    }
    //deficion de los metodos

    //metodo para llenar TODOS los cubiculos
    private void llenarCubiculos() {
        String [] nombres = {"Cesar","Juan","Maria","Luis","Pedro","Miguel","Sofia","Ivan","Sergio","Javier"};
        for (int i=0; i<filas; i++) {
            for (int j=0; j<columnas; j++) {
                if (rand.nextBoolean()) {
                    cubiculos[i][j]=rand.nextInt(100);
                } else {
                    cubiculos[i][j]=nombres[rand.nextInt(nombres.length)];
                }
            }
        }
    }

    //metodo para mostrar oficina COMPLETA

    public void mostrarOficina(int filaUsuario, int columnaUsuario) {
        if (posicionValida(filaUsuario, columnaUsuario)) {
           int fila=convertirIndice(filaUsuario);
           int columna=convertirIndice(columnaUsuario);
           Object valor= cubiculos[fila][columna];
           String mensaje = String.format("(%d, %d): %s", filaUsuario, columnaUsuario, valor);
           if (esJefe(fila, columna)) mensaje += " (Jefe)";
           System.out.println(mensaje);
        } else {
            System.out.println("Posicion no encontrada");
        }
    }
               
    //metodo para asignar empleado a oficina 
    public void asignarOficina(int filaUsuario, int columnaUsuario, Object valor) {
        if (posicionValida(filaUsuario, columnaUsuario)) {
            int fila=convertirIndice(filaUsuario);
            int columna=convertirIndice(columnaUsuario);
            cubiculos[fila][columna]=valor;
            System.out.println("Oficina asignada");
        } else {
            System.out.println("Posicion no encontrada");
        }
    }

    //metodo para eliminar empleado
    public void eliminarEmpleado(int filaUsuario, int columnaUsuario) {
        if (posicionValida(filaUsuario, columnaUsuario)) {
            int fila=convertirIndice(filaUsuario);
            int columna=convertirIndice(columnaUsuario);
            cubiculos[fila][columna]=null;
            if (esJefe(fila, columna)) eliminarJefe(); 
            System.out.println("Empleado eliminado");
        } else {
            System.out.println("Posicion no encontrada");
        }
    }

    //metodo para mostrar jefe
    public void mostrarJefe() {
        if (jefeFila != -1 && jefeColumna != -1) {
            int filaNew=transformarJefe(jefeFila);
            int columnaNew=transformarJefe(jefeColumna);
            System.out.println("Jefe actual: (" + cubiculos[jefeFila][jefeColumna] + " en posicion (" + filaNew + ", " + columnaNew + "))");
        } else {
            System.out.println("No hay jefe asignado");
        }
    }

     //metodo para saber si es jefe
     private boolean esJefe(int fila, int columna) {
        return jefeFila == fila && jefeColumna == columna;
    }
    
    //metodo para asignar jefe
    public void asignarJefe(int filaJefe, int columnaJefe) {
        if (posicionValida(filaJefe, columnaJefe)) {
            int fila=convertirIndice(filaJefe);
            int columna=convertirIndice(columnaJefe);
            jefeFila=fila;
            jefeColumna=columna;
            System.out.println("Jefe asignado");
        } else {
            System.out.println("Posicion no encontrada");
        }
    }

    //metodo para eliminar jefe
    public void eliminarJefe() {
        jefeFila=-1;
        jefeColumna=-1;
        System.out.println("Jefe eliminado");
    }
    
    //metodo para mostrar mostrar oficinas adyacentes
    public void mostrarAdyacentes(int filaUsuario, int columnaUsuario) {
       int fila=convertirIndice(filaUsuario);
       int columna=convertirIndice(columnaUsuario);
       //mostrar posiciones
       System.out.println("Oficinas adyacentes:");
       verPosicion(fila-1, columna, "Arriba: Pared", "Arriba: %s");
       verPosicion(fila+1, columna, "Abajo: Piso", "Abajo: %s");
       verPosicion(fila, columna-1, "Izquierda: Pared", "Izquierda: %s");
       verPosicion(fila, columna+1, "Derecha: Piso", "Derecha: %s");
    }

    //metodo para ver las posiciones
    private void verPosicion(int filaUsuario, int columnaUsuario, String noExiste, String existe) {
        if (posicionValida(filaUsuario, columnaUsuario)) {
            int fila=convertirIndice(filaUsuario);
            int columna=convertirIndice(columnaUsuario);
            Object valor= cubiculos[fila][columna];
            System.out.println(String.format(existe, valor));
        } else {
            System.out.println(noExiste);
        }
    }

    //metodo para ver las filas y columnas en public
    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }

    //metodo para obtener el valor de la casilla
    public Object getValorCubiculo(int fila, int columna) { 
        return posicionValida(fila, columna) ? cubiculos[fila][columna] : null; }
    
    //metodo para la logica de posiciones validas
    private boolean posicionValida(int filaUsuario, int columnaUsuario) {
        return filaUsuario >= 1 && filaUsuario <= filas && columnaUsuario >= 1 && columnaUsuario <= columnas;
    }
    
    //metodo para mostrar la oficina completa (matriz)
    public void mostrarMatriz(){
        System.out.println("\n***"+ nombre +"***");
        System.out.println("(J) = Jefe | (V) = Cubiculo vacio\n");
        //imprimir numero de columnas
        System.out.print("        ");
        //columnas
        for (int j=1; j<=columnas; j++) {
            System.out.printf("|  Col %-4d  ", j);
        }
        System.out.println("|");
        String separador=crearSeparador();
        System.out.println(separador);
        //filas
        for (int i=1; i<=filas; i++) {
        System.out.printf("Fila %-2d ", i);
        for (int j=1; j<=columnas; j++) {
            int filaReal= convertirIndice(i);
            int columnaReal= convertirIndice(j);
            Object valor= cubiculos[filaReal][columnaReal];
            String celda= valor != null ? valor.toString() : "(V)";
            if (esJefe(filaReal, columnaReal)) celda += " (J)";
            System.out.printf("|  %-8s  ", celda.length() > 8 ? celda.substring(0, 5) + "..." : celda);
        }
        System.out.println("|");
        System.out.println(separador);
    } 
}

    //metodo para crear el separador dentro de funcion de mostrarMatriz
    private String crearSeparador() {
        StringBuilder sb= new StringBuilder("        +");
        for (int j=0; j<columnas; j++) {
            sb.append("------------+");
        }
    return sb.toString();
    }

    //metodo para convertir indice y que se mantenga con indice real
    private int convertirIndice(int indiceUsuario) {
        return indiceUsuario - 1;
    }

    //metodo para transformar de -1 a 1 en mostrarJefe
    private int transformarJefe(int indiceJefe) {
        return indiceJefe +1;
    }
}

// main 

public class ProyectoSedes {

    //creacion de variables para scanner y para generar numero aleatorio
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

//public static para consola
public static void main(String[] args) {
    //objeto sede 1
    Sede sede1 = new Sede("Sede 1", 4, 6);
    //objeto sede 2
    Sede sede2 = new Sede("Sede 2", 5, 4);
    //variable para escoger sede
    Sede sedeActual=null;
    System.out.println("***Seleccione sede***");
    System.out.println("1. Sede 1 (4x6)");
    System.out.println("2. Sede 2 (5x4)");
    System.out.print("Opcion: ");
    int opcionSede= sc.nextInt();
    sedeActual= (opcionSede==1) ? sede1 : sede2;

    //menu switch para opciones y ciclo while mientras se haya seleccionado 1 o 2

    while (true) {
        System.out.println("***Menu***");
        System.out.println("1. Mostrar cubiculo");
        System.out.println("2. Mostrar oficina completa");
        System.out.println("3. Asignar oficina");
        System.out.println("4. Mostrar jefe");
        System.out.println("5. Asignar jefe");
        System.out.println("6. Eliminar jefe");
        System.out.println("7. Eliminar empleado");
        System.out.println("8. Mostrar oficinas adyacentes");
        System.out.println("9. Salir");
        System.out.print("Opcion: ");
        //se escanea la variable dentro del switch
        switch(sc.nextInt()) {
            case 1: mostrarOficina(sedeActual); break;
            case 2: sedeActual.mostrarMatriz(); break;
            case 3: asignarOficina(sedeActual); break;
            case 4: sedeActual.mostrarJefe(); break;
            case 5: asignarJefe(sedeActual); break;
            case 6: sedeActual.eliminarJefe(); break;
            case 7: eliminarEmpleado(sedeActual); break;
            case 8: mostrarAdyacentes(sedeActual); break;
            //case 8: guardarArchivo(sedeActual); break;
            case 9: System.exit(0);
            default: System.out.println("Opción inválida!");
            }
        }
    }
    //aplicacion de metodos

    //metodo para mostrar cubiculo
    private static void mostrarOficina(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        sede.mostrarOficina(fila, columna);
    }

    //metodo para asignar cubiculo
    private static void asignarOficina(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        System.out.print("Ingrese el empleado: ");
        Object empleado = sc.next();
        if (sede.getValorCubiculo(fila, columna) != null) {
            System.out.println("La casilla ya tiene un empleado");
            System.out.println("1. Eliminar empleado");
            System.out.println("2. Dejarlo");
            if (sc.next().equalsIgnoreCase("1")) {
                sede.eliminarEmpleado(fila, columna);
            }
        }
        System.out.println("Valor manual o aleatorio (m/a)?");
        Object valor= sc.next().equalsIgnoreCase("m") ? leerValorManual() : generarValorAleatorio();
        sede.asignarOficina(fila, columna, valor);
    }
    //metodo para asignar valor manual o aleatorio dentro del metodo asignar cubiculo
    
    //manual
    private static Object leerValorManual() {
        System.out.print("Ingrese el empleado(numero/texto): ");
        sc.nextLine();
        String input= sc.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return input;
        }
    }
    //aleatorio
    private static Object generarValorAleatorio() {
        return rand.nextBoolean() ? rand.nextInt(100) :
        new String[]{"Juan", "Maria", "Pedro", "Luis", "Miguel", "Sofia", "Ivan", "Sergio", "Javier"}[rand.nextInt(9)];
    }
    
    //metodo para asignar jefe 
    private static void asignarJefe(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        sede.asignarJefe(fila, columna);
    }

    //metodo para eliminar empleado
    private static void eliminarEmpleado(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        sede.eliminarEmpleado(fila, columna);
    }
    //metodo para mostrar adyacentes
    private static void mostrarAdyacentes(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        sede.mostrarAdyacentes(fila, columna);
    }
}