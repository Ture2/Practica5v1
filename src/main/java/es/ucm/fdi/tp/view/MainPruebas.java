package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.extra.jcolor.ColorChooser;

public class MainPruebas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyTableModel myModel;
	private Map<Integer, Color> colors; // Line -> Color
	private ColorChooser colorChooser;
	public MainPruebas() {
		
		super("Mi primera ventana - Ejemplo 4");
		JPanel playerInfoPanel = new JPanel(new BorderLayout());
		colors = new HashMap<>();
		colorChooser = new ColorChooser(new JFrame(), "Choose Line Color", Color.BLACK);

		// names table
		myModel = new MyTableModel();
		myModel.getRowCount();
		JTable table = new JTable(myModel) {
			private static final long serialVersionUID = 1L;

			// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);

				// the color of row 'row' is taken from the colors table, if
				// 'null' setBackground will use the parent component color.
				if (col == 1)
					comp.setBackground(colors.get(row));
				else
					comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};

		table.setToolTipText("Click on a row to change the color of a player");
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					changeColor(row);
				}
			}

		});

		playerInfoPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel ctrlPabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playerInfoPanel.add(ctrlPabel, BorderLayout.PAGE_START);

		
		playerInfoPanel.setOpaque(true);
		setContentPane(playerInfoPanel);
	}

	
		private void changeColor(int row) {
		colorChooser.setSelectedColorDialog(colors.get(row));
		colorChooser.openDialog();
		if (colorChooser.getColor() != null) {
			colors.put(row, colorChooser.getColor());
			repaint();
		}
		}

	public static void main(String []args) {
		
		MainPruebas v = new MainPruebas();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				v.setVisible(true);
			}
		
		});
		
	}
	
	
}
