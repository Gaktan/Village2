package village;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Controls {
	
	private static int zoom = 5;
	private static final int MAX_ZOOM = 10;
	private static float camSpeed = 3;
	private static float scale = 1;
	private static boolean display = true;
	
	public static void update(Camera camera, float dt)
	{
		while(Keyboard.next())
		{
		    if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					camera.setVgX(camSpeed);
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
		        	camera.setVgX(-camSpeed);
		        }	        	
		        if (Keyboard.getEventKey() == Keyboard.KEY_UP){
		        	camera.setVgY(-camSpeed);
		        }  
		        if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
		        	camera.setVgY(camSpeed);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
		        	System.out.println("Stopping process...");
		        	System.exit(0);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
		        	Main.speed = true;
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
		        	System.out.println(Random.randName());
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_A){
		        	if(display){
		        		Main.villageList.setDisplayNames(false);
		        		
		        	}
		        	if(!display){
		        		Main.villageList.setDisplayNames(true);
		        	}
		        	 display = !display;
		        }
		    }
		    else {
		        if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
		        	camera.setVgX(0);
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_LEFT){
		        	camera.setVgX(0);
		        }	        	
		        if (Keyboard.getEventKey() == Keyboard.KEY_UP){
		        	camera.setVgY(0);
		        }  
		        if (Keyboard.getEventKey() == Keyboard.KEY_DOWN){
		        	camera.setVgY(0);
		        }
		        if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
		        	Main.speed = false;
		        }
		    }
		}
		while(Mouse.next()){
			
			if(Mouse.getEventButtonState()){
				if(Mouse.isButtonDown(1)){
					Main.mousePos = getMousePos(camera);
					Main.rMenu = true;
				}
				if(Mouse.isButtonDown(0)){
					Entity e = new Entity(getMousePos(camera), 1, 1, false, false);
					if(e.collide(Main.menu)){
						Main.menu.whichCollide(e);
					}
					Main.rMenu = false;
				}
			}
			
			
			/*
			 * 
			 * ZOOMING
			 * 
			 */
			
	        int dWheel = Mouse.getDWheel();
	        float zoomFactor = 0;
	        boolean set = false;
	        if (dWheel < 0) {
		        if(zoom < MAX_ZOOM){
		        	zoomFactor = 0.8f;
		        	set = true;
		        	zoom++;
		        	scale *= zoomFactor;
		        }
	        } else if (dWheel > 0){
		        if(zoom > 0){
		        	zoomFactor = 1.25f;
		        	set = true;
		        	zoom--;
		        	scale *= zoomFactor;
		        }
	        }
	        
	        if(set){
	        	camSpeed /= zoomFactor;
	        	
	        	camera.setHeight(camera.getHeight() / zoomFactor);
	        	camera.setLength(camera.getLength() / zoomFactor);
	        	
	        	glTranslated(camera.getX(), camera.getY(), 1);
	        	glScaled(zoomFactor, zoomFactor, 1);
	        	glTranslated(-camera.getX(), -camera.getY(), -1);

	        }
		}
	}
	
	public static Vector2f getMousePos(Camera camera){
		float x = (camera.getX() + (Mouse.getX()/scale));
		float y = (camera.getY() - Mouse.getY()/scale);
		
		y = y + camera.getHeight();
		
		return new Vector2f(x, y);
	}
}