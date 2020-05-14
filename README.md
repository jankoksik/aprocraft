# APROCraft

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

## Domyślne Sterowanie

## Receptury Craftingowe

## Moby

##  Ciekawostki 
* chodzenie po koronach drzew jest trudne, więc spowolni ono twoje ruchy
* 

## Przydatne Linki 
* [Prezentacja nr. 1]( https://docs.google.com/presentation/d/1kTFLFQmNeN7e-ZAb40vJj1gkO3F74Sd9fUSSIczJ3Ug/edit?usp=sharing) 
* [Wykres](https://docs.google.com/spreadsheets/d/1eKGbm1aENL7FdOXJLVnKCpnl9LFGtqn9tIk3BIec5yo/edit?usp=sharing)
