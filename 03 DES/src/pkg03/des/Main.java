import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private static SecretKey clave;

    public static void main(String[] args) {
        // Crear la interfaz gráfica
        JFrame frame = new JFrame("Cifrado y Descifrado DES");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new FlowLayout());

        // Botón para seleccionar archivo
        JButton botonCargarArchivo = new JButton("Cargar Archivo");
        JButton botonCifrar = new JButton("Cifrar");
        JButton botonDescifrar = new JButton("Descifrar");
        JTextArea areaTexto = new JTextArea(10, 40);
        areaTexto.setEditable(false);

        frame.add(botonCargarArchivo);
        frame.add(botonCifrar);
        frame.add(botonDescifrar);
        frame.add(new JScrollPane(areaTexto));

        // Crear el generador de claves DES
        try {
            KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
            generadorDES.init(56); // El tamaño de la clave es de 56 bits
            clave = generadorDES.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Acción para cargar un archivo
        botonCargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    areaTexto.setText("Archivo seleccionado: " + file.getAbsolutePath());
                }
            }
        });

        // Acción para cifrar el archivo
        botonCifrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File inputFile = fileChooser.getSelectedFile();
                    String outputFile = inputFile.getAbsolutePath() + ".cifrado";
                    try {
                        // Cifrar el archivo usando DES en modo ECB con PKCS5Padding
                        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        cifrador.init(Cipher.ENCRYPT_MODE, clave);

                        FileInputStream entrada = new FileInputStream(inputFile);
                        FileOutputStream salida = new FileOutputStream(outputFile);

                        byte[] buffer = new byte[1000];
                        byte[] buffercifrado;

                        int bytesleidos = entrada.read(buffer, 0, 1000);
                        while (bytesleidos != -1) {
                            buffercifrado = cifrador.update(buffer, 0, bytesleidos);
                            salida.write(buffercifrado);
                            bytesleidos = entrada.read(buffer, 0, 1000);
                        }

                        buffercifrado = cifrador.doFinal();
                        salida.write(buffercifrado);

                        entrada.close();
                        salida.close();
                        areaTexto.setText("Archivo cifrado guardado en: " + outputFile);
                    } catch (Exception ex) {
                        areaTexto.setText("Error al cifrar el archivo: " + ex.getMessage());
                    }
                }
            }
        });

        // Acción para descifrar el archivo
        botonDescifrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File inputFile = fileChooser.getSelectedFile();
                    String outputFile = inputFile.getAbsolutePath() + ".descifrado";
                    try {
                        // Descifrar el archivo usando DES en modo ECB con PKCS5Padding
                        Cipher cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        cifrador.init(Cipher.DECRYPT_MODE, clave);

                        FileInputStream entrada = new FileInputStream(inputFile);
                        FileOutputStream salida = new FileOutputStream(outputFile);

                        byte[] buffer = new byte[1000];
                        byte[] bufferdescifrado;

                        int bytesleidos = entrada.read(buffer, 0, 1000);
                        while (bytesleidos != -1) {
                            bufferdescifrado = cifrador.update(buffer, 0, bytesleidos);
                            salida.write(bufferdescifrado);
                            bytesleidos = entrada.read(buffer, 0, 1000);
                        }

                        bufferdescifrado = cifrador.doFinal();
                        salida.write(bufferdescifrado);

                        entrada.close();
                        salida.close();
                        areaTexto.setText("Archivo descifrado guardado en: " + outputFile);
                    } catch (Exception ex) {
                        areaTexto.setText("Error al descifrar el archivo: " + ex.getMessage());
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
