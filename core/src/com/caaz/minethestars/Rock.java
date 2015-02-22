package com.caaz.minethestars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Rock extends FixtureDef {
    public float size = 1f;
    private int points = 8;
    public Rock(World world, float size) {
        this.size = size;
        generateShape();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((float) (Math.random() * 20), (float) (Math.random() * 20));
        bodyDef.angularVelocity = (float)((Math.random()-.5)*1.5);
        this.density = .5f;
        Body body = world.createBody(bodyDef).createFixture(this).getBody();
        body.applyLinearImpulse(new Vector2((float)((Math.random()-.5)*2),(float)((Math.random()-.5)*2)),body.getPosition(), true);
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
