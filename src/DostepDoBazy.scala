/*
 * Objekt zastępujący moduł dostępu do bazy danych, z którego korzysta główna część systemu obsługi zadań.
 * Role bazy danych pełni plik tekstowy o nazwie "baza.txt". Zakładam poprawność danych w bazie (dane nie są sprawdzane przy odczycie z bazy)
 */
import java.io._
import scala.io.Source
import java.util.Date

object DostepDoBazy {
  def zapiszDoBazy(lista: List[Zadanie]) = {
    val plikZDanymi = new PrintWriter(new File("baza.txt"))
    for (zadanie <- lista) {
      plikZDanymi.println(zadanie.nazwa);
      plikZDanymi.println(zadanie.tresc);
      plikZDanymi.println(zadanie.status);
      plikZDanymi.println(zadanie.estymacjaCzasowa.toString());

      if (zadanie.isInstanceOf[ZadanieZwykle]) {
        plikZDanymi.println("Zadanie Zwykle");
        if (zadanie.asInstanceOf[ZadanieZwykle].dataUkonczenia == null)
          plikZDanymi.println("null");
        else
          plikZDanymi.println(Zadanie.formatDaty.format(zadanie.asInstanceOf[ZadanieZwykle].dataUkonczenia));
      }
      if (zadanie.isInstanceOf[ZadanieCykliczne]) {
        plikZDanymi.println("Zadanie Cykliczne");
        plikZDanymi.println(zadanie.asInstanceOf[ZadanieCykliczne].cyklPowtarzania);
      }
      if (zadanie.isInstanceOf[ZadanieZDeadlinem]) {
        plikZDanymi.println("Zadanie Z Deadlinem");
        plikZDanymi.println(Zadanie.formatDaty.format(zadanie.asInstanceOf[ZadanieZDeadlinem].deadline));
      }

    }

    plikZDanymi.close();
  }

  def wczytajZBazy(): List[Zadanie] = {
    var listaWynikowa: List[Zadanie] = List();
    var licznik: Int = 0;
    var nazwa: String = null;
    var tresc: String = null;
    var status: String = null;
    var estymacja: Int = 0;
    var data: Date = null;
    var cyklPowtarzania: String = null;
    var typ: String = null;

    for (linia <- Source.fromFile("baza.txt").getLines()) {
      if (licznik == 0)
        nazwa = linia;
      if (licznik == 1)
        tresc = linia;
      if (licznik == 2)
        status = linia;
      if (licznik == 3)
        estymacja = linia.toInt;
      if (licznik == 4)
        typ = linia;
      if (licznik == 5) {
        if (typ == "Zadanie Zwykle") {
          if (linia == "null")
            data = null;
          else
            data = Zadanie.formatDaty.parse(linia);
          listaWynikowa = listaWynikowa.+:(new ZadanieZwykle(nazwa, tresc, estymacja, status, data));
        }
        if (typ == "Zadanie Z Deadlinem") {
          data = Zadanie.formatDaty.parse(linia);
          listaWynikowa = listaWynikowa.+:(new ZadanieZDeadlinem(nazwa, tresc, estymacja, data, status));
        }
        if (typ == "Zadanie Cykliczne") {
          cyklPowtarzania = linia;
          listaWynikowa = listaWynikowa.+:(new ZadanieCykliczne(nazwa, tresc, estymacja, cyklPowtarzania, status));
        }

      }
      licznik += 1;
      licznik %= 6;
    }
    return listaWynikowa;
  }
}