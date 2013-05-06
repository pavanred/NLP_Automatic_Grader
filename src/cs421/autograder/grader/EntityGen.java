package cs421.autograder.grader;

import java.util.ArrayList;

public class EntityGen {

	private String entity;
	private int generation;
	//private boolean removeMark = false;
	private int gender;
	private int number;
	
	public EntityGen(String _entity, int gen) {
		this.entity = _entity.replaceAll("[^A-Za-z]+", "");
		this.generation = gen;
	}
	
	public EntityGen(String _entity, int g, int n) {
		this.entity = _entity.replaceAll("[^A-Za-z]+", "");
		this.gender = g;   //1 - male, 0 - female, 2 - na
		this.number = n;   //1 - singular, 2 - plural
	}

	public String getEntity(){
		return entity;
	}
	
	public int getGeneration(){
		return generation;
	}
	
	public void setEntity(String _entity){
		this.entity = _entity.replaceAll("[^A-Za-z]+", "");
	}
	
	public void setGeneration(int _gen){
		this.generation = _gen;
	}
	
	public void setGender(int _g){
		this.gender = _g;
	}
	
	public int getGender(){
		return this.gender;
	}
	
	public void setNumber(int _n){
		this.number = _n;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public static ArrayList<EntityGen> getEntityList(){
		
		ArrayList<EntityGen> list = new ArrayList<EntityGen>();
		
		//1 - male, 0 - female, 2 - na
		//1 - singular, 2 - plural
		
		list.add(new EntityGen("year",2,1));
		list.add(new EntityGen("years",2,2));
		list.add(new EntityGen("month",2,1));
		list.add(new EntityGen("months",2,2));
		
		list.add(new EntityGen("family",2,1));
		list.add(new EntityGen("families",2,2));
		
		list.add(new EntityGen("child",2,1));
		list.add(new EntityGen("children",2,2));
		
		list.add(new EntityGen("son",1,1));
		list.add(new EntityGen("sons",1,2));
		
		list.add(new EntityGen("daughter",0,1));
		list.add(new EntityGen("daughters",0,2));
		
		list.add(new EntityGen("brother",1,1));
		list.add(new EntityGen("brothers",1,2));
		
		list.add(new EntityGen("sister",0,1));
		list.add(new EntityGen("sisters",0,2));
		
		list.add(new EntityGen("sibling",2,1));
		list.add(new EntityGen("siblings",2,2));
		
		list.add(new EntityGen("parent",2,1));
		list.add(new EntityGen("parents",2,2));
		
		list.add(new EntityGen("father",1,1));
		list.add(new EntityGen("fathers",1,2));
		
		list.add(new EntityGen("mother",0,1));
		list.add(new EntityGen("mothers",0,2));
		
		list.add(new EntityGen("boy",1,1));
		list.add(new EntityGen("boys",1,2));
		
		list.add(new EntityGen("girl",0,1));
		list.add(new EntityGen("girls",0,2));
		
		list.add(new EntityGen("husband",1,1));
		list.add(new EntityGen("husbands",1,2));
		
		list.add(new EntityGen("wife",0,1));
		list.add(new EntityGen("wives",0,2));
		
		list.add(new EntityGen("work",2,1));
		
		list.add(new EntityGen("boyfriend",1,1));
		list.add(new EntityGen("boyfriends",1,2));
		
		list.add(new EntityGen("girlfriend",0,1));
		list.add(new EntityGen("girlfriends",0,2));
		
		list.add(new EntityGen("cousin",2,1));
		list.add(new EntityGen("cousins",2,2));
		
		list.add(new EntityGen("baby",2,1));
		list.add(new EntityGen("babies",2,2));

		list.add(new EntityGen("grandson",1,1));
		list.add(new EntityGen("grandsons",1,2));
		
		list.add(new EntityGen("granddaughter",0,1));
		list.add(new EntityGen("granddaughters",0,2));
		
		list.add(new EntityGen("grandparent",2,1));
		list.add(new EntityGen("grandparents",2,2));
		
		list.add(new EntityGen("grandfather",1,1));
		list.add(new EntityGen("grandfathers",1,2));
		
		list.add(new EntityGen("grandmother",0,1));
		list.add(new EntityGen("grandmothers",0,2));
		
		list.add(new EntityGen("pet",2,1));
		list.add(new EntityGen("pets",2,2));
		
		list.add(new EntityGen("dog",2,1));
		list.add(new EntityGen("dogs",2,2));
		
		list.add(new EntityGen("cat",2,1));
		list.add(new EntityGen("cats",2,2));
		
		list.add(new EntityGen("school",2,1));
		list.add(new EntityGen("schools",2,2));
		
		return list;
	}
}
