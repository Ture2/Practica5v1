package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
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
	private GameController<S,A> gameCtrl;
	
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
		panel.add(txtPanel, BorderLayout.LINE_END);
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
	public void enable() {/*Always Enable, no use*/}

	@Override
	public void disable() {/*Always Enable, no use*/}

	@Override
	public void update(S state) {
		if(gameCtrl.getPlayerId() == state.getWinner()) msgArea.append("You win !");
		if((gameCtrl.getPlayerId() != state.getWinner()) && state.getWinner() != -1) msgArea.append("You have lost!");
		
	}

	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		//There is no Player Info Viewer here
	}

	@Override
	public void setGameController(GameController<S, A> gameCntrl) {
		this.gameCtrl = gameCntrl;
	}

}
