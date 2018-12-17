package com.hydrogennx;

import javafx.scene.image.Image;

import java.util.Random;

public class SpearBullet extends SpriteBullet {

    final double VELOCITY = 1;
    double direction; //0 to 2 pi

    public SpearBullet(GameActionPane context, AttackSequence source) {

        super(context, source, 0.05);

        //TODO create a dictionary for image ids rather than hard-coding them.
        sprite.setImage(new Image("file:res/tracer-bullet.png"));

        Random random = new Random();

        double attackAngle = (double) random.nextInt(10000);
        attackAngle /= 10000;
        attackAngle *= Math.PI * 2;

        double startingX = Math.cos(attackAngle) * 100;
        double startingY = Math.sin(attackAngle) * 100;

        sprite.setX(startingX);
        sprite.setY(startingY);

        direction = attackAngle + Math.PI;
        if (direction > Math.PI * 2) {
            direction -= Math.PI * 2;
        }

        sprite.setRotate(direction * 360 / (Math.PI * 2));

        xVelocity = VELOCITY * Math.cos(direction);
        yVelocity = VELOCITY * Math.sin(direction);

    }

    @Override
    public void update(double time) {

        super.update(time);

        if (sprite.getY() > context.getHeight() || sprite.getY() < 0) {

            context.destroyBullet(this);

        }

        if (sprite.getX() > context.getWidth() || sprite.getX() < 0) {

            context.destroyBullet(this);

        }

    }
}