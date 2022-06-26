package tetris.shapes;

import javagameengine.components.Component;
import javagameengine.components.GameObject;
import javagameengine.components.colliders.SquareCollider;
import javagameengine.components.sprites.Sprite;
import javagameengine.msc.Vector2;

import java.awt.*;

public class Box extends GameObject {

    public static int width = 80;
    public static int height = 80;

    public Box(String color){

        setLocalScale(new Vector2(-100+width,-100+height));

        Sprite sprite = new Sprite();

        sprite.loadAnimation(new String[]{color});

        addChild(sprite);

        SquareCollider s = new SquareCollider();
        s.setVisible(false);
        addChild(s);

    }

    @Override
    public void onCollision(Component c) {
        super.onCollision(c);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }
}
