package games.project.modules.statistiques;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.*;

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

    public static void creationDesGraphes() throws IOException{
        //Creation des graphes des jeux
        ArrayList<String> listeJeux = Surcouche.splitTableau(Surcouche.recupFonction("getAllGame",null));
        String jeuCourant = listeJeux.get(0);
        ArrayList<String> args = new ArrayList<>();
        for(String game : listeJeux){
            long startTime = System.currentTimeMillis();
            System.out.println("Jeu : " + game);
            args.clear();args.add(game);
            Surcouche.recupFonction("getGrapheScoreMoyen",args);
            long endTime = System.currentTimeMillis();
            System.out.println("temps d'execution : " + (endTime - startTime) + " ms");
            args.clear();
        }

        //Creation des graphes des joueurs
        recupFonction("getPieActifsNonActifs",null);

        //Creation des graphes des DPT
        recupFonction("getDPTPie",null);
    }

    public static LinkedHashMap<String, String> splitDPT(String s){
        String dpt[] = s.split(",");
        LinkedHashMap<String,String> dptJoueurs = new LinkedHashMap<>();
        for (String str : dpt){
            if(str.charAt(0)=='{'){
                str = str.substring(1);
            } else if (str.charAt(str.length()-1)=='}'){
                str = str.substring(0,str.length()-1);
            }
            str = str.replaceAll(" ","");
            str = str.replaceAll("'","");
            String[] tab = str.split(":");
            dptJoueurs.put(tab[0],tab[1]);
        }
        return dptJoueurs;
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println(recupFonction("getPieActifsNonActifs",null));
        long endTime = System.currentTimeMillis();
        System.out.println("temps d'execution : " + (endTime - startTime) + " ms");
    }
}