# Avant-garde problem

<a href="https://www.java.com/" target="_blank"><img style="margin: 10px" src="https://profilinator.rishav.dev/skills-assets/java-original-wordmark.svg" alt="Java" height="50" /></a>

## EN - Avant-Garde Problem

### Problem Description

In an $8 \times 8$ matrix, we want to color as many cells as possible while following this rule:

> Starting from any cell, the next colored cell must share exactly one edge with the immediately previous colored cell and must not share an edge with any earlier colored cell.

This problem is based on Task 14 from the 2016 county round of the Hungarian Bolyai Mathematics Competition for 8th grade.

The goal of the program is to find all valid colorings where exactly 42 cells are filled. (We know from the original answer sheet that 42 is the maximum possible.)

📄 The original problem is available [online](https://www.bolyaiverseny.hu/matek/2016-17/megyei8.pdf) or [here](megyei8.pdf). (The description is in Hungarian.)

### Solution and Technical Background

Programming Language: Java

Parallelization: Java ExecutorService

The program implements a multithreaded brute-force search algorithm that tries all valid colorings based on the specified rules. The approach relies on the principle of recursive task decomposition. While the program doesn't use classical recursion (i.e., no method calls itself directly), the logic is still recursive in nature: at each step, when multiple valid directions are available, the program creates new `PaintTask` instances that run on separate threads. These instances can in turn create more instances, effectively breaking the task down into smaller subtasks in a tree-like fashion. Each `PaintTask` maintains its own state and terminates when no further valid moves are available. The goal is to find all valid configurations that contain exactly 42 colored cells.

### Usage

#### Compile

```bash
javac avantgard/*.java
```

#### Run

```bash
java avantgard/Main
```

The program may take a few minutes to run, depending on your computer’s performance.

#### Output

The valid colorings are written to the `output.txt` file. Empty cells are marked with a space, and colored cells with C.
Example:

```txt
  0 1 2 3 4 5 6 7
0 C C   C C C C C 
1 C   C C       C 
2 C C C   C C C C 
3       C C       
4 C C C C   C C C 
5 C       C C   C 
6 C   C C C     C 
7 C C C     C C C 
Count Colored: 42
```

## HU - Avantgárd probléma

### Problémaleírás

Egy $8 \times 8$-as mátrixban úgy szeretnénk beszínezni a lehető legtöbb mezőt, hogy megfeleljen a következő szabálynak:

> Bárhonnan indulva a következő beszínezett mezőnek pontosan egy oldala érintkezik a közvetlenül azelőtt beszínezett mező valamelyik oldalával, de nem érintkezhet oldal mentén a korábban beszínezett mezők egyikével sem.

A probléma alapja a 2016-os matematika Bolyai verseny 8. osztályos megyei fordulójának 14. feladata.

A program célja az összes lehetséges érvényes színezési lehetőség megtalálása, ahol a beszínezett mezők száma negyvenkettő. (Az eredeti feladat megoldókulcsából tudjuk, hogy ennyi a maximum.)

📄 Az eredeti feladat [online](https://www.bolyaiverseny.hu/matek/2016-17/megyei8.pdf) vagy [itt](megyei8.pdf) megtekinthető.

### Megoldás és technikai háttér

Programozási nyelv: Java

Párhuzamosítás: Java ExecutorService

A program egy többszálú, brute-force keresőalgoritmust valósít meg, amely az összes érvényes színezési lehetőséget próbálja végig a megadott szabály szerint. A megközelítés egy úgynevezett rekurzív feladatbontás elvén alapul. Bár nem klasszikus értelemben vett rekurziót használok (azaz nincs önmagát hívó metódus), a program logikája mégis rekurzív jellegű: minden lépés során, amikor több lehetséges továbblépési irány van, a program új `PaintTask` példányokat hoz létre, amelyek új szálakon futnak. Ezek a példányok újabb példányokat indíthatnak, így a feladat fa-szerűen bomlik további részfeladatokra. Minden `PaintTask` önállóan, saját állapottal rendelkezik, és akkor fejeződik be, ha már nincs több érvényes továbblépési lehetőség. A program célja a 42 mezőt tartalmazó érvényes színezések megtalálása.

### Használat

#### Fordítás

```bash
javac avantgard/*.java
```

#### Futás

```bash
java avantgard/Main
```

A futtatás eltarthat néhány percig a számítógép teljesítményétől függően.

#### Kimenet

A program az érvényes színezési megoldásokat az `output.txt` fájlba menti. Az üres mezőket szóköz, a beszínezetteket `C` jelöli. Egy példa:

```txt
  0 1 2 3 4 5 6 7
0 C C   C C C C C 
1 C   C C       C 
2 C C C   C C C C 
3       C C       
4 C C C C   C C C 
5 C       C C   C 
6 C   C C C     C 
7 C C C     C C C 
Count Colored: 42
```
