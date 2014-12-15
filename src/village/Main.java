package village;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;

import catastrophes.Catastrophe;

import algos.AlgoBase;

import village.RightClickMenu.Option;

public class Main {
	public static boolean GameRunning = true;

	public static Camera camera = new Camera();

	public static boolean rMenu;
	public static boolean speed = false;	//Set to false for normal speed
	public static RightClickMenu menu;
	public static Vector2f mousePos;

	public static VillageList villageList;
	public static List<Catastrophe > catastrophes;
	public static Chart2 graph;

	public static void gameLoop() throws InterruptedException {
		double lastFpsTime = 0;
		int fps = 0;
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 900;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;	//1000000000
		
		graph = new Chart2(new Vector2f(400, 100));
		
		catastrophes = new ArrayList<Catastrophe>();

		villageList = new VillageList();

		menu = new RightClickMenu(new Vector2f(0, 0), 0, 150, 10);
		menu.addOption(new Option("Create Village"));
		menu.addOption(new Option("Remove Village"));
		menu.addOption(new Option("Catastrophe"));


		// keep looping round til the game ends
		while (GameRunning)
		{
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			float delta = updateLength / ((float)OPTIMAL_TIME);

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;


			// update our FPS counter if a second has passed since
			// we last recorded

			if (lastFpsTime >= 1000000000)//1000000000
			{
				int x = (int) camera.getPosition().getX();
				int y = (int) camera.getPosition().getY();
				Display.setTitle("Villages " + fps + " FPS. CamPos : (" + x + ", " + y + ")");
				lastFpsTime = 0;
				fps = 0;	

				if(!speed){
					villageList.updateStat();
					graph.update();
				}
			}
			if(speed){
				villageList.updateStat();
				graph.update();
			}
			else
				Display.sync(60);

			update(delta);

			render();

			try{
				Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
			}catch(IllegalArgumentException e){}
		}
	}

	public static void update(float delta){
		Controls.update(camera, delta);
		camera.update(delta);
		villageList.update(delta);
		
		Catastrophe toRemove = null;
		
		for(Catastrophe c : catastrophes){
			if(c.state == Catastrophe.STOPPED){
				toRemove = c;
				continue;
			}
			c.update(delta);
		}
		catastrophes.remove(toRemove);
	}

	public static void render(){

		// Clear the color information.
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		villageList.render(camera);
		
		graph.render(camera);

		if(rMenu){
			menu.render(mousePos);
		}
		
		for(Catastrophe c : catastrophes){
			c.render();
		}

		//Display.sync(60);
		Display.update();
	}

	public static void main(String[] args) {

		Rendering.init();

		try {
			gameLoop();
		} catch (InterruptedException f) {
			System.exit(0);
			f.printStackTrace();
		}
	}
}

