package village;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Chart2 {

	public static final float HEIGHT = 400.0f;
	public static final float LENGTH = 800.0f;
	public final static int STEPS = 2;
	private Vector2f position;
	private float maxValue;
	
	private ChartCurve total;

	private List<ChartCurve> list;
	private int index;

	public Chart2(Vector2f position) {
		this.position = position;
		list = new ArrayList<ChartCurve>();
		index = 0;
		total = new ChartCurve(Color.white, this);
	}

	public void addChartCurve(ChartCurve cc){
		cc.init(index);
		list.add(cc);
	}

	public void removeChartCurve(ChartCurve cc){
		list.remove(cc);
		maxValue = 0.0f;
		newMax();
	}

	public void update(){
		index++;
		
		float v = 0.0f;
		
		for(ChartCurve cc : list){
			cc.update(index);
			if(cc != total)
				v += cc.getLastValue();
			
		}
		total.addPoint(v);
		shift();
	}
	
	public void shift(){
		if(index * STEPS >= LENGTH){
			index -= STEPS;
			newMax();
		}
	}
	
	public void newMax(){
		for(ChartCurve cc : list){
			if(maxValue < cc.getMaxValue()){
				maxValue = cc.getMaxValue();
			}
		}
	}

	public void render(Camera cam){

		drawLines(Color.gray);

		for(ChartCurve cc : list){
			cc.render(position, cam, maxValue);	
		}
		
		float x = position.getX();
		float y = position.getY();
		
		float width = 1.0f;
		
		Rendering.drawLine(new Vector2f(x, y), new Vector2f(x, y - HEIGHT), Color.white, width);
		Rendering.drawLine(new Vector2f(x, y), new Vector2f(x + LENGTH, y), Color.white, width);

		Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), -1, 1), Color.white, width);
		Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), 1, 1), Color.white, width);

		Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, -1), Color.white, width);
		Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, 1), Color.white, width);

	}

	public void drawLines(Color color){
		int step = (int) (maxValue/10);
		int closest = (int) 0;

		float s = HEIGHT / 10;

		float x = position.getX();

		for(int i = 0 ; i < 10; i++){
			float newY = (position.getY() - s*i);

			Rendering.drawLine(new Vector2f(x, newY), new Vector2f(x + LENGTH, newY), color, 1.0f);
			Rendering.printScreen(x + LENGTH, newY - 15, ""+closest, 1);
			closest += step;
		}
	}

	/**
	 * @param p
	 * @param direction
	 * -1 for negative, +1 for positive
	 */
	public Vector2f arrow(Vector2f p, int xDirection, int yDirection){
		Vector2f p2 = p;
		int coef = 5;
		if(xDirection == 1){
			if(yDirection == 1){
				p2.setY(p2.getY() + coef);

			}
			if(yDirection == -1){
				p2.setY(p2.getY() - coef);
			}
			p2.setX(p2.getX() + coef);

			return p2;
		}

		if(xDirection == -1){
			if(yDirection == 1){
				p2.setY(p2.getY() + coef);

			}
			if(yDirection == -1){
				p2.setY(p2.getY() - coef);
			}
			p2.setX(p2.getX() - coef);
			return p2;
		}

		return p2;
	}
}
