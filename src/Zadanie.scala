/*
 * Klasa z której dziedziczą 3 typy zadań które mogą pojawić się w systemie.
 */
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

object Zadanie {
  val formatDaty: SimpleDateFormat = new SimpleDateFormat("dd.mm.yyy");
}

class Zadanie(nazwaInit: String, trescInit: String, estymacjaCzasowaInit: Int, statusInit: String = "Dodane") {
  val nazwa: String = nazwaInit;
  val tresc: String = trescInit;
  var status: String = statusInit;
  var estymacjaCzasowa: Int = estymacjaCzasowaInit;
  var ID: Int = 0;

  def nadajID(noweID: Int) {
    ID = noweID;
  }

  def wypisz() = {
    println("");
    println("ID: " + ID + " | Nazwa: " + nazwa + " | Estymacja czasowa: " + estymacjaCzasowa + " | Status: " + status);
    println("Tresc: " + tresc);
  }

  def zmienStatus(nowyStatus: String) {
    status = nowyStatus;
  }

  def zmienEstymacjeCzasowa(nowaEstymacja: Int) {
    estymacjaCzasowa = nowaEstymacja;
  }

}