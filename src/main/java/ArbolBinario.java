import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;

// Clase Nodo
class Nodo {
    int valor;
    Nodo izquierda, derecha;

    public Nodo(int valor) {
        this.valor = valor;
        izquierda = derecha = null;
    }
}

// Clase ArbolBinario
class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return new Nodo(valor);
        if (valor < nodo.valor) nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        else if (valor > nodo.valor) nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        return nodo;
    }

    public List<Integer> recorridoPreOrden() {
        List<Integer> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            resultado.add(nodo.valor);
            preOrden(nodo.izquierda, resultado);
            preOrden(nodo.derecha, resultado);
        }
    }

    public List<Integer> recorridoInOrden() {
        List<Integer> resultado = new ArrayList<>();
        inOrden(raiz, resultado);
        return resultado;
    }

    private void inOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            inOrden(nodo.izquierda, resultado);
            resultado.add(nodo.valor);
            inOrden(nodo.derecha, resultado);
        }
    }

    public List<Integer> recorridoPostOrden() {
        List<Integer> resultado = new ArrayList<>();
        postOrden(raiz, resultado);
        return resultado;
    }

    private void postOrden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            postOrden(nodo.izquierda, resultado);
            postOrden(nodo.derecha, resultado);
            resultado.add(nodo.valor);
        }
    }

    public void eliminar(int valor) {
        raiz = eliminarRecursivo(raiz, valor);
    }

    private Nodo eliminarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) return null;
        if (valor < nodo.valor) nodo.izquierda = eliminarRecursivo(nodo.izquierda, valor);
        else if (valor > nodo.valor) nodo.derecha = eliminarRecursivo(nodo.derecha, valor);
        else {
            if (nodo.izquierda == null) return nodo.derecha;
            if (nodo.derecha == null) return nodo.izquierda;
            nodo.valor = encontrarMinimo(nodo.derecha);
            nodo.derecha = eliminarRecursivo(nodo.derecha, nodo.valor);
        }
        return nodo;
    }

    private int encontrarMinimo(Nodo nodo) {
        int min = nodo.valor;
        while (nodo.izquierda != null) {
            min = nodo.izquierda.valor;
            nodo = nodo.izquierda;
        }
        return min;
    }

    public int calcularAltura() {
        return calcularAlturaRecursivo(raiz);
    }

    private int calcularAlturaRecursivo(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(calcularAlturaRecursivo(nodo.izquierda), calcularAlturaRecursivo(nodo.derecha));
    }

    public int contarHojas() {
        return contarHojasRecursivo(raiz);
    }

    private int contarHojasRecursivo(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.izquierda == null && nodo.derecha == null) return 1;
        return contarHojasRecursivo(nodo.izquierda) + contarHojasRecursivo(nodo.derecha);
    }

    public int buscarNumero(int valor) {
        return buscarNumeroRecursivo(raiz, valor, 1);
    }

    private int buscarNumeroRecursivo(Nodo nodo, int valor, int altura) {
        if (nodo == null) return -1;
        if (nodo.valor == valor) return altura;
        int izquierda = buscarNumeroRecursivo(nodo.izquierda, valor, altura + 1);
        return izquierda != -1 ? izquierda : buscarNumeroRecursivo(nodo.derecha, valor, altura + 1);
    }

    public void dibujarArbol(Graphics g, int x, int y, Nodo nodo, int espaciado) {
        if (nodo == null) return;
        g.drawOval(x, y, 30, 30);
        g.drawString(String.valueOf(nodo.valor), x + 10, y + 20);
        if (nodo.izquierda != null) {
            g.drawLine(x + 15, y + 30, x - espaciado + 15, y + 60);
            dibujarArbol(g, x - espaciado, y + 60, nodo.izquierda, espaciado / 2);
        }
        if (nodo.derecha != null) {
            g.drawLine(x + 15, y + 30, x + espaciado + 15, y + 60);
            dibujarArbol(g, x + espaciado, y + 60, nodo.derecha, espaciado / 2);
        }
    }
}

// Interfaz gráfica
class PanelDibujo extends JPanel {
    private ArbolBinario arbol;

    public PanelDibujo(ArbolBinario arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        arbol.dibujarArbol(g, getWidth() / 2, 20, arbol.raiz, getWidth() / 4);
    }
}