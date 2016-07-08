/* 
 * Klasa realizująca wymagania funkcjonalne zadania.
 * Działanie poszczególnych metod szczegółowo opisane w dołączonym opisie rozwiązania
 */

import java.util.Date
object MenedzerZadan {
  private var listaZadan: List[Zadanie] = List();
  private var aktualneID: Int = 1;

  def dodajZadanie(zadanieDoDodania: Zadanie) {
    //sprawdzenie poprawności danych wejściowych
    if (zadanieDoDodania.estymacjaCzasowa > 0) {
      zadanieDoDodania.nadajID(aktualneID);
      aktualneID += 1;
      listaZadan = listaZadan.+:(zadanieDoDodania);
    }
  }

  //metoda zwracająca listę zadań. Przyjmuje ona funkcję która decyduje jakie zadania mają zostać zwrócone
  def zwrocListeZadan(f: Zadanie => Boolean): List[Zadanie] = {
    var listaDoZwrotu: List[Zadanie] = List();
    for (zadanie <- listaZadan) {
      if (f(zadanie))
        listaDoZwrotu = listaDoZwrotu.+:(zadanie);
    }
    return listaDoZwrotu;
  }

  //metoda generująca wymagany raport z estymowanym czasem wykoaniania zadań obecnych w systemie
  def raportuj(): Int = {
    var suma: Int = 0;
    for (
      zadanie <- listaZadan if (zadanie.status!="Wykonane"&&(zadanie.isInstanceOf[ZadanieZwykle] || zadanie.isInstanceOf[ZadanieZDeadlinem]))
    ) {
      suma += zadanie.estymacjaCzasowa;
    }
    return suma;
  }
  //metoda wczytująca dane z bazy danych. Wymaga ona modułu który taki dostęp obsłuży. W tym przypadku rolę bazy pełni plik tekstowy, z obsługą w objekcie DostepDoBazy.
  def wczytajDaneZBazy() {
    listaZadan = DostepDoBazy.wczytajZBazy();
    aktualneID = 1;
    for (zadanie <- listaZadan) {
      zadanie.nadajID(aktualneID);
      aktualneID += 1;
    }
  }

  //metoda dostarczająca listę zadań moduyłowi zajmującemu się zapisem danych do bazy.
  def zapiszDaneDoBazy() {
    DostepDoBazy.zapiszDoBazy(listaZadan);
  }

  def zmienStatusZadania(ID: Int, nowyStatus: String) {
    val zadanie: Option[Zadanie] = listaZadan.find((zadanie: Zadanie) => zadanie.ID == ID);
    if (!zadanie.isEmpty) {
      zadanie.get.zmienStatus(nowyStatus);
    }
  }

  def zmienEstymacjeCzasowaZadania(ID: Int, nowaEstymacja: Int) {
    val zadanie: Option[Zadanie] = listaZadan.find((zadanie: Zadanie) => zadanie.ID == ID);
    if (!zadanie.isEmpty) {
      zadanie.get.zmienEstymacjeCzasowa(nowaEstymacja);
    }
  }

  def okreslDateWykonaniaZadania(ID: Int, data: Date) {
    val zadanie: Option[Zadanie] = listaZadan.find((zadanie: Zadanie) => zadanie.ID == ID);
    if (!zadanie.isEmpty) {
      if (zadanie.get.isInstanceOf[ZadanieZwykle]) zadanie.get.asInstanceOf[ZadanieZwykle].zmienDateUkonczenia(data);
    }
  }
}