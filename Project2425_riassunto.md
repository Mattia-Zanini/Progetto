Il progetto richiede l'implementazione di una coda con priorità utilizzando una "skip list", una struttura dati probabilistica che supporta efficientemente operazioni di ricerca e aggiornamento. La skip list è composta da più liste ordinate, dove ogni lista successiva contiene un sottoinsieme casuale della lista precedente, con un livello massimo che contiene solo sentinelle.

### Requisiti principali:
1. **Ricerca di una chiave (k)**: Cerca l’elemento con la chiave maggiore ≤ k partendo dalla posizione iniziale in cima alla skip list, e scendendo verso il basso.
   
2. **Inserimento di una nuova chiave (k)**: Usa la ricerca per trovare la posizione corretta, quindi inserisci la nuova chiave in una lista casuale, con un’altezza determinata da lanci di moneta. Se l’altezza è maggiore del livello attuale, viene aggiunto un nuovo livello alla struttura.

3. **Efficienza**: Le operazioni di ricerca, inserimento e cancellazione hanno complessità \( O(\log n) \) con alta probabilità, regolata dal parametro probabilistico α.

### Implementazione:
Sono richieste tre classi:
1. **MyEntry**: rappresenta coppie di valori (Integer, String).
2. **SkipListPQ**: gestisce la skip list con metodi specifici (es. `size`, `min`, `insert`, `removeMin`, `print`).
3. **TestProgram**: esegue operazioni specificate in un file di testo su un'istanza di SkipListPQ.

### Compiti aggiuntivi per il punteggio completo:
- **Modifica del metodo `insert`**: Deve restituire il numero di nodi attraversati durante l'inserimento.
- **Statistiche finali**: Dopo l’esecuzione, il programma stampa α, il numero totale di elementi, il numero di inserimenti e la media dei nodi attraversati per inserimento.

### Consegna:
Caricare il file `TestProgram.java` seguendo il formato indicato e utilizzando i file di test forniti su Moodle per verificare la correttezza.
