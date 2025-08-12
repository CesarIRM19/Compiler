import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AnalexProyecto {
    static String entrada,salida;
	static int c = 0;
	static int Renglon;
	static boolean fin_archivo = false;
	static int filesize;
	static int a_a;
	static int a_i;
	static char[] linea;
	static int COM, EST;
	static String LEX = "";
	static String MITOKEN;
	static int contador = 0;
	static String[] pr = new String[27];
	
	
	public static void rut_error(){
		System.out.println("\n\nERROR Lexicografico("+Renglon+"):  compilacion terminada, en el caracter["+Character.toString(c)+"] !!!!\n");
		System.exit(4);
	}

	public static int lee_car(){
		if(a_a<=filesize-1){
			//System.out.println("Que ocurre?");
			if(linea[a_a]==10){
			Renglon++;
			}
			return(linea[a_a++]);
		}else{
			fin_archivo=true;
			return 255;
		}
	}

	public static String pausa(){
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String nada=null;
		try{
			nada = entrada.readLine();
			return(nada);
		} catch(Exception e) {
			System.err.println(e);
		}
		return("");
	}

	public static char[] abreLeeCierra(String xName){
		File xFile = new File(xName);
		char[] data;
		try{
			FileReader fin = new FileReader(xFile);
			filesize = (int)xFile.length();
			data= new char[filesize+1];
			fin.read(data,0,filesize);
			data[filesize]=' ';
			filesize++;
			System.out.println("leyo los caracteres");
			return(data);
		} catch (FileNotFoundException exc){}
			catch (IOException exc){}
			return null;
	}

	public static boolean creaEscribeArchivo(File xFile, String mensaje){
		try {
			PrintWriter fileOut = new PrintWriter(new FileWriter(xFile, true) );
			fileOut.println(mensaje);
			fileOut.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
	
	public static File xArchivo(String xName){
		File xFile = new File(xName);
		return xFile;
	}
	
	public static String obten_lex(int ini, int fin){
		String x="";
		for(int i=ini; i<fin; i++){
			x = x+linea[i];
		}
		return(x);
	}
    public static void crearPR(){
        

        pr[0]="programa";
		pr[1]="fin_programa";
		pr[2]="rutinas";
		pr[3]="fin_rutinas";
        pr[4]="funcion";
        pr[5]="fin_funcion";
        pr[6]="ciclo_si";
        pr[7]="fin_ciclo";
        pr[8]="verdad";
        pr[9]="haz";
        pr[10]="falso";
        pr[11]="fin_verdad";
        pr[12]="hacer";
        pr[13]="hasta";
        pr[14]="lee";
        
        pr[15]="imprime";
        pr[16]="imprime_ret";
        pr[17]="oo";
        pr[18]="yy";
        pr[19]="nopi";
        pr[20]="div";
        pr[21]="mod";
        pr[22]="abs";
        pr[23]="fin";
        pr[24]="var";
        pr[25]="numero";
        pr[26]="cadena";


    }
	
	public static String reservada (String x){
		for(int i=0; i<pr.length; i++) {
			if(pr[i].equals(x)) {
				return(x);
			}
		}
		return("id");
	}
	
	public static boolean es_digito(int x){
		if ((x>=48 && x<=57)){
			return (true);
		}
		return(false);
	}
	public static boolean es_letra(int x){
		if ((x>=65 && x<=90)||(x>=97 && x<=122)){
			return (true);
		}
		return(false);
	}
	public static boolean es_esp(int x){
		if (x==32 ||x==13||x==10 || x==9){
			return (true);
		}
		return(false);
	}

    public static boolean Any1(int x){
        if (x=='"'||x==9||x==10||x==13||x==255){
            return (false);
            
        }
        return (true);
    }
    
    public static boolean Any2(int x){
        if (x=='"'||x==10||x==13||x=='%'){
            return (false);
            
        }
        return (true);
    }
    public static boolean Any3(int x){
        if (x==10||x==13){
            return (false);
            
        }
        return (true);
    }
    public static boolean Any4(int x){
        if (x=='%'||x==255){
            return (false);
            
        }
        return (true);
    }
    public static int DIAGRAMA(){
		a_a = a_i;
		//System.out.println(COM);
		switch(COM){
			case 0:
				COM = 4;
				break;
			case 4:
				COM = 9;
				break;
			case 9:
				COM = 12;
				break;
			case 12:
				COM = 15;
				break;
			case 15:
				COM = 19;
				break;
			case 19:
				COM = 32;
				break;
			case 32:
				COM = 37;
				break;
			case 37:
				COM = 42;
				break;
			case 42:
				COM = 55;
				break;
			case 55:
				rut_error();
				break;
			default:
			
				//System.out.println("Error");
		}
		return (COM);
	}
    public static String TOKEN (){
		while(true){
			
			switch(EST){
                case 0:
					c = lee_car();
					
					if(es_letra(c)){
						
						EST = 1;
						
					}else{
						
						EST = DIAGRAMA();
						
					}
					break;
				case 1:
                c = lee_car();
				
					if(es_letra(c)||es_digito(c)){
						EST = 1;
						
					}else{
						if(c == '_'){
							EST = 2;
							
						}else{
							EST = 3;
							
						}
					}
				break;
				case 2:
					c = lee_car();
				
					if(es_letra(c)||es_digito(c)){
						EST = 1;
						
					}else{
						EST = DIAGRAMA();
					}
				break;
				case 3:
					a_a--;
					LEX = obten_lex(a_i,a_a);
					a_i = a_a;

					return(reservada(LEX));
                    //return(LEX);



                case 4:
					c = lee_car();
					
					if(es_digito(c)){
						
						EST = 5;
					}else{
						
						EST = DIAGRAMA();
					}
				break;
				case 5:
					c = lee_car();
					
					if(es_digito(c)){
						
						EST = 5;
					}else{
						
						switch (c) {
							case '.':
								EST = 6;
								break;
							default:
								EST = DIAGRAMA();
								break;
						}
					}
				break;
                case 6:
					c = lee_car();
					
					if(es_digito(c)){
						
						EST = 7;
					}else{
						EST = DIAGRAMA();
					}
				break;
                case 7:
					c = lee_car();
					
					if(es_digito(c)){
						
						EST = 7;
					}else{
						EST = 8;
					}
				break;
                case 8:
					a_a--;
					LEX = obten_lex(a_i,a_a);
					a_i = a_a;
					return("num");
                case 9:
					c = lee_car();
					if(es_digito(c)){
						EST = 10;
					}else{
						EST = DIAGRAMA();
					}
				break;
                case 10:
					c = lee_car();
					if(es_digito(c)){
						EST = 10;
					}else{
						EST = 11;
					}
				break;
                case 11:
					a_a--;
					LEX = obten_lex(a_i,a_a);
					a_i = a_a;
					return("num");
                case 12:
					c = lee_car();
					if(es_esp(c)){
						EST = 13;
					}else{
						EST = DIAGRAMA();
					}
				break;
				case 13:
					c = lee_car();
					if(es_esp(c)){
						EST = 13;
					}else{
						EST = 14;
					}
				break;
				case 14:
					a_a--;
					LEX = obten_lex(a_i,a_a);
					a_i = a_a;
					return("nosirve");
                case 15:
                    c = lee_car();
                    if(c=='"'){
                        EST = 16;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 16:
                    c = lee_car();
                    if(Any1(c)){
                        EST = 17;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 17:
                    c = lee_car();
                    if(c=='"'){
                        EST = 18;
                    }else{
                        if (Any1(c)) {
                            EST = 17;
                        }else{
                            EST = DIAGRAMA();
                        }
                    }
                break;
                case 18:
                    
                    LEX = obten_lex(a_i+1,a_a-1);
                    a_i = a_a;
                    return("cad");
                case 19:
                    c = lee_car();
                    switch (c) {
                        case '>':
                            EST = 20;
                            break;
                        case '<':
                            System.err.println("deberia esta aqui en 19");
                            EST = 24;
                            break;
                        case '=':
                        
                            EST = 29;
                            break;
                        default:
                            EST = DIAGRAMA();
                            break;
                    }
                break;
                case 20:
                    c = lee_car();
                    
                    switch (c) {
                        case '=':
                            EST=22;
                            break;
                        case '<':
                            EST=23;
                            break;
                        default:
                            EST = 21;
                            break;
                    }
                break;
                case 21:
                    a_a--;
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return(">");
                case 22:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("mai");
                case 23:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("dif");
                case 24:
                    c = lee_car();
                    switch (c) {
                        case '>':
                            EST = 23;
                            break;
                        case '=':

                            EST = 25;
                            break;
                        default:
                            EST = 28;
                            break;
                    }
                break;
                case 25:
                    c = lee_car();
                    if (c=='>'){
                        EST = 26;
                    }else{
                        EST = 27;
                    }
                break;
                case 26:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("asig");
                case 27:
                    a_a--;
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("mei");
                case 28:
                    //System.err.println("Error:" +a_a+"Apuntador:"+a_i+ "Caracter:"+c);
                    a_a--;
                    LEX = obten_lex(a_i,a_a);
                    //System.err.println("Este es el lexema: "+LEX);
                    a_i = a_a;

                    return("<");
                case 29:
                    c = lee_car();
                    
                    switch (c) {
                        case '<':
                            EST = 30;
                            break;
                        case '>':
                            EST = 22;
                            break;
                        default:
                        //System.err.println("Error");
                            EST = 31;
                            break;
                    }
                break;
                case 30:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("mei");
                case 31:
                    a_a--;
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("=");
                case 32:
                    c = lee_car();
                    if (c=='%'){
                        EST = 33;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 33:
                    c = lee_car();
                    if(c==10||c==13){
                        EST = 34;
                    }else{
                        if (Any2(c)){
                            EST = 35;
                        }else{
                            EST = DIAGRAMA();
                        }
                    
                    }
                break;
                case 34:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("nosirve");
                case 35:
                    c = lee_car();
                    if(c==10||c==13){
                        EST = 36;
                    }else{
                        if (Any3(c)){
                            EST = 35;
                        }else{
                            EST = DIAGRAMA();
                        }
                    }
                break;
                case 36:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("nosirve");
                case 37:
                    c = lee_car();
                    if (c=='%'){
                        EST = 38;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 38:
                    c = lee_car();
                    if (c=='%'){
                        EST = 39;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 39:
                    c = lee_car();
                    if(c=='%'){
                        EST = 40;
                    }else{
                        if (Any4(c)){
                            EST = 39;
                        }else{
                            EST = DIAGRAMA();
                        }
                    }
                break;
                case 40:
                    c = lee_car();
                    if(c=='%'){
                        EST = 41;
                    }else{
                        if (Any4(c)){
                            EST = 39;
                        }else{
                            EST = DIAGRAMA();
                        }
                    }

                break;
                case 41:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("nosirve");
                case 42:
                    c=lee_car();
                    switch (c) {
                        case '{':
                            EST = 43;
                            break;
                        case '}':
                            EST = 44;
                            break;
                        case '(':
                            EST = 45;
                            break;
                        case ')':
                            EST = 46;
                            break;
                        case '[':
                            EST = 47;
                            break;
                        case ']':
                            EST = 48;
                            break;
                        case '+':
                            EST = 49;
                            break;
                        case '-':
                            EST = 50;
                            break;
                        case '*':
                            EST = 51;
                            break;
                        case '/':
                            EST = 52;
                            break;
                        case ':':
                            EST = 53;
                            break;
                        case ';':
                            EST = 54;
                            break;
						case ',':
							EST = 55;
							break;
                        default:
                            EST = DIAGRAMA();
                            break;
                    }
                break;
                case 43:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("{");
                case 44:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("}");

                case 45:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("(");
                case 46:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return(")");
                case 47:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("[");
                case 48:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("]");
                case 49:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("+");
                case 50:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("-");
                case 51:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("*");
                case 52:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("/");
                case 53:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return(":");
                case 54:
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return(";");
                
				case 55:
					LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return(",");
				
                case 56:
                    c = lee_car();
                    if (c==255){
                        EST = 55;
                    }else{
                        EST = DIAGRAMA();
                    }
                break;
                case 57:
                    
                    LEX = obten_lex(a_i,a_a);
                    a_i = a_a;
                    return("nosirve");
                default:
                    rut_error();
                    break;
            }
		}
	}
	
    public static void main(String[] argumento) {
		crearPR();
		



		if (argumento.length == 0){
			System.out.println("ERROR");
			System.exit(4);
		}
		entrada = argumento[0]+".cm";
        System.err.println(entrada);
		if(!xArchivo(entrada).exists()){
			System.out.println("Error, no hay archivo");
			System.exit(4);
		}
		salida = argumento[0]+".cm1";
		linea = abreLeeCierra(entrada);
		
		System.out.println(linea.length);
	
		do{
			EST=0;
			COM=0;
			MITOKEN=TOKEN();
			if (!MITOKEN.equals("nosirve")) {
				System.out.println("Token: "+MITOKEN+"  |  Lex: "+LEX+"  |  Renglon: "+Renglon);
				creaEscribeArchivo(xArchivo(salida), MITOKEN);
				creaEscribeArchivo(xArchivo(salida), LEX);
				creaEscribeArchivo(xArchivo(salida), Renglon+"");
				//creaEscribeArchivo(xArchivo(salida), "--------");
			} 
			//System.out.println("Token: "+MITOKEN+"  |  Lex: "+LEX);
			

			
		}while (!fin_archivo);
		System.out.println("Compilacion Terminada");
        creaEscribeArchivo(xArchivo(salida),"fin");
		creaEscribeArchivo(xArchivo(salida),"fin");
		creaEscribeArchivo(xArchivo(salida),666+"");
	}
}
