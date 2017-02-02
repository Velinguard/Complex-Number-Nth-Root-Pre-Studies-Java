/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NthRoots;

/**
 *
 * @author Sam
 */
public class Root {
    int n;
    double[] exp;
    ComplexNum[] points;
    double[] m;
        
    public Root(int n, boolean one){
        this.n = n;
        this.points = new ComplexNum[n];
        this.m = new double[n + 1];
        //findM();
        findRoots(one);
    }
    private void findM(){
        for (int i = 0; i < n; i++){
            double a = Math.tan(findAngle(i, true));
            System.out.println(n);
            while (a > Math.PI){
                a -= 2 * Math.PI;
            }
            while (a < - Math.PI){
                a += 2 * Math.PI;
            }
            m[i] = a;
        }
    }
    private double findAngle(int k, Boolean one){
        double a;
        if (one) {
            a = 2 * Math.PI * k; 
        } else { 
            a = (2 * k - 1)  * Math.PI; 
        }
        a /= n;
        return a;
    }
    private void findRoots(Boolean one){
        for (int i = 0; i < n; i++){
            double r = Math.cos(findAngle(i, one));
            double comp = Math.sin(findAngle(i, one));
            points[i] = new ComplexNum(r, comp);
        }
    }
}
class ComplexNum{
    double real;
    double complex;
    public ComplexNum(double r, double i){
        this.real = r;
        this.complex = i;
    }
}