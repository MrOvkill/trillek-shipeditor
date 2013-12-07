// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

public class FPSCamera
{
    public Vector3f position = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    
    public FPSCamera(float x, float y, float z)
    {
        position = new Vector3f(x, y, z);
    }
    
    public void yaw(float amount)
    {
        yaw += amount;
    }

    public void pitch(float amount)
    {
    	if(pitch < 90 && (amount + pitch) < 90 && (amount + pitch) > -90)
    		pitch += amount;
    	if(pitch > 90 && (amount + pitch) > -90 && (amount + pitch) < 90)
    		pitch += amount;
    		
    }
    
    public void walkForward(float distance)
    {
    		position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
    		position.z += distance * (float)Math.cos(Math.toRadians(yaw));
    }

    public void walkBackwards(float distance)
    {    	
    		position.x += distance * (float)Math.sin(Math.toRadians(yaw));
    		position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
    }

    public void strafeLeft(float distance)
    {
    		position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
    		position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
    }

    public void strafeRight(float distance)
    {
    		position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
    		position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
    }
    
    public void goUp(float distance)
    {
    	position.y -= distance;
    }
    
    public void goDown(float distance)
    {
		position.y += distance;
    }
    
    public void lookThrough()
    {
        glRotatef(pitch, 1, 0, 0);
        glRotatef(yaw, 0, 1, 0);
        glTranslatef(position.x, position.y, position.z);
    }
}