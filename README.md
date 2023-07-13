# Convertisseur jeton collection en markdown

Petit outil permettaant à partir de l'export du site collection-jetons-touristiques.com de transformer ma collection au format Markdown pour mon site.


Pour cela il faut se rendre sur le site collection-jetons-touristiques.com, s'identifier, Ma Collection, Possédés.

Puis cliquer sur la disquette avec le mot "Tout"

Une fois telecharger il faut enregistrer le fichier monnaie.csv au format csv avec un separateur ";" et en UTF-8.

Indiquer ou votre fichier csv se trouve dans la propriete : sourceFilePath

Puis indiquer si vous souhaitez avoir un export Markdown et/ou JS avec les proprietes suivantes :
 
```

generateJSONfile=true
generateMarkdownfile=true

```