package catastrophes;

import org.lwjgl.util.vector.Vector2f;

import village.Entity;
import village.Random;
import village.Rendering;

public class TornadoThing extends Entity{
	
	private static final int MIN_SIZE = 4, MAX_SIZE = 10, MAX_LIFE = 800;
	private float angle;
	private Vector2f posDeBase;
	public int life;
	
	public TornadoThing(Vector2f pos, Vector2f pos2Base) {
		super(pos, 0, 0, true, true);
		
		float size = Random.randFloat(MIN_SIZE, MAX_SIZE);
		setHeight(size); setLength(size);
		
		angle = Random.randFloat(0.005f, 0.08f);
		
		posDeBase = pos2Base;
		
		life = (int) (Math.random()*MAX_LIFE);
	}
	
	@Override
	public void render() {
		Rendering.renderTextureQuad(this, Rendering.TEX_WIND);
	}
	
	@Override
	public void update(float delta) {
		rotate(posDeBase, angle);
		
		super.update(delta);
	}
}
