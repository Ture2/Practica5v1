package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleView<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameObserver<S,A>{
	
	private ConsoleController<S, A> controller;
	private GameObserver<S, A> gameTable;
	private GameTable<S, A> game;
	private GameEvent<S,A> event;
	
	public ConsoleView(GameObserver<S, A> gameTable) {
		this.gameTable = gameTable;
	}
	
	public ConsoleView(GameTable<S, A> game) {
		this.game = game;
	}
	
	public GameEvent<S,A> getEvent(){
		return event;
	}
	
	public void notifyType(EventType type){
		String gameName = getEvent().getState().getGameDescription();
		
		switch(type){
		case Start:
			System.out.println("Comienza el juego " + gameName + " !");
			break;
		case Stop:
			System.out.println("Fin del juego " + gameName + " !");
			break;
		case Change:
			System.out.println("El estado del juego ha cambiado!" + System.getProperty("line.separator") + 
			 "Es el turno del jugador " +  event.getState().getTurn() + " ." + System.getProperty("line.separator") +
			"Jugador"+ event.getState().getTurn() + " : " + event.getAction().toString());
			break;
		case Error:
			System.out.println("Se ha producido un error durante el juego!");
			System.out.println(event.getError().getMessage());
			break;
		case Info:
			System.out.println("Informacion sobre el juego.");
			break;
		default:
			break;
		}
	}
	
	public void notifyWinner(){
		String endText = "";
		int winner = event.getState().getWinner(); 
		
		if (winner == -1) 
			endText += "Empate!";
	    else 
			endText += "Jugador " + game.getState().getTurn() + " :" + " ha ganado la partida!";
		
		System.out.println(endText);
	}

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		e = event;
		boolean partidaAcabada = e.getState().isFinished();
		game.addObserver(gameTable);  //gameObservable se registra como observer. ???
		notifyType(e.getType());
		
		if(partidaAcabada) notifyWinner();
		
	}

}


