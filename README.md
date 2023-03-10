# CG Games Project

Ce document rencense des instructions et des astuces sur l'utilisation de Git et plus particulièrement de ce repo.

## Rappel

Le Super PO est [Mathis Viviers](https://github.com/Kelwarin).

Le seul (à ce jour) mainteneur du repo est [Valentin Vanhove](https://github.com/ValentinVnh). Il s'occupe de la gestion des branches. Pour assurer la qualité du projet et de son code, un administrateur sera par la suite élu pour pouvoir approuver et gérer les branches.

## Organisation du code

Il y a une branche principale (protégée) `main` qui constituera la base de toutes les autres sous-branches.

Chaque module dispose d'une branche. Ils auront une copie de ce qui est contenu de la branche principale et pourront travailler dessus.
- Chaque branche sera nommée `equipeX-equipier1-equipier2-equipier3` et correspond à votre équipe de travaille.
- (Il est possible de créer une sous-branche de votre équipe ce qui permet de diviser votre travail, celle-ci peut être nommée `equipeX-nomBranche`)

Libre à chacun d'utiliser les branches de fonctionnalités. 
Toutefois, il est recommandé d'y recourir puisque cela permet d'éviter les conflits lorsque plusieurs personnes travaillent sur la branche au même moment.

## Branche principale (protégée)

> Une branche est une copie du code source d'un projet à un moment donné. Les développeurs utilisent des branches pour travailler sur des modifications de code sans affecter la version principale. Lorsque les modifications sont terminées, les développeurs effectuent un "merge" nommée Pull requests pour fusionner leur travail dans la version principale.

La branche principale contient les fichiers du projet final. Elle est sécurisée et ne peut recevoir de push sans l'autorisation préalable d'une personne ayant-droit.

Voici un tutoriel de comment effectuer une demande de tirage "Pull request" sur Git (source : Internet) :

1. Assurez-vous d'être sur la branche que vous souhaitez fusionner : avant de commencer la demande de tirage. Vous pouvez vérifier votre position actuelle en utilisant la commande `git branch` dans votre terminal.

2. Mettez à jour la branche cible : assurez-vous que la branche à laquelle vous souhaitez fusionner votre travail est à jour en récupérant les dernières modifications de la branche principale avec la commande `git pull`.

3. Effectuez vos modifications de code : effectuez vos modifications de code en fonction de votre tâche ou de votre fonctionnalité. Utilisez les commandes `git add` et `git commit` pour ajouter et enregistrer vos modifications localement.

4. Publiez votre branche : une fois que vous avez terminé vos modifications et que vous avez effectué des commits, vous devez publier votre branche sur le référentiel distant en utilisant la commande `git push. Cela permettra aux autres membres de l'équipe de voir votre travail.

5. Créez une demande de tirage : maintenant que votre travail est publié, vous pouvez créer une demande de tirage pour fusionner votre branche avec la branche cible dans notre cas "main". Vous pouvez créer une demande de tirage en utilisant l'interface graphique de GitHub. Vous devez fournir des informations telles que le nom de la branche source et la branche cible, ainsi qu'une description détaillée de vos modifications.

6. Attendez la revue et l'approbation : une fois que vous avez créé la demande de tirage, d'autres membres de l'équipe peuvent examiner votre travail et laisser des commentaires. Il est possible que des modifications supplémentaires vous soit demande avant que celle-ci ne soit approuvée.

## Documentation du code et commit messages

Pour chacune des fonctionnalités, il est **essentiel** de documenter son code (comme vu en Qualité de Développement). Pensez également à tester régulièrement votre code et à respecter la qualité du code.
N'hésitez pas à tenir un document (hors code) qui contient des explications de comment votre code fonctionne — en cas de maintenance de personnes extérieures au projet (également pour la base de données).

Pensez également à détailler le plus possible vos messages de commit, afin qu'ils puissent être compris par tous.

## Règles sur les `Pull requests`

Tout Pull request ne respectant pas les règles susnommées est susceptible d'être refusé.
