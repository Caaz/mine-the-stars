package com.caaz.minethestars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by caaz on 2/21/15.
 */
public class Player extends FixtureDef {
    private static float MAX_VELOCITY = 40f;
    private static float VELOCITY = 2f;
    Body body;
    public Player(Game game) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((float)(game.worldSize/2),(float)(game.worldSize/2));

        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0,1);
        vertices[1] = new Vector2(.6f,-1);
        vertices[2] = new Vector2(0,-.6f);
        //vertices[3] = new Vector2(-.6f,-1);
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
    }
    public void update() {
        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();
        float angle = (float)(body.getAngle()+Math.toRadians(90));
        if((System.nanoTime()%100)==0) System.out.println("Angle is "+angle);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && vel.y < MAX_VELOCITY){
            body.applyForceToCenter(new Vector2((float)(Math.cos(angle)*VELOCITY),(float)(Math.sin(angle)*VELOCITY)), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyAngularImpulse(-.01f, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyAngularImpulse(.01f, true);
        }
    }
}
