package sandbox

class SceneObjectType {
	
	private String name
	private boolean can_be_entered
	private boolean can_be_rendered
	private Drawable drawable
	
	public SceneObjectType(String name) {
		this.name = name
	}
	
	public SceneObjectType(String name, Drawable d) {
		this.name = name
		this.drawable = d
	}
	
}
