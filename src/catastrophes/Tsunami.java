package catastrophes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import village.Village;
import village.Villager;

public class Tsunami extends Catastrophe{

	private Village village;
	private List<Vague> vagues;
	private static final int MIN_WAVES = 10, MAX_WAVES = 40;

	public Tsunami(Village village){
		this.village = village;

		state = STARTED;

		vagues = new ArrayList<Vague>();

		int waves = (int) (MAX_WAVES - (Math.random() * (MAX_WAVES-MIN_WAVES)));

		for(int i = 0; i < waves; i++){
			Vector2f vec = village.randomCoordInVillage();
			vec.x -= 300;
			Vague v = new Vague(vec);
			v.setvX(0.05f);
			v.setColor(Color.blue.brighter().brighter());
			vagues.add(v);
		}
	}

	public void update(float delta){

		if(vagues.isEmpty()){
			stop();
		}
		List<Vague> vToRemove = new ArrayList<Vague>();
		List<Villager> toRemove = new ArrayList<Villager>();
		for(Vague v : vagues){
			if(v.life > 0)
				v.life--;
			else{
				vToRemove.add(v);
				continue;
			}
			v.update(delta);

			for(Villager vi : village.getVillagerList()){
				if(v.collide(vi)){
					toRemove.add(vi);
				}
			}
		}
		for(Villager vi : toRemove){
			village.remVillager(vi);
		}
		for(Vague v : vToRemove){
			vagues.remove(v);
		}
	}

	public void render(){
		for(Vague v : vagues){
			v.render();
		}
	}

	public void stop(){
		vagues = new ArrayList<Vague>();
		state = STOPPED;
	}
}
