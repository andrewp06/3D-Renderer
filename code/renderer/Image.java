package code.renderer;

public class Image {
    int width;
    int height;

    public Image( int width,int height){
        this.width = width;
        this.height = height;
    }

    public Image(){
        this(256, 192);
    }
}
