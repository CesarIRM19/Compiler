package Compiled_Programs;
import java.io.*;

public class MaqCAPJava {

	static String code[]=new String[10000];
	static String data[]=new String[10000];
	static String stack[]=new String[10000];
	static int IP=0;
	static int sp=-1;
	static double a;
	static double b;
	static double c;
	static int d;
	static String T;
	static boolean Trace=false;
	static int traceline=0;
	static int tracedata=0;


	public static int es_entero(String var){
		boolean resultado;
		try {
			return(Integer.parseInt(var));
		} catch (NumberFormatException excepcion) {
			System.out.println("\n\n\7ERROR: El parametro debe ser entero");
			System.exit(4);
		}
		return(-1);
	}

	public static void print_data(int n){
		for(int i=0;i<=n;i++)
			System.out.println("data["+i+"]="+data[i]);
		pausa();
	}
	
	public static void print_stack(){
		for(int i=0;i<=sp;i++)
			System.out.println("PILA["+i+"]="+stack[i]);
		pausa();
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
	
	public static void push_stack(String x){
		if(sp>=9999){
			System.out.println("Error pila llena");
			System.exit(4);
		}
		stack[++sp]=x;
	}

	public static String pop_stack(){
		if(sp<0){
			System.out.println("Error pila vacia");
			System.exit(4);
		}
		return(stack[sp--]);
	}

	static void ejecuta(String INST, String Parm){
		//System.out.println(INST+" "+Parm);
		//pausa();
		switch(INST){
			case "opsum":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a+b;
				push_stack(c+"");
				break;
			case "opdif":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a-b;
				push_stack(c+"");
				break;
			case "opmul":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a*b;
				push_stack(c+"");
				break;
			case "opcoc":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a/b;
				push_stack(c+"");
				break;
			case "opdiv":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a/b;
				d=(int)c;
				push_stack(d+"");
				break;
			case "opmod":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				c=a%b;
				push_stack(c+"");
				break;
			case "opabs":
				a=Double.parseDouble(pop_stack());
				c=Math.abs(a);
				push_stack(c+"");
				break;
			case "asig":
				T=pop_stack();
				b=Double.parseDouble(pop_stack());
				
				d=(int)b;
				data[d]=T;
				break;
			case "mem":
				a=Double.parseDouble(pop_stack());
				d=(int)a;
				push_stack(data[d]);
				break;
			case "lee":
				push_stack(pausa());
				break;
			case "escribe":
				System.out.print(pop_stack());
				break;
			case "escribenl":
				System.out.println(pop_stack());
				break;
			case "saca":
				pop_stack();
				break;
			case "copia":
				if(sp!=-1)
					push_stack(stack[sp]);
				else
					push_stack("0");
				break;
			case "ira":
				a=Double.parseDouble(pop_stack());
				d=(int)a;
				IP=d;
				//System.out.println("se ejecutara la"+IP);
				//pausa();
				break;
			case "irfalso":
				d=Integer.parseInt(pop_stack());
				a=Double.parseDouble(pop_stack());
				if(a==0)
					IP=d;
				break;
			case "irverdad":
				d=Integer.parseInt(pop_stack());
				a=Double.parseDouble(pop_stack());
				if(a!=0)
					IP=d;
				break;
			case "opand":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				if(a!=0&&b!=0)
					push_stack("1");
				else
					push_stack("0");
				break;
			case "opor":
				b=Double.parseDouble(pop_stack());
				a=Double.parseDouble(pop_stack());
				if(a==0&&b==0)
					push_stack("0");
				else
					push_stack("1");
				break;
			case "opnot":
				a=Double.parseDouble(pop_stack());
				if(a!=0)
					push_stack("0");
				else
					push_stack("1");
				break;
			case "meteip":
				push_stack(IP+"");
				break;
			case "fin":
				System.exit(0);
				break;
			case "mete":
				push_stack(Parm);
				break;
			case "ppila":
				for(int i=0;i<=sp;i++)
					System.out.println("stack["+i+"]="+stack[i]);
				break;
			case "pdatos":
				d=Integer.parseInt(Parm);
				for(int i=0;i<=d;i++)
					System.out.println("data["+i+"]="+data[i]);
				break;
			default:
				System.out.println("Instruccion invalida:["+INST+"]");
				System.exit(4);
		}
	}

	static void procesa_parms(String[] parm){
		for(int i=0;i<parm.length;i++){
			switch(i){
				case 0:
					if(!parm[i].equals("trace")){
						System.out.println("Error, debe ser trace");
						System.exit(4);
					}
					Trace=true;
					break;
				case 1:
					traceline=es_entero(parm[i]);
					break;
				case 2:
					tracedata=es_entero(parm[i]);
					break;
			}
		}
	}

	public static void programa(String[] inst, String[] args){
		procesa_parms(args);
		
		while(true){
			int actIP=IP;
			String I[]=inst[IP++].split(" ");
			if(Trace&&IP>=traceline){
				System.out.println((actIP)+" "+inst[actIP]);
				print_stack();
				if(tracedata>0)
					print_data(tracedata);
			}
			
			if(inst[actIP].length()!=I[0].length()){
				String Parm=inst[actIP].substring(I[0].length()+1);
				ejecuta(I[0],Parm);
			} else
				ejecuta(I[0],"");
		}
	}

}
