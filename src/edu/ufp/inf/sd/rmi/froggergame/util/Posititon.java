package edu.ufp.inf.sd.rmi.froggergame.util;

import java.io.Serializable;

public class Posititon implements Serializable {
    private double x;
    private double y;

    public Posititon(double X, double Y) {
        x = X;
        y = Y;
    }

    public Double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return x+","+y;
    }
}
