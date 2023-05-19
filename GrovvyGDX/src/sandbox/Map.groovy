package sandbox

class Map {
	
	// the map size will be adjusted according to the added elements 
	private int width = 0
	private int height = 0
	
	// fixed map elements
	private ground = new ArrayList<SceneObject>()
	
	public void add_ground(SceneObject ground_element) {
		if (ground_element.x > width) width = ground_element.x
		if (ground_element.y > height) height = ground_element.y
		this.ground << ground_element
	}
	
	public void load_ground(String filename) {
		def map_text = new File(filename).text
		// TODO load_ground
		int x, y
		map_text.each { line ->
			line.toCharArray().each { c ->
				
			}
		}
	}
	
	public void draw_ground() {
		ground.each{ SceneObject element ->
			element.draw()
		}
	}
}
