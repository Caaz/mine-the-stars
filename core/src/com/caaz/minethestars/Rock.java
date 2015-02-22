package com.caaz.minethestars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Rock extends FixtureDef implements Updatable, Destroyable {
    public float size = 1f;
    private int points = 8;
    private World world;
    private Body body;
    private boolean destroyMe = false;
    public Rock(World world, float size) {
        this.world = world;
        this.size = size;
        generateShape();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((float) (Math.random() * 20), (float) (Math.random() * 20));
        bodyDef.angularVelocity = (float)((Math.random()-.5)*1.5);
        this.density = .5f;
        body = world.createBody(bodyDef).createFixture(this).getBody();
        body.applyLinearImpulse(new Vector2((float)((Math.random()-.5)*4),(float)((Math.random()-.5)*4)),body.getPosition(), true);
        body.setUserData(new SpaceObject(0,this));
    }
    public Rock(World world, Vector2 position, float size) {
        this.world = world;
        this.size = size;
        generateShape();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.angularVelocity = (float)((Math.random()-.5)*1.5);
        this.density = .5f;
        body = world.createBody(bodyDef).createFixture(this).getBody();
        body.applyLinearImpulse(new Vector2((float)((Math.random()-.5)*2),(float)((Math.random()-.5)*2)),body.getPosition(), true);
        body.setUserData(new SpaceObject(0,this));
    }
    public void destroy() {
        destroyMe = true;
    }
    public void update() {
        if(destroyMe && (!world.isLocked())){
            if(size > 1) for (int i = 0; i < 3; i++) new Rock(world, body.getPosition(), size - 1);
            world.destroyBody(body);
        }
    }
    private void generateShape() {
        Vector2[] vertices = new Vector2[points];
        for(int i = 0; i<points; i++){
            double distance = Math.random()*size+.5;
            double angle = Math.toRadians(i*(360/points));
            vertices[i] = new Vector2((float)(distance * Math.cos(angle)), (float)(distance * Math.sin(angle)));
        }
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);
        shape = polygonShape;
    }
}
