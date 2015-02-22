package com.caaz.minethestars;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {
    public World world;
    public float worldSize = 20f;
    public Player player;
    private Box2DDebugRenderer debugRenderer;
    private Camera camera;
    private Viewport viewport;
    public RayHandler rayHandler;
    //private PointLight[] lights = new PointLight[STARS*2];
    @Override
	public void create () {
        world = new World(new Vector2(0, 0f), true);
        world.setContactListener(new CollisionListener());
        debugRenderer = new Box2DDebugRenderer();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(new Color(1,1,1,.1f));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(30, 30 * (h / w));
        viewport = new FitViewport(worldSize,worldSize, camera);
        //rayHandler.useCustomViewport(viewport.getLeftGutterWidth(),viewport.getBottomGutterHeight(),viewport.getScreenWidth(),viewport.getScreenHeight());
        camera.position.set(worldSize/2,worldSize/2,0f);
        for(int i = 0; i<5; i++) new Rock(world,3);
        player = new Player(this);
	}
    public void resize(int width, int height) {
        viewport.update(width, height);
        rayHandler.useCustomViewport(viewport.getLeftGutterWidth(), viewport.getBottomGutterHeight(), viewport.getScreenWidth(), viewport.getScreenHeight());

    }
    public void update() {
        world.step(1 / 60f, 6, 2);
        float margins = 1f;
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body body : bodies) {
            SpaceObject spaceObject = (SpaceObject)(body.getUserData());
            Updatable updatable = (Updatable)spaceObject.obj;
            updatable.update();
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
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
        update();
	}
}
interface Updatable {
    public void update();
}
interface Destroyable {
    public void destroy();
}
class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
       // contact.getFixtureA().isSensor();
        SpaceObject dataA = (SpaceObject)(contact.getFixtureA().getBody().getUserData());
        SpaceObject dataB = (SpaceObject)(contact.getFixtureB().getBody().getUserData());
        if(dataB.isBullet()) {
            if(dataA.isRock()) {
                Destroyable rock = (Destroyable)dataA.obj;
                rock.destroy();
                Destroyable bullet = (Destroyable)dataB.obj;
                bullet.destroy();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}