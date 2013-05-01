package cs421.autograder.grader;

public class EntityGen {

	private String entity;
	private int generation;
	
	public EntityGen(String _entity, int gen) {
		this.entity = _entity;
		this.generation = gen;
	}

	public String getEntity(){
		return entity;
	}
	
	public int getGeneration(){
		return generation;
	}
	
	public void setEntity(String _entity){
		this.entity = _entity;
	}
	
	public void setGeneration(int _gen){
		this.generation = _gen;
	}
}
