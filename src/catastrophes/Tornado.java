package catastrophes;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import village.Entity;
import village.Random;
import village.Village;
import village.Villager;

public class Tornado extends Catastrophe{
	
	private List<TornadoThing> winds = new ArrayList<TornadoThing>();
	private Vector2f position;
	private Vector2f posDeBase;
	private Village village;
	private static final int MAX_DISTANCE = 10;
	
	public Tornado(Village village) {
		this.village = village;
		state = STARTED;
		
		position = village.randomCoordInVillage();
		posDeBase = village.randomCoordInVillage();
		
		int nbWinds = (int) (Math.random()*80);
		
		winds = new ArrayList<TornadoThing>();
		
		for(int i = 0; i < nbWinds; i++){			
			float x = Random.randFloat(position.x - MAX_DISTANCE, position.x + MAX_DISTANCE);
			float y = Random.randFloat(position.y - MAX_DISTANCE, position.y + MAX_DISTANCE);
			
			TornadoThing tt = new TornadoThing(new Vector2f(x, y), position);
			tt.setColor(Color.gray);
			
			winds.add(tt);
		}
	}
	
	@Override
	public void render() {
		for(TornadoThing tt : winds){
			tt.render();
		}
	}
	
	@Override
	public void update(float delta) {
		
		if(winds.isEmpty()){
			stop();
		}
		
		List<TornadoThing> vToRemove = new ArrayList<TornadoThing>();
		for(TornadoThing tt : winds){
			if(tt.life > 0)
				tt.life--;
			else{
				vToRemove.add(tt);
				continue;
			}
			tt.update(delta);
			
			List<Villager> toRemove = new ArrayList<Villager>();

			for(Villager vi : village.getVillagerList()){
				if(tt.collide(vi)){
					toRemove.add(vi);
				}
			}
			for(Villager vi : toRemove){
				village.remVillager(vi);
			}
		}
		
		for(TornadoThing tt : vToRemove){
			winds.remove(tt);
		}
		
		float angle = 0.002f;
		
		Entity.rotate(position, posDeBase, angle);
	}
	
	public void stop(){
		winds = new ArrayList<TornadoThing>();
		state = STOPPED;
	}
}
