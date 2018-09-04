import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/****************************************************************
 * computerPlayer.java
 * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
 * evaluator (SBE) function is simple: the # of stones in computerPlayer's
 * mancala minue the # in opponent's mancala.
 */




//################################################################
// computerPlayer class
//################################################################

public class computerPlayer extends Player {

    /*Use IDS search to find the best move. The step starts from 1 and keeps incrementing by step 1 until
	 * interrupted by the time limit. The best move found in each step should be stored in the
	 * protected variable move of class Player.
     */
    public void move(GameState state)
    {

    	// maxAction gets called here
    	int step = 1;
    	boolean timeLimitNotOver = true;
    	while(timeLimitNotOver){

    		int num = maxAction(state,step);
    		if(num!=-1 && !state.illegalMove(num) ){
    			this.move= num;
    		}
    		step++;
    	}

    }


    // Return best move for max player. Note that this is a wrapper function created for ease to use.
	// In this function, you may do one step of search. Thus you can decide the best move by comparing the
	// sbe values returned by maxSbe. This function should call minAction with 5 parameters.
    public int maxAction(GameState state,int maxDepth)
    {

    	// getSuccesors for state
    		int best=-1;
		    double a = -999999999;
			double b = 999999999;
			double bestMoveSbe = a ;

		double v = -999999999; //best minimax value found so far at
		// apply all possible moves
		 for(int i = 0; i<6 ; i++){
	    	 // create a new state with the current array
    		 GameState stateCfg =new GameState(state.toArray());
    		 if(!stateCfg.illegalMove(i)){
    		 if(stateCfg.applyMove(i)){
    			 v = Math.max( v, maxAction(stateCfg,1, maxDepth , a, b,i));

    		 }else{
    			 v = Math.max( v, minAction(stateCfg,1, maxDepth , a, b,i));
    		 }

    		 if(v>bestMoveSbe){
    			 bestMoveSbe = v;
    			 best =i;
    		 }
    		if (v >= b ) { return best; } // then prune remaining since
				a = Math.max(a, v); // set alpha to the v if higher than existing alpha
		 }
		 }
		return best;
		 // return value of best child
    }

	//return sbe value related to the best move for max player
    public double maxAction(GameState state, int currentDepth, int maxDepth, double alpha, double beta, int move)
    {

  		    double a = alpha;
 			double b = beta;
    	    if (currentDepth>=maxDepth || state.gameOver() ){
    	    	return sbe(state,move);
    	}
    		double v = -999999999; //best minimax value found so far at
    		// apply all possible moves
    		 for(int i = 0; i<6 ; i++){
    	    	 // create a new state with the current array
        		 GameState stateCfg =new GameState(state.toArray());

        		 //only stop applying when there is no more moves
        		 if(stateCfg.applyMove(i)){
        			 v = Math.max( v, maxAction(stateCfg,currentDepth+1, maxDepth , a, b,i));

        		 }else{
        		// after move is applied, see whose sbe value is higher, parent or this new state
        		// in order to find the sbe, one must check the next move, since the next move is
        		 // an opponents's move, call minAction to find the best sbe that could result from opponents move
        			 v = Math.max( v, minAction(stateCfg,currentDepth+1, maxDepth , a, b,i));
        		 }
        		if (v >= b ) { return v; } // then prune remaining since
   				a = Math.max(a, v); // set alpha to the v if higher than existing alpha
        	 }
    		return v; // return value of best child
    }

    //return sbe value related to the bset move for min player
    public double minAction(GameState state, int currentDepth, int maxDepth, double alpha, double beta,int move)
    {
    	GameState curr = state;
  		double a = alpha;
 			double b = beta;
    	    if (currentDepth>=maxDepth || curr.gameOver() ){
    	    	double i =  sbe(curr,move);
	    		 return i;
    	}

    		double v = 999999999; //best minimax value found so far at s
    		 for(int i = 7; i<13 ; i++){
    	    	 // create a new state with the current array
        		 GameState stateCfg =new GameState(state.toArray());

        		 //only stop applying when there is no more moves
        		if(stateCfg.applyMove(i)){
            		v = Math.min( v, minAction(stateCfg,currentDepth+1, maxDepth , a, b,i));

        		}else{
        		// after move is applied, see whose sbe value is higher, parent or this new state
        		// in order to find the sbe, one must check the next move, since the next move is
        		 // an computer's move, call maxAction to find the best sbe that could result from opponents move
        		v = Math.min( v, maxAction(stateCfg,currentDepth+1, maxDepth , a, b,i));
        		}
    			if (a >= v) { return v; }// prune remaining children
    			b = Math.min(beta, v);
    		 }
    		return v; // return value of best child
    	}




    //the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.
    private double sbe(GameState state, int ggmove)
    {
    	double diffMancala = state.stoneCount(6)-state.stoneCount(13) ;
    	 return diffMancala;

    }
}
