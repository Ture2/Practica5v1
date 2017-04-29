package es.ucm.fdi.tp.view;

import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Mostrara los mensajes en una barra orientada a la derecha de la ventana. 
 *
 */
public class MessageViewerComp<S extends GameState<S,A>, A extends GameAction<S,A>> extends MessageViewer<S,A> {
	private JTextArea msgArea;
	
	public MessageViewerComp(){
		initGUI();
	}
	
	/**
	 * Metodo que crea el area de texto y lo coloca en el JPanel
	 */
	private void initGUI() {
		JPanel txtPanel = new JPanel();
		msgArea = new JTextArea(15,20); 
		msgArea.setPreferredSize(new Dimension(200, 300));
		msgArea.setEditable(false);
		JScrollPane txtScroll = new JScrollPane(msgArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		txtPanel.add(txtScroll);
		window.add(txtPanel);
	}
		@Override
	public void addContent(String msg) {
		msgArea.append(msg);
		
	}

	@Override
	public void setContent(String msg) {
		msgArea.setText(msg);
	}

	//para esta clase enable, disable y update no hacen nada.
	@Override
	public void enable() {//Always Enable, so no use}

	@Override
	public void disable() {//Always Enable, so no use}

	@Override
	public void update(S state) {}

	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGameController(GameController<S, A> gameCntrl) {
		// TODO Auto-generated method stub
		
	}

}
