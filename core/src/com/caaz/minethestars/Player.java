package com.caaz.minethestars;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by caaz on 2/21/15.
 */
public class Player extends FixtureDef {
    float density = .2f;
    float friction = 0f;
    float restitution = 0f;
    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((float)(Math.random()*20),(float)(Math.random()*20));
        world.createBody(bodyDef).createFixture(this);
    }
}
