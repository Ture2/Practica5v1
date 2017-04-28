package es.ucm.fdi.tp.view;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

public class GameContainer<S extends GameState<S,A>, A extends GameAction<S,A>> extends GUIView<S,A> implements GameObserver {
	private GUIView<S, A> gameView;
	private MessageViewer<S, A> messageViewer;
	private PlayersInfoViewer<S, A> playersInfoViewer;
	private ControlPanel<S, A> controlPanel;
	private GameController<S, A> gameCtrl;
	
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

	private void handleEvent(GameEvent e) {		//Menejar Eventos
		switch ( e.getType() ) {
		case Change:
			break;
		case Error:
			break;
		case Info:
			break;
		case Start:
				initGUI();
			break;
		case Stop:
			break;
		default:
			break;
		
		
		}
		SwingUtilities.invokeLater(new Runnable() { 
			 public void run() { gameCtrl.handleEvent(e); } 
		});
	} 
	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(S state) {
		// TODO Auto-generated method stub
		
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
