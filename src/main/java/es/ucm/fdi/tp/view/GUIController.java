package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameTable;

/**
 * 
 * GUIController, como su nombre indica, controla la Interfaz Grafica de Usuario e implementa a GameController.
 *  Inicia, para y reinicia el juego ademas de realizar los movimientos para cada jugador.
 *
 * @param <S>
 * @param <A>
 */
public class GUIController<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameController<S,A> {
	private GamePlayer randPlayer;
	private GamePlayer smartPlayer;
	private PlayerMode playerMode;
	private int playerId;
	private GameTable<S,A> game;
	
	public GUIController(int playerId, GamePlayer randPlayer, GamePlayer smartPlayer, GameTable<S,A> game){
		this.playerId = playerId;
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPlayer;
		this.game = game;
		this.playerMode = PlayerMode.Manual;
	}
	
	//Hay que ver si las acciones son validas. O ya lo comprobamos al hacer requestAction?
	@Override
	public void makeManualMove(A a) {
		if(playerId == game.getState().getTurn())
			a.applyTo(game.getState());
	}

	@Override
	public void makeRandomMove() {
		if(playerId == game.getState().getTurn()){
			A action = randPlayer.requestAction(game.getState());
			game.execute(action);
		}	
	}

	@Override
	public void makeSmartMove() {
		if(playerId == game.getState().getTurn()){
			A action = smartPlayer.requestAction(game.getState());	
			game.execute(action);
		}
	}

	@Override
	public void restartGame() {
		game.stop();
		game.start();
	}

	@Override
	public void stopGame() {
		game.stop();
	}

	//a este metodo se le llama cuando el usuario selecciona un nuevo modo de juego.
	@Override
	public void changePlayerMode(PlayerMode p) {
		playerMode = p;
		if(p.equals(PlayerMode.Smart)|| p.equals(PlayerMode.Random)) decideMakeAutomaticMove();
	}

	private void decideMakeAutomaticMove() {
		if(!playerMode.equals(PlayerMode.Manual)){
			if(playerMode.equals(PlayerMode.Smart)) 
				makeSmartMove();
			
			if(playerMode.equals(PlayerMode.Random))
				makeRandomMove();
		}
	}

	//este metodo lo llamara la vista cuando le llegue una notificacion del modelo. Si el evento es info o change, y es el turno del
	//jugador, se llama a decideMakeA...Move();
	@Override
	public void handleEvent(GameEvent<S, A> e) {
		if(e.equals(EventType.Info) || e.equals(EventType.Change)){
			if(playerId == e.getState().getTurn()){
				decideMakeAutomaticMove();
			}
		}
	}

	@Override
	public PlayerMode getPlayerMode() {
		return playerMode;
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

}
