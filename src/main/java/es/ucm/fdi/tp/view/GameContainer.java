package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class GameContainer<S extends GameState<S,A>, A extends GameAction<S,A>> extends GUIView<S,A> implements GameObserver<S,A> {
	private GUIView<S, A> gameView;
	private MessageViewer<S, A> messageViewer;
	private PlayersInfoViewer<S, A> playersInfoViewer;
	private ControlPanel<S, A> controlPanel;
	private GameController<S, A> gameCtrl;
	//private WolfAndSheepView wasView;
	//private TicTacToeView tttView;
	
	
	public GameContainer(GUIView<S, A> gameView, GameController<S, A> gameCtrl,
			 GameObservable<S, A> game){
		this.gameView = gameView;
		this.gameCtrl = gameCtrl;
		initGUI();
		game.addObserver(this);
	}
	private void initGUI() {
		this.panel = new JPanel(new BorderLayout(5,5));
		messageViewer = new MessageViewerComp<S, A>(); 
		playersInfoViewer = new PlayersInfoComp<S, A>();
		controlPanel = new ControlPanel<S, A>();
		window.setContentPane(panel); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void notifyEvent(GameEvent e) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { handleEvent(e); }
		});
	}

	private void handleEvent(GameEvent<S,A> e) {		//Menejar Eventos
		switch ( e.getType() ) {
		case Change:
				update(e.getState()); 
			break;
		case Error:
				disable();
			break;
		case Info:
				update(e.getState());
			break;
		case Start:
				initGUI();
			break;
		case Stop:
				disable();
			break;
		}
		SwingUtilities.invokeLater(new Runnable() { 
			 public void run() { gameCtrl.handleEvent(e); } 
		});
	} 
	@Override
	public void enable() {
		messageViewer.enable();
		playersInfoViewer.enable();
		controlPanel.enable();
		gameView.disable();
	}
	@Override
	public void disable() {
		messageViewer.disable();
		playersInfoViewer.disable();
		controlPanel.disable();
		gameView.disable();
	}
	@Override
	public void update(S state) {
		messageViewer.update(state);
		playersInfoViewer.update(state);
		gameView.update(state);
	}
	@Override
	public void setMessageViewer(MessageViewer<S, A> infoViewer) {
		
		
	}
	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGameController(GameController<S, A> gameCntrl) {
		gameView.setGameController(gameCntrl);
		messageViewer.setGameController(gameCntrl);
	}

}
