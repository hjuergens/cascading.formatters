package net.sf.hajuergens.format
package net.sf.hajuergens.format

import java.util.Locale
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.{DatePicker => FxDatePicer}
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
  * Created by juergens on 23.05.16.
  */
object DatePickerSample {

  locally {
    Locale.setDefault(Locale.US)
  }

  def main(args: Array[String]) {
    val appClass : Class[_ <: Application]= classOf[DatePickerSample]
    Application.launch(appClass, args: _*)
  }
}

class DatePickerSample extends Application {

  override def start(primaryStage: Stage): Unit = {
    val  datePicker = new FxDatePicer()
    datePicker.setConverter(new StringConverterLocalDate(datePicker.getChronology))
    primaryStage.setScene(new Scene(new StackPane(Array(datePicker): _*), 375, 120))
    primaryStage.show()
  }
}