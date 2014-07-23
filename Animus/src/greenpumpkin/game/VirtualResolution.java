package greenpumpkin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class VirtualResolution extends OrthographicCamera{
    
    public VirtualResolution(int width, int height){
        super(width, height);
        this.position.x=width/2;
        this.position.y=height/2;
    }
}