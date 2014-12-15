package village;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VillageList {
	
	private List<Village> list;
	private boolean displayNames;
	private int globalTime=0;
	private int lvlTech=1;
	private boolean upTech=false;

	public VillageList(){
		list = new ArrayList<Village>();
		displayNames = true;
	}


	public void addVillage(Village v){
		list.add(v);
		v.setOwner(this);
		v.render();
	}

	public void remVillage(Village v){
		list.remove(v);
		v.printDeath();
	}

	public void updateStat(){

		if(this.globalTime==this.lvlTech*1000 && this.lvlTech<20){ //On vérifie si il est temps de faire
			lvlTech++;											  //passer un niveau techonologique.
			upTech = true;
			System.out.println(lvlTech);
		}


		List<Village> temp = new ArrayList<Village>();

		if(!list.isEmpty()){
			globalTime++;

			for(Village v : list){

				v.updateBaby();
				v.updateHealth();

				if(upTech || !v.isUpToDate()){
					tech(v); //Si le village n'est pas à jour technologiquement 
					//ou si la technologie a augmenté cette année on le met à jour.
				}

				if(v.getPopulation() <= 0){
					temp.add(v);
				}

				boolean collide = false;
				for(Village v2 : list){
					if(v != v2){
						if(v.collide(v2)){
							v.setGrowing(false);
							v2.setGrowing(false);
							collide = true;
						}
					}
				}

				if(!collide){
					v.setGrowing(true);
				}
			}

			if(upTech){
				upTech=false; //Une fois le parcours de cette année fini
				//on ne considère plus que la technologie a augmenté
			}
			if(!temp.isEmpty()){
				Iterator<Village> it2 = temp.iterator();
				while(it2.hasNext()){
					Village v2 = it2.next();
					list.remove(v2);
					v2.printDeath();
				}
			}

		}
		else{
			globalTime=0; //Si la liste de village est vide 
			lvlTech=1;   //on remet le temps et la technologie au point initial
		}

	}

	public void update(float delta){
		for(Village v : list){
			v.update(delta);
		}
	}

	public void tech(Village v){
		if(v.isUpToDate()){ //Si le village est à jour on ne lui accorde qu'un seul bonus technologique
			v.upTech();
		}
		else{// Si il ne l'est pas alors 
			// on lui accorde autant de bonus que de technologies ont été dévérouillées
			for(int i=1;i<=lvlTech;i++){
				v.upTech();
			}
			v.setUpToDate(true);
		}
	}

	public void saveStuffAsFile(Village v){

		// CLIMAT; taux naissance
		// tauxMort; tauxNat
		// climat; couleur rouge

		try{
			File file = new File("total.xls");

			if (!file.exists()) {
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

				//bw.write("climat; naissance\n"); 
				//bw.write("TauxMort; TauxNat\n");
				bw.write("Nom; Couleur\n"); 
				bw.close();
			}

			BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));

			StringBuilder builder = new StringBuilder();
			String aux = "";

			while ((aux = br.readLine()) != null) {
				builder.append(aux);
				builder.append('\n');
			}

			String content = builder.toString();
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(content);
			float va = round(v.tauxNat, 2);
			float va2 = round(v.tauxMort, 2);
			//bw.write(v.climat.name + "; " + va);
			//bw.write(va2 + "; " + va);
			bw.write(v.climat.name +"; " + v.getColor().getRed());
			bw.close();

		}catch(Exception e){

		}
	}

	public float round(float v, int n){
		int mul = 1;
		for(int i = 0; i < n; i++){
			mul *= 10;
		}
		return (float)Math.round(v * (mul)) / mul;
	}

	public void render(Camera cam){
		for(Village v : list){
			v.drawBoundaries();
			v.render(cam);
		}
	}

	public boolean sameName(String str){
		for(Village v : list){
			if(v.getName().equals(str)){
				return true;
			}
		}
		return false;
	}

	public boolean isDisplayNames() {
		return displayNames;
	}


	public void setDisplayNames(boolean displayNames) {
		this.displayNames = displayNames;
	}


	public List<Village> getList() {
		return list;
	}


	public void setList(List<Village> list) {
		this.list = list;
	}
}