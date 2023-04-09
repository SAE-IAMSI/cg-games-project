package games.project.modules.statistiques;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(recupFonction("getNbPlayers",null));
    }

    public static String recupFonction(String nomFunc, ArrayList<String> args) throws IOException {
        String absolutePath = FileSystems.getDefault().getPath("src/main/PythonFileStats/app/execute.py").normalize().toAbsolutePath().toString();
        ArrayList<String> commandeListe = new ArrayList<>();
        commandeListe.add("python");commandeListe.add(absolutePath);commandeListe.add(nomFunc);
        if(args != null){
            commandeListe.addAll(args);
        }
        String[] cmd = commandeListe.toArray(String[]::new);
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            return line;
        }
        in.close();
        return "Erreur de lecture";
    }
}