package test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleGame extends ApplicationAdapter {
    private static final int TILE_SIZE = 16;
    private static final int ROOM_WIDTH = 20;
    private static final int ROOM_HEIGHT = 20;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture tileset;
    private int[][] roomMap;
    private int playerX, playerY;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();

        tileset = new Texture("assets/Ultima_5_-_Tiles-pc.PNG");

        // Initialize the room map with sample data
        roomMap = new int[ROOM_WIDTH][ROOM_HEIGHT];
        // Fill the room map with values representing different cell types
        // 0 = wall, 1 = ground, 2 = door
        for (int x = 0; x < ROOM_WIDTH; x++) {
            for (int y = 0; y < ROOM_HEIGHT; y++) {
                if (x == 0 || x == ROOM_WIDTH - 1 || y == 0 || y == ROOM_HEIGHT - 1) {
                    roomMap[x][y] = 0; // Wall cell
                } else if (x == ROOM_WIDTH / 2 && y == ROOM_HEIGHT / 2) {
                    roomMap[x][y] = 2; // Door cell
                } else {
                    roomMap[x][y] = 1; // Ground cell
                }
            }
        }

        // Set the initial player position
        playerX = ROOM_WIDTH / 2;
        playerY = ROOM_HEIGHT / 2;
    }

    @Override
    public void render() {
        // Handle player movement
        handleInput();

        // Clear the screen with a black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int x = 0; x < ROOM_WIDTH; x++) {
            for (int y = 0; y < ROOM_HEIGHT; y++) {
                int cellType = roomMap[x][y];

                // Calculate the position to render the current cell
                float xPos = x * TILE_SIZE;
                float yPos = y * TILE_SIZE;

                // Render the current cell
                batch.draw(tileset, xPos*2, yPos*2, TILE_SIZE*2, TILE_SIZE*2,
                        cellType * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, false, false);
            }
        }

        // Render the player character
        float playerXPos = playerX * TILE_SIZE;
        float playerYPos = playerY * TILE_SIZE;
        batch.draw(tileset, playerXPos*2, playerYPos*2, TILE_SIZE*2, TILE_SIZE*2,
                12 * TILE_SIZE, 10 * TILE_SIZE, TILE_SIZE, TILE_SIZE, false, false);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            movePlayer(-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            movePlayer(1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(0, 1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            movePlayer(0, -1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			dispose();
			Gdx.app.exit();
        }
    }

    private void movePlayer(int deltaX, int deltaY) {
        int newPlayerX = playerX + deltaX;
        int newPlayerY = playerY + deltaY;

        // Check if the new position is within the bounds and not a wall cell
        if (newPlayerX >= 0 && newPlayerX < ROOM_WIDTH && newPlayerY >= 0 && newPlayerY < ROOM_HEIGHT
                && roomMap[newPlayerX][newPlayerY] != 0) {
            playerX = newPlayerX;
            playerY = newPlayerY;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        tileset.dispose();
    }
}
