package tetris.shapes;

import javagameengine.backend.input.Input;
import javagameengine.backend.input.Keys;
import javagameengine.components.Component;
import javagameengine.components.GameObject;
import javagameengine.components.physics.PhysicsBody;
import javagameengine.msc.Vector2;
import tetris.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Shape extends GameObject {

    PhysicsBody b = new PhysicsBody(false);
    public String color = "/yellowCube.png";
    public int[][] shape = {
            {0,0,0,0},
            {0,0,1,0},
            {0,0,1,0},
            {0,1,1,1}};

    public Shape(){
        addChild(b);
    }

    public void setRandomShape(){
        Random random = new Random();
        switch (random.nextInt(6)){
            case 0:
                shape = Shapes.L;
                color = "/yellowCube.png";
                break;
            case 1:
                shape = Shapes.J;
                color = "/pinkCube.png";

                break;
            case 2:
                shape = Shapes.O;
                color = "/darkblueCube.png";

                break;
            case 3:
                shape = Shapes.T;
                color = "/lightgreenCube.png";

                break;
            case 4:
                shape = Shapes.I;
                color = "/blueCube.png";
                break;
            case 5:
                shape = Shapes.S;
                color = "/redCube.png";
                break;
            case 6:
                shape = Shapes.Z;
                color = "/greenCube.png";
                break;
        }
        loadShape();
    }
    public void loadShape(){
        setChildren(new LinkedList<Component>());
        int rowIndex = 0;
        int colIndex = 0;
        for(int[] row : shape){
            for(int p : row){
                if(p==1){

                    Box box = new Box(color);
                    float m = box.getLocalScale().getX()*4;
                    box.setLocalPosition(new Vector2(colIndex*m,rowIndex*m));

                    addChild(box);
                }
                colIndex++;
            }
            rowIndex++;
            colIndex=0;
        }

    }


    @Override
    public void draw(Graphics g) {
        //super.draw(g);
       drawChildren(g);
    }

    public Shape clone(){
        Shape shape1 = new Shape();
        shape1.setPosition(getPosition());
        shape1.setScale(getScale());
        for(Component child : getChildren()){
            shape1.addChild(child);
        }
        return shape1;
    }

    private int[][] rotateMatrix(int N, int mat[][])
    {
        // Consider all squares one by one
        for (int x = 0; x < N / 2; x++) {
            // Consider elements in group
            // of 4 in current square
            for (int y = x; y < N - x - 1; y++) {
                // Store current cell in
                // temp variable
                int temp = mat[x][y];

                // Move values from right to top
                mat[x][y] = mat[y][N - 1 - x];

                // Move values from bottom to right
                mat[y][N - 1 - x]
                        = mat[N - 1 - x][N - 1 - y];

                // Move values from left to bottom
                mat[N - 1 - x][N - 1 - y]
                        = mat[N - 1 - y][x];

                // Assign temp to left
                mat[N - 1 - y][x] = temp;
            }
        }
        return mat;
    }

    public ArrayList<Box> getBoxes(){
        ArrayList<Box> boxes = new ArrayList<>();
        for(Component box : getChildren(new Box(""))){
            boxes.add((Box) box);
        }
        return boxes;
    }

    @Override
    public void update() {
        super.update();
        if(Input.isKeyPressed(Keys.A)){
            movePosition(getPosition().add(Vector2.left.subtract(new Vector2(Box.width,0))));
        }
        if(Input.isKeyPressed(Keys.D)){
            movePosition(getPosition().add(Vector2.right.add(new Vector2(Box.width,0))));
        }
        if(Input.isKeyPressed(Keys.W)){
            rotateMatrix(4,shape);
            loadShape();
        }
        if(Input.isKeyDown(Keys.S)){
            Main.mult = 2;
        }
        else{
            Main.mult = 1;

        }
    }
}


