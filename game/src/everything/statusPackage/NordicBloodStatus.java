package everything.statusPackage;
import everything.*;
public class NordicBloodStatus extends Status{
	
	public NordicBloodStatus(Player user){
		this.user=user;
		turns=3;
	}

	@Override
	public void statusEffectBeginning(){
		turns--;
		if(turns==0){
		user.decreaseDefense(3);
		}
	}
}
