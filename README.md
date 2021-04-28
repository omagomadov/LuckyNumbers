# Lucky numbers - 54516

<p align="center"><img src="https://x.boardgamearena.net/data/themereleases/current/games/luckynumbers/210412-1603/img/game_box180.png"></p>

### Le jeu utilise la variante en ligne de [Board Game Arena](https://fr.boardgamearena.com/). C'est-à-dire qu'au début du jeu chacun reçoit 4 tuiles qui sont placés automatiquement sur la diagonale dans l'ordre croissant. 

## L'implémentation de cette variante :

Pour ce faire une méthode privée `setTilesOnDiagonal()` a été ajouter dans la classe `Game`. Cette méthode consiste à ajouter sur le plateau de chaques joueurs 4 tuiles d'ordre croissant. 
* Plus précisement, pour chaque joueur elle pioche 4 * tuiles faces cachées.
* Une fois les tuiles piochées, elles sont désormais face visible sur la table. Ensuite elle utilise la méthode `sort` de la classe `Collections` pour pouvoir les trier en fournissant un `Comparator`, c'est-à-dire pouvoir se baser sur quoi les tuiles seront trier. Dans notre cas, ce sera leurs numéros.

```// Sort face up tiles in order from smallest to largest
        Collections.sort(this.deck.getAllFaceUp(),
                Comparator.comparing(tiles -> tiles.getValue()));
```
* Une fois les tuiles faces visibles trier dans l'ordre croissant, elles seront placer dans la diagonal du plateau de chaques joueurs avec la méthode `put()` de la classe `Board`.

* Pour terminer, la table (ou deck) sera vider avec la méthode `clear()` fournis par la classe `List`, car la méthode `put()` de la classe `Board` pose une tuile à une position sur le plateau mais le supprime pas de la table.

## Remarque :
Les tests qui utilisent la méthode `fullPlay()` de la classe `GameTest` retournent une `IllegalArgumentException`, car cette méthode essaye de mettre une tuile à une position qui ne respecte pas les règles du jeu. C'est dû à la méthode `setTilesOnDiagonal()` qui ajoute des tuiles d'ordres croissants sur la diagonal dans le plateau au début du jeu.

```
the tile can't be put on that position (position outside of the board or position not allowed by the rules)
java.lang.IllegalArgumentException
	at g54516.luckynumbers.model.GameTest.fullPlay(GameTest.java:47)
	at g54516.luckynumbers.model.GameTest.start_when_game_over_ok(GameTest.java:34)
```
