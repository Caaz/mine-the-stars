package com.caaz.minethestars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Bullet extends FixtureDef implements Updatable,Destroyable {
    private static float VELOCITY = 15f;
    private static float RADIUS = .2f;
    private int lifeTime = 60;
    private boolean destroyMe = false;
    private World world;
    public Body body;
    public Bullet(Game game,Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        world = game.world;

        this.density = 0f;
        this.isSensor = true;

        body = game.world.createBody(bodyDef);
        shape = new CircleShape();
        shape.setRadius(RADIUS);
        body.createFixture(this);

        float angle = (float)(game.player.body.getAngle()+Math.toRadians(90));
        body.applyLinearImpulse(new Vector2((float) (Math.cos(angle) * VELOCITY), (float) (Math.sin(angle) * VELOCITY)), position, true);
        body.setUserData(new SpaceObject(2,this));
    }
    public void destroy() {
        destroyMe = true;
    }
    public void update() {
        lifeTime--;
        if((destroyMe || (lifeTime<1)) && (!world.isLocked())){
            world.destroyBody(body);
        }
    }
}
