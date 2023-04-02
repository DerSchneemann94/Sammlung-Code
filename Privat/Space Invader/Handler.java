package rothenberger.domenic.SpaceInvader;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Handler {

	protected ArrayList<GameObject> object;
	protected ArrayList<GameObject> objectAdd; 
	protected ArrayList<Integer> objectRemove;
	
	
	public Handler() {
		object = new ArrayList<GameObject>(0);
		objectAdd = new ArrayList<GameObject>(0);
		objectRemove = new ArrayList<Integer>(0); 
	}	

	public void tick() {
		for(GameObject o: object) 
			o.tick();
		
//		for(GameObject o:object) {
//			if(o.remove) {
//				objectRemove.add(o);
//			}
		
		for(int i=object.size()-1;i>=0;i--) {
			if(object.get(i).remove==true)
				objectRemove.add(i);
		}
		
		for(Integer i:objectRemove) {
			int u = i.intValue();
			if(object.get(u).id==ID.Enemy)
				Level.score+=10;
			object.remove(u);
		}
		
//		object.removeAll(objectRemove);
		object.addAll(objectAdd);
		
		
		objectAdd.clear();
		objectRemove.clear();
		object.trimToSize();
	
	}	
	
	public void render(Graphics2D g) {
		for(GameObject o: object)
			o.render(g);
	}

}
