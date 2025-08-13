package Source_files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;



public class SLR1 {

    static String E0, E1, E2; 
    static int contetq = 0;

	static int[] E = new int[200];
	static int[] direcciones = new int[200];
	static int eCount = 0;

    static int Posicion = 0;
    static String a, LEX, RENGLON, Entrada;
    static String pila[] = new String[9999];
    static int tope = -1;
    static String t[] = new String[50];
    static String nt[] = new String[31];
    static int m[][] = new int[162][81];
    static String pi[] = new String[76];
    static int lpd[] = new int[76];
    static String S;
    static int e;

	static String[] ID=new String[200];
	static String[] TIPO=new String[200];
	static String[] OBJ=new String[200];
	static String[] DIRMEM=new String[200];
	static String[] TAM=new String[200];
	static String[] ETQ=new String[200];
	static String[] RUT=new String[200];
	static int isym=-1; 


	static String VAL;
	static String LONG;
	static String IDX;
	static String DIR;
	static String ID_F;
	static String TIPO_F;
	static String DIRIDX;
	static int dirmem=0;
	static String Tmp1;
	static int contentq=-1;

	static String TIPO_t;
	static String PPAL_c;
	static String SFUN_c;
	static  String[] BLQF_c = new String[200];
	static String[] BLQ_c = new String[200];
	static int iBLQ_cc =-1;
	static String[] INST_c = new String[50];
	static int iINSTc =-1;
	static String[] ASIG_c = new String[50];
	static int iASIGc =-1;
	static String[] VASIG_c = new String[50];
	static int iVASIGc =-1;
	static String[] VASIG_t = new String[50];
	static int iVASIGt =-1;
	static String[] VARL_c = new String[50];
	static int iVARLc =-1;
	static String[] F_c = new String[50];
	static int iFc=-1;
	static String[] F_t = new String[50];
	static int iFt=-1;
	static String[] T_c = new String[50];
	static int iTc=-1;
	static String[] T_t = new String[50];
	static int iTt=-1;
	static String[] E_c = new String[50];
	static int iEc=-1;
	static String[] E_t = new String[50];
	static int iEt=-1;
	static String[] N_c = new String[50];
	static int iNc=-1;
	static String[] N_t = new String[50];
	static int iNt=-1;
	static String[] L_c = new String[50];
	static int iLc=-1;
	static String[] L_t = new String[50];
	static int iLt=-1;
	static String[] K_c = new String[50];
	static int iKc=-1;
	static String[] K_t = new String[50];
	static int iKt=-1;
	static String[] IF_c = new String[50];
	static int iIFc=-1;
	static String[] W_c = new String[50];
	static int iWc=-1;
	static String[] REP_c = new String[50];
	static int iREPc=-1;
	static String[] LEE_c = new String[50];
	static int iLEEc=-1;
	static String[] ESC_c = new String[50];
	static int iESCc=-1;

	

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

	public static void guardarEnArchivo(String contenido, String nombreArchivo) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
			writer.write(contenido);
		} catch (IOException e) {
			System.out.println("Error al guardar el archivo: " + e.getMessage());
		}
	}

	public static void preProcesador() {
        String salir = "";
		String atri = "";
		boolean existe = false;

        while (!salir.equals("fin")) {

            lee_token(xArchivo(Entrada));
            salir = LEX;
			if (a.equals("rutina")) {
				existe = true;
			}else{
				if (a.equals("funcion")) {
					lee_token(xArchivo(Entrada));
					if (a.equals("numero") || a.equals("cadena")) {
						TIPO_F = LEX;
						lee_token(xArchivo(Entrada));
						if (a.equals("id")) {
							ID_F = LEX;
							//lee_token(xArchivo(Entrada));
							while (!a.equals(")")) {
								lee_token(xArchivo(Entrada));					

								if (a.equals("numero") || a.equals("cadena")){
									atri += "," + LEX;
								}
								//pausa();

							}
							inserta(ID_F, TIPO_F, "fun", (dirmem++) + "", "", "", atri);
							
							
						} else {
							System.out.println("\n\n\7ERROR: Funcion mal declarada => \" " + LEX + " \""
									+ " en el renglon [ " + RENGLON + " ]");
							System.exit(4);
						}
					} else {
						System.out.println("\n\n\7ERROR: Funcion mal declarada => \" " + LEX + " \"" + " en el renglon [ "
								+ RENGLON + " ]");
						System.exit(4);
					}

				}
			}
            

            if (salir.equals("fin_rutinas")) {
				
                print_tabla();
				System.out.println(salir);
                System.out.println("\nPreprocesamiento terminado\n");
                Posicion = 0;
				//ejecucionFuncion(ID_F,TIPO_F,Posicion);
				//pausa();
                break;
            }
			if (salir.equals("fin") && existe) {
				System.out.println("\n\n\7ERROR: fin_rutinas faltante o mal redactado en el RENGLON [ " + RENGLON + " ]");
				System.exit(4);
			}
        }
		System.out.println("\nPreprocesamiento terminado\n");
        Posicion = 0;
    }

    public static void rut_error() {
        System.out.println(
                "\n\nERROR Sintactico(" + RENGLON + "):  compilacion terminada, en el token[" + a + "] !!!!\n");
        System.exit(4);
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
            return (nada);
        } catch (Exception e) {
            System.err.println(e);
        }
        return ("");
    }

    public static void print_pila() {
        for (int i = 0; i <= tope; i++) {
            System.out.println(pila[i] + " ");
        }
        System.out.println(" ");
    }

    public static void lee_token(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
			
            Posicion = Posicion + linea.length() + 2;
            a = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
            // System.out.print(".");
        } catch (IOException e) {
            System.out.println("Errorsote");
        }
    }

    public static void push(String x) {
        if (tope >= 99999) {
            System.out.println("Error pila llena");
            System.exit(4);
        }
        if (!x.equals("epsilon")) {
            pila[++tope] = x;
        }
    }

    public static String pop() {
        if (tope < 0) {
            System.out.println("Error pila vacia");
            System.exit(4);
        }
        return (pila[tope--]);
    }

    public static int esterm(String x) {
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals(x)) {
                return i;
            }
        }
        return (-1);
    }

    public static int esnt(String x) {
        for (int i = 0; i < nt.length; i++) {
            if (nt[i].equals(x)) {
                return (i + t.length);
            }
        }
        return (-1);
    }

    public static void crearTabla() {
        t[0]="{";
		t[1]="}";
		t[2]="var";
		t[3]=":";
		t[4]="numero";
		t[5]="cadena";
		t[6]=",";
		t[7]="id";
		t[8]="[";
		t[9]="num";
		t[10]="]";
		t[11]="programa";
		t[12]="fin_programa";
		t[13]="rutinas";
		t[14]="fin_rutinas";
		t[15]=";";
		t[16]="funcion";
		t[17]="(";
		t[18]=")";
		t[19]="fin_funcion";
		t[20]="ciclo_si";
		t[21]="fin_ciclo";
		t[22]="verdad";
		t[23]="haz";
		t[24]="falso";
		t[25]="fin_verdad";
		t[26]="hacer";
		t[27]="hasta";
		t[28]="lee";
		t[29]="asig";
		t[30]="imprime";
		t[31]="imprime_ret";
		t[32]="oo";
		t[33]="yy";
		t[34]="nopi";
		t[35]="=";
		t[36]="dif";
		t[37]="mai";
		t[38]="mei";
		t[39]=">";
		t[40]="<";
		t[41]="+";
		t[42]="-";
		t[43]="*";
		t[44]="/";
		t[45]="div";
		t[46]="mod";
		t[47]="abs";
		t[48]="cad";
		t[49]="fin";



		nt[0]="PROGP";
		nt[1]="PROG";
		nt[2]="SDAT";
		nt[3]="LDATOS";
		nt[4]="DEC";
		nt[5]="TIPO";
		nt[6]="LV";
		nt[7]="VAR";
		nt[8]="PPAL";
		nt[9]="SFUN";
		nt[10]="LFUN";
		nt[11]="BLQ";
		nt[12]="FUN";
		nt[13]="PRM";
		nt[14]="INST";
		nt[15]="W";
		nt[16]="IF";
		nt[17]="REP";
		nt[18]="LEE";
		nt[19]="VARL";
		nt[20]="ASIG";
		nt[21]="VASIG";
		nt[22]="ESC";
		nt[23]="K";
		nt[24]="L";
		nt[25]="N";
		nt[26]="E";
		nt[27]="T";
		nt[28]="F";
		nt[29]="NFUN";
		nt[30]="ARGS";




		pi[0]="PROGP";
		pi[1]="PROG";
		pi[2]="SDAT";
		pi[3]="SDAT";
		pi[4]="LDATOS";
		pi[5]="LDATOS";
		pi[6]="DEC";
		pi[7]="TIPO";
		pi[8]="TIPO";
		pi[9]="LV";
		pi[10]="LV";
		pi[11]="VAR";
		pi[12]="VAR";
		pi[13]="PPAL";
		pi[14]="SFUN";
		pi[15]="SFUN";
		pi[16]="LFUN";
		pi[17]="LFUN";
		pi[18]="BLQ";
		pi[19]="BLQ";
		pi[20]="FUN";
		pi[21]="PRM";
		pi[22]="PRM";
		pi[23]="PRM";
		pi[24]="INST";
		pi[25]="INST";
		pi[26]="INST";
		pi[27]="INST";
		pi[28]="INST";
		pi[29]="INST";
		pi[30]="W";
		pi[31]="IF";
		pi[32]="IF";
		pi[33]="REP";
		pi[34]="LEE";
		pi[35]="VARL";
		pi[36]="VARL";
		pi[37]="VARL";
		pi[38]="ASIG";
		pi[39]="VASIG";
		pi[40]="VASIG";
		pi[41]="VASIG";
		pi[42]="ESC";
		pi[43]="ESC";
		pi[44]="K";
		pi[45]="K";
		pi[46]="K";
		pi[47]="K";
		pi[48]="L";
		pi[49]="L";
		pi[50]="L";
		pi[51]="L";
		pi[52]="L";
		pi[53]="L";
		pi[54]="L";
		pi[55]="N";
		pi[56]="E";
		pi[57]="E";
		pi[58]="E";
		pi[59]="T";
		pi[60]="T";
		pi[61]="T";
		pi[62]="T";
		pi[63]="T";
		pi[64]="F";
		pi[65]="F";
		pi[66]="F";
		pi[67]="F";
		pi[68]="F";
		pi[69]="F";
		pi[70]="F";
		pi[71]="F";
		pi[72]="NFUN";
		pi[73]="ARGS";
		pi[74]="ARGS";
		pi[75]="ARGS";






		lpd[0]=1;
		lpd[1]=3;
		lpd[2]=3;
		lpd[3]=0;
		lpd[4]=1;
		lpd[5]=2;
		lpd[6]=4;
		lpd[7]=1;
		lpd[8]=1;
		lpd[9]=1;
		lpd[10]=3;
		lpd[11]=1;
		lpd[12]=4;
		lpd[13]=3;
		lpd[14]=3;
		lpd[15]=0;
		lpd[16]=1;
		lpd[17]=2;
		lpd[18]=3;
		lpd[19]=2;
		lpd[20]=8;
		lpd[21]=4;
		lpd[22]=2;
		lpd[23]=0;
		lpd[24]=1;
		lpd[25]=1;
		lpd[26]=1;
		lpd[27]=1;
		lpd[28]=1;
		lpd[29]=1;
		lpd[30]=6;
		lpd[31]=9;
		lpd[32]=7;
		lpd[33]=6;
		lpd[34]=4;
		lpd[35]=1;
		lpd[36]=4;
		lpd[37]=4;
		lpd[38]=3;
		lpd[39]=1;
		lpd[40]=4;
		lpd[41]=4;
		lpd[42]=4;
		lpd[43]=4;
		lpd[44]=3;
		lpd[45]=3;
		lpd[46]=4;
		lpd[47]=1;
		lpd[48]=3;
		lpd[49]=3;
		lpd[50]=3;
		lpd[51]=3;
		lpd[52]=3;
		lpd[53]=3;
		lpd[54]=1;
		lpd[55]=1;
		lpd[56]=3;
		lpd[57]=3;
		lpd[58]=1;
		lpd[59]=3;
		lpd[60]=3;
		lpd[61]=3;
		lpd[62]=3;
		lpd[63]=1;
		lpd[64]=3;
		lpd[65]=4;
		lpd[66]=1;
		lpd[67]=4;
		lpd[68]=4;
		lpd[69]=1;
		lpd[70]=1;
		lpd[71]=4;
		lpd[72]=1;
		lpd[73]=1;
		lpd[74]=3;
		lpd[75]=0;

        for (int i = 0; i < 162; i++) {
            for (int j = 0; j < t.length + nt.length; j++)
                m[i][j] = 0;
        }

        m[0][0]=3;
		m[2][11]=5;
		m[3][2]=8;
		m[4][13]=10;
		m[5][22]=20;
		m[5][20]=21;
		m[5][26]=22;
		m[5][28]=23;
		m[5][30]=24;
		m[5][31]=25;
		m[5][7]=26;
		m[6][1]=27;
		m[7][2]=8;
		m[8][7]=31;
		m[10][16]=34;
		m[11][12]=35;
		m[11][22]=20;
		m[11][20]=21;
		m[11][26]=22;
		m[11][28]=23;
		m[11][30]=24;
		m[11][31]=25;
		m[11][7]=26;
		m[12][15]=37;
		m[19][29]=38;
		m[20][17]=39;
		m[21][17]=40;
		m[22][22]=20;
		m[22][20]=21;
		m[22][26]=22;
		m[22][28]=23;
		m[22][30]=24;
		m[22][31]=25;
		m[22][7]=26;
		m[23][17]=42;
		m[24][17]=43;
		m[25][17]=44;
		m[26][8]=45;
		m[29][3]=46;
		m[30][6]=47;
		m[31][8]=48;
		m[32][14]=49;
		m[33][16]=34;
		m[34][4]=52;
		m[34][5]=53;
		m[36][15]=54;
		m[38][34]=56;
		m[38][17]=62;
		m[38][47]=63;
		m[38][7]=64;
		m[38][9]=65;
		m[38][48]=66;
		m[39][34]=56;
		m[39][17]=62;
		m[39][47]=63;
		m[39][7]=64;
		m[39][9]=65;
		m[39][48]=66;
		m[40][34]=56;
		m[40][17]=62;
		m[40][47]=63;
		m[40][7]=64;
		m[40][9]=65;
		m[40][48]=66;
		m[41][27]=70;
		m[41][22]=20;
		m[41][20]=21;
		m[41][26]=22;
		m[41][28]=23;
		m[41][30]=24;
		m[41][31]=25;
		m[41][7]=26;
		m[42][7]=72;
		m[43][34]=56;
		m[43][17]=62;
		m[43][47]=63;
		m[43][7]=64;
		m[43][9]=65;
		m[43][48]=66;
		m[44][34]=56;
		m[44][17]=62;
		m[44][47]=63;
		m[44][7]=64;
		m[44][9]=65;
		m[44][48]=66;
		m[45][9]=75;
		m[45][7]=76;
		m[46][4]=52;
		m[46][5]=53;
		m[47][7]=31;
		m[48][9]=79;
		m[51][7]=80;
		m[55][32]=81;
		m[55][33]=82;
		m[56][17]=83;
		m[57][35]=84;
		m[57][36]=85;
		m[57][37]=86;
		m[57][38]=87;
		m[57][39]=88;
		m[57][40]=89;
		m[59][41]=90;
		m[59][42]=91;
		m[60][43]=92;
		m[60][44]=93;
		m[60][45]=94;
		m[60][46]=95;
		m[62][34]=56;
		m[62][17]=62;
		m[62][47]=63;
		m[62][7]=64;
		m[62][9]=65;
		m[62][48]=66;
		m[63][17]=97;
		m[64][8]=98;
		m[67][17]=99;
		m[68][18]=100;
		m[68][32]=81;
		m[68][33]=82;
		m[69][18]=101;
		m[69][32]=81;
		m[69][33]=82;
		m[70][17]=102;
		m[71][18]=103;
		m[72][8]=104;
		m[73][18]=105;
		m[73][32]=81;
		m[73][33]=82;
		m[74][18]=106;
		m[74][32]=81;
		m[74][33]=82;
		m[75][10]=107;
		m[76][10]=108;
		m[79][10]=109;
		m[80][17]=110;
		m[81][17]=62;
		m[81][47]=63;
		m[81][7]=64;
		m[81][9]=65;
		m[81][48]=66;
		m[82][17]=62;
		m[82][47]=63;
		m[82][7]=64;
		m[82][9]=65;
		m[82][48]=66;
		m[83][34]=56;
		m[83][17]=62;
		m[83][47]=63;
		m[83][7]=64;
		m[83][9]=65;
		m[83][48]=66;
		m[84][17]=62;
		m[84][47]=63;
		m[84][7]=64;
		m[84][9]=65;
		m[84][48]=66;
		m[85][17]=62;
		m[85][47]=63;
		m[85][7]=64;
		m[85][9]=65;
		m[85][48]=66;
		m[86][17]=62;
		m[86][47]=63;
		m[86][7]=64;
		m[86][9]=65;
		m[86][48]=66;
		m[87][17]=62;
		m[87][47]=63;
		m[87][7]=64;
		m[87][9]=65;
		m[87][48]=66;
		m[88][17]=62;
		m[88][47]=63;
		m[88][7]=64;
		m[88][9]=65;
		m[88][48]=66;
		m[89][17]=62;
		m[89][47]=63;
		m[89][7]=64;
		m[89][9]=65;
		m[89][48]=66;
		m[90][17]=62;
		m[90][47]=63;
		m[90][7]=64;
		m[90][9]=65;
		m[90][48]=66;
		m[91][17]=62;
		m[91][47]=63;
		m[91][7]=64;
		m[91][9]=65;
		m[91][48]=66;
		m[92][17]=62;
		m[92][47]=63;
		m[92][7]=64;
		m[92][9]=65;
		m[92][48]=66;
		m[93][17]=62;
		m[93][47]=63;
		m[93][7]=64;
		m[93][9]=65;
		m[93][48]=66;
		m[94][17]=62;
		m[94][47]=63;
		m[94][7]=64;
		m[94][9]=65;
		m[94][48]=66;
		m[95][17]=62;
		m[95][47]=63;
		m[95][7]=64;
		m[95][9]=65;
		m[95][48]=66;
		m[96][18]=126;
		m[96][32]=81;
		m[96][33]=82;
		m[97][34]=56;
		m[97][17]=62;
		m[97][47]=63;
		m[97][7]=64;
		m[97][9]=65;
		m[97][48]=66;
		m[98][9]=128;
		m[98][7]=129;
		m[99][34]=56;
		m[99][17]=62;
		m[99][47]=63;
		m[99][7]=64;
		m[99][9]=65;
		m[99][48]=66;
		m[100][23]=132;
		m[101][22]=20;
		m[101][20]=21;
		m[101][26]=22;
		m[101][28]=23;
		m[101][30]=24;
		m[101][31]=25;
		m[101][7]=26;
		m[102][34]=56;
		m[102][17]=62;
		m[102][47]=63;
		m[102][7]=64;
		m[102][9]=65;
		m[102][48]=66;
		m[104][9]=135;
		m[104][7]=136;
		m[110][4]=52;
		m[110][5]=53;
		m[111][35]=84;
		m[111][36]=85;
		m[111][37]=86;
		m[111][38]=87;
		m[111][39]=88;
		m[111][40]=89;
		m[112][35]=84;
		m[112][36]=85;
		m[112][37]=86;
		m[112][38]=87;
		m[112][39]=88;
		m[112][40]=89;
		m[113][18]=139;
		m[113][32]=81;
		m[113][33]=82;
		m[114][41]=90;
		m[114][42]=91;
		m[115][41]=90;
		m[115][42]=91;
		m[116][41]=90;
		m[116][42]=91;
		m[117][41]=90;
		m[117][42]=91;
		m[118][41]=90;
		m[118][42]=91;
		m[119][41]=90;
		m[119][42]=91;
		m[120][43]=92;
		m[120][44]=93;
		m[120][45]=94;
		m[120][46]=95;
		m[121][43]=92;
		m[121][44]=93;
		m[121][45]=94;
		m[121][46]=95;
		m[127][18]=140;
		m[127][32]=81;
		m[127][33]=82;
		m[128][10]=141;
		m[129][10]=142;
		m[130][18]=143;
		m[130][6]=144;
		m[131][32]=81;
		m[131][33]=82;
		m[132][22]=20;
		m[132][20]=21;
		m[132][26]=22;
		m[132][28]=23;
		m[132][30]=24;
		m[132][31]=25;
		m[132][7]=26;
		m[133][21]=146;
		m[133][22]=20;
		m[133][20]=21;
		m[133][26]=22;
		m[133][28]=23;
		m[133][30]=24;
		m[133][31]=25;
		m[133][7]=26;
		m[134][18]=147;
		m[134][32]=81;
		m[134][33]=82;
		m[135][10]=148;
		m[136][10]=149;
		m[137][18]=150;
		m[137][6]=151;
		m[138][7]=152;
		m[144][34]=56;
		m[144][17]=62;
		m[144][47]=63;
		m[144][7]=64;
		m[144][9]=65;
		m[144][48]=66;
		m[145][24]=154;
		m[145][22]=20;
		m[145][20]=21;
		m[145][26]=22;
		m[145][28]=23;
		m[145][30]=24;
		m[145][31]=25;
		m[145][7]=26;
		m[145][25]=155;
		m[150][22]=20;
		m[150][20]=21;
		m[150][26]=22;
		m[150][28]=23;
		m[150][30]=24;
		m[150][31]=25;
		m[150][7]=26;
		m[151][4]=52;
		m[151][5]=53;
		m[153][32]=81;
		m[153][33]=82;
		m[154][22]=20;
		m[154][20]=21;
		m[154][26]=22;
		m[154][28]=23;
		m[154][30]=24;
		m[154][31]=25;
		m[154][7]=26;
		m[156][19]=159;
		m[156][22]=20;
		m[156][20]=21;
		m[156][26]=22;
		m[156][28]=23;
		m[156][30]=24;
		m[156][31]=25;
		m[156][7]=26;
		m[157][7]=160;
		m[158][25]=161;
		m[158][22]=20;
		m[158][20]=21;
		m[158][26]=22;
		m[158][28]=23;
		m[158][30]=24;
		m[158][31]=25;
		m[158][7]=26;
		m[0][51]=1;
		m[0][52]=2;
		m[2][58]=4;
		m[3][53]=6;
		m[3][54]=7;
		m[4][59]=9;
		m[5][61]=11;
		m[5][64]=12;
		m[5][70]=13;
		m[5][66]=14;
		m[5][65]=15;
		m[5][67]=16;
		m[5][68]=17;
		m[5][72]=18;
		m[5][71]=19;
		m[7][53]=28;
		m[7][54]=7;
		m[8][56]=29;
		m[8][57]=30;
		m[10][60]=32;
		m[10][62]=33;
		m[11][64]=36;
		m[11][70]=13;
		m[11][66]=14;
		m[11][65]=15;
		m[11][67]=16;
		m[11][68]=17;
		m[11][72]=18;
		m[11][71]=19;
		m[22][61]=41;
		m[22][64]=12;
		m[22][70]=13;
		m[22][66]=14;
		m[22][65]=15;
		m[22][67]=16;
		m[22][68]=17;
		m[22][72]=18;
		m[22][71]=19;
		m[33][60]=50;
		m[33][62]=33;
		m[34][55]=51;
		m[38][73]=55;
		m[38][74]=57;
		m[38][75]=58;
		m[38][76]=59;
		m[38][77]=60;
		m[38][78]=61;
		m[38][79]=67;
		m[39][73]=68;
		m[39][74]=57;
		m[39][75]=58;
		m[39][76]=59;
		m[39][77]=60;
		m[39][78]=61;
		m[39][79]=67;
		m[40][73]=69;
		m[40][74]=57;
		m[40][75]=58;
		m[40][76]=59;
		m[40][77]=60;
		m[40][78]=61;
		m[40][79]=67;
		m[41][64]=36;
		m[41][70]=13;
		m[41][66]=14;
		m[41][65]=15;
		m[41][67]=16;
		m[41][68]=17;
		m[41][72]=18;
		m[41][71]=19;
		m[42][69]=71;
		m[43][73]=73;
		m[43][74]=57;
		m[43][75]=58;
		m[43][76]=59;
		m[43][77]=60;
		m[43][78]=61;
		m[43][79]=67;
		m[44][73]=74;
		m[44][74]=57;
		m[44][75]=58;
		m[44][76]=59;
		m[44][77]=60;
		m[44][78]=61;
		m[44][79]=67;
		m[46][55]=77;
		m[47][56]=78;
		m[47][57]=30;
		m[62][73]=96;
		m[62][74]=57;
		m[62][75]=58;
		m[62][76]=59;
		m[62][77]=60;
		m[62][78]=61;
		m[62][79]=67;
		m[81][74]=111;
		m[81][75]=58;
		m[81][76]=59;
		m[81][77]=60;
		m[81][78]=61;
		m[81][79]=67;
		m[82][74]=112;
		m[82][75]=58;
		m[82][76]=59;
		m[82][77]=60;
		m[82][78]=61;
		m[82][79]=67;
		m[83][73]=113;
		m[83][74]=57;
		m[83][75]=58;
		m[83][76]=59;
		m[83][77]=60;
		m[83][78]=61;
		m[83][79]=67;
		m[84][76]=114;
		m[84][77]=60;
		m[84][78]=61;
		m[84][79]=67;
		m[85][76]=115;
		m[85][77]=60;
		m[85][78]=61;
		m[85][79]=67;
		m[86][76]=116;
		m[86][77]=60;
		m[86][78]=61;
		m[86][79]=67;
		m[87][76]=117;
		m[87][77]=60;
		m[87][78]=61;
		m[87][79]=67;
		m[88][76]=118;
		m[88][77]=60;
		m[88][78]=61;
		m[88][79]=67;
		m[89][76]=119;
		m[89][77]=60;
		m[89][78]=61;
		m[89][79]=67;
		m[90][77]=120;
		m[90][78]=61;
		m[90][79]=67;
		m[91][77]=121;
		m[91][78]=61;
		m[91][79]=67;
		m[92][78]=122;
		m[92][79]=67;
		m[93][78]=123;
		m[93][79]=67;
		m[94][78]=124;
		m[94][79]=67;
		m[95][78]=125;
		m[95][79]=67;
		m[97][73]=127;
		m[97][74]=57;
		m[97][75]=58;
		m[97][76]=59;
		m[97][77]=60;
		m[97][78]=61;
		m[97][79]=67;
		m[99][80]=130;
		m[99][73]=131;
		m[99][74]=57;
		m[99][75]=58;
		m[99][76]=59;
		m[99][77]=60;
		m[99][78]=61;
		m[99][79]=67;
		m[101][61]=133;
		m[101][64]=12;
		m[101][70]=13;
		m[101][66]=14;
		m[101][65]=15;
		m[101][67]=16;
		m[101][68]=17;
		m[101][72]=18;
		m[101][71]=19;
		m[102][73]=134;
		m[102][74]=57;
		m[102][75]=58;
		m[102][76]=59;
		m[102][77]=60;
		m[102][78]=61;
		m[102][79]=67;
		m[110][63]=137;
		m[110][55]=138;
		m[132][61]=145;
		m[132][64]=12;
		m[132][70]=13;
		m[132][66]=14;
		m[132][65]=15;
		m[132][67]=16;
		m[132][68]=17;
		m[132][72]=18;
		m[132][71]=19;
		m[133][64]=36;
		m[133][70]=13;
		m[133][66]=14;
		m[133][65]=15;
		m[133][67]=16;
		m[133][68]=17;
		m[133][72]=18;
		m[133][71]=19;
		m[144][73]=153;
		m[144][74]=57;
		m[144][75]=58;
		m[144][76]=59;
		m[144][77]=60;
		m[144][78]=61;
		m[144][79]=67;
		m[145][64]=36;
		m[145][70]=13;
		m[145][66]=14;
		m[145][65]=15;
		m[145][67]=16;
		m[145][68]=17;
		m[145][72]=18;
		m[145][71]=19;
		m[150][61]=156;
		m[150][64]=12;
		m[150][70]=13;
		m[150][66]=14;
		m[150][65]=15;
		m[150][67]=16;
		m[150][68]=17;
		m[150][72]=18;
		m[150][71]=19;
		m[151][55]=157;
		m[154][61]=158;
		m[154][64]=12;
		m[154][70]=13;
		m[154][66]=14;
		m[154][65]=15;
		m[154][67]=16;
		m[154][68]=17;
		m[154][72]=18;
		m[154][71]=19;
		m[156][64]=36;
		m[156][70]=13;
		m[156][66]=14;
		m[156][65]=15;
		m[156][67]=16;
		m[156][68]=17;
		m[156][72]=18;
		m[156][71]=19;
		m[158][64]=36;
		m[158][70]=13;
		m[158][66]=14;
		m[158][65]=15;
		m[158][67]=16;
		m[158][68]=17;
		m[158][72]=18;
		m[158][71]=19;
		m[0][11]=-3;
		m[1][49]=666;
		m[4][49]=-15;
		m[7][1]=-4;
		m[9][49]=-1;
		m[10][49]=666;
		m[13][15]=-24;
		m[14][15]=-25;
		m[15][15]=-26;
		m[16][15]=-27;
		m[17][15]=-28;
		m[18][15]=-29;
		m[26][29]=-39;
		m[27][11]=-2;
		m[28][1]=-5;
		m[30][3]=-9;
		m[31][6]=-11;
		m[31][3]=-11;
		m[33][14]=-16;
		m[35][13]=-13;
		m[35][49]=-13;
		m[37][12]=-19;
		m[37][22]=-19;
		m[37][20]=-19;
		m[37][26]=-19;
		m[37][28]=-19;
		m[37][30]=-19;
		m[37][31]=-19;
		m[37][7]=-19;
		m[37][19]=-19;
		m[37][21]=-19;
		m[37][24]=-19;
		m[37][25]=-19;
		m[37][27]=-19;
		m[49][49]=-14;
		m[50][14]=-17;
		m[52][7]=-7;
		m[52][2]=-7;
		m[52][1]=-7;
		m[53][7]=-8;
		m[53][2]=-8;
		m[53][1]=-8;
		m[54][12]=-18;
		m[54][22]=-18;
		m[54][20]=-18;
		m[54][26]=-18;
		m[54][28]=-18;
		m[54][30]=-18;
		m[54][31]=-18;
		m[54][7]=-18;
		m[54][19]=-18;
		m[54][21]=-18;
		m[54][24]=-18;
		m[54][25]=-18;
		m[54][27]=-18;
		m[55][15]=-38;
		m[57][18]=-47;
		m[57][32]=-47;
		m[57][33]=-47;
		m[57][15]=-47;
		m[57][6]=-47;
		m[58][35]=-54;
		m[58][36]=-54;
		m[58][37]=-54;
		m[58][38]=-54;
		m[58][39]=-54;
		m[58][40]=-54;
		m[58][18]=-54;
		m[58][32]=-54;
		m[58][33]=-54;
		m[58][15]=-54;
		m[58][6]=-54;
		m[59][35]=-55;
		m[59][36]=-55;
		m[59][37]=-55;
		m[59][38]=-55;
		m[59][39]=-55;
		m[59][40]=-55;
		m[59][18]=-55;
		m[59][32]=-55;
		m[59][33]=-55;
		m[59][15]=-55;
		m[59][6]=-55;
		m[60][41]=-58;
		m[60][42]=-58;
		m[60][35]=-58;
		m[60][36]=-58;
		m[60][37]=-58;
		m[60][38]=-58;
		m[60][39]=-58;
		m[60][40]=-58;
		m[60][18]=-58;
		m[60][32]=-58;
		m[60][33]=-58;
		m[60][15]=-58;
		m[60][6]=-58;
		m[61][43]=-63;
		m[61][44]=-63;
		m[61][45]=-63;
		m[61][46]=-63;
		m[61][41]=-63;
		m[61][42]=-63;
		m[61][35]=-63;
		m[61][36]=-63;
		m[61][37]=-63;
		m[61][38]=-63;
		m[61][39]=-63;
		m[61][40]=-63;
		m[61][18]=-63;
		m[61][32]=-63;
		m[61][33]=-63;
		m[61][15]=-63;
		m[61][6]=-63;
		m[64][43]=-66;
		m[64][44]=-66;
		m[64][45]=-66;
		m[64][46]=-66;
		m[64][41]=-66;
		m[64][42]=-66;
		m[64][35]=-66;
		m[64][36]=-66;
		m[64][37]=-66;
		m[64][38]=-66;
		m[64][39]=-66;
		m[64][40]=-66;
		m[64][18]=-66;
		m[64][32]=-66;
		m[64][33]=-66;
		m[64][15]=-66;
		m[64][6]=-66;
		m[65][43]=-69;
		m[65][44]=-69;
		m[65][45]=-69;
		m[65][46]=-69;
		m[65][41]=-69;
		m[65][42]=-69;
		m[65][35]=-69;
		m[65][36]=-69;
		m[65][37]=-69;
		m[65][38]=-69;
		m[65][39]=-69;
		m[65][40]=-69;
		m[65][18]=-69;
		m[65][32]=-69;
		m[65][33]=-69;
		m[65][15]=-69;
		m[65][6]=-69;
		m[66][43]=-70;
		m[66][44]=-70;
		m[66][45]=-70;
		m[66][46]=-70;
		m[66][41]=-70;
		m[66][42]=-70;
		m[66][35]=-70;
		m[66][36]=-70;
		m[66][37]=-70;
		m[66][38]=-70;
		m[66][39]=-70;
		m[66][40]=-70;
		m[66][18]=-70;
		m[66][32]=-70;
		m[66][33]=-70;
		m[66][15]=-70;
		m[66][6]=-70;
		m[64][17]=-72;
		m[72][18]=-35;
		m[77][2]=-6;
		m[77][1]=-6;
		m[78][3]=-10;
		m[99][18]=-75;
		m[99][6]=-75;
		m[103][15]=-34;
		m[105][15]=-42;
		m[106][15]=-43;
		m[107][29]=-40;
		m[108][29]=-41;
		m[109][6]=-12;
		m[109][3]=-12;
		m[110][18]=-23;
		m[110][6]=-23;
		m[111][18]=-44;
		m[111][32]=-44;
		m[111][33]=-44;
		m[111][15]=-44;
		m[111][6]=-44;
		m[112][18]=-45;
		m[112][32]=-45;
		m[112][33]=-45;
		m[112][15]=-45;
		m[112][6]=-45;
		m[114][35]=-48;
		m[114][36]=-48;
		m[114][37]=-48;
		m[114][38]=-48;
		m[114][39]=-48;
		m[114][40]=-48;
		m[114][18]=-48;
		m[114][32]=-48;
		m[114][33]=-48;
		m[114][15]=-48;
		m[114][6]=-48;
		m[115][35]=-49;
		m[115][36]=-49;
		m[115][37]=-49;
		m[115][38]=-49;
		m[115][39]=-49;
		m[115][40]=-49;
		m[115][18]=-49;
		m[115][32]=-49;
		m[115][33]=-49;
		m[115][15]=-49;
		m[115][6]=-49;
		m[116][35]=-50;
		m[116][36]=-50;
		m[116][37]=-50;
		m[116][38]=-50;
		m[116][39]=-50;
		m[116][40]=-50;
		m[116][18]=-50;
		m[116][32]=-50;
		m[116][33]=-50;
		m[116][15]=-50;
		m[116][6]=-50;
		m[117][35]=-51;
		m[117][36]=-51;
		m[117][37]=-51;
		m[117][38]=-51;
		m[117][39]=-51;
		m[117][40]=-51;
		m[117][18]=-51;
		m[117][32]=-51;
		m[117][33]=-51;
		m[117][15]=-51;
		m[117][6]=-51;
		m[118][35]=-52;
		m[118][36]=-52;
		m[118][37]=-52;
		m[118][38]=-52;
		m[118][39]=-52;
		m[118][40]=-52;
		m[118][18]=-52;
		m[118][32]=-52;
		m[118][33]=-52;
		m[118][15]=-52;
		m[118][6]=-52;
		m[119][35]=-53;
		m[119][36]=-53;
		m[119][37]=-53;
		m[119][38]=-53;
		m[119][39]=-53;
		m[119][40]=-53;
		m[119][18]=-53;
		m[119][32]=-53;
		m[119][33]=-53;
		m[119][15]=-53;
		m[119][6]=-53;
		m[120][41]=-56;
		m[120][42]=-56;
		m[120][35]=-56;
		m[120][36]=-56;
		m[120][37]=-56;
		m[120][38]=-56;
		m[120][39]=-56;
		m[120][40]=-56;
		m[120][18]=-56;
		m[120][32]=-56;
		m[120][33]=-56;
		m[120][15]=-56;
		m[120][6]=-56;
		m[121][41]=-57;
		m[121][42]=-57;
		m[121][35]=-57;
		m[121][36]=-57;
		m[121][37]=-57;
		m[121][38]=-57;
		m[121][39]=-57;
		m[121][40]=-57;
		m[121][18]=-57;
		m[121][32]=-57;
		m[121][33]=-57;
		m[121][15]=-57;
		m[121][6]=-57;
		m[122][43]=-59;
		m[122][44]=-59;
		m[122][45]=-59;
		m[122][46]=-59;
		m[122][41]=-59;
		m[122][42]=-59;
		m[122][35]=-59;
		m[122][36]=-59;
		m[122][37]=-59;
		m[122][38]=-59;
		m[122][39]=-59;
		m[122][40]=-59;
		m[122][18]=-59;
		m[122][32]=-59;
		m[122][33]=-59;
		m[122][15]=-59;
		m[122][6]=-59;
		m[123][43]=-60;
		m[123][44]=-60;
		m[123][45]=-60;
		m[123][46]=-60;
		m[123][41]=-60;
		m[123][42]=-60;
		m[123][35]=-60;
		m[123][36]=-60;
		m[123][37]=-60;
		m[123][38]=-60;
		m[123][39]=-60;
		m[123][40]=-60;
		m[123][18]=-60;
		m[123][32]=-60;
		m[123][33]=-60;
		m[123][15]=-60;
		m[123][6]=-60;
		m[124][43]=-61;
		m[124][44]=-61;
		m[124][45]=-61;
		m[124][46]=-61;
		m[124][41]=-61;
		m[124][42]=-61;
		m[124][35]=-61;
		m[124][36]=-61;
		m[124][37]=-61;
		m[124][38]=-61;
		m[124][39]=-61;
		m[124][40]=-61;
		m[124][18]=-61;
		m[124][32]=-61;
		m[124][33]=-61;
		m[124][15]=-61;
		m[124][6]=-61;
		m[125][43]=-62;
		m[125][44]=-62;
		m[125][45]=-62;
		m[125][46]=-62;
		m[125][41]=-62;
		m[125][42]=-62;
		m[125][35]=-62;
		m[125][36]=-62;
		m[125][37]=-62;
		m[125][38]=-62;
		m[125][39]=-62;
		m[125][40]=-62;
		m[125][18]=-62;
		m[125][32]=-62;
		m[125][33]=-62;
		m[125][15]=-62;
		m[125][6]=-62;
		m[126][43]=-64;
		m[126][44]=-64;
		m[126][45]=-64;
		m[126][46]=-64;
		m[126][41]=-64;
		m[126][42]=-64;
		m[126][35]=-64;
		m[126][36]=-64;
		m[126][37]=-64;
		m[126][38]=-64;
		m[126][39]=-64;
		m[126][40]=-64;
		m[126][18]=-64;
		m[126][32]=-64;
		m[126][33]=-64;
		m[126][15]=-64;
		m[126][6]=-64;
		m[131][18]=-73;
		m[131][6]=-73;
		m[139][18]=-46;
		m[139][32]=-46;
		m[139][33]=-46;
		m[139][15]=-46;
		m[139][6]=-46;
		m[140][43]=-65;
		m[140][44]=-65;
		m[140][45]=-65;
		m[140][46]=-65;
		m[140][41]=-65;
		m[140][42]=-65;
		m[140][35]=-65;
		m[140][36]=-65;
		m[140][37]=-65;
		m[140][38]=-65;
		m[140][39]=-65;
		m[140][40]=-65;
		m[140][18]=-65;
		m[140][32]=-65;
		m[140][33]=-65;
		m[140][15]=-65;
		m[140][6]=-65;
		m[141][43]=-67;
		m[141][44]=-67;
		m[141][45]=-67;
		m[141][46]=-67;
		m[141][41]=-67;
		m[141][42]=-67;
		m[141][35]=-67;
		m[141][36]=-67;
		m[141][37]=-67;
		m[141][38]=-67;
		m[141][39]=-67;
		m[141][40]=-67;
		m[141][18]=-67;
		m[141][32]=-67;
		m[141][33]=-67;
		m[141][15]=-67;
		m[141][6]=-67;
		m[142][43]=-68;
		m[142][44]=-68;
		m[142][45]=-68;
		m[142][46]=-68;
		m[142][41]=-68;
		m[142][42]=-68;
		m[142][35]=-68;
		m[142][36]=-68;
		m[142][37]=-68;
		m[142][38]=-68;
		m[142][39]=-68;
		m[142][40]=-68;
		m[142][18]=-68;
		m[142][32]=-68;
		m[142][33]=-68;
		m[142][15]=-68;
		m[142][6]=-68;
		m[143][43]=-71;
		m[143][44]=-71;
		m[143][45]=-71;
		m[143][46]=-71;
		m[143][41]=-71;
		m[143][42]=-71;
		m[143][35]=-71;
		m[143][36]=-71;
		m[143][37]=-71;
		m[143][38]=-71;
		m[143][39]=-71;
		m[143][40]=-71;
		m[143][18]=-71;
		m[143][32]=-71;
		m[143][33]=-71;
		m[143][15]=-71;
		m[143][6]=-71;
		m[146][15]=-30;
		m[147][15]=-33;
		m[148][18]=-36;
		m[149][18]=-37;
		m[152][18]=-22;
		m[152][6]=-22;
		m[153][18]=-74;
		m[153][6]=-74;
		m[155][15]=-32;
		m[159][16]=-20;
		m[159][14]=-20;
		m[160][18]=-21;
		m[160][6]=-21;
		m[161][15]=-31;
    }

	public static void print_tabla(){
		System.out.println("  ==================================================================");
		System.out.println("                         TABLA DE SIMBOLOS");
		System.out.println("  ------------------------------------------------------------------");
		System.out.println("         ID          TIPO    OBJ   DIR    TAM   ETQ      RUTINA");
		System.out.println("  ---------------  --------  ---  -----  -----  ---  ---------------");
		for(int i=0;i<=isym;i++)
			System.out.printf("  %-15s  %-8s  %-3s  %5s  %5s  %3s  %-15s\n",ID[i],TIPO[i],OBJ[i],DIRMEM[i],TAM[i],ETQ[i],RUT[i]);
		System.out.println("  ==================================================================");
	}
	public static boolean es_atributo(String cadena){
		if (TipoEquiv(K_t[iKt], RUT[renSYM(ID_F)].split(",")[1])) {
			return true;
		} 
		System.out.println("\n\n\7ERROR: El atributo no es del mismo tipo que el de la variable  => " + cadena + " !! " + RUT[renSYM(ID_F)].split(",")[1] + " en el RENGLON [ " + RENGLON + " ]");
		System.exit(4);
		return false;
	}

	public static void ejecucionFuncion(String nombreFuncion, String tipoFuncion, int retorno, String valorS) {
		boolean fun_enc = false;
		int posFun=0;
		int e_f = 0;
		pop();
		e_f = Integer.parseInt(pila[tope]);
		while (!fun_enc) {
			posFun=Posicion;
			lee_token(xArchivo(Entrada));
			if (a.equals("rutinas")) {
				fun_enc = true;
			}
		}
		Posicion= posFun;
		lee_token(xArchivo(Entrada));
		
		push("4");
		push(a);
		push("10");
		lee_token(xArchivo(Entrada));
		while (true) {
			S = pila[tope];
			if (S.equals("9")) {
				
				Posicion = retorno;
				System.out.println(retorno);
				pop();
				pop();
				pop();
				push("143");
				S = pila[tope];
				a = valorS;
				System.out.println("Esto es el texto: "+esterm(a)+ " y esto es el valor: " + a);
				System.out.println("Esto es el valor de S: " + Integer.parseInt(S));
				System.out.println("Esto es el valor de m: " + (m[Integer.parseInt(S)][esterm(a)]));
				System.out.println("Esto es el valor de la pila: " + (pi[m[Integer.parseInt(S)][esterm(a)] * -1]));
				e = e_f; 
				esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1]);
				//print_pila();
				break;
			}else{
				
				System.out.println("Esto es la pila {");
				print_pila();
				System.out.println("} fin de la pila ");
				
				System.out.println(a);
				System.err.println("S= " + S + " con a= " + a + " y esto es esterm= " + esterm(a)+ " y esto es m= " + m[Integer.parseInt(S)][esterm(a)]);
				if (m[Integer.parseInt(S)][esterm(a)] > 0) {
						System.out.println("m[" + S + "][" + a + "] = Shift(" + m[Integer.parseInt(S)][esterm(a)] + ")");
						//pausa();
						shift(m[Integer.parseInt(S)][esterm(a)]);
						push(a);
						push(m[Integer.parseInt(S)][esterm(a)] + "");
						lee_token(xArchivo(Entrada));
                } else {
                    if (m[Integer.parseInt(S)][esterm(a)] < 0) {
                        System.out
                                .println("m[" + S + "][" + a + "] = Reduce(" + m[Integer.parseInt(S)][esterm(a)] + ")");
                        //pausa();
                        for (int k = 1; k <= lpd[m[Integer.parseInt(S)][esterm(a)] * -1] *2; k++) {
                            pop();

                        }
                        e = Integer.parseInt(pila[tope]);
                        System.out.println("Esto es el termino salido de M= " + (m[Integer.parseInt(S)][esterm(a)] * -1)
                                + ",  Esto es pi= " + (pi[m[Integer.parseInt(S)][esterm(a)] * -1]));
                        push(pi[m[Integer.parseInt(S)][esterm(a)] * -1]);
						reduce(m[Integer.parseInt(S)][esterm(a)]);
						print_pila();
						//pausa();
                        //System.out.println("Esto es e= " + e + " y esto es esnt= "
                        //        + esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1]));
                        //print_pila();
						//System.out.println("Esto es el valor de la pila"+m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])]);
                        
						if (m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])] == 0) {
                            rut_error();

                        } else {
                            push(m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])] + "");
							//print_pila(); 
                        }

                    } else {
                        rut_error();
                    }
                }
			}
		}
	}
	
	
	public static String genetq(){
        return((contetq++)+"");
    }

	public static boolean Entero5mil(String cadena) {
        int xx = Integer.parseInt(cadena);
        if(!(xx>=1 && xx<=5000)){
            System.out.println("\n\n\7ERROR: El numero tiene que estar entre [1 ... 5000] => \" " + cadena +" \"" + " en el RENGLON [ " + RENGLON + " ]");
            System.exit(4);
        }
            return true;
	}

	public static int renSYM(String lex){
		for (int i = 0; i <=isym; i++) {
			if (ID[i].equals(lex)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean existe_variable(String var){
        for(int i=0; i<=isym; i++){
            if(var.equals(ID[i])){
                return true;
            }
        }
        return false;
    }

	public static boolean QueExista(String var){
		if(existe_variable(var)){
            return true;
        } else {
            //   ----- /7 el caracter numero siete hace que pite la computadora
            System.out.println("\n\n\7ERROR: Variable no declarada => \" " + var +" \"" + " en el renglon [ " + RENGLON + " ]");
            System.exit(4);
            return false;
        }
	}
	
	public static boolean QueNoExista(String var){
		if(!existe_variable(var)){
            return true;
        } else {
            //   ----- /7 el caracter numero siete hace que pite la computadora
            System.out.println("\n\n\7ERROR: Variable duplicada => \" " + var +" \"" + " en el renglon [ " + RENGLON + "]");
            System.exit(4);
            return false;
        }
	}

	public static boolean NoSeaArr(String var){
		if(OBJ[renSYM(var)].equals("arr")){
            System.out.println("\n\n\7ERROR: La variable no debe ser un arreglo => \" " + var +" \"" + " en el RENGLON [ " + RENGLON + " ]");
            System.exit(4);
            return false;
        }
        return true;
	}

	public static boolean SeaArr(String var){
		if(!OBJ[renSYM(var)].equals("arr")){
            System.out.println("\n\n\7ERROR: =La variable no es un arreglo => \" " + var +" \"" + " en el RENGLON [ " + RENGLON + "]");
            System.exit(4);
            return false;
        }
        return true;
	}

	public static int es_entero(String var){
		if (var.equals("entero")) {
			return 1;
		}
		return 0;
	}

	public static boolean TipoEquiv(String A, String B) {
		if (A.equals(B)) {
			return true;
		} else {
			System.out.println("\n\n\7ERROR: Los tipos no son equivalentes => " + A + " y " + B + " en el RENGLON [ " + RENGLON + " ]");
			System.exit(4);
			return false;
		}
	}

	public static void inserta(String id, String tipo, String obj, String dirmem, String tam, String etq, String rutina) {
		ID[++isym] = id;
		TIPO[isym] = tipo;
		OBJ[isym] = obj;
		DIRMEM[isym] = dirmem;
		TAM[isym] = tam;
		ETQ[isym] = etq;
		RUT[isym] = rutina;
	}
	
	public static void Rango(String var, String idx){
		if(Integer.parseInt(idx) <= 0 || Integer.parseInt(idx) > Integer.parseInt(TAM[renSYM(var)])){
			System.out.println("\n\n\7ERROR: Indice fuera de rango => \" " + idx +" \"" + " en el RENGLON [ " + RENGLON + " ]");
			System.exit(4);
		}
	}

	public static void primeraPasada() {
		String[] instrucciones = PPAL_c.split("\n");
		for (int i = 0; i < instrucciones.length; i++) {
			String instruccion = instrucciones[i];
			if (Character.isDigit(instruccion.charAt(0))) {
				int espacioIndex = instruccion.indexOf(' ');
				int numero = Integer.parseInt(instruccion.substring(0, espacioIndex));
				String nuevaInstruccion = instruccion.substring(espacioIndex + 1);
				E[eCount] = numero;
				direcciones[eCount] = i;
				eCount++;
				instrucciones[i] = nuevaInstruccion;
			}
		}
		PPAL_c = String.join("\n", instrucciones);
	}

	public static void segundaPasada() {
        String[] instrucciones = PPAL_c.split("\n");
        for (int i = 0; i < instrucciones.length; i++) {
            String instruccion = instrucciones[i];
            int dolarIndex = instruccion.indexOf('$');
            if (dolarIndex != -1) {
                int numero = Integer.parseInt(instruccion.substring(dolarIndex + 1).trim());
                int direccion = -1;
                for (int j = 0; j < eCount; j++) {
                    if (E[j] == numero) {
                        direccion = direcciones[j];
                        break;
                    }
                }
                String nuevaInstruccion = instruccion.substring(0, dolarIndex) + " " + direccion;
                instrucciones[i] = nuevaInstruccion;
            }
        }
        PPAL_c = String.join("\n", instrucciones);
    }

	public static void shift(int shift) {
		switch(shift) {
			case 26:
				VAL = LEX;
				QueExista(VAL);
				DIR=DIRMEM[renSYM(LEX)];
				break;
			case 31:
				VAL = LEX;
				break;
			case 52:
				VAL = LEX;
				break;
			case 53:
				VAL = LEX;
				break;
			case 64:
				VAL = LEX;
				break;
			case 65:
				VAL = LEX;
				break;
			case 66:
				VAL = LEX;
				break;
			case 72:
				VAL = LEX;
				QueExista(VAL);
				
				DIR = DIRMEM[renSYM(LEX)];
				break;
			case 75:
				SeaArr(VAL);
				IDX = LEX;
				Rango(VAL, IDX);
				DIR = (Integer.parseInt(DIRMEM[renSYM(VAL)]) + Integer.parseInt(IDX) - 1) + "";
				break;
			case 76:
				SeaArr(VAL);
				IDX=LEX;
				QueExista(IDX);
				NoSeaArr(IDX);
				TipoEquiv(TIPO[renSYM(IDX)], "numero");
				DIRIDX=DIRMEM[renSYM(IDX)];
				break;
			case 79:
				LONG=LEX;
				Entero5mil(LONG);
				break;
			case 128:
				IDX = LEX;
				break;
			case 129:
				IDX = LEX;
				break;
			case 135:
				SeaArr(VAL);
				IDX = LEX;
				Rango(VAL, IDX);
				DIR = (Integer.parseInt(DIRMEM[renSYM(VAL)]) + Integer.parseInt(IDX) - 1) + "";

				break;
			case 136:
				IDX = LEX;
				SeaArr(VAL);
				QueExista(IDX);
				NoSeaArr(IDX);
				TipoEquiv(TIPO[renSYM(IDX)], "numero");
				DIRIDX = DIRMEM[renSYM(IDX)];
				break;
		}
	}

	public static void reduce(int reduce) {
		switch(reduce){
				case -6:
					for (int i = 0; i <= isym; i++) {
						if (TIPO[i].equals("pend")) {
							TIPO[i] = TIPO_t;
						}
					}
					break;
				case -7:
					TIPO_t = VAL;
					break;
				case -8:
					TIPO_t = VAL;
					break;
				case -11:
					QueNoExista(VAL);
					inserta(VAL, "pend", "var", (dirmem++) + "", "", "", "");
					break;
				case -12:
					QueNoExista(VAL);
					inserta(VAL, "pend", "arr", dirmem + "", LONG, "", "");
					dirmem = dirmem + Integer.parseInt(LONG);
					break;
				case -13:
					PPAL_c = BLQ_c[iBLQ_cc--] + "fin";
					break;
				case -18:
					BLQ_c[iBLQ_cc] = BLQ_c[iBLQ_cc] + INST_c[iINSTc--];
					break;
				case -19:
					BLQ_c[++iBLQ_cc] = INST_c[iINSTc--];
					break;
				case -24:
					INST_c[++iINSTc] = ASIG_c[iASIGc--];
					break;
				case -25:
					INST_c[++iINSTc] = IF_c[iIFc--];
					break;
				case -26:
					INST_c[++iINSTc] = W_c[iWc--];
					break;
				case -27:
					INST_c[++iINSTc] = REP_c[iREPc--];
					break;
				case -28:
					INST_c[++iINSTc] = LEE_c[iLEEc--];
					break;
				case -29:
					INST_c[++iINSTc] = ESC_c[iESCc--];
					break;
				case -30:
					E0 = genetq();
					E1 = genetq();
					W_c[++iWc] = E0 + " " + K_c[iKc--] + "mete$ " + E1 + "\r\nirfalso\r\n" + BLQ_c[iBLQ_cc--] + "mete$ " + E0 + "\r\nira\n" + E1+ " copia\r\nsaca\r\n";
					break;
				case -31:
					E0 = genetq();
					E1 = genetq();
					IF_c[++iIFc] = K_c[iKc--] + "mete$ " + E0 + "\r\nirverdad\r\n" + BLQ_c[iBLQ_cc--] + "mete$ " + E1 + "\r\nira\n" + E0 + " " + BLQ_c[iBLQ_cc--] + E1 + " copia\r\nsaca\r\n";
					break;
				case -32:
					E0 = genetq();
					IF_c[++iIFc] = K_c[iKc--] + "mete$ " + E0 + "\r\nirfalso\r\n" + BLQ_c[iBLQ_cc--] + E0 + " copia\r\nsaca\r\n";
					break;
				case -33:
					E0 = genetq();
					REP_c[++iREPc] = E0 + " " + BLQ_c[iBLQ_cc--] + K_c[iKc--] + "mete$ " + E0 + "\r\nirfalso\r\n";
					break;
				case -34:
					LEE_c[++iLEEc] = VARL_c[iVARLc--] + "lee\nasig\n";
					break;
				case -35:
					VARL_c[++iVARLc] = "mete " + DIR + "\n";
					NoSeaArr(VAL);
					break;
				case -36:
					VARL_c[++iVARLc] = "mete " + DIR + "\n";
					break;
				case -37:
					VARL_c[++iVARLc] = "mete " + DIRIDX + "\nmem\nmete 1\nopdif\nmete " + DIR + "\nopsum\n";
					break;
				case -38:
					TipoEquiv(VASIG_t[iVASIGt], K_t[iKt]);
					ASIG_c[++iASIGc] = VASIG_c[iVASIGc--] + K_c[iKc--] + "asig\n";
					break;
				case -39:
					NoSeaArr(VAL);
					VASIG_t[++iVASIGt]=TIPO[renSYM(VAL)];
					VASIG_c[++iVASIGc]="mete "+DIRMEM[renSYM(VAL)]+ "\r\n";
					break;
			    case -40:
					VASIG_t[++iVASIGt]=TIPO[renSYM(VAL)];
					VASIG_c[++iVASIGc]="mete "+ DIR+ "\r\n";
					break;
				case -41:
					VASIG_t[++iVASIGt]=TIPO[renSYM(VAL)];
					VASIG_c[++iVASIGc]="mete "+ DIRIDX+ "\r\nmem\r\nmete 1\r\nopdif\r\nmete "+DIR+ "\r\nopsum\r\n";
					break;
				case -42:
					ESC_c[++iESCc] = K_c[iKc--] + "escribe\n";
					break;
				case -43:
					ESC_c[++iESCc] = K_c[iKc--] + "escribenl\n";
					break;
				case -44:
					TipoEquiv(K_t[iKt], "numero");
					TipoEquiv(L_t[iLt], "numero");
					K_c[iKt] = K_c[iKt] + L_c[iLt--] + "opor\n";
					K_t[iKt] = "numero";
					break;
				case -45:
					TipoEquiv(K_t[iKt], "numero");
					TipoEquiv(L_t[iLt], "numero");
					K_c[iKt] = K_c[iKt] + L_c[iLt--] + "opand\n";
					K_t[iKt] = "numero";
					break;
				case -46:
					TipoEquiv(K_t[iKt], "numero");
					K_c[iKt] = K_c[iKt] + "opnot\n";
					K_t[iKt] = "numero";
					break;
				case -47:
					K_c[++iKc]=L_c[iLc--];
					K_t[++iKt]=L_t[iLt--];
					break;
				case -48:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = L_c[iLt] + E_c[iEt] + "opdif\nopnot\n";
					L_t[iLt] = "numero";
					break;
				case -49:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = L_c[iLt] + E_c[iEt] + "opdif\n";
					L_t[iLt] = "numero";
					break;
				case -50:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = L_c[iLt] + E_c[iEt] + "opdif\ncopia\nopabs\nopsum\n" + L_c[iLt] + E_c[iEt] + "opdif\nopnot\nopsum\n";
					L_t[iLt] = "numero";
					break;
				case -51:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = E_c[iEt] + L_c[iLt] + "opdif\ncopia\nopabs\nopsum\n" + E_c[iEt] + L_c[iLt] + "opdif\nopnot\nopsum\n";
					L_t[iLt] = "numero";
					break;
				case -52:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = L_c[iLt] + E_c[iEt] + "opdif\ncopia\nopabs\nopsum\n";
					L_t[iLt] = "numero";
					break;
				case -53:
					TipoEquiv(L_t[iLt], "numero");
					TipoEquiv(E_t[iEt], "numero");
					L_c[iLt] = E_c[iEt] + L_c[iLt] + "opdif\ncopia\nopabs\nopsum\n";
					L_t[iLt] = "numero";
					break;
				case -54:
					L_c[++iLc] = N_c[iNc--];
					L_t[++iLt] = N_t[iNt--];
					break;
				case -55:
					N_c[++iNc] = E_c[iEc--];
					N_t[++iNt] = E_t[iEt--];
					break;
				case -56:
					TipoEquiv(E_t[iEt--], "numero");
					TipoEquiv(T_t[iTt--], "numero");
					Tmp1=E_c[iEc--]+T_c[iTc--]+"opsum\r\n";
					E_c[++iEc]=Tmp1;
					E_t[++iEt]="numero";
					break;
				case -57:
					TipoEquiv(E_t[iEt], "numero");
					TipoEquiv(T_t[iTt], "numero");
					E_c[iEt] = E_c[iEt] + T_c[iTt] + "opdif\n";
					E_t[iEt] = "numero";
					break;
				case -58:
					E_c[++iEc]=T_c[iTc--];
					E_t[++iEt]=T_t[iTt--];
					break;
				case -59:
					TipoEquiv(T_t[iTt], "numero");
					TipoEquiv(F_t[iFt], "numero");
					T_c[iTt] = T_c[iTt] + F_c[iFt] + "opmul\n";
					T_t[iTt] = "numero";
					break;
				case -60:
					TipoEquiv(T_t[iTt], "numero");
					TipoEquiv(F_t[iFt], "numero");
					T_c[iTt] = T_c[iTt] + F_c[iFt] + "opdiv\n";
					T_t[iTt] = "numero";
					break;
				case -61:
					TipoEquiv(T_t[iTt], "numero");
					TipoEquiv(F_t[iFt], "numero");
					T_c[iTt] = T_c[iTt] + F_c[iFt] + "opdiv\n";
					T_t[iTt] = "numero";
					break;
				case -62:
					TipoEquiv(T_t[iTt], "numero");
					TipoEquiv(F_t[iFt], "numero");
					T_c[iTt] = T_c[iTt] + F_c[iFt] + "opmod\n";
					T_t[iTt] = "numero";
					break;
				case -63:
					T_c[++iTc]=F_c[iFc--];
					T_t[++iTt]=F_t[iFt--];
					break;
				case -64:
					F_c[++iFc]=K_c[iKc--];
					F_t[++iFt]=K_t[iKt--];
					break;
				case -65:
					TipoEquiv(K_t[iKt], "numero");
					F_c[++iFc] = K_c[iKc--] + "opabs\n";
					F_t[++iFt] = "numero";
					break;
				case -66:
					QueExista(VAL);
					NoSeaArr(VAL);
					DIR = DIRMEM[renSYM(VAL)];
					F_c[++iFc] = "mete " + DIR + "\nmem\n";
					F_t[++iFt] = TIPO[renSYM(VAL)];
					break;
				case -67:
					QueExista(VAL);
					SeaArr(VAL);
					Rango(VAL, IDX);
					DIR = DIRMEM[renSYM(VAL)];
					DIR = (Integer.parseInt(DIR) + Integer.parseInt(IDX) - 1) + "";
					F_c[++iFc] = "mete " + DIR + "\nmem\n";
					F_t[++iFt] = TIPO[renSYM(VAL)];
					break;
				case -68:
					QueExista(VAL);
					SeaArr(VAL);
					QueExista(IDX);
					NoSeaArr(IDX);
					TipoEquiv(TIPO[renSYM(IDX)], "numero");
					DIR = DIRMEM[renSYM(VAL)];
					DIRIDX = DIRMEM[renSYM(IDX)];
					F_t[++iFt] = TIPO[renSYM(VAL)];
					F_c[++iFc] = "mete " + DIRIDX + "\nmem\nmete 1\nopdif\nmete " + DIR + "\nopsum\nmem\n";
					break;
				case -69:
					F_c[++iFc]="mete "  + VAL + "\r\n";
					F_t[++iFt]="numero";
					break;
				case -70:
					F_c[++iFc] = "mete " + VAL + "\n";
					F_t[++iFt] = "cadena";
					break;	
				case -71:
					ejecucionFuncion(ID_F,TIPO[renSYM(ID_F)],Posicion, a);
					F_c[++iFc]=BLQ_c[iBLQ_cc--];
					System.out.println("Valor: "+renSYM(ID_F));
					F_t[++iFt]=TIPO[renSYM(ID_F)];
					break;
				case -72:
					ID_F = VAL;
					System.out.println("Identificador"+renSYM(ID_F));
					
					break;
				case -73:

					System.out.println("Esto es el caso de una funcion");
					System.out.println("Identificador"+renSYM(ID_F)+ ", Tipo: "+ K_t[iKt]);
					es_atributo(K_t[iKt]);
					break;
				case -74:
					
					es_atributo(K_t[iKt]);
					
					break;
			}
			
	}



    public static void main(String[] argumento) {
        crearTabla();

        Entrada =  argumento[0] + ".cm1";

        push("0");

		preProcesador();

        lee_token(xArchivo(Entrada));

        while (true) {
			
            S = pila[tope];
			
            System.out.println("Esto es la pila {");
            print_pila();
            System.out.println("} fin de la pila ");
			
            System.out.println(a);
            System.err.println("S= " + S + " con a= " + a + " y esto es esterm= " + esterm(a)+ " y esto es m= " + m[Integer.parseInt(S)][esterm(a)]);
            if (m[Integer.parseInt(S)][esterm(a)] == 666) {
				print_tabla();
				//System.out.println("\nCodigo1: \n" + PPAL_c);
				primeraPasada();
    			segundaPasada();
				System.out.println("\nCodigo2: \n" + PPAL_c);
				//pausa();
				guardarEnArchivo(PPAL_c, argumento[0] + ".cm3");
                System.out.println("\nCompilacion terminada");
                System.exit(0);
            } else {
                if (m[Integer.parseInt(S)][esterm(a)] > 0) {
                    System.out.println("m[" + S + "][" + a + "] = Shift(" + m[Integer.parseInt(S)][esterm(a)] + ")");
					//pausa();
					shift(m[Integer.parseInt(S)][esterm(a)]);
					push(a);
					push(m[Integer.parseInt(S)][esterm(a)] + "");
                    lee_token(xArchivo(Entrada));
                } else {
                    if (m[Integer.parseInt(S)][esterm(a)] < 0) {
                        System.out
                                .println("m[" + S + "][" + a + "] = Reduce(" + m[Integer.parseInt(S)][esterm(a)] + ")");
                        //pausa();
                        for (int k = 1; k <= lpd[m[Integer.parseInt(S)][esterm(a)] * -1] *2; k++) {
                            pop();

                        }
                        e = Integer.parseInt(pila[tope]);
						
                        System.out.println("Esto es el termino salido de M= " + (m[Integer.parseInt(S)][esterm(a)] * -1)
                                + ",  Esto es pi= " + (pi[m[Integer.parseInt(S)][esterm(a)] * -1]));
                        push(pi[m[Integer.parseInt(S)][esterm(a)] * -1]);
						reduce(m[Integer.parseInt(S)][esterm(a)]);
						
				
                        System.out.println("Esto es e= " + e + " y esto es esnt= "
                                + esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1]));
						System.out.println("Esto es el valor de la pila"+m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])]);
                        //print_pila();
                        if (m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])] == 0) {
                            rut_error();

                        } else {
                            push(m[e][esnt(pi[m[Integer.parseInt(S)][esterm(a)] * -1])] + "");
							//print_pila(); 
                        }

                    } else {
                        rut_error();
                    }
                }
            }
        }
    }
}
