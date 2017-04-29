package es.ucm.fdi.tp.view;

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
	private WolfAndSheepView wasView;
	private TicTacToeView tttView;
	
	
	public GameContainer(GUIView<S, A> gameView, GameController<S, A> gameCtrl,
			 GameObservable<S, A> game){
		this.gameView = gameView;
		this.gameCtrl = gameCtrl;
		initGUI();
		game.addObserver(this);
	}
	private void initGUI() {
		messageViewer = new MessageViewerComp<S, A>();
		playersInfoViewer = new PlayersInfoComp<S, A>();
		controlPanel = new ControlPanel<S, A>();
		
		
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
				messageViewer.addContent(e.getError().getMessage());
			break;
		case Info:
				messageViewer.addContent(e.getError().getMessage());
			break;
		case Start:
				initGUI();
				messageViewer.addContent(e.getError().getMessage());
			break;
		case Stop:
				disable();
				messageViewer.addContent(e.getError().getMessage());
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
		//wasView.enable();
	}
	@Override
	public void disable() {
		messageViewer.disable();
		playersInfoViewer.disable();
		controlPanel.disable();
		//wasView.disable();
	}
	@Override
	public void update(S state) {
		messageViewer.update(state);
		playersInfoViewer.update(state);
		controlPanel.update(state);
		//wasView.update(state);
	}
	@Override
	public void setMessageViewer(MessageViewer<S, A> infoViewer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGameController(GameController<S, A> gameCntrl) {
		// TODO Auto-generated method stub
		
	}

}
