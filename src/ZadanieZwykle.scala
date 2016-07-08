/*
 * Klasa reprezentująca najbardziej podstawowe zadanie, które oferuje mozliwość przypisania do niego terminu w którym zostało wykonane.
 */
import java.util.Date

class ZadanieZwykle(nazwaInit: String, trescInit: String, estymacjaCzasowaInit: Int, statusInit: String = "Dodane", dataUkonczeniaInit: Date = null)
    extends Zadanie(nazwaInit, trescInit, estymacjaCzasowaInit, statusInit) {
  var dataUkonczenia: Date = dataUkonczeniaInit;

  def zmienDateUkonczenia(nowaData: Date) {
    dataUkonczenia = nowaData;
  }
  override def wypisz = {
    super.wypisz();
    if (dataUkonczenia != null)
      println("Ukonczono: " + Zadanie.formatDaty.format(dataUkonczenia));
  }
}