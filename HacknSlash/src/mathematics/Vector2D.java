package mathematics;


public class Vector2D extends Vector {
	public static final Vector2D ZERO = new Vector2D();
	
	public Vector2D(){
		super(2);
	}
	
	public Vector2D(double x,double y){
		super (x,y);
	}
	public Vector2D(Vector temp) {
		super(temp.vector[0],temp.vector[1]);
	}

	public double getX(){
		return this.getElement(0);
	}
	public double getY(){
		return this.getElement(1);
	}
	protected void compareOrThrow(Vector v){
		if(this.vector.length!=v.vector.length){
			throw new IllegalArgumentException("invalid dimensions :"+this.vector.length +","+v.vector.length);
		}
	}
	
	
	public int getDimension(){
		return vector.length;
	}
	public int getSize(){
		return vector.length;
	}
	
	public Vector2D scale(double s){
		return new Vector2D(this.getX()*s,this.getY()*s);
	}
	
	public Vector2D add(Vector2D v){
		return new Vector2D(this.getX()+v.getX(),this.getY()+v.getY());
	}
	public Vector2D subtract(Vector2D v){
		this.compareOrThrow(v);
		return new Vector2D(this.getX()-v.getX(),this.getY()-v.getY());
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		
		Vector other = (Vector) obj;
		if(other.vector.length!=this.vector.length){
			return false;
		}
		
		for(int i=0;i<this.vector.length;i++){
			if(Math.round(this.vector[i]*1000)!=Math.round(other.vector[i]*1000)){
				return false;
			}
		}
		return true;
	}
	public Vector2D getZero(){
		return new Vector2D();
	}
	public Vector2D getUnitVector(){
		return this.scale(1.0/this.getLength());
	}

}
