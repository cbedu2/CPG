package everything.AI;

import everything.AI.AICards.WolfBite;
import everything.AI.AICards.WolfDefense;
import everything.AI.AICards.WolfPack;
import everything.AI.AICards.WolfRavage;
import everything.Player;

/**
 * Created by Steve on 4/22/2017.
 */
public class Wolf extends AI {
    public Wolf(Player user){
    	super("Wolf",20,0, user);
    	this.addCard(new WolfBite());
    	this.addCard(new WolfDefense());
    	this.addCard(new WolfRavage());
    	this.addCard(new WolfPack());
    	
    	depositMoney(50);
    }
    @Override
	public void setNextCard(){	
		nextCard =randomWithRange(0,getHandSize()-1);
	}
}
