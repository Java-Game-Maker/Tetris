package tetris;

import com.sun.jdi.InconsistentDebugInfoException;
import javagameengine.components.Component;
import javagameengine.msc.CameraMovement;
import tetris.shapes.Box;
import tetris.shapes.Shape;
import javagameengine.JavaGameEngine;
import javagameengine.backend.Scene;
import javagameengine.components.GameObject;
import javagameengine.components.colliders.SquareCollider;
import javagameengine.msc.Debug;
import javagameengine.msc.Vector2;

import javax.swing.plaf.DesktopIconUI;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaGameEngine {

    public Shape currentShape = new Shape();
    ArrayList<Shape> prev = new ArrayList<>();
    Scene scene = new Scene();

    public static int mult = 1;

    public Main(){
        currentShape.setRandomShape();
        scene.components.add(currentShape);

        Debug.showWhere = true;
        scene.getCamera().setScale(new Vector2(0.5f,0.5f));
        //scene.getCamera().addChild(new CameraMovement());

        GameObject floor = new GameObject();
        floor.setPosition(new Vector2(0,800));
        floor.setScale(new Vector2(2000,10));

        floor.addChild(new SquareCollider());

        scene.components.add(floor);

        setSelectedScene(scene);
    }
    private float isFilled(){
        HashMap<Float,Integer> levels = new HashMap<Float, Integer>();

        for(Component comp : scene.components){
            levels.put(comp.getPosition().getY(),(levels.get(comp.getPosition().getY()))!=null?levels.get(comp.getPosition().getY())+1:1);
        }
        Float key = Collections.max(levels.entrySet(), Map.Entry.comparingByValue()).getKey();
        Debug.log(key);
        Debug.log(levels.get(key));
        if(levels.get(key)>4)
            return key;
        return 0;
    }

    private void removeRow(float level){
        // * remove boxes at
        // * move boxes above down

        for(Component comp : scene.components){
            if(comp.getPosition().getY()==level){
                comp.destroy();
            }
        }
        for(Component comp : scene.components){
            if(comp instanceof Box && comp.getPosition().getY()<level){
                comp.setPosition(new Vector2(comp.getPosition().getX(),comp.getPosition().getY()+Box.width));
            }
        }

    }

    @Override
    public void update() {
        super.update();

        if(currentShape.movePosition(currentShape.getPosition().add(Vector2.down.multiply(mult))).getY()==0){


            for(Box box : currentShape.getBoxes()){
                Box box1 = new Box(currentShape.color);
                box1.setPosition((box.getPosition().subtract(origin)).subtract(origin));
                box1.setScale(new Vector2(Box.width,Box.width));

                scene.components.add(box1);
            }
            currentShape.setPosition(origin.add(new Vector2(0,0)));
            currentShape.setRandomShape();

            if(isFilled()!=0){
                removeRow(isFilled());
            }
        }
    }
    public static void main(String[] args){
        Main m = new Main();
        m.start();
    }

}
