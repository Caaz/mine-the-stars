package com.caaz.minethestars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends FixtureDef implements Updatable {
    private static float MAX_VELOCITY = 40f;
    private static float VELOCITY = 2f;
    private static int COOLDOWN = 15;
    private int heat = 0;
    private Game game;
    public Body body;
    public Player(Game game) {
        this.game = game;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((float)(game.worldSize/2),(float)(game.worldSize/2));

        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0,1);
        vertices[1] = new Vector2(.6f,-1);
        vertices[2] = new Vector2(0,-.6f);

        this.density = .5f;
        body = game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        shape = polygonShape;
        body.createFixture(this);
        for(Vector2 vec : vertices) { vec.x = -vec.x; }
        polygonShape.set(vertices);
        shape = polygonShape;
        body.createFixture(this);
        body.setUserData(new SpaceObject(1,this));
    }
    public void update() {
        if(heat > 0) heat--;
        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && vel.y < MAX_VELOCITY){
            float angle = (float)(body.getAngle()+Math.toRadians(90));
            body.applyForceToCenter(new Vector2((float) (Math.cos(angle) * VELOCITY), (float) (Math.sin(angle) * VELOCITY)), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) body.applyAngularImpulse(-.01f, true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) body.applyAngularImpulse(.01f, true);
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if(heat<=0) {
                new Bullet(game,pos);
                heat+=COOLDOWN;
            }
        }
    }
}
