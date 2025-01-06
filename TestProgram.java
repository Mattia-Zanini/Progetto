import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Class my entry
class MyEntry {
    private Integer key;
    private String value;

    public MyEntry(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }
}

class Node {
    private Node below;
    private Node above;
    private Node next;
    private Node prev;
    private MyEntry e;

    public Node() {
        this(null);
        initNearNodes();
    }

    public Node(MyEntry _e) {
        e = _e;
        initNearNodes();
    }

    public Integer getKey() {
        return e.getKey();
    }

    public String getValue() {
        return e.getValue();
    }

    public MyEntry getEntry() {
        return e;
    }

    public void setBelow(Node _below) {
        below = _below;
    }

    public void setAbove(Node _above) {
        above = _above;
    }

    public void setNext(Node _next) {
        next = _next;
    }

    public void setPrev(Node _prev) {
        prev = _prev;
    }

    public Node getBelow() {
        return below;
    }

    public Node getAbove() {
        return above;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public String toString() {
        return "This: " + e.toString() +
                "\nBelow: " + below.e.toString() +
                "\nAbove: " + above.e.toString() +
                "\nNext: " + next.e.toString() +
                "\nPrev: " + prev.e.toString();
    }

    private void initNearNodes() {
        below = null;
        above = null;
        next = null;
        prev = null;
    }
}

// Class SkipListPQ
class SkipListPQ {
    private double alpha;
    private Random rand;

    private Node head;
    private Node tail;

    private boolean listUpdated;
    private int listSize;

    // Tiene traccia ti quanti nodi sono stati visitati per la search
    private int nodeVisited;

    public SkipListPQ(double _alpha) {
        alpha = _alpha;
        rand = new Random();

        // Inizializzo la testa e la coda della SkipList
        head = new Node(new MyEntry(Integer.MIN_VALUE, null));
        tail = new Node(new MyEntry(Integer.MAX_VALUE, null));

        // Inizializzo il secondo livello
        head.setBelow(new Node(new MyEntry(Integer.MIN_VALUE, null)));
        tail.setAbove(new Node(new MyEntry(Integer.MAX_VALUE, null)));

        // Collego i due livelli
        below(head).setAbove(head);
        above(tail).setBelow(tail);

        // Inizializzo gli altri collegamenti
        head.setAbove(null);
        head.setPrev(null);
        head.setNext(above(tail));

        tail.setBelow(null);
        tail.setNext(null);
        tail.setPrev(below(head));

        below(head).setPrev(null);
        below(head).setBelow(null);
        below(head).setNext(tail);

        above(tail).setNext(null);
        above(tail).setAbove(null);
        above(tail).setPrev(head);

        // La lista è stata creata, di fatto quindi aggiornata
        listUpdated = true;

        listSize = 0;
        nodeVisited = 0;
    }

    public int size() {
        // Se la lista non è stata aggiornata, non serve ricontare tutti i nodi
        if (listUpdated == false)
            return listSize;

        int size = 1; // Comincio dalla coda, quindi la conto
        Node current = tail;
        while (prev(current) != null) {
            current = prev(current);
            size++;
        }
        // Escludo i due nodi di riferimento
        size -= 2;

        // Ho appena contato tutti i nodi, quindi la lista non può essersi aggiornata
        listUpdated = false;
        listSize = size;

        return size;
    }

    public MyEntry min() {
        Node bottomHead = getTowerBottom(head);
        return next(bottomHead).getEntry();
    }

    // Implementazione della SkipSearch
    public Node search(int k) {
        return search(k, 0);
    }

    // Cerco il nodo con la chiave k < rispetto alla chiave della entry da inserire
    //
    // Non aggiungo al totale dei nodi visitati, le visite di 'getNodeHeight()'
    // perchè da consegna devo tener traccia dei nodi attraversati, partendo da s
    // (il nodo head) per inserire la nuova entry in S0
    private Node search(int k, int minHeight) {
        Node p = head;
        int height = getNodeHeight(p);

        while (below(p) != null && height > minHeight) {
            p = below(p);
            nodeVisited++;
            height = getNodeHeight(p);

            while (k >= next(p).getKey()) {
                p = next(p);
                nodeVisited++;
            }
        }
        return p;
    }

    public int insert(int key, String value) {
        // Devo mantenere i due riferimenti, ovvero i
        // nodi con chiave -infinito e +infinito
        if (key == Integer.MIN_VALUE || key == Integer.MAX_VALUE)
            return 0;

        nodeVisited = 0;

        Node tmpNode = null;
        Node prevNode = search(key);
        Node nextNode = next(prevNode);

        // levelsToInsert = 0 -> inserisco il nodo solo nella lista più in basso
        int levelsToInsert = generateEll(alpha, key);

        // Inserisco il nuovo nodo nella lista più in basso
        Node newNode = new Node(new MyEntry(key, value));
        prevNode.setNext(newNode);
        newNode.setPrev(prevNode);
        nextNode.setPrev(newNode);
        newNode.setNext(nextNode);

        // Ora inserisco i restanti livelli della stessa entry
        // Per costruzione un Node, di default punta a nodi 'null'
        for (int height = 1; height < levelsToInsert; height++) {
            tmpNode = new Node(new MyEntry(key, value));
            newNode.setAbove(tmpNode);
            tmpNode.setBelow(newNode);

            // Cerco il nodo precedente e sucessivo nella nuova altezza
            prevNode = search(key, height);
            nextNode = next(prevNode);

            tmpNode.setPrev(prevNode);
            prevNode.setNext(tmpNode);

            tmpNode.setNext(nextNode);
            nextNode.setPrev(tmpNode);

            // Aggiorno newNode, per continuare il ciclo
            newNode = above(newNode);

            // Se l'ultima lista non è vuota allora ne creo una vuota
            if (isLastListEmpty() == false) {
                head.setAbove(new Node(new MyEntry(Integer.MIN_VALUE, null)));
                above(head).setBelow(head);
                head = above(head);

                Node tailTop = getTowerTop(tail);
                tailTop.setAbove(new Node(new MyEntry(Integer.MAX_VALUE, null)));
                above(tailTop).setBelow(tailTop);
                tailTop = above(tailTop);

                head.setNext(tailTop);
                tailTop.setPrev(head);
            }
        }

        listUpdated = true;

        return nodeVisited;
    }

    public MyEntry removeMin() {
        Node current = next(getTowerBottom(head));

        // Non elimino il punto di riferimento +inf
        if (current.getKey() == Integer.MAX_VALUE)
            return null;

        MyEntry e = current.getEntry();
        while (current != null) {
            Node prevNode = prev(current);
            Node nextNode = next(current);

            // Collego il nodo successivo con quello precedente al corrente nodo,
            // eliminando così il nodo corrente
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);

            // Passo al nodo di sopra, per risalire la torre man mano che il ciclo va avanti
            current = above(current);
        }

        current = head;
        while (below(current) != null) {
            // Se il penultimo livello è vuoto allora rimuovo tutto l'ultimo livello,
            // devo però mantenere gli ultimi due livelli, anche se la SkipList è vuota
            if (isCurrentNodeLevelEmpty(below(current)) && getNodeHeight(current) > 1) {
                // Abbasso la testa ad un livello inferiore
                head = below(head);

                while (current != null) {
                    below(current).setAbove(null);
                    current = next(current);
                }
                current = head;
            } else
                current = below(current);
        }

        listUpdated = true;

        return e;
    }

    // Stampa i nodi presenti in livello per livello
    public void printDebug() {
        System.out.print("\n");

        Node startLevelNode = head;
        while (startLevelNode != null) {
            Node current = startLevelNode;
            while (current != null) {
                int k = current.getKey();
                if (k != Integer.MIN_VALUE && k != Integer.MAX_VALUE)
                    System.out.print("(" + k + ")\s");
                else if (k == Integer.MIN_VALUE)
                    System.out.print("(-INF)\s");
                else if (k == Integer.MAX_VALUE)
                    System.out.print("(+INF)\s");

                current = next(current);
            }
            startLevelNode = below(startLevelNode);
            System.out.print("\n");
        }

        System.out.print("\n");
    }

    // Stampa tutte le entry con le rispettive chiavi e valori
    public void print() {
        Node current = getTowerBottom(head);

        while (next(current) != tail) {
            current = next(current);
            int nodeLevels = getNodeLevels(current) + 1;
            System.out.print(current.getEntry() + "\s" + nodeLevels);

            if (next(current) != tail)
                System.out.print(",\s");
        }

        System.out.print("\n");
    }

    // Generare il livello (altezza) dei nodi in modo casuale o deterministico, a
    // seconda del valore di alpha
    private int generateEll(double alpha_, int key) {
        int level = 0;
        if (alpha_ >= 0. && alpha_ < 1)
            while (rand.nextDouble() < alpha_)
                level += 1;
        else
            while (key != 0 && key % 2 == 0) {
                key = key / 2;
                level += 1;
            }

        return level;
    }

    private Node below(Node p) {
        return p.getBelow();
    }

    private Node above(Node p) {
        return p.getAbove();
    }

    private Node next(Node p) {
        return p.getNext();
    }

    private Node prev(Node p) {
        return p.getPrev();
    }

    private int getNodeLevels(Node p) {
        int levels = 0;
        if (p == null)
            return levels;

        while (above(p) != null) {
            p = above(p);
            levels++;
        }
        return levels;
    }

    private int getNodeHeight(Node p) {
        int height = 0;
        if (p == null)
            return height;

        while (below(p) != null) {
            p = below(p);
            height++;
        }
        return height;
    }

    // ritorna se la lista più in alto è vuota
    // (ovvero quella dove si trova il nodo puntato da head)
    private boolean isLastListEmpty() {
        return isCurrentNodeLevelEmpty(head);
    }

    // Guardo se l'altezza/livello del nodo che passo per parametro è vuota,
    // quindi p deve essere un nodo della testa
    private boolean isCurrentNodeLevelEmpty(Node p) {
        if (p.getKey() == Integer.MIN_VALUE && next(p).getKey() == Integer.MAX_VALUE)
            return true;
        return false;
    }

    private Node getTowerTop(Node p) {
        if (p == null)
            return p;

        while (above(p) != null)
            p = above(p);

        return p;
    }

    private Node getTowerBottom(Node p) {
        if (p == null)
            return p;

        while (below(p) != null)
            p = below(p);

        return p;
    }
}

// TestProgram

public class TestProgram {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TestProgram <file_path>");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            double alpha = Double.parseDouble(firstLine[1]);
            System.out.println(N + " " + alpha);

            SkipListPQ skipList = new SkipListPQ(alpha);

            double totalNodesVisited = 0;
            double entryInserted = 0;

            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                int operation = Integer.parseInt(line[0]);

                switch (operation) {
                    case 0:
                        System.out.println(skipList.min());
                        break;
                    case 1:
                        skipList.removeMin();
                        break;
                    case 2:
                        totalNodesVisited += skipList.insert(Integer.parseInt(line[1]), line[2]);
                        entryInserted++;
                        break;
                    case 3:
                        skipList.print();
                        break;
                    default:
                        System.out.println("Invalid operation code");
                        return;
                }
            }

            System.out.println(alpha + "\s" +
                    skipList.size() + "\s" +
                    (int) (entryInserted) + "\s" + (totalNodesVisited / entryInserted));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
