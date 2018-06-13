**Automata**

This project is made for an undergraduate computer science course, it revolves around the theory of Formal languages and Automata Theory.

Inside this project the following structure is used :

```
..
 ├── Omschrijving_automata.docx
 ├── automata.iml
 ├── graphVizNodeJs
 │   ├── Dockerfile
 │   ├── GraphVizGenerator.iml
 │   ├── app.js
 │   ├── bin
 │   │   └── www
 │   ├── package-lock.json
 │   ├── package.json
 │   ├── public
 │   │   ├── index.html
 │   │   └── stylesheets
 │   │       └── style.css
 │   └── routes
 │       └── index.js
 ├── input
 │   └── regexes.txt
 ├── output
 ├── pom.xml
 ├── readme.md
 ├── src
 │   └── main
 │       ├── java
 │       │   ├── automata
 │       │   │   ├── Automata.java
 │       │   │   ├── TestAutomata.java
 │       │   │   └── Transition.java
 │       │   ├── fileservice
 │       │   │   └── FileIO.java
 │       │   ├── main.java
 │       │   ├── regex
 │       │   │   ├── RegExp.java
 │       │   │   ├── TestRegExp.java
 │       │   │   └── Thompson.java
 │       │   └── reggram
 │       │       ├── RegGram.java
 │       │       ├── TestRegGram.java
 │       │       └── TransitionGram.java
 │       └── main.iml
```

Small explanation :


```
/graphVizNodeJs (Node.js / Express api for graphviz)
    entrypoint: app.js
    contains dockerfile
    
/input
    Regexes.txt (This file gets parsed line by line for regexes.
    
/output
    This folder is where all the images from graphviz end up.

/src
    /main
        /java
            /automata
             Automata.java (Class for automata methods)
             TestAutomata.java (Class for easy test automata generation)
             Transition.java (Class for transitions used by automata)
             
            /fileservice
             FileIO.java (Class used to do regex parsing from file and generating graphviz image)
            
            /regex
             RegExp.java (Class for Regular Expressions)
             TestRegExp.java (Class for easy test Regular Expression setups) 
             Thomposon.java (Class to use the thompson algorithm for constructing Automata from Regular expressions)
            
            /reggram
             RegGram.java (Class to go to regular grammar and back)
             TestRegGram.java (Class for easy testing of regular grammer)
             TransitionGram.java (Class for transitions used by RegGram).
            
          main.java

```
