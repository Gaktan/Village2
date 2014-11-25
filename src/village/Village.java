package village;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import algos.*;


public class Village extends Entity{

	private List<Villager> villagerList;
	private int population;
	private String name;
	private Chart popChart;
	private final float SIZE_LIM = 350;
	private final int MAX_POP = 3000;
	private float maxSize;
	private boolean growing;
	private float maxVelocity;
	public Iterator<Villager> iterator;
	public Class<? extends Algo> typeAlgo;
	public Climat climat;
	public float maxRessource;
	public int age;
	public int maxPop;

	public float tauxNat = 0.045f;
	public float tauxMort = 0.040f;


	private VillageList owner;

	public Village(float x, float y, String name, Color color, Class<? extends Algo> c) {
		this(new Vector2f(x, y), name, color, c);
	}

	public Village(Vector2f position, String name, Color color, Class<? extends Algo> c) {
		super(position, 0, 0, false, false);
		this.name = name;
		setColor(color);

		villagerList = new ArrayList<Villager>();
		population = 0;
		maxSize = 20;
		growing = true;
		setLength(maxSize); setHeight(maxSize);

		popChart = new Chart(new Vector2f(400, 200), false);

		typeAlgo = c;

		int nb = new java.util.Random().nextInt(Climat.getNbClimats());

		climat = new Climat(Climat.getClimat(nb));
		maxRessource = climat.getRessources();

		age = 0;
		maxPop = 0;
	}

	public void addVillager(Villager v){
		population++;
		villagerList.add(v);
		v.setVillage(this);
		if(maxSize < SIZE_LIM && growing){
			maxSize += 0.025f;
		}
		else
			growing = false;

		v.setDestination(v.getPosition());

		if(maxPop < population)
			maxPop = population;
	}

	public void remVillager(Villager v){
		population--;
		villagerList.remove(v);
	}

	public void render(Camera cam){
		for(Villager v : villagerList){
			if(cam.collide(v)){
				v.render();
			}
		}	
		popChart.render(cam, getColor());

		if(owner.isDisplayNames()){
			float x = getX() + getLength() + 20;
			float y = getY() - 20 ;
			Rendering.printScreen(x,  y+=20,  name, 2);
			Rendering.printScreen(x,  y+=20,  ""+population, 0);
			Rendering.printScreen(x,  y+=20,  typeAlgo.getName(), 0);
			Rendering.printScreen(x,  y+=20,  climat.name, 0);

			int a = age;
			if(typeAlgo.equals(AlgoBaseX100.class)){
				a /= 100;
			}
			Rendering.printScreen(x,  y+=20,  a + " ans", 0);
		}
	}

	public void update(float delta){		
		for(Villager v : villagerList){
			if(v.isArrived()){
				destination(v, null);
			}
			v.update(delta);
		}
		maxVelocity = 0.2f / getSize();		//set the max speed of villagers
	}

	public void updateBaby(){
		setHeight(maxSize); setLength(maxSize);
		
		int oldPop = population;

		Iterator<Villager> it = villagerList.iterator();
		List<Villager> temp = new ArrayList<Villager>();

		while(it.hasNext()){
			Villager v = it.next();

			Villager v2 = v.makeBaby();
			if(v2 != null){
				temp.add(v2);
			}
		}

		int nbNaissances = 0;

		if(!temp.isEmpty()){
			Iterator<Villager> it2 = temp.iterator();
			while(it2.hasNext()){
				Villager v2 = it2.next();
				addVillager(v2);

				nbNaissances++;
			}
		}
		
		float tempTauxNat = tauxNat;
		if(oldPop != 0)
			tempTauxNat = (float)(nbNaissances) / (float)(oldPop);
		
		if(nbNaissances > 0)
			tauxNat = (tempTauxNat + tauxNat) / 2;
		System.out.println("taux nat : " + tauxNat);
		
		popChart.addValue(population);
		age++;
	}

	public void populate(int amount){
		Villager v;
		boolean dude;
		for(int i = 0 ; i < amount ; i++){
			dude = Random.randBool();
			v = new Villager(this, dude);
			addVillager(v);
		}
	}

	public void clear(){
		villagerList.clear();
		population = 0;
	}

	public void drawBoundaries(){
		Vector2f a1 = getPosition();
		Vector2f a2 = new Vector2f(getX() + getSize(), getY());
		Vector2f b1 = new Vector2f(getX(), getY() + getSize());
		Vector2f b2 = new Vector2f(getX() + getSize(), getY() + getSize());

		Rendering.renderQuad(this, climat.color);

		float width = 3.0f;

		Rendering.drawLine(a1, a2, this.getColor(), width);
		Rendering.drawLine(a2, b2, this.getColor(), width);
		Rendering.drawLine(a1, b1, this.getColor(), width);
		Rendering.drawLine(b1, b2, this.getColor(), width);
	}

	public void updateHealth(){
		int oldPop = population;
		
		int nbMorts = 0;

		List<Villager> temp = new ArrayList<Villager>();
		
		for(Villager v : villagerList){
			boolean c = v.updateHealth();
			
			if(c) temp.add(v);
		}
		
		for(Villager v2 : temp){
			remVillager(v2);

			nbMorts++;
		}
		
		float tempTauxMort = tauxMort;
		if(oldPop != 0)
			tempTauxMort = (float)(nbMorts) / (float)(oldPop);
		if(tempTauxMort != 0)
			tauxMort = tempTauxMort;
		
		System.out.println("Taux mort : " + tauxMort + "\n");
	}

	public Vector2f randomCoordInVillage(){		
		float x = Random.randFloat(getX(), getX() + getLength());
		float y = Random.randFloat(getY(), getY() + getHeight());

		return new Vector2f(x, y);
	}

	public void destination(Villager v, Vector2f p){

		if(p == null)
			v.setDestination(randomCoordInVillage());
		else
			v.setDestination(p);

		Vector2f a = bToA(v.getDestination(), v.getPosition());
		a.setX(a.getX() * maxVelocity);
		a.setY(a.getY() * maxVelocity);
		v.setVelocity(a);	
		v.setArrived(false);
	}

	public Vector2f bToA(Vector2f destination, Vector2f position){

		float x = destination.x - position.x;
		float y = destination.y - position.y;

		return new Vector2f(x, y);
	}

	public void printDeath(){
		System.err.println(name + " s'est éteint après sa fondation il y a " 
				+ age + " ans.\nSon climat était " + climat.name
				+"\nSa population maximum était de : " + maxPop + "\n");
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	public float getSIZE_LIM() {
		return SIZE_LIM;
	}

	public int getMAX_POP() {
		return MAX_POP;
	}

	public boolean isGrowing() {
		return growing;
	}

	public void setGrowing(boolean growing) {
		this.growing = growing;
	}

	public VillageList getOwner() {
		return owner;
	}

	public void setOwner(VillageList owner) {
		this.owner = owner;
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public List<Villager> getVillagerList() {
		return villagerList;
	}

}
