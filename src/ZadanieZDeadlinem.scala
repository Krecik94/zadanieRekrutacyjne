/*
 * Klasa reprezentująca zadanie które musi być wykonane przed określonym terminem.
 */
import java.util.Date

class ZadanieZDeadlinem(nazwaInit: String, trescInit: String, estymacjaCzasowaInit: Int, deadlineInit: Date, statusInit: String = "Dodane")
    extends Zadanie(nazwaInit, trescInit, estymacjaCzasowaInit, statusInit) {
  val deadline: Date = deadlineInit;
  override def wypisz = {
    super.wypisz();
    println("Deadline: " + Zadanie.formatDaty.format(deadline));
  }
}