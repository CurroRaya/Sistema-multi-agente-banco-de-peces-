package sample;

public class Objeto{
    public double posX;
    public double posY;

    public Objeto(){}

    public Objeto(double _x, double _y){
        posX = _x;
        posY = _y;
    }

    public double Distancia(Objeto o){
        return Math.sqrt((o.posX - posX) * (o.posX - posX) + (o.posY - posY) * (o.posY - posY));
    }

    public double DistanciaCuadrado(Objeto o){
        return (o.posX - posX) * (o.posX - posX) + (o.posY - posY) * (o.posY - posY);
    }
}
