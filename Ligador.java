import java.io.*;

public class Ligador {
   static String[] inst = new String[100000];
    static int[] etiqueta = new int[2000];
    static int[] posicion = new int[2000];
    static String entrada;
    static String salida;
    static int contador = 0;
    static int pos = 0;
    static int i_etiqueta = 0;
    static int i_posicion = 0;
    static boolean finArchivo = false;
    
    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }
    public static void lee_archivo(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            
            do {
                linea = br.readLine();
                if (linea != null) {
                    inst[contador] = linea;
                    contador++;
                }
            } while (linea != null);
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    public static boolean creaEscribeArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(xFile, true));
            fileOut.println(mensaje);
            fileOut.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public static boolean es_digito(int x) {
        if (x >= 48 && x <= 57) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        entrada = args[0] + ".cm3";
        salida = args[0] + ".java";
        if (!xArchivo(entrada).exists()) {
            System.out.println("ERROR: El archivo: " + entrada + " no existe");
            System.exit(4);
        }
        for (int i = 0; i < inst.length; i++) {
            inst[i] = "";
        }
        lee_archivo(xArchivo(entrada));
        //contador--;
        //inst[contador] = "";
        for (int i = 0; i < contador; i++) {
            int c = inst[i].charAt(0);
            if (es_digito(c)) {
                int ind = inst[i].indexOf(" ");
                String num = inst[i].substring(0, ind);
                int etiq = Integer.parseInt(num.trim());
                etiqueta[i_etiqueta] = etiq;
                i_etiqueta++;
                posicion[i_posicion] = i;
                i_posicion++;
                String nuevaLinea = inst[i].substring(ind);
                inst[i] = nuevaLinea.trim();
            }
        }
        for (int i = 0; i < contador; i++) {
            if (inst[i].startsWith("mete$")) {
                int ind = inst[i].indexOf("$") + 1;
                String num = inst[i].substring(ind);
                int etq = Integer.parseInt(num.trim());
                for (int j = 0; j < i_etiqueta; j++) {
                    if (etiqueta[j] == etq) {
                        inst[i] = "mete " + posicion[j];
                    }
                }
            }
        }
        creaEscribeArchivo(xArchivo(salida), "import java.io.*;");
        creaEscribeArchivo(xArchivo(salida), "");
        creaEscribeArchivo(xArchivo(salida), "public class " + args[0] + " {");
        creaEscribeArchivo(xArchivo(salida), "  static String inst[] = new String[10000];");
        creaEscribeArchivo(xArchivo(salida), "");
        creaEscribeArchivo(xArchivo(salida), "  public static void main(String[] args) {");
        for (int i = 0; i < contador; i++) {
            creaEscribeArchivo(xArchivo(salida), "      inst[" + i + "] = \"" + inst[i] + "\";");
        }
        creaEscribeArchivo(xArchivo(salida), "      MaqCAPJava.programa(inst, args);");
        creaEscribeArchivo(xArchivo(salida), "  }");
        creaEscribeArchivo(xArchivo(salida), "}");
    }
 }