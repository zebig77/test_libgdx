package sandbox

class Scene {
	
	private SceneObjectLibrary scene_objects_lib
	private Map map
	private scene_objects
	
	public Scene(SceneObjectLibrary scene_objects_lib) {
		this.scene_objects_lib = scene_objects_lib
		this.map = new Map()
		this.scene_objects = new ArrayList<SceneObject>()
	}
	
	public void render() {
		map.draw_ground()
		scene_objects.each{ SceneObject o ->
			o.draw()
		}
	}
	
}
