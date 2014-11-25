package catastrophes;

import village.Renderable;

public abstract class Catastrophe implements Renderable{
	
	public int state;
	
	public static final int STARTED = 1, STOPPED = 0;

	@Override
	public abstract void render();

	@Override
	public abstract void update(float delta);

}
