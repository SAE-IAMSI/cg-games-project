package games.project.modules.statistiques;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;

public class Surcouche {

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

    public static ArrayList<String> splitTableau(String s){
        ArrayList<String> res = new ArrayList<>();
        s = s.substring(1, s.length() - 1);
        String[] tab = s.split(",");
        for (String str : tab){
            str = str.replaceAll(" ","");
            str = str.replaceAll("'","");
            res.add(str);
        }
        return res;
    }

    /*public static void main(String[] args) throws IOException {
        ArrayList<String> arg = new ArrayList<>();
        arg.add("KR");
        recupFonction("getGrapheScoreMoyen", arg);
    }*/
}