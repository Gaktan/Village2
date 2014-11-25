package village;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import algos.*;
import algos.AlgoBase;
import algos.AlgoBaseX100;
import algos.AlgoMalthus;

public class Villager extends Entity{

	public Algo algo;
	private Village village;
	private Vector2f destination;
	private boolean arrived;

	public Villager(Village vi, boolean dude){
		this(vi.randomCoordInVillage(), dude, vi);
	}

	public Villager(Vector2f position, boolean dude, Village village) {
		//size = 3
		super(position, 3,3, true, true);

		arrived = false;
		
		this.village = village;

		if(!dude)
			setColor(new Color(Color.pink));
		else
			setColor(new Color(0, 0.5f, 1));
		
		if(village.typeAlgo.equals(AlgoBase.class)){
			algo = new AlgoBase(dude, this);
		}
		if(village.typeAlgo.equals(AlgoBaseX100.class)){
			algo = new AlgoBaseX100(dude, this);
		}
		if(village.typeAlgo.equals(AlgoMalthus.class)){
			algo = new AlgoMalthus(dude, this);
		}

		boolean rand = Random.randBool();
		setRender(rand);
		setMoving(rand);
	}

	public Villager makeBaby(){
		if(algo == null)
			return null;
		return algo.makeBaby(this);
	}

	public boolean updateHealth(){
		if(algo == null)
			return false;
		return algo.updateHealth(this);
	}


	public void update(float delta) {
		if(isMoving()){
			super.update(delta);
		}

		int diff = 10;
		Entity e = new Entity(destination, diff, diff, false, false);
		if(collide(e)){
			arrived = true;
			setvX(0); setvY(0);
		}

		if(getX() + getLength() > village.getX() + village.getLength()){
			setX(village.getX() + village.getLength() - getLength());
			setvX(Random.randFloat(village.getMaxVelocity(), 0));
		}
		if(getX() < village.getX()){
			setX(village.getX());
			setvX(Random.randFloat(0, village.getMaxVelocity()));
		}
		if(getY() + getHeight() > village.getY() + village.getHeight()){
			setY(village.getY() + village.getHeight() - getHeight());
			setvY(Random.randFloat(-village.getMaxVelocity(), 0));
		}
		if(getY() < village.getY()){
			setY(village.getY());
			setvY(Random.randFloat(0, village.getMaxVelocity()));
		}
	}

	public void render(){
		if(isRender()){
			Rendering.renderQuad(this, getColor());
		}
	}


	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
	}

	public boolean isArrived() {
		return arrived;
	}

	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
}
