package village;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Color;

public class ChartCurve {
	
	private List<Float> list;
	private Color color;
	
	public ChartCurve(Color color) {
		list = new ArrayList<Float>();
		this.color = color;
	}
	
	public void addPoint(float f){
		list.add(f);
	}
	
	public void removeFirst(){
		list.remove(0);
	}
	
	public void init(int index){
		for(int i = 0; i < index; i++){
			list.add(0.0f);
		}
	}
}
