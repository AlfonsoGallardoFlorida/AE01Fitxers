package es_florida_avaluable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemEvent;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDir;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtPalabra;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtDir = new JTextField();
		txtDir.setBounds(74, 25, 150, 20);
		txtDir.setToolTipText("Elegix el directori que vuigues consultar");
		contentPane.add(txtDir);
		txtDir.setColumns(10);
		
		JLabel lblDirectori = new JLabel("Directori");
		lblDirectori.setBounds(14, 28, 59, 14);
		contentPane.add(lblDirectori);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 159, 465, 300);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		
		
		JRadioButton rdbAscen = new JRadioButton("Ascendent");
		rdbAscen.setBounds(389, 10, 109, 23);
		rdbAscen.setSelected(true);
		buttonGroup.add(rdbAscen);
		contentPane.add(rdbAscen);
		
		JRadioButton rdbDescend = new JRadioButton("Descendent");
		rdbDescend.setBounds(389, 42, 109, 23);
		buttonGroup.add(rdbDescend);
		contentPane.add(rdbDescend);

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setBounds(375, 78, 100, 22);
		comboBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		            if(rdbAscen.isSelected()) {
		                Funcions.filtrarFitxersAscen(txtDir.getText(), comboBox.getSelectedItem().toString(), textArea);
		            } else {
		                Funcions.filtrarFitxersDescen(txtDir.getText(), comboBox.getSelectedItem().toString(), textArea);
		            }
		        }
		    }
		});
		comboBox.setToolTipText("Filtra el directori");
		contentPane.add(comboBox);
		comboBox.addItem("Nom");
		comboBox.addItem("Grandària");
		comboBox.addItem("Data");
		
		rdbAscen.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Funcions.filtrarFitxersAscen(txtDir.getText(), comboBox.getSelectedItem().toString(), textArea);
		    }
		});

		rdbDescend.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Funcions.filtrarFitxersDescen(txtDir.getText(), comboBox.getSelectedItem().toString(), textArea);
		    }
		});
		JButton bttLlistar = new JButton("Llistar");
		bttLlistar.setBounds(268, 24, 89, 23);
		bttLlistar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dir = txtDir.getText();
				String item = comboBox.getSelectedItem().toString();
				boolean isAscendent = rdbAscen.isSelected();
				
				if (isAscendent) {
					Funcions.filtrarFitxersAscen(dir, item, textArea);
				} else {
					Funcions.filtrarFitxersDescen(dir, item, textArea);
				}
			}
		});
		
		contentPane.add(bttLlistar);
		
		txtPalabra = new JTextField();
		txtPalabra.setBounds(74, 77, 150, 23);
		contentPane.add(txtPalabra);

		JButton bttBuscar = new JButton("Buscar");
		bttBuscar.setBounds(268, 77, 93, 23);
		bttBuscar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        textArea.setText("");
		        Funcions.buscarEnArxius(txtDir.getText(), txtPalabra.getText(), textArea);
		    }
		});
		contentPane.add(bttBuscar);

		
		JLabel lblParaula = new JLabel("Paraula");
		lblParaula.setBounds(14, 81, 46, 14);
		
		contentPane.add(lblParaula);
		
		JButton bttFusionar = new JButton("Fusionar");
		bttFusionar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setMultiSelectionEnabled(true);
		        int seleccion = fileChooser.showOpenDialog(null);

		        if (seleccion == JFileChooser.APPROVE_OPTION) {
		            File[] arxius = fileChooser.getSelectedFiles();
		            
		            if (arxius.length >= 2) {
		                String nomNouArxiu = JOptionPane.showInputDialog(null, "Ingrese el nom del nou arxiu:");

		                if (nomNouArxiu != null && !nomNouArxiu.isEmpty()) {
		                    List<String> rutasArxius = new ArrayList<>();
		                    for (File archivo : arxius) {
		                        rutasArxius.add(archivo.getAbsolutePath());
		                    }

		                    String rutaArchivoDestino = arxius[0].getParentFile() + File.separator + nomNouArxiu;
		                    
		                    Funcions.fusionarVariosFitxers(rutasArxius, rutaArchivoDestino);
		                    JOptionPane.showMessageDialog(null, "Arxius fusionats amb éxit.");
		                }
		            } else {
		                JOptionPane.showMessageDialog(null, "Per favor seleccione al menys dos arxius per a fusionar.");
		            }
		        }
		    }
		});


		bttFusionar.setBounds(268, 125, 89, 23);
		contentPane.add(bttFusionar);
	}

}
