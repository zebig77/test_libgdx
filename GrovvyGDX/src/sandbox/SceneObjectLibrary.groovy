package sandbox

class SceneObjectLibrary {
	
	private entries = [:]
	
	public void add_entry(String id, SceneObjectType ot) {
		entries[id] = ot
	}
	
	public SceneObject get_entry(String id) {
		return entries[id]
	}
}
