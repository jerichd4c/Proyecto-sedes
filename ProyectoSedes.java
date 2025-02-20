//librerias para escaner y para revolver personas 
import java.io.*;
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

    //var para 1. ints 2. strings 3. random
    private int tipoCubiculos;

    //usar atributos privados en public
    public Sede (String nombre, int filas, int columnas, int tipoCubiculos) {
        this.nombre=nombre;
        this.filas=filas;
        this.columnas=columnas;
        this.tipoCubiculos=tipoCubiculos;
        this.cubiculos=new Object[filas][columnas];
        llenarCubiculos();
    }
    //deficion de los metodos

    //metodo para llenar TODOS los cubiculos
    private void llenarCubiculos() {
        String [] nombres = {"Cesar","Juan","Maria","Luis","Pedro","Miguel","Sofia","Ivan","Sergio","Javier"};
        for (int i=0; i<filas; i++) {
            for (int j=0; j<columnas; j++) {
                if (tipoCubiculos==1) {
                    cubiculos[i][j]=rand.nextInt(100);
                } else if (tipoCubiculos==2) {
                    cubiculos[i][j]=nombres[rand.nextInt(nombres.length)];
                } else{
                    //mezcla
                    if (rand.nextBoolean()) {
                    cubiculos[i][j]=rand.nextInt(100);
                } else {
                    cubiculos[i][j]=nombres[rand.nextInt(nombres.length)];
                }
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
     public boolean esJefe(int fila, int columna) {
        return jefeFila == fila && jefeColumna == columna;
    }
    
    //metodo para asignar jefe
    public void asignarJefe(int filaJefe, int columnaJefe) {
        if (posicionValida(filaJefe, columnaJefe)) {
            int fila=convertirIndice(filaJefe);
            int columna=convertirIndice(columnaJefe);
            jefeFila=fila;
            jefeColumna=columna;
            System.out.println("DEBUG: Llamando a asignarJefe con índices usuario: (" 
            + filaJefe + ", " + columnaJefe + ")");
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
       //mostrar posiciones
       System.out.println("Oficinas adyacentes:");
       verPosicion(filaUsuario-1, columnaUsuario, "Arriba: Techo", "Arriba: %s");
       verPosicion(filaUsuario+1, columnaUsuario, "Abajo: Piso", "Abajo: %s");
       verPosicion(filaUsuario, columnaUsuario-1, "Izquierda: Pared", "Izquierda: %s");
       verPosicion(filaUsuario, columnaUsuario+1, "Derecha: Pared", "Derecha: %s");
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
    public Object getValorCubiculo(int filaUsuario, int columnaUsuario) {
        int fila=convertirIndice(filaUsuario);
        int columna=convertirIndice(columnaUsuario);
        System.out.println("DEBUG: getValorCubiculo(" + filaUsuario + "," + columnaUsuario + ") accediendo a [" + fila + "][" + columna + "]. Valor: " + cubiculos[fila][columna]);
        return posicionValida(fila, columna) ? cubiculos[fila][columna] : null; }
    
    //metodo para la logica de posiciones validas
    private boolean posicionValida(int filaUsuario, int columnaUsuario) {
        return filaUsuario >= 0 && filaUsuario <= filas && columnaUsuario >= 0 && columnaUsuario <= columnas; //probar mayor o igual a 0
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

    //metodo para guardar en archivo.txt
    public void guardarArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            //tipo de sede
            writer.write("***" + nombre + "***\n");
            //informacion del jefe
            writer.write("jefe: " );
            if (jefeFila != -1 && jefeColumna != -1) {
                int filaNew=transformarJefe(jefeFila);
                int columnaNew=transformarJefe(jefeColumna);
                    System.out.println("Jefe actual: (" + cubiculos[jefeFila][jefeColumna] + " en posicion (" + filaNew + ", " + columnaNew + "))\n");
                } else {
                    System.out.println("No hay jefe asignado\n");
                }
            //3. Escribir matrices
            writer.write("\n***Informacion de cubiculos***\n");
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    //sumar +1 a indice
                    String posicion = String.format("(%d, %d)", i + 1, j + 1);
                    String contenido = cubiculos[i][j] != null ? cubiculos[i][j].toString() : "(V)";
                    //4. Marcar jefe
                    if (esJefe(i, j)) {contenido += " (J)";
                }
                writer.write(posicion + ": " + contenido + "\n");
            }
            System.out.println("Datos guardados en archivo "+archivo);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }   
    }

    //metodo para saber si el cubiculo es jefe
    public boolean casillaJefe(int filaUsuario, int columnaUsuario) {
        if (!posicionValida(filaUsuario, columnaUsuario)) return false;
        int fila=convertirIndice(filaUsuario);
        int columna=convertirIndice(columnaUsuario);
        System.out.println("DEBUG: isJefeAt(" + filaUsuario + "," + columnaUsuario + ") -> indices convertidos: (" 
        + fila + "," + columna + "), jefe en: (" + jefeFila + "," + jefeColumna + ")");
    return esJefe(fila, columna);
    }

}
// main 

public class ProyectoSedes {

    //creacion de variables para scanner y para generar numero aleatorio
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

//public static para consola
public static void main(String[] args) {
    //escoger tipo de dato
    int tipo;
    do {
    System.out.println("Escoga el tipo de dato para llenar las sedes: ");
    System.out.println("1. Integer");
    System.out.println("2. String");
    System.out.println("3. Mezcla (int y string)");
    System.out.print("Opcion: ");
    while (!sc.hasNextInt()) { // validar que la entrada sea un entero
        System.out.println("Opcion no valida");
        sc.next();
    }
    tipo = sc.nextInt();
    sc.nextLine(); // limpiar el buffer de entrada
    if (tipo <1 || tipo > 3) {
        System.out.println("Opcion no valida");
    }
    } while (tipo <1 || tipo > 3);

    //objeto sede 1
    Sede sede1 = new Sede("Sede 1", 4, 6, tipo);
    //objeto sede 2
    Sede sede2 = new Sede("Sede 2", 5, 4, tipo);
    //variable para escoger sede
    Sede sedeActual=null;
    //creacion de la variable para el archivo
    String archivoNombre;
    //menu para escoger sede
    System.out.println("***Seleccione sede***");
    System.out.println("1. Sede 1 (4x6)");
    System.out.println("2. Sede 2 (5x4)");
    System.out.print("Opcion: ");
    int opcionSede= sc.nextInt();
    if (opcionSede==1) {
        sedeActual=sede1;
        archivoNombre= "sede1.txt";
    } else {
        sedeActual=sede2;
        archivoNombre= "sede2.txt";
    }

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
            case 1: mostrarOficina(sedeActual); 
            break;
            case 2: sedeActual.mostrarMatriz(); 
            break;
            case 3: asignarOficina(sedeActual);
            break;
            case 4: sedeActual.mostrarJefe(); 
            break;
            case 5: asignarJefe(sedeActual); 
            break;
            case 6: sedeActual.eliminarJefe(); 
            break;
            case 7: eliminarEmpleado(sedeActual); 
            break;
            case 8: mostrarAdyacentes(sedeActual); 
            break;
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
        System.out.print("Ingrese el empleado (ingrese '(V)' para dejar la casilla vacia): ");
        String inputEmpleado = sc.next();
        Object empleado;
        if (inputEmpleado.equalsIgnoreCase("(V)")) {
            empleado = null;
        } else {
            try {
                empleado = Integer.parseInt(inputEmpleado);
            } catch (NumberFormatException e) {
                empleado = inputEmpleado;
            }
        }

        Object currentEmpleado= sede.getValorCubiculo(fila, columna);
        // Depurar: imprime el valor actual para verificar qué se almacena
        if (currentEmpleado != null) {
        System.out.println("DEBUG: current = [" + currentEmpleado.toString() + "]");    
        }

        boolean cubiculoOcupado= (currentEmpleado != null && !currentEmpleado.toString().trim().equals("(V)"));
        if (cubiculoOcupado) {
            if (sede.casillaJefe(fila, columna) ) {
                System.out.println("La casilla esta ocupada por el jefe");
                System.out.println("1. Reemplazarlo por empleado normal");
                System.out.println("2. Dejarlo");
                String decision= sc.next();
                if (decision.equals("1")){
                    sede.eliminarJefe();
                } else {
                    return; 
                }
            } else {
            System.out.println("La casilla ya tiene un empleado");
            System.out.println("1. Eliminar empleado");
            System.out.println("2. Dejarlo");
            String decision= sc.next();
            if (decision.equals("1")) {
                sede.eliminarEmpleado(fila, columna);
            } else {
                return;
            }
        }
    }
        sede.asignarOficina(fila, columna, empleado);
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
        Object currentEmpleado= sede.getValorCubiculo(fila, columna);
        if (currentEmpleado == null) {
            System.out.println("El cubiculo esta vacio, no se puede asignar el jefe");
        }
    }

    //metodo para eliminar empleado
    private static void eliminarEmpleado(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();

        Object currentEmpleado= sede.getValorCubiculo(fila, columna);
        // Depurar: imprime el valor actual para verificar qué se almacena
        if (currentEmpleado != null) {
        System.out.println("DEBUG: current = [" + currentEmpleado.toString() + "]");    
        }

        boolean cubiculoOcupado= (currentEmpleado != null && !currentEmpleado.toString().trim().equals("(V)"));
        if (cubiculoOcupado) {
            if (sede.casillaJefe(fila, columna) ) {
                System.out.println("La casilla esta ocupada por el jefe");
                System.out.println("1. Eliminar");
                System.out.println("2. Dejarlo");
                String decision= sc.next();
                if (decision.equals("1")){
                    sede.eliminarJefe();
                    System.out.println("Se elimino el jefe");
                } else {
                //Si no es el jefe, se elimina normalmente
                System.out.println("No se elimino el jefe");
                }
            } else {
                sede.eliminarEmpleado(fila, columna);
            }
        } else {
            System.out.println("La casilla esta vacia");
        }
    }

    //metodo para mostrar adyacentes
    private static void mostrarAdyacentes(Sede sede) {
        System.out.print("Ingrese la fila: ");
        int fila = sc.nextInt();
        System.out.print("Ingrese la columna: ");
        int columna = sc.nextInt();
        sede.mostrarAdyacentes(fila, columna);
    }

    //metodo para convertir indice y que se mantenga con indice real
    private int convertirIndice(int indiceUsuario) {
        return indiceUsuario - 1;
    }

}