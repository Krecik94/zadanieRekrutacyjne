/*
 * Klasa reprezentująca zadanie które musi być wykonywane cyklicznie. 
 * Cykliczność opisana jest okresem powtarzania zadania, w formie tekstu.
 */
import java.util.Date

class ZadanieCykliczne(nazwaInit: String, trescInit: String, estymacjaCzasowaInit: Int, cyklPowtarzaniaInit: String = "Tydzien", statusInit: String = "Dodane")
    extends Zadanie(nazwaInit, trescInit, estymacjaCzasowaInit, statusInit) {
  val cyklPowtarzania: String = cyklPowtarzaniaInit;
  override def wypisz = {
    super.wypisz();
    println("Powtarzane co: " + cyklPowtarzania);
  }
}