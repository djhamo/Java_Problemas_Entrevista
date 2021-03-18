public class ProxPI {
 
    public static void main(String[] args) {
        System.out.println(calcPI());
    }
 
    public static double calcPI() {
 
        int limite = 1000000000;
        double pi = 0;
        double denominator = 1;
        double erro = 0.0001;

        for (int x = 0; x < limite; x++) {
 
            if (x % 2 == 0) {
                pi = pi + (4 / denominator);
            } else {
                pi = pi - (4 / denominator);
            }
            denominator = denominator + 2;
            
            if ( Math.abs(Math.PI - pi) <= erro)
                    break;
        }

        return pi;
    }
}