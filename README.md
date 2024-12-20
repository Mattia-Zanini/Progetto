# Skip List Priority Queue Project

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
   - Esempio di output (file: IO_FILES/input_example_1):
    ```
    -1.0 6 8 6.25
    ```
    Dove:
      - `-1.0`: valore di `α`.
      - `6`: numero di elementi nella skip list.
      - `8`: numero totale di operazioni `insert` eseguite.
      - `6.25`: media dei nodi attraversati per ogni inserimento.

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

---

### Requisiti per i punteggi:

#### **Per ottenere 2 punti:**
1. **Implementazione di una Priority Queue con Skip List**:
   - Realizzare le seguenti classi:
     - **MyEntry**: rappresenta le coppie (Integer, String).
     - **SkipListPQ**: implementa le operazioni di Priority Queue con metodi:
       - `size()`: restituisce il numero di elementi nella skip list.
       - `min()`: restituisce e stampa l’elemento con la chiave minima.
       - `insert(key, string)`: aggiunge un nuovo elemento nella skip list. Gli elementi con chiavi duplicate sono ammessi.
       - `removeMin()`: rimuove e restituisce l’elemento con chiave minima.
       - `print()`: stampa gli elementi ordinati per chiave e indica la dimensione della lista verticale per ciascuno.
     - **TestProgram**: legge un file di input e esegue le operazioni richieste, tra cui:
       - Lettura di `N` operazioni e del valore `α`.
       - Esecuzione di `insert`, `min`, `removeMin`, e `print` come specificato nel file.

#### **Per ottenere 3 punti (punteggio massimo):**
1. **Estensione dell'implementazione precedente**:
   - Modifica di `insert(key, string)` per restituire il numero di nodi attraversati nella skip list durante l'inserimento.
   - Al termine dell’esecuzione, il programma deve stampare:
     - Valore di `α`.
     - Numero totale di elementi nella skip list.
     - Numero totale di operazioni di inserimento eseguite.
     - Media dei nodi attraversati per ciascun inserimento.

2. **Test con file specifici**:
   - Eseguire il programma su 6 file di input forniti (`alphaEfficiencyTest_m_i.txt`).
   - Per ciascun file, annotare le statistiche finali stampate e riportarle nella piattaforma Moodle.

3. **Conformità alle specifiche**:
   - Il programma deve compilare ed eseguire correttamente usando il comando `javac TestProgram.java`.
   - L’output deve essere formattato esattamente come negli esempi forniti.