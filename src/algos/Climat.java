package algos;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class Climat {
	
	public Color color;
	public float ressources;
	public float difficulty;
	public String name;
	
	private static List<Climat> climats = new ArrayList<Climat>();
	
	static final Climat desert = new Climat("Désert", new Color(209, 203, 115), 
			0.40f, 0.70f);
			
	static final Climat tundra = new Climat("Tundra", new Color(161, 209, 206), 
			0.50f, 0.60f);
			
	static final Climat oceanique = new Climat("Océanique", new Color(11, 140, 19), 
			0.80f, 0.50f);
	
	static final Climat tropical = new Climat("Tropical", new Color(118, 255, 0), 
			0.90f, 0.55f);
	
	static final Climat montagneux = new Climat("Montagneux", new Color(89, 105, 101), 
			0.76f, 0.60f);
	
	static final Climat equatorial = new Climat("Equatorial", new Color(124, 194, 101), 
			0.75f, 0.80f);
	
	static final Climat continental = new Climat("Continental", new Color(194, 104, 88),
			0.60f, 0.55f);
	
	public Climat(String name, Color color, float ressources, float difficulty) {
		super();
		this.color = color;
		this.ressources = ressources;
		this.difficulty = 1-difficulty;
		this.name = name;
		
		climats.add(this);
	}
	
	public Climat(Climat climat){
		this.color = climat.color;
		this.ressources = climat.ressources;
		this.difficulty = climat.difficulty;
		this.name = climat.name;
	}
	
	public static int getNbClimats(){
		return climats.size();
	}
	
	public static Climat getClimat(int i){
		return climats.get(i);
	}
	
	public float getRessources(){
		return ressources;
	}
	
	public float getDifficulty() {
		return difficulty;
	}
}
