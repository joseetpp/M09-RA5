public class Rot13{
    public static void main(String[] args){
        String msgs[] =("ABC","XYZ","Hola, Mr. calçot", "Perdó, per tu què és?");
        String msgXifrat[] = new String[msgs.length];

        System.out.println("\nXifrat\n-------------");

        for(int i=0; i<msgXifrat.length; i++){
            msgXifrat[i] = xifraRor13(msgs[i]);
            System.out.println("%-23s => %s%n", msgs[i], msgXifrat[i]);
        }
        System.out.println("\nDesxifrat\n--------");
        
        for(String msg: msgXifrat){
            System.out.println("%-23s => %s%n", msg, desxifraRor13[msg]);
        }
    }
}