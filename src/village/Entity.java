package village;


import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

public class Entity implements Renderable{

	private Vector2f position;
	private float height;
	private float length;
	private boolean render;
	private boolean moving;
	private Vector2f velocity;
	private Color color;
	
	public Entity(Vector2f position, float height, float length, boolean render, boolean moving) {
		super();
		this.position = position;
		this.render = render;
		this.moving = moving;
		this.height = height;
		this.length = length;
		this.velocity = new Vector2f(0, 0);
		color = new Color(Color.white);
	}
	public Entity(float x, float y, float height, float length, boolean render, boolean moving) {
		this(new Vector2f(x, y), height, length, render, moving);
	}
	
	public Entity(){
		this(new Vector2f(0,0), 0, 0, false, false);
	}

	public void render(){
		if(render){
			Rendering.renderQuad(this, color);
		}
	}

	public void update(float delta){
		if(moving){
			this.setX(this.getX() + this.getvX() * delta);
			this.setY(this.getY() + this.getvY() * delta);
		}
	}
	
	public boolean collide(Entity e){
		
		if(		((getX() <= e.getX())					&& (getX() + getLength() >= e.getX()))
			|| 	((getX() <= e.getX() + e.getLength()) 	&& (getX() + getLength() >= e.getX() + getLength()))){
			
			if(		((getY() <= e.getY())					&& (getY() + getHeight() >= e.getY()))
					|| 	((getY() <= e.getY() + e.getHeight()) 	&& (getY() + getHeight() >= e.getY() + getHeight()))){
					
						return true;
					}
			}
		
		
		return false;
	}
	public boolean collide(Vector2f point, Vector2f point2){
		
		if(		((getX() <= point.getX())					&& (getX() + getLength() >= point.getX()))
			|| 	((getX() <= point.getX() + point2.getX()) 	&& (getX() + getLength() >= point.getX() + point2.getX()))){
			
			if(		((getY() <= point.getY())					&& (getY() + getHeight() >= point.getY()))
					|| 	((getY() <= point.getY() + point2.getY()) 	&& (getY() + getHeight() >= point.getY() + point2.getY()))){
					
						return true;
					}
			}
		
		
		return false;
	}
	
	public static void rotate(Vector2f position, Vector2f posDeBase, float angle){
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		
		float x = posDeBase.getX() + ((position.getX()-posDeBase.getX()) * cos) 
		- ((position.getY()-posDeBase.getY())*sin);
		float y = posDeBase.getY() + ((position.getX()-posDeBase.getX()) * sin) 
		+ ((position.getY()-posDeBase.getY()*cos));
		
		position.setX(x);
		position.setY(y);
	}
	
	public void rotate(Vector2f posDeBase, float angle){
		rotate(position, posDeBase, angle);
	}
	
	public float getSize(){
		return getHeight();
	}

	public float getX(){
		return position.getX();
	}
	
	public float getY(){
		return position.getY();
	}
	
	public void setX(float x){
		position.setX(x);
	}
	
	public void setY(float y){
		position.setY(y);
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public boolean isRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}
	
	public float getvX(){
		return velocity.getX();
	}
	
	public float getvY(){
		return velocity.getY();
	}
	
	public void setvX(float x){
		velocity.setX(x);
	}
	
	public void setvY(float y){
		velocity.setY(y);
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	@Override
	public String toString() {
		return "Entity [position=" + position + ", height=" + height
				+ ", length=" + length + ", render=" + render + ", moving="
				+ moving + ", velocity=" + velocity + ", color=" + color + "]";
	}
	
	
}
