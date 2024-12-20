# Progetto Dati e Algoritmi
## Skip List Priority Queue Project

## Descrizione del Progetto
Questo progetto richiede l'implementazione di una Priority Queue utilizzando una Skip List, una struttura dati probabilistica introdotta nel 1989 da William Pugh. L'obiettivo è ottimizzare le operazioni di ricerca, inserimento e rimozione in un'implementazione efficiente della Map Abstract Data Type (ADT).

Le Skip List consistono in una serie di liste ordinate che sfruttano la randomizzazione per garantire buone prestazioni in termini di tempo di esecuzione e spazio.

---

## Specifiche Tecniche
1. **Classi richieste:**
   - **`MyEntry`**: rappresenta le coppie `(key, value)` della Priority Queue.
   - **`SkipListPQ`**: implementa la struttura della Skip List con operazioni di Priority Queue.
   - **`TestProgram`**: gestisce l'esecuzione del programma, leggendo i dati da un file di input e processando le operazioni specificate.

2. **Metodi principali di `SkipListPQ`:**
   - **`size()`**: restituisce il numero di elementi nella Skip List.
   - **`min()`**: restituisce l'elemento con la chiave minima, stampandone chiave e valore.
   - **`insert(key, string)`**: inserisce una nuova coppia `(key, string)` nella struttura e restituisce il numero di nodi attraversati durante l'inserimento.
   - **`removeMin()`**: rimuove l'elemento con la chiave minima e aggiorna la struttura.
   - **`print()`**: stampa gli elementi della Skip List, ordinati per chiave, con il numero di livelli di ogni nodo.

3. **Input e Output:**
   - Il programma accetta un file di input contenente:
     - Linea 1: `N` (numero di operazioni) e `α` (probabilità di testa nel lancio di una moneta).
     - Da linea 2: operazioni codificate (`0` per `min`, `1` per `removeMin`, `2` per `insert key string`, `3` per `print`).
   - Esempio di output:
     ```
     0.5, 6, 8, 6.25
     ```

---

## Implementazione
Il progetto richiede:
- Utilizzo della randomizzazione per determinare il numero di livelli di un nuovo nodo.
- La gestione dinamica della struttura per minimizzare lo spazio utilizzato, eliminando livelli inutilizzati.

### Dettagli della Skip List:
- Ogni lista è ordinata e contiene due sentinelle (`-∞` e `+∞`).
- La probabilità di promuovere un nodo a un livello superiore è determinata dal parametro `α`.

### Complessità:
- **Ricerca, Inserimento, Cancellazione**: O(log n) in media.
- **Spazio totale**: O(n) in media.