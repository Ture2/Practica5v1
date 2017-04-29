package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    // define fields here
  private GameEvent<S,A> event; 
  private List<GameObserver<S,A>> ob = new ArrayList<GameObserver<S,A>>();
  private S initialState;
  private S actualState;
  private GameError error;
  private boolean stop = false;
  private boolean start = false;

    public GameTable(S initState) {
    	
    	this.initialState = initState;
    	this.actualState = initState;
    }
    
    public void start() {
    	actualState = initialState;
    	event = new GameEvent<S,A>(EventType.Start, null, actualState, null, actualState.getGameDescription());
    	this.start = true;
    	for(int i = 0; i < ob.size(); i++) ob.get(i).notifyEvent(event);
    }
    public void stop() { 
    	error = new GameError("No es posible parar el juego de nuevo. ");
    	if(this.stop){
    		event = new GameEvent<S,A>(EventType.Stop, null, actualState, 
    				error, actualState.getGameDescription());
    		for(int i = 0; i < ob.size(); i++) ob.get(i).notifyEvent(event);
    		throw error;
    		
    	}else{
	    	event = new GameEvent<S,A>(EventType.Stop, null, actualState, null, actualState.getGameDescription());
	    	for(int i = 0; i < ob.size(); i++) ob.get(i).notifyEvent(event);
	    	this.stop = true;
    	}
    }
    
    public void execute(A action) {
	if(this.stop || !this.start){
        	error = new GameError("No es posible realizar la accion. ");
        	event = new GameEvent<S,A>(EventType.Error, null, actualState, 
    				error, actualState.getGameDescription());
    		for(int i = 0; i < ob.size(); i++) ob.get(i).notifyEvent(event);
    		throw error;
    	}
    	else{
    		actualState = action.applyTo(actualState);
    		event = new GameEvent<S,A>(EventType.Change, action, actualState, null, actualState.getGameDescription()); //no se si se le pasa el actualState.
    		for(int i = 0; i < ob.size(); i++) ob.get(i).notifyEvent(event); 
    	}
    }
    
    public S getState() {
    	return actualState;
    }

    public void addObserver(GameObserver<S, A> o) {
    	ob.add(o);
    }
    
    public void removeObserver(GameObserver<S, A> o) {
    	ob.remove(o);
    }
}
