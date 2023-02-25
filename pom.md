# CG Games Project

Ce document recense des instructions concernant le bon fonctionnement du `pom.xml`.

## Dépendances à ajouter/modifier

```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <junit.version>5.8.1</junit.version>
    <skipDeploy>true</skipDeploy>
</properties>

```

    <dependency>
        <groupId>com.oracle.ojdbc</groupId>
        <artifactId>ojdbc10</artifactId>
        <version>19.3.0.0</version>
    </dependency>

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-deploy-plugin</artifactId>
    <version>3.0.0-M1</version>
    <configuration>
        <skip>true</skip>
    </configuration>
</plugin>
```

## Explications

Comme expliqué dans le document `README.md`, les `merge requests` vont permettre de fusionner votre travail avec la **branche principale**.

Nous avons pour cela créé des **Actions** directement sur GitHub qui permettent de vérifier que votre code est correctement **compilé** et que les **tests unitaires** passent.

Pour cela, nous avons besoin de définir les **dépendances** nécessaires à la compilation et au bon fonctionnement de votre code.

## Vérification

Vérifier que les trois blocs de code ci-dessus sont bien présents dans votre `pom.xml`.

Vous pouvez également aller avoir le contenu du `pom.xml` sur la branche principale du jeu Koala Rock.


