package catastrophes;

import org.lwjgl.util.vector.Vector2f;

import village.Entity;
import village.Random;
import village.Rendering;

public class Vague extends Entity {
	
	private static final int MAX_LIFE = 800;
	private static final int MIN_LIFE = 200;
	public int life;
	private static final float MAX_SIZE = 16;
	private static final float MIN_SIZE_Y = 16;
	public Vague(Vector2f pos){ 
		super(pos, Random.randFloat(MIN_SIZE_Y, MAX_SIZE), (float)Math.random()*MAX_SIZE,
				true, true);
		//int randomNum = rand.nextInt((max - min) + 1) + min;
		life = (int) ((Math.random()*(MAX_LIFE - MIN_LIFE) + 1) + MIN_LIFE);
	}
	
	@Override
	public void render() {
		Rendering.renderTextureQuad(this, Rendering.TEX_WAVE);
	}
}
