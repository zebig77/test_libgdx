package sandbox

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.ScreenUtils

class MyGdxGame extends ApplicationAdapter {
	
	private SceneObjectLibrary scene_objects_lib
	private Scene scene
	private SpriteBatch batch
	private  map_lines = new ArrayList<String>()
	private Texture wall_texture
	private Texture pavement_texture
	private Texture door_texture
	private Texture player_texture
	private float blinking_timer
	private int player_pos_x, player_pos_y
	private long lastKeyPressTime = TimeUtils.millis()

	@Override
	public void create () {
		scene_objects_lib = new MyGameSceneObjectLibrary()
		scene = new Scene()
		scene.map.load_ground('assets/ground_map.txt')
		batch = new SpriteBatch()
		new File('assets/map.txt').text.eachLine{ String line ->
			map_lines << line
		}
		find_player()
		wall_texture = create_texture(30, 30, Color.DARK_GRAY)
		pavement_texture = create_texture(30, 30, Color.GREEN)
		door_texture = create_texture(30, 30, Color.BLUE)
		player_texture = create_texture(30, 30, Color.YELLOW)
		blinking_timer = 0
		lastKeyPressTime = TimeUtils.millis()
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		update()
		batch.begin();
		int y = 0
		map_lines.each { String line ->
			int x = 0
			for(int i=0; i < line.size(); i++) {
				def c = line.charAt(i)
				switch(c) {
					case '*': draw_texture(wall_texture, x, y); break
					case ' ': draw_texture(pavement_texture, x, y); break
					case 'D': draw_texture(door_texture, x, y); break
					case 'P':
						blinking_timer += Gdx.graphics.getDeltaTime()
						if (blinking_timer < 0.025f) {
							draw_texture(pavement_texture, x, y);
							break
						}
						draw_texture(player_texture, x, y);
						if (blinking_timer >= 0.2f) {
							blinking_timer = 0
						}
						break
					default:
						abort("unknown map char $c")
				}
				x += 32
			}
			y += 32
		}
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
	Texture create_texture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap( width, height, Pixmap.Format.RGBA8888 )
		pixmap.setColor(color)
		pixmap.fillRectangle(0,  0, width, height)
		def texture = new Texture(pixmap)
		pixmap.dispose()
		return texture
	}

	private boolean player_can_enter(int pos_x, int pos_y) {
		if (pos_x < 0) return false
		def current_line = map_lines[player_pos_y]
		if (pos_x >= current_line.length()) return false
		if (pos_y < 0) return false
		if (pos_y >= map_lines.size()) return false
		if (current_line.charAt(pos_x) == '*') return false // wall
		return true
	}

	private draw_texture(Texture texture, int x, int y) {
		int pos_x = 120+x
		int pos_y = 350-y
		batch.draw(texture, pos_x, pos_y)
	}
	
	private map_write(int x, int y, c) {
		def line = map_lines[y].toCharArray()
		line[x] = c
		map_lines[y] = line.toString()
		
	}
	
	private void check_player_input() {
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			println "ESCAPE"
			dispose()
			Gdx.app.exit()
		}
		
		// add a delay between 2 key pressed events
		if (TimeUtils.timeSinceMillis(lastKeyPressTime) < 200) {
			return // too early
		}
		
		// check for player input
		int old_player_pos_x = player_pos_x
		int old_player_pos_y = player_pos_y
		if (Gdx.input.isKeyPressed(Keys.LEFT)) 	player_pos_x--
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) player_pos_x++
		if (Gdx.input.isKeyPressed(Keys.UP)) 	player_pos_y--
		if (Gdx.input.isKeyPressed(Keys.DOWN)) 	player_pos_y++

		// player moved ?		
		boolean player_moved = (old_player_pos_x != player_pos_x || old_player_pos_y != player_pos_y)
		
		if (player_moved && player_can_enter(player_pos_x, player_pos_y)) {
			// OK, move
			map_write(old_player_pos_x, old_player_pos_y, ' ')
			map_write(player_pos_x, player_pos_y, 'P')
			lastKeyPressTime = TimeUtils.millis()
			return
		}
		
		// cancel move
		player_pos_x = old_player_pos_x
		player_pos_y = old_player_pos_y
	}

	private void update() {
		check_player_input()
	}

	private void abort(String msg) {
		println "ERROR: $msg"
		dispose()
		Gdx.app.exit()
	}

	private void find_player() {
		boolean found = false
		int line_num = 0
		map_lines.each { String line ->
			if (line.contains('P')) {
				player_pos_x = line.indexOf('P')
				player_pos_y = line_num
				found = true
			}
			line_num++
		}
		if (!found) {
			abort("no player in map!!!")
		}
	}

}
