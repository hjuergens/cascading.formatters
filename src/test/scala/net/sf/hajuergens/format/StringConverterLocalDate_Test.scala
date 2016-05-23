package net.sf.hajuergens.format

import java.time.LocalDate
import java.time.chrono.Chronology
import java.util.Locale

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class StringConverterLocalDate_Test extends WordSpec with Matchers {

  val chrono = Chronology.ofLocale(Locale.getDefault(Locale.Category.FORMAT))

  "An instance of StringConverterLocalDate" when {
    val converter = new StringConverterLocalDate(chrono)
    "fromString is invoked with '150216'" should {
      val d = converter.fromString("150216")
      "correctly parse to the date '15.02.2016" in {
        d.getYear shouldBe 2016
        d.getMonth shouldBe java.time.Month.FEBRUARY
        d.getDayOfMonth  shouldBe 15
      }
    }
    "fromString is invoked with '8.6.1975'" should {
      val d = converter.fromString("8.6.1975")
      "correctly parse to the date '08.06.1975" in {
        d.getYear shouldBe 1975
        d.getMonth shouldBe java.time.Month.JUNE
        d.getDayOfMonth  shouldBe 8
      }
    }
    "fromString is invoked with '7.05.45'" should {
      val d = converter.fromString("7.05.45")
      "correctly parse to the date '07.05.2045" in {
        d.getYear shouldBe 2045
        d.getMonth shouldBe java.time.Month.MAY
        d.getDayOfMonth  shouldBe 7
      }
    }
    "fromString is invoked with '8/9/17'" should {
      val d = converter.fromString("8/9/17")
      "correctly parse to the date '09.08.2017" in {
        d.getYear shouldBe 2017
        d.getMonth shouldBe java.time.Month.AUGUST
        d.getDayOfMonth  shouldBe 9
      }
    }
    "fromString is invoked with '3/07/1522'" should {
      val d = converter.fromString("3/07/1522")
      "correctly parse to the date '07.03.1522" in {
        d.getYear shouldBe 1522
        d.getMonth shouldBe java.time.Month.MARCH
        d.getDayOfMonth shouldBe 7
      }
    }
    "toString is invoked with '3/07/1522'" should {
      val date = LocalDate.of(1522, java.time.Month.MARCH, 7)
      "correctly format to the text '07.03.1522'" in {
        converter.toString(date) shouldBe "07.03.1522"
      }
    }
  }
}
