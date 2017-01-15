import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        PublicatiesGenerator generator = new PublicatiesGenerator();
        String publicaties = generator.generatePublicaties();

        try (PrintWriter out = new PrintWriter("publicaties.txt")) {
            out.print(publicaties);
        }
    }
}
