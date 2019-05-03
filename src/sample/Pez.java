package sample;

import java.util.ArrayList;

public class Pez extends Objeto {
    public static final double PASO = 3;
    public static final double DISTANCIA_MIN = 5;
    public static final double DISTANCIA_MIN_CUADRADO = 25;
    public static final double DISTANCIA_MAX = 40;
    public static final double DISTANCIA_MAX_CUADRADO = 1600;

    protected double velocidadX;
    protected double velocidadY;

    public Pez(double _x, double _y, double _dir){
        posX = _x;
        posY = _y;
        velocidadX = Math.cos(_dir);
        velocidadY = Math.sin(_dir);
    }

    public double getVelocidadX() {
        return velocidadX;
    }

    public double getVelocidadY() {
        return velocidadY;
    }

    protected void ActualizarPosicion(){
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
    }

    protected boolean EnAlineacion(Pez p){
        double distanciaCuadrado = DistanciaCuadrado(p);

        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }

    protected double DistanciaAlMuro(double muroXMin, double muroYMin, double muroXMax, double muroYMax){
        double min = Math.min(posX - muroXMin, posY - muroYMin);
        min = Math.min(min, muroXMax - posX);
        min = Math.min(min, muroYMax - posY);

        return min;
    }

    protected void Normalizar(){
        double longitud = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= longitud;
        velocidadY /= longitud;
    }

    protected boolean EvitarMuros(double muroXMin, double muroYMin, double muroXMax, double muroYMax){
        if(posX < muroXMin){
            posX = muroXMin;
        } else if(posY < muroYMin){
            posY = muroYMin;
        } else if(posX > muroXMax){
            posX = muroXMax;
        } else if(posY > muroYMax){
            posY = muroYMax;
        }

        double distancia = DistanciaAlMuro(muroXMin, muroYMin, muroXMax, muroYMax);
        if(distancia < DISTANCIA_MIN){
            if(distancia == (posX - muroXMin)){
                velocidadX += 0.3;
            } else if(distancia == (posY - muroYMin)){
                velocidadY += 0.3;
            } else if(distancia == (muroXMax - posX)){
                velocidadX -= 0.3;
            } else if(distancia == (muroYMax - posY)){
                velocidadY -= 0.3;
            }

            Normalizar();
            return true;
        }

        return false;
    }

    protected boolean EvitarObstaculos(ArrayList<ZonaAEvitar> obstaculos){
        if(!obstaculos.isEmpty()){
            ZonaAEvitar obstaculoProximo = obstaculos.get(0);
            double distanciaAlCuadrado = DistanciaCuadrado(obstaculoProximo);
            for(ZonaAEvitar o: obstaculos){
                if(DistanciaCuadrado(o) < distanciaAlCuadrado){
                    obstaculoProximo = o;
                    distanciaAlCuadrado = DistanciaCuadrado(o);
                }
            }

            if(distanciaAlCuadrado < obstaculoProximo.radio * obstaculoProximo.radio){
                double distancia = Math.sqrt(distanciaAlCuadrado);
                double diffX = (obstaculoProximo.posX - posX) / distancia;
                double diffY = (obstaculoProximo.posY - posY) / distancia;
                velocidadX = velocidadX - diffX / 2;
                velocidadY = velocidadY - diffY / 2;
                Normalizar();
                return true;
            }
        }
        return false;
    }

    protected boolean EvitarPeces(Pez[] peces){
        Pez p;
        if(!peces[0].equals(this)){
            p = peces[0];
        } else {
            p = peces[1];
        }

        double distanciaAlCuadrado = DistanciaCuadrado(p);
        for(Pez pez: peces){
            if(DistanciaCuadrado(pez) < distanciaAlCuadrado && !pez.equals(this)){
                p = pez;
                distanciaAlCuadrado = DistanciaCuadrado(p);
            }
        }

        if(distanciaAlCuadrado < DISTANCIA_MIN_CUADRADO){
            double distancia = Math.sqrt(distanciaAlCuadrado);
            double diffX = (p.posX - posX) / distancia;
            double diffY = (p.posY - posY) / distancia;
            velocidadX = velocidadX - diffX / 4;
            velocidadY = velocidadY - diffY / 4;
            Normalizar();
            return true;
        }

        return false;
    }

    protected void CalcularDireccionMedia(Pez[] peces){
        double velocidadXTotal = 0;
        double velocidadYTotal = 0;
        int numTotal = 0;

        for(Pez p: peces){
            velocidadXTotal += p.velocidadX;
            velocidadYTotal += p.velocidadY;
            numTotal++;
        }

        if(numTotal >= 1){
            velocidadX = (velocidadXTotal / numTotal + velocidadX) / 2;
            velocidadY = (velocidadYTotal / numTotal + velocidadY) / 2;
            Normalizar();
        }
    }

    protected void ActualizarPez(Pez[] peces, ArrayList<ZonaAEvitar> obstaculos, double ancho, double alto){
        if(!EvitarMuros(0,0,ancho,alto)){
            if(!EvitarObstaculos(obstaculos)){
                if(!EvitarPeces(peces)){
                    CalcularDireccionMedia(peces);
                }
            }
        }

        ActualizarPosicion();
    }
}
