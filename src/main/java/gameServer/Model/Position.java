package gameServer.Model;


import lombok.Data;

@Data
public class Position {

    private int positionX;
    private int positionY;

    Position(int x,int y){
        this.positionX=x;
        this.positionY=y;
    }

    void posUp(){
        this.positionX--;
    }
    void posDown(){
        this.positionX++;
    }
    void posLeft(){
        this.positionY--;
    }
    void posRight(){
        this.positionY++;
    }
}
