package view;

public class Pair<D> {
	private D d1;
	private D d2;
	
	public Pair() {
		this.d1=this.d2=null;
	}
	public Pair(D d1,D d2) {
		this.d1=d1;
		this.d2=d2;
	}
	public D getFirst() {return d1;}
	public D getSecond() {return d2;}
	public void setFirst(D d) {this.d1=d;}
	public void setSecond(D d) {this.d2=d;}
}
