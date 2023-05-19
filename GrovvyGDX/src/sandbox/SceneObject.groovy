package sandbox

class SceneObject {
	
	private Drawable drawable
	private int x, y
	
	public SceneObject(Drawable drawable, final int x, final int y) {
		this.drawable = drawable
		this.x = x
		this.y = y
	}
	
	public void draw() {
		drawable.draw(x, y)
	}
}
