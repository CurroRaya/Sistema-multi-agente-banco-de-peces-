package sample;

import java.io.Console;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Oceano extends Observable {
    protected Pez[] peces;
    protected ArrayList<ZonaAEvitar> obstaculos;
    protected Random generator;
    protected double ancho;
    protected double alto;

    public Oceano(int _numPeces, double _ancho, double _alto){
        ancho = _ancho;
        alto = _alto;
        generator = new Random();
        obstaculos = new ArrayList<>();
        peces = new Pez[_numPeces];

        for(int i = 0; i < _numPeces; i++){
            peces[i] = new Pez(generator.nextDouble() * ancho, generator.nextDouble() * alto, generator.nextDouble() * 2 * Math.PI);
        }
    }

    public void AgregarObstaculo(double _posX, double _posY, double _radio){
        obstaculos.add(new ZonaAEvitar(_posX, _posY, _radio));
    }

    protected void ActualizarObstaculos(){
        for(ZonaAEvitar obstaculo: obstaculos){
            obstaculo.Actualizar();
        }

        obstaculos.removeIf(o -> o.estaMuerto());
    }

    protected void ActualizarPeces(){
        for(Pez p: peces){
            p.ActualizarPez(peces, obstaculos, ancho, alto);
        }
    }

    public void ActualizarOceano(){
        ActualizarObstaculos();
        ActualizarPeces();
        setChanged();
        notifyObservers();
    }
}
