package cs421.autograder.grader;

public class EntityGen {

	private String entity;
	private int generation;
	//private boolean removeMark = false;
	
	public EntityGen(String _entity, int gen) {
		this.entity = _entity.replaceAll("[^A-Za-z]+", "");
		this.generation = gen;
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
	
	/*public void setRemoveFlag(boolean flag){
		this.removeMark = flag;
	}
	
	public boolean getRemoveFlag(){
		return this.removeMark;
	}*/
}
