// Knoten für den Huffman-Trie
class HNode{
	// chars enthält bei Blattknoten ein Zeichen, ansonsten alle Zeichen der darunterliegenden Knoten
	// Beispiel:
	// 			ab
	//         /  \
	//        a    b
	public String chars;
	// Linkes Kind
	public HNode leftChild;
	// Rechtes Kind
	public HNode rightChild;
}
class Huffman {
	// Feld mit Huffman-Codes zu den einzelnen Zeichen.
	// Wenn char c = 'a', dann ist codes[c] ein Code, der aus Nullen und Einsen besteht, mit dem das Zeichen a kodiert werden soll.
	private String[] codes;

	// Wurzelknoten des Präfix-Codebaums
	private HNode root;

	// Konstruktor
	public Huffman() {
		// TODO
	}
	
	// Prüfen, ob ein Text mit dem aktuell erstellten Huffman-Code kodiert werden kann, ob also alle Zeichen einen Präfix-Code besitzen. Wenn ja, return true, wenn nein, return false. 
	// Prüfen, ob ein Text mit dem aktuell erstellten Huffman-Code kodiert werden kann, ob also alle Zeichen einen Präfix-Code besitzen. Wenn ja, return true, wenn nein, return false.
	public boolean canEncode(String text){
		// TODO
		return true;
	}

	// Vor dem eigentlichen Algorithmus kann mit dieser Funktion die Häufigkeit der einzelnen Zeichen aus dem übergebenen Text errechnet werden.
	// Hierzu kann die Anzahl des Vorkommens eines Zeichens berechnet werden und in einem Array gespeichert werden.
	// Für jedes Zeichen c enthält das Array f an Stelle c die Häufigkeit (also f['a'] ist die Häufigkeit von a im Text. Kommt das Zeichen nicht vor, ist die Häufigkeit 0.)
	// Zur Erinnerung: ein char kann wie eine Ganzzahl verwendet werden, daher funktioniert f[c] für jedes char c.
	public Integer[] calculateFrequencies(String text){
		Integer[] f = new Integer[256];
		// TODO
		return f;
	}

	// Iterativer Algorithmus zur Erstellung des Präfix-Codes (Skript S.115) mithilfe von BinHeap.
	// frequencies enthält die Häufigkeiten (siehe calculateFrequencies). Häufigkeit von 0 bedeutet, das entsprechende Zeichen ist nicht im Text vorhanden und wir brauchen keinen Präfixcode dafür.
	// Die Funktion setzt den Knoten root auf den Wurzelknoten des PräfixCode-Baums und gibt diesen Wurzelknoten außerdem zurück
	public HNode constructPrefixCode(Integer[] frequencies){
		// TODO
		return root;
	}

	// Kodierung einer Zeichenkette text (Skript S.108)
	// Die Ergebnis-Zeichenkette enthält nur Nullen und Einsen
	// (der Einfachheit halber wird dennoch ein String-Objekt verwendet)
	// Kodierung: linker Teilbaum -> 0, rechter Teilbaum -> 1
	// Erster Parameter: Zu kodierender Text
	// Zweiter Parameter zeigt an, ob ein neuer Präfixcode erzeugt werden soll (true) oder mit dem aktuellen Präfixcode gearbeitet werden soll (false)
	public String encode(String text, boolean newPrefixCode){
		String result = "";
		// TODO
		return result;
	}

	// Dekodierung eines Huffman-Kodierten Textes. (Skipt S.107)
	// Die Ergebnis-Zeichenkette ist der ursprüngliche Text vor der Huffman-Kodierung
	public String decode(String huffmanEncoded){
		// TODO
		return "";
	}

	// Dekodierung eines Huffman-Kodierten Textes mithilfe des übergebenen Präfix-Codebaums. (Skipt S.107) Der aktuelle Baum soll dabei nicht überschrieben werden.
	// Die Ergebnis-Zeichenkette ist der ursprüngliche Text vor der Huffman-Kodierung
	public String decode(String huffmanEncoded, HNode rootNode){
		String result = "";
		// TODO
		return result;
	}

	// Präfixcodes ausgeben
	// Reihenfolge: preOrder, also WLR, zuerst Wurzel, dann linker Teilbaum, dann rechter Teilbaum
	public void dumpPrefixCodes(){
		// TODO
	}
}

// Interaktives Testprogramm für die Klasse Huffman.
class HuffmanTest {
	public static void main(String[] args) throws java.io.IOException {
		// Häufigkeiten (ASCII-Tabelle)
		Integer[] exampleFrequencies = new Integer[]{
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 3676, 3, 160, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 472, 1, 252, 8, 39, 38,
				18, 12, 13, 14, 13, 9, 9, 15, 45, 12,
				1, 1, 1, 18, 1, 23, 76, 110, 160, 62, // 65 = A
				66, 104, 19, 33, 9, 74, 148, 108, 402, 11,
				44, 1, 300, 270, 270, 12, 40, 62, 2, 11,
				42, 0, 0, 0, 0, 0, 0, 1164, 389, 593, // 97 = a
				832, 3186, 332, 525, 908, 1666, 46, 370, 739, 541,
				2010, 560, 220, 5, 1508, 1382, 1348, 648, 199, 313,
				13, 56, 214, 1, 1, 1, 1, 1
		};

		HNode lastPrefixCode = null;

		// Standardeingabestrom System.in als InputStreamReader
		// und diesen wiederum als BufferedReader "verpacken",
		// damit man bequem zeilenweise lesen kann.
		java.io.BufferedReader r = new java.io.BufferedReader(
				new java.io.InputStreamReader(System.in));
		Huffman h = new Huffman();
		// Endlosschleife.
		while (true) {

			// Eingabezeile vom Benutzer lesen, ggf. ausgeben (wenn das
			// Programm nicht interaktiv verwendet wird) und in einzelne
			// Wörter zerlegen.
			// Abbruch bei Ende der Eingabe oder leerer Eingabezeile.
			System.out.print(">>> ");
			String line = r.readLine();
			if (line == null || line.equals("")) return;
			if (System.console() == null) System.out.println(line);
			String[] cmd = line.split(" ");

			String funct = cmd[0];
			String text = null;
			if (cmd.length == 1) {
				if (funct == "dec") {
					System.out.println("Nothing to decode.");
					continue;
				} else
					if (funct.startsWith("enc")){
						System.out.println("Nothing to encode.");
						continue;
					}
			} else {
				text = line.substring(line.indexOf(' ')+1);
			}
			String result;
			switch (funct) {
				case "enc0": // Kodieren ohne neue Präfixcodes zu errechnen
					result = h.encode(text, false);
					if (result!=null)
						System.out.println("Kodierter Text: "+result);
					break;
				case "enc1": // Kodieren mit Berechnung neuer Präfixcodes
					result = h.encode(text, true);
					if (result!=null)
						System.out.println("Kodierter Text: "+result);
					break;
				case "dec": // Dekodieren eines Textes mit aktuellem Präfixcode
					result = h.decode(text);
					if (result!=null)
						System.out.println("Dekodierter Text: "+result);
					break;
				case "decpref": // Dekodieren mit übergebenem Präfixcode
					result = h.decode(text, lastPrefixCode);
					if (result!=null)
						System.out.println("Dekodierter Text: "+result);
					break;
				case "prefixes": // Präfix-Codes erstellen mit Häufigkeiten aus vorgeg. Feld
					lastPrefixCode = h.constructPrefixCode(exampleFrequencies);
					break;
				case "dump": // Präfix-Codes ausgeben
					h.dumpPrefixCodes();
					break;
				default:
					System.out.println("Unknown Function: " + funct);
					System.out.println("	Possible values: ");
					System.out.println("		enc0 - Encode using current prefix codes");
					System.out.println("		enc1 - Construct new prefix codes and then encode");
					System.out.println("		dec - Decode using current prefix codes");
					System.out.println("		decpref - Decode using a given prefix code");
					System.out.println("		prefixes - Construct new prefix codes");
					System.out.println("		dump - Dump prefix code tree");
					return;
			}
		}
	}
}