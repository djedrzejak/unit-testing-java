

public class Test {

	public static void main(String[] args) {
		System.out.print(A.i);
        System.out.print(B.i);
    }
	
}

class A {
	static int i = 1;
    static {
        i = 2;
    }
}

class B {
    static {
        i = 2;
    }
    static int i = 1;
}