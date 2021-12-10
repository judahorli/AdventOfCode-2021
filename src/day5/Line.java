package day5;

import java.util.ArrayList;

public class Line {
    final Point p1;
    final Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean isHorizOrVertLine() {
        return p1.equalsX(p2) || p1.equalsY(p2) || p1.equals(p2);
    }

    public ArrayList<Point> getDiagonalPoints() {
        System.out.println(this);
        ArrayList<Point> points = new ArrayList<>();
        if(p1.getX() < p2.getX()){
            if(p1.getY() < p2.getY()){
                for (int i = 0; i <= Math.abs(p2.getX() - p1.getX()); i++) {
                    points.add(new Point(p1.getX() + i, p1.getY() + i));
                }
            } else {
                for(int i = 0; i <= Math.abs(p2.getX() - p1.getX()); i++){
                    points.add(new Point(p1.getX() + i, p1.getY() - i));
                }
            }
        }
        if(p1.getX() > p2.getX()) {
            if(p1.getY() < p2.getY()){
                for(int i = 0; i <= Math.abs(p2.getX() - p1.getX()); i++){
                    points.add(new Point(p1.getX() - i, p1.getY() + i));
                }
            } else {
                for(int i = 0; i <= Math.abs(p2.getX() - p1.getX()); i++){
                    points.add(new Point(p1.getX() - i, p1.getY() - i));
                }
            }
        }

        System.out.println(points);
        return points;
    }

    public ArrayList<Point> getHorizVertBetweenPoints(){
        ArrayList<Point> points = new ArrayList<>();
        if(p1.equalsX(p2)){
            if(p1.getY()<p2.getY()) {
                for (int i = p1.getY(); i <= p2.getY(); i++) {
                    points.add(new Point(p1.getX(), i));
                }
            } else {
                for (int i = p2.getY(); i <= p1.getY(); i++) {
                    points.add(new Point(p1.getX(), i));
                }
            }
        } else if (p1.equalsY(p2)){
            if(p1.getX() < p2.getX()){
                for(int i = p1.getX(); i <= p2.getX(); i++){
                    points.add(new Point(i, p1.getY()));
                }
            } else {
                for(int i = p2.getX(); i <= p1.getX(); i++){
                    points.add(new Point(i, p1.getY()));
                }
            }

        }
        return points;
    }

    @Override
    public String toString() {
        return "Line{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
