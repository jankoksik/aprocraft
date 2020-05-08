import org.joml.*;

public class Camera {
    private Vector3f position, rotation;
    private Matrix4f projection;

    public Camera(int width, int height){
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);

      //  projection = new Matrix4f().setOrtho2D((float)-width/2, (float)width/2, (float)-height/2, (float)height/2);

    }

    public Matrix4f getViewMatrix(){
     Matrix4f rotateX = new Matrix4f().rotate(rotation.x, new Vector3f(1,0,0));
        Matrix4f rotateY = new Matrix4f().rotate(rotation.y, new Vector3f(0,1,0));
        Matrix4f rotateZ = new Matrix4f().rotate(rotation.z, new Vector3f(0,0,1));
        Matrix4f rotation = rotateX.mul(rotateY.mul(rotateZ));

        Vector3f negPosition = position.mul(-1);
        Matrix4f translation = new Matrix4f().translate(negPosition);

        return translation.mul(rotation);
    }

    public void setPosition(Vector3f position){
        this.position = position;
    }
    public void setRotation(Vector3f rotation){
        this.rotation = rotation;
    }
    public void setPosition(float x, float y, float z){
        this.position =new Vector3f(x,y,z);
    }
    public void setRotation(float x, float y, float z){
        this.rotation = new Vector3f(x,y,z);
    }
    public void addPosition(Vector3f position)
    {
        this.position.add(position);
    }
    public void addRotation(Vector3f rotation)
    {
        this.rotation.add(rotation);
    }
    public void addPosition(float x, float y, float z) {this.position = position.add(new Vector3f(x,y,z));}
    public void addRotation(float x, float y, float z)
    {
        this.rotation = rotation.add(new Vector3f(x,y,z));
    }





    public Vector3f getPosition() {return  position;}

    public Matrix4f getProjection(){
        Matrix4f target = new Matrix4f();
        Matrix4f pos = new Matrix4f().setTranslation(position);

        target = projection.mul(pos, target);
        return  target;

    }

}
