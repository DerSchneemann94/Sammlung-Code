// Schnittstelle einer Streuwertfunktion mit Einschränkung des
// Wertebereichs, die zu jedem Schlüssel einen gültigen Streuwert/Index
// liefert.
interface HashFunction {
    // Größe N der Streuwerttabelle (Anzahl der Plätze).
    int size ();

    // Streuwert für Schlüssel key liefern, indem key.hashCode()
    // berechnet und geeignet in den Wertebereich von 0 bis N-1
    // abgebildet wird.
    int compute (Object key);
}

// Abstrakte Oberklasse für Implementierungen von Streuwertfunktionen.
abstract class AbstractHashFunction implements HashFunction {
    // Größe der Streuwerttabelle (Anzahl der Plätze).
    protected int size;

    // Größe mit N initialisieren.
    protected AbstractHashFunction (int N) {
	size = N;
    }

    // Größe liefern.
    public int size () {
	return size;
    }
}

// Streuwertfunktion gemäß Divisionsrestmethode.
class DivisionMethod extends AbstractHashFunction {
    // Divisionsrestmethode für Tabellengröße N.
    public DivisionMethod (int N) {
	super(N);
    }
}

// Streuwertfunktion gemäß Multiplikationsmethode
// (Implementierung mit 32-Bit-Ganzzahlarithmetik).
class MultiplicationMethod extends AbstractHashFunction {
    // Anzahl p von Bits.
    private int bits;

    // Parameter s = A'.
    private int seed;

    // Multiplikationsmethode für Tabellengröße N = 2 hoch p
    // mit Parameter s.
    public MultiplicationMethod (int p, int s) {
	super(1 << p);	// 1 << p entspricht 2 hoch p.
	bits = p;
	seed = s;
    }
}

// Schnittstelle einer Sondierungsfunktion für offene Adressierung,
// die zu jedem Schlüssel eine gültige Sondierungssequenz liefert.
interface HashSequence {
    // Größe N der Streuwerttabelle (Anzahl der Plätze).
    int size ();

    // Ersten bzw. nächsten Streuwert für Schlüssel key liefern.
    // Alle Werte liegen zwischen 0 und N-1.
    // Ein Aufruf von first und N-1 nachfolgende Aufrufe von next
    // liefern jeden Wert zwischen 0 und N-1 jeweils genau einmal.
    int first (Object key);
    int next ();
}

// Abstrakte Oberklasse für Implementierungen von Sondierungsfunktionen.
abstract class AbstractHashSequence implements HashSequence {
    // Zugrundeliegende Streuwertfunktion.
    protected HashFunction func;

    // Letzter von first oder next gelieferter Streuwert.
    protected int prev;

    // Streuwertfunktion mit f initialisieren.
    protected AbstractHashSequence (HashFunction f) {
	func = f;
    }

    // Tabellengröße liefern.
    public int size () {
	return func.size();
    }
}

// Sondierungssequenz gemäß linearer Sondierung.
class LinearProbing extends AbstractHashSequence {
    // Lineare Sondierung mit Streuwertfunktion f.
    public LinearProbing (HashFunction f) {
	super(f);
    }
}

// Sondierungssequenz gemäß quadratischer Sondierung
// (Implementierung nur mit Ganzzahlarithmetik).
class QuadraticProbing extends AbstractHashSequence {
    // Quadratische Sondierung mit Streuwertfunktion f.
    public QuadraticProbing (HashFunction f) {
	super(f);
    }
}

// Sondierungssequenz gemäß doppelter Streuung.
class DoubleHashing extends AbstractHashSequence {
    // Zweite Streuwertfunktion.
    private HashFunction func2;

    // Doppelte Streuung mit Streuwertfunktionen f1 und f2.
    public DoubleHashing (HashFunction f1, HashFunction f2) {
	super(f1);
	func2 = f2;
    }
}

// Schnittstelle einer Streuwerttabelle.
interface HashTable {
    // Eintrag mit Schlüssel key und Wert val einfügen, sofern key und
    // val nicht null sind und die Tabelle bei offener Adressierung
    // noch nicht voll ist.
    // Wenn es bereits einen Eintrag mit dem gleichen Schlüssel gibt,
    // wird er durch diesen neuen Eintrag ersetzt.
    // Resultatwert true, falls erfolgreich (Eintrag hinzugefügt oder
    // ersetzt), sonst false (Tabelle unverändert).
    // Bei Verkettung muss am Anfang der jeweiligen Liste eingefügt
    // werden.
    // Die als Schlüssel verwendeten Objekte müssen konsistente
    // Implementierungen von hashCode und equals besitzen, d. h.
    // Objekte, die gemäß equals "gleich" sind, müssen den gleichen
    // hashCode-Wert besitzen. Außerdem darf sich der hashCode-Wert
    // eines Schlüssels nicht ändern, solange es einen Tabelleneintrag
    // mit diesem Schlüssel gibt.
    // key und val können jeweils einen beliebigen dynamischen Typ
    // besitzen.
    boolean put (Object key, Object val);

    // Wert zum Schlüssel key liefern, falls vorhanden, sonst null.
    // (Resultatwert null, wenn key gleich null ist.)
    Object get (Object key);

    // Eintrag mit Schlüssel key entfernen, falls vorhanden.
    // Resultatwert true, falls erfolgreich (Eintrag war vorhanden),
    // sonst false (Eintrag war nicht vorhanden).
    // (Resultatwert false, wenn key gleich null ist.)
    boolean remove (Object key);

    // Inhalt der Tabelle zu Testzwecken ausgeben:
    // Pro Eintrag eine Zeile bestehend aus der Nummer des Platzes,
    // Schlüssel und Wert, jeweils getrennt durch genau ein Leerzeichen.
    // Dieses Ausgabeformat muss exakt eingehalten werden.
    // Leere Plätze und Plätze mit Löschmarkierung werden nicht
    // ausgegeben.
    void dump ();
}

// Implementierung von Streuwerttabellen mit Verkettung.
class HashTableChaining implements HashTable {
    // Streuwerttabelle mit Streuwertfunktion f.
    public HashTableChaining (HashFunction f) {
    }
}

// Implementierung von Streuwerttabellen mit offener Adressierung.
class HashTableOpenAddressing implements HashTable {
    // Streuwerttabelle mit Sondierungsfunktion s.
    public HashTableOpenAddressing (HashSequence s) {
    }
}

// Einfaches Testprogramm.
class WordCount {
    // Aufruf mit folgenden Kommandozeilenargumenten:
    // 1) Buchstabe c (chaining), l (linear probing),
    //    q (quadratic probing) oder d (double hashing).
    // 2) Buchstabe d (Divisionsrestmethode)
    //    oder m (Multiplikationsmethode).
    // 3) Größe N der Streuwerttabelle (bei Divisionsrestmethode)
    //    oder Anzahl p von Bits (bei Multiplikationsmethode).
    // 4) Parameter s (nur bei Multiplikationsmethode).
    public static void main (String [] args) throws java.io.IOException {
	// Größe N der Streuwerttabelle oder Anzahl p von Bits.
	int Np = Integer.parseInt(args[2]);

	// Streuwertfunktion mit Einschränkung des Wertebereichs
	// gemäß Divisionsrest- oder Multiplikationsmethode.
	HashFunction f;
	switch (args[1]) {
	case "d":
	    f = new DivisionMethod(Np);
	    break;
	case "m":
	    int s = Integer.parseInt(args[3]);
	    f = new MultiplicationMethod(Np, s);
	    break;
	default:
	    return;
	}

	// Streuwerttabelle mit Verkettung oder offener Adressierung.
	HashTable tab;
	if (args[0].equals("c")) {
	    tab = new HashTableChaining(f);
	}
	else {
	    HashSequence s;
	    switch (args[0]) {
	    case "l":
		s = new LinearProbing(f);
		break;
	    case "q":
		s = new QuadraticProbing(f);
		break;
	    case "d":
		// Als zweite Streuwertfunktion für doppelte Streuung
		// wird eine Funktion verwendet, die immer eine
		// ungerade Zahl zwischen 1 und N-1 liefert.
		// Wenn die Tabellengröße N dann entweder eine Primzahl
		// (ratsam bei Divisionsrestmethode) oder eine
		// Zweierpotenz (automatisch bei Multiplikationsmethode)
		// ist, sind alle Werte der Funktion teilerfremd zu N.
		class HashFunction2 extends AbstractHashFunction {
		    public HashFunction2 (int N) {
			super(N);
		    }
		    public int compute (Object key) {
			int h = Math.abs(key.hashCode()) % (size - 1);
			if (h % 2 == 0) h++;
			return h;
		    }
		}
		s = new DoubleHashing(f, new HashFunction2(f.size()));
		break;
	    default:
		return;
	    }
	    tab = new HashTableOpenAddressing(s);
	}

	// Standardeingabestrom System.in als InputStreamReader
	// und diesen wiederum als BufferedReader "verpacken",
	// damit man bequem zeilenweise lesen kann.
	java.io.BufferedReader r = new java.io.BufferedReader(
			      new java.io.InputStreamReader(System.in));

	// Standardeingabe zeilenweise lesen
	// (Annahme: ein Wort pro Zeile).
	// (readLine kann java.io.IOException werfen.)
	while (true) {
	    String word = r.readLine();
	    if (word == null) break;

	    // Überprüfen, ob die Tabelle bereits einen Eintrag mit
	    // Schlüssel word enthält.
	    // Wenn nicht, wird word mit Wert 1 neu eingetragen.
	    // Ansonsten wird der vorhandene Eintrag durch einen neuen
	    // Eintrag mit altem Wert plus 1 ersetzt.
	    // (int-Werte werden bei Bedarf automatisch in Integer-
	    // Objekte umgewandelt und umgekehrt.)
	    Integer count = (Integer)tab.get(word);
	    if (count == null) count = 0;
	    tab.put(word, count + 1);
	}

	// Inhalt der Tabelle ausgeben.
	tab.dump();
    }
}
