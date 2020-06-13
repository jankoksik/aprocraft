# aprocraft.APROCraft

## O Projekcie : 
Minecraft jest grą sandboxową, co oznacza że nie ma odgórnego celu rozgrywki. Każdy znajdzie coś dla siebie. Są elementy eksploracji, kopania, budowania.
Podczas projektu będziemy zmagać się z problemem optymalizacji gry pod kątem liczby generowanych bloków jak i oświetlenia. Dodatkowo zostaną wykorzystane algorytmy do generowania struktur, terenu ( z szumu), biomów?.
Liczba dodanych przez nas elementów jak i mechanik będzie zależeć od tego ile czasu będziemy mieli i jak będziemy się wyrabiać z projektem.
Gracz może atakować napotkane istoty, zbierać surowce czy wytwarzać przedmioty.

## Problemy i zadania:
* Optymalizacja zużycia pamięci - każdy blok składa się z 8 vertexów. Każdy vertex posiada 3 zmienne typu float przechowujące jego pozycję, 2 przechowujące informację o teksturze i 1 o oświetleniu. Przekłada się to na 192 bajty na blok. Zakładając, że w zasięgu gracza znajduje się 16 chunków (każdy 16x16x256 bloków), na samo przechowanie informacji o blokach potrzebujemy 192MB pamięci. Będziemy musieli znaleźć rozwiązanie pozwalające na zaoszczędzenie pamięci.
* Renderowanie takiej ilości bloków jako osobnych modeli miałoby bardzo zły wpływ na wydajność gry. Konieczne będzie zaimplementowanie algorytmu generującego model tylko widocznej części powierzchni mapy w czasie rzeczywistym (nie warto renderować tego, czego gracz i tak nie widzi).
* Generowanie ciekawego i różnorodnego świata, jaskiń i struktur (np drzew).
* Dynamiczne wczytywanie potrzebnych części mapy i wyrzucanie z pamięci tych aktualnie niepotrzebnych.
* Detekcja kolizji
* Animacja proceduralna postaci
* Ekwipunek i tworzenie przedmiotów
* Zapis i odczyt mapy (wraz z odtworzeniem stanu rozgrywki)

## id Bloków

| id         | blok                |
|------------|---------------------|
| 1          | trawa               |
| 2          | ziemia              |
| 3          | kamień              |
| 4          | cobble              |
| 17         | drewno zwykłe       |
| 18         | drewno ciemne       |
| 19         | drewno jasne        |
| 20         | deski zwykłe        |
| 21         | deski ciemne        |
| 22         | deski jasne         |
| 23         | liście zwykłe       |
| 24         | liście ciemne       |
| 25         | liście jasne        |
| 15         | bedrock             |
| 50         | chmura              |
| 10         | piach               |
| 51         | lód                 |
| 13         | czerwony piaskowiec |
| 26         | kaktus              |
| 37         | ruda diamentów      |
| 36         | ruda węgla          |
| 33         | ruda złota          |
| 34         | ruda żelaza         |
| 35         | ruda miedzi         |
| 38         | ruda rubinu         |
| 39         | ruda szafiru        |
| 40         | ruda szmaragdu      |
| 64 + kolor | kolorowa wełna      |
| 81         | stół craftingowy    |
| 52         | beton               |



## Domyślne Sterowanie

## Receptury Craftingowe

## Moby

##  Ciekawostki 
* chodzenie po koronach drzew jest trudne, więc spowolni ono twoje ruchy
* 

## Wykresy
![Wykres0](https://docs.google.com/spreadsheets/d/e/2PACX-1vQBJXsq7isWDX3QzbCtcnmvPF7k0Qym3KAqQyubQCl_bKuLNvk1_lat6uu_vRzu2EN1btbbe_6Sd-kA/pubchart?oid=1796947460&format=image)
![Wykres1](https://docs.google.com/spreadsheets/d/e/2PACX-1vQBJXsq7isWDX3QzbCtcnmvPF7k0Qym3KAqQyubQCl_bKuLNvk1_lat6uu_vRzu2EN1btbbe_6Sd-kA/pubchart?oid=1053310127&format=image)


## Zdjęcia

## Przydatne Linki 
* [Prezentacja nr. 1]( https://docs.google.com/presentation/d/1kTFLFQmNeN7e-ZAb40vJj1gkO3F74Sd9fUSSIczJ3Ug/edit?usp=sharing) 
* [Wykres](https://docs.google.com/spreadsheets/d/1eKGbm1aENL7FdOXJLVnKCpnl9LFGtqn9tIk3BIec5yo/edit?usp=sharing)
