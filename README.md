### Application de messagerie client Java Socket

Cette application est la partie client d'une application de messagerie plus large composée de deux fichiers JAR exécutables et de packages associés :
- Côté Serveur
- Côté Client

L'application actuelle créera une connexion Socket à l'`adresse:port` du serveur.

Une fois connecté, le client devra choisir un pseudo qui n'est pas encore pris. Si le pseudo est accepté, le client peut alors commencer à envoyer des messages.

Chaque client connecté au même serveur aura accès aux messages à venir et verra clairement le pseudo des autres.

Les messages sont diffusés à tous les clients.

Pour arrêter l'application, il suffit d'entrer la commande :
`exit` sur la sortie standard.

> Attention à ne pas envoyer "exit" EN TANT QUE MESSAGE. Sinon, le serveur le considérera comme une demande de déconnexion.

### Lancement

Pour lancer le client, allez simplement dans le répertoire principal.
Puis exécutez cette commande :

```bash
java -jar DevoirSocketClient.jar
```

Le client se connectera par défaut à `localhost` sur le port `10810`.

> Si le serveur est connecté sur le port par défaut, alors le client doit être lancé ainsi.

Cependant, il est possible de sélectionner le port sur lequel nous voulons lancer le client. Mais le serveur DOIT être lancé sur le même port.

Pour ce faire, ajoutez simplement le port en paramètre à la commande de lancement :

```bash
java -jar DevoirSocketClient.jar <port>
```

### Exceptions

Si le serveur plante, l'erreur de connexion sera capturée et un message sera affiché à tous les clients connectés.

Si un client se déconnecte de manière inappropriée (Ctrl-C / Ctrl-Q), la déconnexion sera capturée par le serveur et affichée, en plus d'être notifiée aux autres clients.
