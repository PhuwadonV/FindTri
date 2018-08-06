import java.util.*;
import java.io.*;

public class FindTri {
	private static final Console CONSOLE = System.console();
	private static Map<Long, Map<Double, Double>> aMap = new HashMap<Long, Map<Double, Double>>();
	private static Map<Long, Map<Double, Double>> bMap = new HashMap<Long, Map<Double, Double>>();
	private static Map<Long, Map<Double, Double>> cMap = new HashMap<Long, Map<Double, Double>>();
	private static Map<Long, Map<Double, Double>> heightMap = new HashMap<Long, Map<Double, Double>>();
	private static Map<Long, Double> amountMap = new HashMap<Long, Double>();
	private static Map<Long, Double> piMap = new HashMap<Long, Double>();
	
	public static void print(String str) {
		System.out.print(str);
	}
	
	public static void println(String str) {
		System.out.println(str);
	}
	
	public static String readLine() {
		return CONSOLE.readLine();
	}
	
	public static Double readDouble() {
		return Double.parseDouble(readLine());
	}
	
	public static Integer readInt() {
		return Integer.parseInt(readLine());
	}
	
	public static Long readLong() {
		return Long.parseLong(readLine());
	}
	
	public static double calA(double radius, long n) {
		if(n <= 0L) return 0.0;
		else {
			Map<Double, Double> map = aMap.get(n);
			if(map == null) {
				map = new HashMap<Double, Double>();
				aMap.put(n, map);
			}
			Double obj = map.get(radius);
			if(obj != null) return obj;
			else {
				double a = radius - calHeight(radius, n - 1);
				map.put(radius, a);
				return a;
			} 
		}
	}
	
	public static double calB(double radius, long n) {
		if(n <= 0L) return 0.0;
		else {
			Map<Double, Double> map = bMap.get(n);
			if(map == null) {
				map = new HashMap<Double, Double>();
				bMap.put(n, map);
			}
			Double obj = map.get(radius);
			if(obj != null) return obj;
			else {
				double b = calC(radius, n - 1) / 2.0;
				map.put(radius, b);
				return b;
			} 
		}
	}
	
	public static double calC(double radius, long n) {
		if(n <= 0L) return 2.0 * radius;
		else {
			Map<Double, Double> map = cMap.get(n);
			if(map == null) {
				map = new HashMap<Double, Double>();
				cMap.put(n, map);
			}
			Double obj = map.get(radius);
			if(obj != null) return obj;
			else {
				double a = calA(radius, n);
				double b = calB(radius, n);
				double c = Math.sqrt((a * a) + (b * b));
				map.put(radius, c);
				return c;
			} 
		}
	}
	
	public static double calHeight(double radius, long n) {
		Map<Double, Double> map = heightMap.get(n);
		if(map == null) {
			map = new HashMap<Double, Double>();
			heightMap.put(n, map);
		}
		Double obj = map.get(radius);
		if(obj != null) return obj;
		else {
			double buff = calC(radius, n) / 2.0;
			double height = Math.sqrt((radius * radius) - (buff * buff));
			map.put(radius, height);
			return height;
		}
	}
	
	public static double calAmount(long n) {
		Double obj = amountMap.get(n);
		if(obj != null) return obj;
		else {
			double amount = Math.pow(2.0 , n + 1L);
			amountMap.put(n, amount);
			return amount;
		}
	}
	
	public static double calPi(long n) {
		Double obj = piMap.get(n);
		if(obj != null) return obj;
		else {
			double pi = (calC(1.0, n) * calAmount(n)) / 2.0;
			piMap.put(n, pi);
			return pi;
		}
	}
	
	public static double calCosToSin(double cos) {
		return Math.sqrt(1.0 - (cos * cos));
	}
	
	public static double calCosHelper(double angle, double pi, long i, long n) {
		double angleFragment = pi / calAmount(i);
		if(i >= n) {
			double heightA = calHeight(1.0, i);
			double halfWidthA = calCosToSin(heightA);
			if(angle >= (3.0 * angleFragment) / 2.0)
				return (heightA * heightA) - (halfWidthA * halfWidthA);
			else if(angle >= angleFragment / 2.0)
				return heightA;
			else
				return 1.0;
		}
		else {
			if(angle >= angleFragment) {
				double heightA = calHeight(1.0, i);
				double heightB = calCosHelper(angle - angleFragment, pi, i + 1L, n);
				double halfWidthA = calCosToSin(heightA);
				double halfWidthB = calCosToSin(heightB);
				return (heightA * heightB) - (halfWidthA * halfWidthB);
			}
			else
				return calCosHelper(angle, pi, i + 1L, n);
		}
	}
	
	public static double calCos(double angle, long n) {
		double pi = calPi(n);
		double twoPi = 2.0 * pi;
		angle = Math.abs(angle);
		angle -= twoPi * ((long)(angle / twoPi));
		if(angle >= pi)
			return -calCosHelper(angle - pi, pi, 0L, n);
		else
			return calCosHelper(angle, pi, 0L, n);
	}
	
	public static void findPi() {
		try {
			print("Precision : ");
			long n = readLong();
			if(n < 0L) return;
			println("PI        : " + calPi(n));
		}
		catch(NumberFormatException e) {}
	}
	
	public static void findCos() {
		try {
			print("Precision   : ");
			long n = readLong();
			print("Angle (RAD) : ");
			double angle = readDouble();
			print("Result      : " + calCos(angle, n));
		}
		catch(NumberFormatException e) {}
	}
	
	public static void main(String[] args) {
		println("Select Mode");
		println("1. PI");
		println("2. Cos");
		try {
			print("Mode : ");
			int mode = readInt();
			println("------------------------------");
			switch(mode) {
				case 1:
					findPi();
					break;
				case 2:
					findCos();
					break;
			}
		}
		catch(NumberFormatException e) {}
	}
}