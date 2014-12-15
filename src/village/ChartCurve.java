package village;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class ChartCurve {
	
	private List<Float> list;
	private float maxValue;
	private Color color;
	private static final int STEPS = Chart2.STEPS;
	public final float HEIGHT = Chart2.HEIGHT, LENGTH = Chart2.LENGTH / STEPS;
	
	public ChartCurve(Color color, Chart2 c2) {
		list = new ArrayList<Float>();
		this.color = color;
		c2.addChartCurve(this);
	}
	
	public void render(Vector2f pos, Camera cam, float maxValue){
		if(list.size() < 2)
			return;
	
		Float previous = list.get(0);
		float preX = pos.getX();
		float preY = HEIGHT + scaled(maxValue, previous);
		
		for(Float f : list.subList(1, list.size())){
			
			float x = preX + STEPS;
			float y = pos.getY() - scaled(maxValue, f);
			
			Vector2f vec = new Vector2f(x, y);
			Vector2f preVec = new Vector2f(preX, preY);
			
			if(cam.collide(vec, preVec))
				Rendering.drawLine(preVec, vec, color, 1.0f);
			
			preX = x;
			preY = y;
		}
	}
	
	public void update(int index){
		if(list.size() >= LENGTH){
			removeFirst();
		}
	}
	
	public void addPoint(float f){
		list.add(f);
		if(f > maxValue)
			maxValue = f;
	}
	
	public void removeFirst(){
		float f = list.remove(0);
		if(f == maxValue){
			newMax();
		}
	}
	
	public void newMax(){
		maxValue = 0; 
		for(Float f : list){
			if(f > maxValue)
				maxValue = f;
		}
	}
	
	public void init(int index){
		for(int i = 0; i < index; i++){
			list.add(0.0f);
		}
	}
	
	public float getLastValue(){
		if(list.isEmpty())
			return 0.0f;
		return list.get(list.size() - 1);
	}
	
	public float scaled(float maxValue, Float dist){
		return dist / maxValue;
	}

	public float getMaxValue() {
		return maxValue;
	}
	
}
