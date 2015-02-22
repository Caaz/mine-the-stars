package com.caaz.minethestars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Camera camera;
    private Viewport viewport;
    private float worldSize = 20f;
    @Override
	public void create () {
        world = new World(new Vector2(0, 0f), true);
        debugRenderer = new Box2DDebugRenderer();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(30, 30 * (h / w));
        viewport = new FitViewport(worldSize,worldSize, camera);
        camera.position.set(worldSize/2,worldSize/2,0f);
        for(int i = 0; i<5; i++) new Rock(world,3);
	}
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public void update() {
        world.step(1/60f, 6, 2);

        float margins = 2f;

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body : bodies) {
            Vector2 position = body.getPosition();
            if(position.x < -margins) position.x += (worldSize+margins*2);
            else if(position.x > worldSize+margins) position.x -= (worldSize+margins*2);
            else if(position.y < -margins) position.y += (worldSize+margins*2);
            else if(position.y > worldSize+margins) position.y -= (worldSize+margins*2);
            body.setTransform(position, body.getAngle());

        }
    }
	@Override
	public void render() {
        camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
        update();
	}
}
