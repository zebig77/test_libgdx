package sandbox

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

new Lwjgl3ApplicationConfiguration().with { config->
	setForegroundFPS(60)
	setTitle("My GDX Game")
	new Lwjgl3Application(new MyGdxGame(), config)
}
