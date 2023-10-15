package es_florida_avaluable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.util.Arrays;
import java.util.List;

/**
 * La classe `Funcions` conté métodes per operacions en arxius i manipulació de text.
 */
public class Funcions {
	
	/**
     * Filtra y mostra els arxius en un directori, en orden ascendent.
     *
     * @param dir      Ruta del directori a filtrar.
     * @param combobox Filtre seleccionat("Nom", "Grandària" o "Data").
     * @param pane     JTextArea on es mostra la informació.
     */
	public static void filtrarFitxersAscen(String dir, String combobox, JTextArea pane) {
	    pane.setText("");
	    File directori = new File(dir);
	    File[] arrFitxers = directori.listFiles(new FiltroExtension(".txt"));

	    if (arrFitxers != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	        if (combobox.equals("Nom")) {
	            Arrays.sort(arrFitxers);
	        } else if (combobox.equals("Grandària")) {
	            Arrays.sort(arrFitxers, (a, b) -> Long.compare(a.length(), b.length()));
	        } else if (combobox.equals("Data")) {
	            Arrays.sort(arrFitxers, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
	        }

	        for (File fitxer : arrFitxers) {
	            pane.append(fitxer.getName() + ".txt\n");
	            pane.append("Ruta: " + fitxer.getAbsolutePath() + "\n");
	            long fileSize = fitxer.length();
	            pane.append("Tamany: " + fileSize + " bytes\n");
	            long lastModified = fitxer.lastModified();
	            String formattedDate = sdf.format(lastModified);

	            pane.append("Última modificació: " + formattedDate + "\n\n");
	        }
	    }else pane.append("Introduïx un directori vàlid.");

	}
	/**
     * Filtra i mostra els arxius en un directori, en ordre descendent.
     *
     * @param dir      Ruta del directori a filtrar.
     * @param combobox Filtre seleccionat ("Nom", "Grandària" o "Data").
     * @param pane     JTextArea on es mostra la informació.
     */
	public static void filtrarFitxersDescen(String dir, String combobox, JTextArea pane) {
	    pane.setText("");
	    File directori = new File(dir);
	    File[] arrFitxers = directori.listFiles(new FiltroExtension(".txt"));

	    if (arrFitxers != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	        if (combobox.equals("Nom")) {
	            Arrays.sort(arrFitxers, (a, b) -> b.getName().compareTo(a.getName()));
	        } else if (combobox.equals("Grandària")) {
	            Arrays.sort(arrFitxers, (a, b) -> Long.compare(b.length(), a.length()));
	        } else if (combobox.equals("Data")) {
	            Arrays.sort(arrFitxers, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
	        }

	        for (File fitxer : arrFitxers) {
	            pane.append(fitxer.getName() + ".txt\n");
	            pane.append("Ruta: " + fitxer.getAbsolutePath() + "\n");
	            long fileSize = fitxer.length();
	            pane.append("Tamany: " + fileSize + " bytes\n");
	            long lastModified = fitxer.lastModified();
	            String formattedDate = sdf.format(lastModified);

	            pane.append("Última modificació: " + formattedDate + "\n\n");
	        }
	    } else pane.append("Introduïx un directori vàlid.");
	}
	 /**
     * Compta el numero de vegades que una paraula apareix en un arxiu de text.
     *
     * @param archivo Ruta de l'arxiu a analitzar.
     * @param paraula Paraula que es busca a l'arxiu.
     * @return Numero de coincidències de la paraula a l'arxiu.
     */
	public static int contarCoincidenciesEnArxiu(String arxiu, String paraula) {
        int coincidencies = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arxiu))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                coincidencies += contarCoincidenciesEnLinea(linea, paraula);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coincidencies;
    }
	
	/**
	 * Compta el numero de vegades que una paraula apareix en una línia de text.
	 *
	 * @param linea   Línia de text en la qual es compta les coincidències.
	 * @param palabra Paraula que es busca a la línia de text.
	 * @return Numero de coincidències de la paraula en la línia de text.
	 */
    private static int contarCoincidenciesEnLinea(String linea, String paraula) {
        int coincidencies = 0;
        String[] paraules = linea.split(" ");

        for (String pa : paraules) {
            if (pa.equals(paraula)) {
                coincidencies++;
            }
        }

        return coincidencies;
    }
    
    /**
     * Busca una paraula en els arxius d'un directori i mostra les coincidències en un JTextArea.
     *
     * @param dir      Ruta del directori a buscar.
     * @param paraula  Paraula a buscar en els arxius.
     * @param textArea JTextArea on es mostraran les coincidències.
     */
    public static void buscarEnArxius(String dir, String paraula, JTextArea textArea) {
        textArea.setText("");

        if (!paraula.isEmpty()) {
            File directori = new File(dir);
            File[] arrFitxers = directori.listFiles(new FiltroExtension(".txt"));

            if (arrFitxers != null) {
                for (File fitxer : arrFitxers) {
                    int coincidencies = contarCoincidenciesEnArxiu(fitxer.getAbsolutePath(), paraula);
                    textArea.append(fitxer.getName() + " -> " + coincidencies + " coincidències\n\n");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Cal introduir un directori vàlid.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Cal introduir una paraula vàlida.");
        }
    }
    
    /**
     * Fusiona diversos arxius en un nou arxiu.
     *
     * @param rutasArxius       Llista de rutes dels arxius a fusionar.
     * @param rutaArxiuDestino  Ruta del nou arxiu fusionat.
     */
    public static void fusionarVariosFitxers(List<String> rutasArxius, String rutaArxiuDestino) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArxiuDestino));

            for (String rutaArxiu : rutasArxius) {
                BufferedReader br = new BufferedReader(new FileReader(rutaArxiu));

                String linea;
                while ((linea = br.readLine()) != null) {
                    bw.write(linea);
                    bw.newLine();
                }

                br.close();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
