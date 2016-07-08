/*
 * Kod ten ma za zadanie umożliwić zweryfikowanie poprawności działania systemu, oraz możliwości prostej interakcji z głównym modułem obsługującym zadania. 
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import scala.io._
import java.io.IOException
import java.text.ParseException

object main {

  def main(args: Array[String]) {
    var wybor: Int = -1;
    var wyborZewnetrzny: Int = -1;
    var ID: Int = 0;
    var nazwa: String = null;
    var tresc: String = null;
    var status: String = null;
    var estymacja: Int = 0;
    var data: Date = null;
    var cyklPowtarzania: String = null;

    while (wybor != 0) {
      try {
        println("");

        println("1: Dodanie zadania | 2: Wyswietlanie zadan | 3: Modyfikacja zadan | 4: Dostep do bazy danych | 5: Raport | 0: Wyjscie");
        wybor = StdIn.readInt();

        if (wybor == 1) {
          println("1: Zadanie Zwykle | 2: Zadanie Cykliczne | 3: Zadanie z Deadlinem");
          wybor = StdIn.readInt();
          if (wybor == 1) {
            println("Podaj nazwe zadania");
            nazwa = StdIn.readLine();
            println("Podaj tresc zadania");
            tresc = StdIn.readLine();
            println("Podaj estymacje czasowa zadania");
            estymacja = StdIn.readInt();
            MenedzerZadan.dodajZadanie(new ZadanieZwykle(nazwa, tresc, estymacja));
            wybor = -1;
          }
          if (wybor == 2) {
            println("Podaj nazwe zadania");
            nazwa = StdIn.readLine();
            println("Podaj tresc zadania");
            tresc = StdIn.readLine();
            println("Podaj estymacje czasowa zadania");
            estymacja = StdIn.readInt();
            println("Podaj cykl powtarzania zadania");
            cyklPowtarzania = StdIn.readLine();
            MenedzerZadan.dodajZadanie(new ZadanieCykliczne(nazwa, tresc, estymacja, cyklPowtarzania));
            wybor = -1;
          }
          if (wybor == 3) {
            println("Podaj nazwe zadania");
            nazwa = StdIn.readLine();
            println("Podaj tresc zadania");
            tresc = StdIn.readLine();
            println("Podaj estymacje czasowa zadania");
            estymacja = StdIn.readInt();
            println("Podaj deadline zadania (w formacie dd.mm.yyyy)");
            data = Zadanie.formatDaty.parse(StdIn.readLine());
            MenedzerZadan.dodajZadanie(new ZadanieZDeadlinem(nazwa, tresc, estymacja, data));
            wybor = -1;
          }
        }

        if (wybor == 2) {
          println("1: Wypisz wszystkie zadania");
          println("2: Wypisz zadania wykonane");
          println("3: Wypisz zadania do wykonania");
          println("4: Zadnaia bez przypisanego terminu wykonania");
          println("5: Kalendarz zadan z deadlinem");

          wybor = StdIn.readInt();
          if (wybor == 1) {
            for (zadanie <- MenedzerZadan.zwrocListeZadan((zadanie: Zadanie) => true)) {
              zadanie.wypisz();
            }
          }
          if (wybor == 2) {
            for (zadanie <- MenedzerZadan.zwrocListeZadan((zadanie: Zadanie) => zadanie.status == "Wykonane")) {
              zadanie.wypisz();
            }
          }
          if (wybor == 3) {
            for (zadanie <- MenedzerZadan.zwrocListeZadan((zadanie: Zadanie) => zadanie.status == "Dodane")) {
              zadanie.wypisz();
            }
          }
          if (wybor == 4) {
            for (zadanie <- MenedzerZadan.zwrocListeZadan((zadanie: Zadanie) => zadanie.isInstanceOf[ZadanieZwykle])) {
              zadanie.wypisz();
            }
          }
          if (wybor == 5) {
            //pętla ta iteruje po posortowanej według daty deadline'u liście zadań pobranych z głównego modułu, odfiltrowanej tak, aby zawierała tylko zadania które mają określony deadline.
            for (
              zadanie <- (MenedzerZadan.zwrocListeZadan(
                (zadanie: Zadanie) => zadanie.isInstanceOf[ZadanieZDeadlinem]))
                .sortWith((zad1: Zadanie, zad2: Zadanie) => zad1.asInstanceOf[ZadanieZDeadlinem].deadline.before(zad2.asInstanceOf[ZadanieZDeadlinem].deadline))
            ) {
              zadanie.wypisz();
            }
          }

          wybor = -1;
        }
        if (wybor == 3) {
          println("1:Zmien status zadania");
          println("2:Zmien estymacje czasowa zadania");
          println("3:Okresl date wykonania zadania");
          wybor = StdIn.readInt();
          if (wybor == 1) {
            println("1: Ustaw jako wykonane | 2: Ustaw jako anulowane");
            wybor = StdIn.readInt();
            if (wybor == 1) {
              println("Podaj ID zadania");
              ID = StdIn.readInt();
              MenedzerZadan.zmienStatusZadania(ID, "Wykonane");
              wybor = -1;
            }
            if (wybor == 2) {
              println("Podaj ID zadania");
              ID = StdIn.readInt();
              MenedzerZadan.zmienStatusZadania(ID, "Anulowane");
              wybor = -1;
            }
          }
          if (wybor == 2) {
            println("Podaj nowa estymacje czasowa");
            estymacja = StdIn.readInt();
            println("Podaj ID zadania");
            ID = StdIn.readInt();
            MenedzerZadan.zmienEstymacjeCzasowaZadania(ID, estymacja);
          }
          if (wybor == 3) {
            println("Podaj date");
            data = Zadanie.formatDaty.parse(StdIn.readLine());
            println("Podaj ID zadania");
            ID = StdIn.readInt();
            MenedzerZadan.okreslDateWykonaniaZadania(ID, data);
          }
          wybor = -1;
        }
        if (wybor == 4) {
          println("1: Wczytaj dane | 2: Zapisz dane");
          wybor = StdIn.readInt();
          if (wybor == 1)
            MenedzerZadan.wczytajDaneZBazy();
          if (wybor == 2)
            MenedzerZadan.zapiszDaneDoBazy();

        }
        if (wybor == 5) {
          println("Estymowany czas wykonania wszystkich zadań:" + MenedzerZadan.raportuj());
        }
      } catch {
        //obsługa wyjątków które występują przy podaniu niepoprawnych danych do programu.
        case ex: NumberFormatException => { println("Niepoprana liczba") }
        case ex: ParseException        => { println("Niepoprana data") }
      }

    }

  }
}