
package net.sf.hajuergens.format

import java.time.LocalDate
import java.time.chrono.Chronology
import java.time.format.DateTimeFormatter
import java.util.Locale
import javafx.util.StringConverter

import scala.util._
import scala.util.control.TailCalls.TailRec
import scala.util.control.TailCalls._

/**
  * @see [[http://alvinalexander.com/java/simpledateformat-convert-string-to-date-formatted-parse]]
  */
class StringConverterLocalDate(chrono : Chronology) extends StringConverter[LocalDate] {

   chrono.ensuring( _ != null, "Chronology must be specified")

  val patterns = Seq(
    "dd.MM.yyyy",
    "d.M.yyyy",
    "M/d/yyyy",
    "ddMMyyyy",
    "d.M.yy",
    "M/d/yy",
    "ddMMyy"
  )

  /**
    * An array of date formats that we have allowed for.
    */
  val dateTimeFormatters : Seq[DateTimeFormatter] = patterns.map{ DateTimeFormatter.ofPattern(_, Locale.US) }

  private def format(formatter:DateTimeFormatter) = {
    {(text:String) => formatter.parse(text)}
      .andThen(chrono.date)
      .andThen(LocalDate.from)
  }

  private def cascadeFormats(dateTimeFormatters: Seq[DateTimeFormatter], text: String): TailRec[LocalDate] = {
    if (text == null || text.isEmpty) done(null)
    if(dateTimeFormatters.isEmpty) done(null)
    val hd :: tail = dateTimeFormatters
    Try{ format(hd)(text) } match {
      case Success(d) => done(d)
      case Failure(_) =>
        if(tail.isEmpty) done(null)
        else tailcall{ cascadeFormats(tail, text) }
    }
  }


  /**
    * @param text An input String, presumably from a user or a database table.
    * @return A Date (java.time.LocalDate) reference. The reference will be null if
    *         we could not match any of the known formats.
    */
   override def fromString(text: String): LocalDate = {
    cascadeFormats(dateTimeFormatters, text).result
  }

   override def toString(value: LocalDate): String =
      if (value == null) "" else dateTimeFormatters.head.format(value)
}
