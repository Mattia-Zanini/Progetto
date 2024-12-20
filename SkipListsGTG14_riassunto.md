Il documento descrive le skip list, una struttura dati probabilistica per implementare mappe ordinate, con prestazioni simili agli alberi bilanciati. Una skip list è composta da più liste ordinate, dove ogni lista contiene un sottoinsieme casuale della lista sottostante, con chiavi delimitate da valori sentinella (-∞ e +∞). Questa struttura consente di eseguire ricerche, inserimenti e cancellazioni in tempo \( O(\log n) \) in media, grazie all'uso di un generatore di numeri casuali per determinare l'altezza degli elementi.

### Operazioni principali:
1. **Ricerca**: La funzione di ricerca scende e scorre a destra nei livelli fino a trovare la chiave più vicina (o uguale) alla chiave cercata.
2. **Inserimento**: Dopo la ricerca della posizione corretta, viene deciso casualmente quanti livelli della lista ospiteranno il nuovo elemento, inserendolo in ognuno di questi livelli fino al più alto.
3. **Rimozione**: Parte dalla posizione dell’elemento e rimuove l’elemento dalla lista in ciascun livello, riallineando i puntatori orizzontali.

### Efficienza:
La skip list offre complessità \( O(\log n) \) in media per le operazioni principali e occupa spazio proporzionale al numero di elementi, \( O(n) \).
