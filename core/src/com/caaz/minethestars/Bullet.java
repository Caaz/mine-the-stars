package com.caaz.minethestars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet extends FixtureDef {
    private static float VELOCITY = 25f;
    private static float RADIUS = .2f;
    public Body body;
    public Bullet(Game game,Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        this.density = 0f;
        this.isSensor = true;

        body = game.world.createBody(bodyDef);
        shape = new CircleShape();
        shape.setRadius(RADIUS);
        body.createFixture(this);

        float angle = (float)(game.player.body.getAngle()+Math.toRadians(90));
        body.applyLinearImpulse(new Vector2((float) (Math.cos(angle) * VELOCITY), (float) (Math.sin(angle) * VELOCITY)), position, true);
    }
}
