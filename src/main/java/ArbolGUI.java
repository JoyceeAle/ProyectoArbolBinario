import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class ArbolGUI extends JFrame {
    private ArbolBinario arbol = new ArbolBinario();
    private JTextField txtNumero;
    private JTextArea txtResultado;
    private PanelDibujo panelDibujo;

    public ArbolGUI() {
        setTitle("Árbol Binario");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        txtNumero = new JTextField(5);
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnPreOrden = new JButton("PreOrden");
        JButton btnInOrden = new JButton("InOrden");
        JButton btnPostOrden = new JButton("PostOrden");
        JButton btnAltura = new JButton("Altura");
        JButton btnHojas = new JButton("Hojas");
        JButton btnLeerArchivo = new JButton("Leer desde archivo");
        JButton btnGuardarRecorridos = new JButton("Guardar recorridos");

        txtResultado = new JTextArea(5, 50);
        JScrollPane scroll = new JScrollPane(txtResultado);

        panel.add(new JLabel("Número:"));
        panel.add(txtNumero);
        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);
        panel.add(btnPreOrden);
        panel.add(btnInOrden);
        panel.add(btnPostOrden);
        panel.add(btnAltura);
        panel.add(btnHojas);
        panel.add(btnLeerArchivo);
        panel.add(btnGuardarRecorridos);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.SOUTH);

        panelDibujo = new PanelDibujo(arbol);
        add(panelDibujo, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            int valor = Integer.parseInt(txtNumero.getText());
            arbol.insertar(valor);
            txtResultado.append("Agregado: " + valor + "\n");
            panelDibujo.repaint();
        });

        btnPreOrden.addActionListener(e -> txtResultado.append("PreOrden: " + arbol.recorridoPreOrden() + "\n"));
        btnInOrden.addActionListener(e -> txtResultado.append("InOrden: " + arbol.recorridoInOrden() + "\n"));
        btnPostOrden.addActionListener(e -> txtResultado.append("PostOrden: " + arbol.recorridoPostOrden() + "\n"));

        btnEliminar.addActionListener(e -> {
            int valor = Integer.parseInt(txtNumero.getText());
            arbol.eliminar(valor);
            txtResultado.append("Eliminado: " + valor + "\n");
            panelDibujo.repaint();
        });

        btnBuscar.addActionListener(e -> {
            int valor = Integer.parseInt(txtNumero.getText());
            int nivel = arbol.buscarNumero(valor);
            if (nivel != -1) {
                // Mensaje que muestra la altura del número
                txtResultado.append("Encontrado en nivel: " + nivel + "\n");
                JOptionPane.showMessageDialog(this, "El número " + valor + " está en la altura: " + nivel,
                        "Número Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtResultado.append("No encontrado\n");
                JOptionPane.showMessageDialog(this, "El número " + valor + " no fue encontrado.",
                        "Número No Encontrado", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnAltura.addActionListener(e -> txtResultado.append("Altura del árbol: " + arbol.calcularAltura() + "\n"));

        btnHojas.addActionListener(e -> txtResultado.append("Cantidad de hojas: " + arbol.contarHojas() + "\n"));

        btnLeerArchivo.addActionListener(e -> leerArchivoYAgregarNumeros());

        btnGuardarRecorridos.addActionListener(e -> guardarRecorridosEnArchivo());

    }

    private void leerArchivoYAgregarNumeros() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    int valor = Integer.parseInt(linea.trim());
                    arbol.insertar(valor);
                    txtResultado.append("Agregado desde archivo: " + valor + "\n");
                }
                panelDibujo.repaint();
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarRecorridosEnArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                List<Integer> preOrden = arbol.recorridoPreOrden();
                List<Integer> inOrden = arbol.recorridoInOrden();
                List<Integer> postOrden = arbol.recorridoPostOrden();

                bw.write("Recorrido PreOrden: " + preOrden + "\n");
                bw.write("Recorrido InOrden: " + inOrden + "\n");
                bw.write("Recorrido PostOrden: " + postOrden + "\n");
                JOptionPane.showMessageDialog(this, "Recorridos guardados en el archivo", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArbolGUI().setVisible(true));
    }
}