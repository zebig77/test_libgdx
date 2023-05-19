package sandbox

import java.awt.TexturePaint

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Pixmap


class Drawable {

	private SpriteBatch batch
	private TexturePaint texture
	private boolean can_be_taken

	public Drawable(SpriteBatch batch, Texture texture) {
		this.batch = batch
		this.texture = texture
	}

	public Drawable(SpriteBatch batch, Color color, int width, int height) {
		this.batch = batch
		Pixmap pixmap = new Pixmap( width, height, Pixmap.Format.RGBA8888 )
		pixmap.fillRectangle(0,  0, width, height)
		pixmap.setColor(color)
		texture = new Texture(pixmap)
		pixmap.dispose()
	}

	public void draw(int x, int y) {
		batch.draw(texture, x, y)
	}
}
