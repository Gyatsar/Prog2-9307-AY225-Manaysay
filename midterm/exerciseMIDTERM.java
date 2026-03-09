public class exerciseMIDTERM {
    public static void main(String[] args) {
        int a = -3, b = -5, c = -1;
        int d =  2, e = -4, f =  3;
        int g =  1, h =  2, i = -2;

        int t1 = e * i - f * h;
        int t2 = d * i - g * f;
        int t3 = d * h - g * e;

        int solution = (a * t1) - (b * t2) + (c * t3);

        System.out.println("Step 1: a x (exi - fxh) = " + a + " x (" + e + "x" + i + " - " + f + "x" + h + ") = " + a + " x " + t1 + " = " + (a * t1));
        System.out.println("Step 2: b x (dxi - gxf) = " + b + " x (" + d + "x" + i + " - " + g + "x" + f + ") = " + b + " x " + t2 + " = " + (b * t2));
        System.out.println("Step 3: c x (dxh - gxe) = " + c + " x (" + d + "x" + h + " - " + g + "x" + e + ") = " + c + " x " + t3 + " = " + (c * t3));
        System.out.println();
        System.out.println("Final: " + (a * t1) + " - (" + (b * t2) + ") + (" + (c * t3) + ") = " + solution);
        System.out.println("Solution: " + solution);
    }
}
