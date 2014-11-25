package village;

import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector2f;

public class Camera extends Entity{
	
	private Vector2f VectorGoal;
	private float smoothValue = 50;
	
	public Camera(){
		this.setX(0);
		this.setY(0);
		setHeight(Rendering.getHeight());
		setLength(Rendering.getLength());
		VectorGoal = new Vector2f(0, 0);
	}

	public void update(float dt){
		
		setvX(approach(getVgX(), this.getvX(), dt * smoothValue));
		setvY(approach(getVgY(), this.getvY(), dt * smoothValue));
		
		float x = getvX();
		float y = getvY();

		glTranslatef(-x, -y, 0);
		
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
		
	}
	
	public float approach(float vg, float v, float dt){
		
		float dif = vg - v;
		
		if(dif > dt)
			return v + dt;
		if(dif < -dt)
			return v - dt;
		
		return vg;
	}
	
	
	public Vector2f camPos(){
		return new Vector2f(getHeight() + getX(), getLength() + getY());
	}

	public float getxCenter() {
		return getX() + getHeight() / 2;
	}

	public float getyCenter() {
		return getY() + getLength() / 2;
	}

	public Vector2f getVectorGoal() {
		return VectorGoal;
	}

	public void setVectorGoal(Vector2f vectorGoal) {
		VectorGoal = vectorGoal;
	}
	
	public float getVgX(){
		return VectorGoal.getX();
	}
	public void setVgX(float x){
		VectorGoal.setX(x);
	}
	public float getVgY(){
		return VectorGoal.getY();
	}
	public void setVgY(float y){
		VectorGoal.setY(y);
	}
}
 