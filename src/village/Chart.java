package village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Chart {

	private int i;
	private float x, y;
	private List<ChartPoint> list;
	private final float HEIGHT = 400, LENGTH = 800;
	private static float maxValue;
	private boolean global;
	private static int iGlobal;
	private final int STEPS = 2;
	private static final float width = 1.0f;

	public Chart(Vector2f position, boolean global){
		x = position.getX();
		y = position.getY();
		list = new ArrayList<ChartPoint>();
		i = 0;
		maxValue = 0;
		this.global = global;

		if(global){
			iGlobal = i;
		}
		if(iGlobal != i){
			init();
		}
	}

	public Chart(){
		this(new Vector2f(0, 0), false);
	}

	public void init(){
		for(int j = 0; j < iGlobal ; j+= STEPS){
			addValue(0);
		}
	}

	public void addValue(float f){
		if(f > maxValue)
			maxValue = f;

		ChartPoint p = new ChartPoint(x + i, y - f);
		p.setRy(scaled(f, y));
		p.setRx(x + i);
		list.add(p);
		i += STEPS;
		if(global){
			iGlobal += STEPS;
		}
	}

	public void render(Camera cam, Color color){
		if(global)
			drawLines(Color.gray);

		if(i > 2){
			shift();
			ChartPoint previous = list.get(0);
			for(ChartPoint p : list){
				float y1;
				y1 = scaled(p.getY() - y, y);
				p.setRy(y1);
				if(cam.collide(p.getRenderPosition(), previous.getRenderPosition())){

					Rendering.drawLine(p.getRenderPosition(), previous.getRenderPosition(), color, width);
				}
				previous = p;
			}
		}

		if(global){
			Rendering.drawLine(new Vector2f(x, y), new Vector2f(x, y - HEIGHT), Color.white, width);
			Rendering.drawLine(new Vector2f(x, y), new Vector2f(x + LENGTH, y), Color.white, width);

			Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), -1, 1), Color.white, width);
			Rendering.drawLine(new Vector2f(x, y - HEIGHT), arrow(new Vector2f(x, y - HEIGHT), 1, 1), Color.white, width);

			Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, -1), Color.white, width);
			Rendering.drawLine(new Vector2f(x + LENGTH, y), arrow(new Vector2f(x + LENGTH, y), -1, 1), Color.white, width);
		}
	}

	public void drawLines(Color color){
		int step = (int) (maxValue/10);
		int closest = (int) maxValue;

		float s = HEIGHT / 10;

		for(int i = 0 ; i < 10; i++){
			float newY = -y +s*i;
			Rendering.drawLine(new Vector2f(x, newY), new Vector2f(x + LENGTH, newY), color, width);
			Rendering.printScreen(x + LENGTH, newY - 15, ""+closest, 1);
			closest -= step;
		}
	}

	public float scaled(float f, float dist){
		return ((f / maxValue) * HEIGHT) + dist;
	}

	public void shift(){
		if(i > LENGTH){
			if(global){
				iGlobal -= STEPS;
			}
			i -= STEPS;
			Iterator<ChartPoint> it = list.iterator();
			ChartPoint rem = null;
			while(it.hasNext()){
				ChartPoint p = it.next();
				p.setX(p.getX() - STEPS);
				p.setRx(p.getRx() - STEPS);
				if(p.getX() < x){
					rem = p;
				}
			}
			if(rem != null)
				list.remove(rem);
			if(y - rem.getY() == maxValue){
				newMax();
			}
		}
	}

	public void newMax(){
		maxValue = y; 
		for(ChartPoint p : list){
			if(y - p.getY() > maxValue)
				maxValue = y - p.getY();
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