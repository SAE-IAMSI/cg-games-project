package games.project.modules.statistiques;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;

public class Main {
    public static void main(String[] args) throws IOException {
        String absolutePath = FileSystems.getDefault().getPath("src/main/PythonFileStats/test.py").normalize().toAbsolutePath().toString();
        // execute le fichier python et récupère les lignes affichées dans la console
        String[] cmd = new String[]{"python", absolutePath};
        System.out.println("Executing command: " + String.join(" ", cmd));
        Process p = Runtime.getRuntime().exec(cmd);

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();

    }
}
