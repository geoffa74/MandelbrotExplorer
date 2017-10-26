package math;

public class Complex {
	
	public double real;
	public double complex;
	
	public Complex(double real, double complex) {
		this.real = real;
		this.complex = complex;
	}
	
	public Complex add(Complex n) {
		return new Complex(real + n.real, complex + n.complex);
	}
	
	public Complex multiply(Complex n) {
		return new Complex(real * n.real - complex * n.complex, real * n.complex + n.real * complex);
	}

}
