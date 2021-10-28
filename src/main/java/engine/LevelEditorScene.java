package engine;

import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        //POKEMON
        //GEN 1-3
        //sprites = AssetPool.getSpritesheet("assets/images/spritesheet2.png");
        //GEN 1-4
        //sprites = AssetPool.getSpritesheet("assets/images/spritesheet3.png");
        //MARIO
        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 4);
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 2);
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(7)));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        //POKEMON
        //GEN 1-3
        //AssetPool.addSpritesheet("assets/images/spritesheet2.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet2.png"), 64, 64, 385, 0));
        //GEN 1-4
        //AssetPool.addSpritesheet("assets/images/spritesheet3.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet3.png"), 80, 80, 492, 0));
        //MARIO
        AssetPool.addSpritesheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
