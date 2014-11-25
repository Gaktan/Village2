package algos;

import village.Random;
import village.Village;
import village.Villager;

public class AlgoBaseX100 extends Algo {

	private int health;
	private boolean dude;
	private int babyCoolDown;
	private static final int MAX_BABIES = 5;
	private int kids;
	private static final int LIFE_EXPECTANCY = 60*100;
	private static final int MAX_HEALTH_FOR_BABIES = 65*100;
	private static final int MAX_TIME_TO_MAKE_BABY = 15*100;
	private int healthToDie;

	public AlgoBaseX100(boolean dude, Villager villager){
		health = (int) (LIFE_EXPECTANCY + Math.random() * (villager.getVillage().climat.difficulty * LIFE_EXPECTANCY));
		this.dude = dude;
		kids = 0;

		healthToDie = (int) (LIFE_EXPECTANCY + villager.getVillage().climat.difficulty * LIFE_EXPECTANCY);
	}

	@Override
	public Villager makeBaby(Villager villager) {
		Village village = villager.getVillage();

		if(village.getPopulation() <= village.getMAX_POP()){	//there is room
			if(!dude){											//girl
				if(kids <= (MAX_BABIES*village.climat.ressources)){							//if less than the maximum amount of babies
					if(health > 18 && health < MAX_HEALTH_FOR_BABIES){				//18 - 60
						if(babyCoolDown == 0){					//wants kids
							if(village.getPopulation() > 2){	//people in town
								int x = 0 + (int) (Math.random() * (100 - 0));
								if(x > (100) - 25){						//25% chance making baby
									Villager baby = new Villager(village, Random.randBool());

									AlgoBaseX100 algo = (AlgoBaseX100) baby.algo;
									algo.health = 0;

									kids++;
									babyCoolDown = 1 + (int) (Math.random() * 
											((MAX_TIME_TO_MAKE_BABY * village.climat.ressources) 
													- 1));
									return baby;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean updateHealth(Villager villager) {

		Village village = villager.getVillage();

		health++;

		if(health < healthToDie){
			int x = (int) (Math.random() * 10000);

			if(x < 2){
				return true;
			}
		}

		if(health >= healthToDie){
			int x = (int) (Math.random() * 100);

			if(x < 80){
				return true;
			}
		}
		if(!dude){
			if(babyCoolDown > 0){
				babyCoolDown--;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "AlgoBase [health=" + health + ", dude=" + dude
		+ ", babyCoolDown=" + babyCoolDown + ", kids=" + kids + "]";
	}
}
