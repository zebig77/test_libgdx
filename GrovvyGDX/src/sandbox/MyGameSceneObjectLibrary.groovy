package sandbox

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class MyGameSceneObjectLibrary extends SceneObjectLibrary {
	
	public MyGameSceneObjectLibrary(SpriteBatch batch) {
		super()	
		add_entry("*", new SceneObjectType("Wall", new Drawable(batch, Color.DARK_GRAY, 30, 30)))
		add_entry(" ", new SceneObjectType("Soil", new Drawable(batch, Color.GREEN, 30, 30)))
	}
}
