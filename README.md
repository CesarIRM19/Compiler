# Compilador en Java para Lenguaje Propio

Este proyecto implementa un **compilador en Java** para un lenguaje de programación diseñado específicamente para propósitos educativos y experimentales.  
El lenguaje permite declarar variables, escribir un programa principal y definir funciones propias.

---

## Estructura General de un Programa

Un programa está dividido en tres secciones principales:

1. **Variables**
2. **Programa**
3. **Funciones**

---

## Sección de Variables

Se define dentro de llaves `{ }` con la siguiente sintaxis:

```plaintext
{
    var id:TipoDeVariable
    var id[tamaño]:TipoDeVariable
}
```

### Sintaxis de identificadores (id)
- Puede contener letras, números y el carácter `_`.
- No puede terminar con `_`.

### Tipos de variables
- **Cadena** → Texto entre comillas dobles `" "`.
- **Número** → Enteros o decimales.
- **Arreglos** → De **Cadenas** o **Números**, almacenan varios valores del mismo tipo.

#### Ejemplos de arreglos
```plaintext
miArray[5]         // tamaño fijo
miArray[otraVar]   // tamaño definido por otra variable
```

---

## 🔹 Sección del Programa

Se define así:

```plaintext
programa
    BLOQUE_DE_CÓDIGO
fin_programa
```

### Bloques de código

#### 1. **Asignación**
```plaintext
id <=> K;
id[] <=> K;
```

#### 2. **Condiciones**
```plaintext
verdad (K) haz
    BLOQUE
falso
    BLOQUE
fin_verdad
```

O sin `falso`:
```plaintext
verdad (K) haz
    BLOQUE
fin_verdad
```

#### 3. **Bucles**
```plaintext
ciclo_si (K)
    BLOQUE
fin_ciclo
```

```plaintext
hacer
    BLOQUE
hasta (K)
```

#### 4. **Escritura**
```plaintext
imprime(variable);
imprime("Texto");
imprime_ret(variable);
imprime_ret("Texto");
```

#### 5. **Lectura**
```plaintext
lee(variable);
```

---

## Operadores

### Lógicos
| Operador | Símbolo | Ejemplo |
|----------|---------|---------|
| OR       | `oo`    | `A oo B` |
| AND      | `yy`    | `A yy B` |
| NOT      | `nopi`  | `nopi(A)` |

### Relacionales
| Operador         | Símbolo | Ejemplo |
|------------------|---------|---------|
| Igualdad         | `=`     | `A = B` |
| Diferencia       | `dif`   | `A dif B` |
| Mayor igual que  | `mai`   | `A mai B` |
| Menor igual que  | `mei`   | `A mei B` |
| Mayor que        | `>`     | `A > B` |
| Menor que        | `<`     | `A < B` |

### Aritméticos
| Operador           | Símbolo / Forma | Ejemplo |
|--------------------|-----------------|---------|
| Suma               | `+`             | `A + B` |
| Resta              | `-`             | `A - B` |
| Multiplicación     | `*`             | `A * B` |
| División           | `/` o `div`     | `A / B` |
| Módulo             | `mod`           | `A mod B` |
| Valor absoluto     | `abs(K)`        | `abs(-5)` |

---

## Sección de Funciones

Se define así:

```plaintext
rutinas
    FUNCIONES
fin_rutinas
```

### Estructura de una función
```plaintext
funcion TIPO id (PARAMETROS)
    BLOQUE
fin_funcion
```

---

## Ejemplo Completo

```plaintext
{
    var nombre:Cadena
    var edad:Numero
    var listaNombres[10]:Cadena
}

programa
    imprime("Bienvenido");
    lee(nombre);
    edad <=> 25;
fin_programa

rutinas
    funcion Numero sumar(Numero a, Numero b)
        devolver a + b;
    fin_funcion
fin_rutinas
```

---
## Ejecución del compilador

Para compilar y ejecutar tu programa en el lenguaje propio, sigue estos pasos:

1. **Prepara tu código fuente**  
   Crea un archivo con extensión `.cm` que contenga el código del programa.

2. **Ubicación de archivos**  
   Guarda el archivo `.cm` en la misma carpeta donde se encuentra el archivo `Compilador.bat`.

3. **Compilar el programa**  
   Abre la terminal (CMD o PowerShell) en esa carpeta y ejecuta el archivo `.bat` con el siguiente formato:  
   ```bash
   .\Compilador.bat Nombre_del_codigo
4. **Ejecutar el programa compilado**
   Una vez terminada la compilación sin errores, ejecuta el programa con el siguiente comando:
   ```bash
    java Compiled_Programs.Nombre_del_codigo
---

## Tecnologías Utilizadas
- **Java** (implementación del compilador)

