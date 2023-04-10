package games.project.modules.statistiques;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static String dateLisible(String s){
        String[] tab = s.split(" ");
        String[] date = tab[0].split("-");
        return date[2] + "/" + date[1] + "/" + date[0];
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        ArrayList<String> listeJeux = Surcouche.splitTableau(Surcouche.recupFonction("getAllGame",null));
        String jeuCourant = listeJeux.get(0);
        ArrayList<String> args2 = new ArrayList<>();
        Map<String,String> mapAvgScore = new HashMap<>();
        Map<String,String> mapBestScore = new HashMap<>();

        for(String game : listeJeux){
            args2.clear();args2.add(game);
            mapAvgScore.put(game,Surcouche.recupFonction("getScoreMoyen",args2));
            mapBestScore.put(game,Surcouche.recupFonction("getBestScore",args2));
            Surcouche.recupFonction("getGrapheScoreMoyen",args2);
            args2.clear();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("finish");
        System.out.println("temps d'execution : " + (endTime - startTime) + " ms");
    }
}