package jp.seraphyware.utils

import javafx.fxml.FXMLLoader
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author seraphy, Masaya SUZUKI
 */
class ErrorDialogUtils private constructor() {
    companion object {
        fun showException(ex: Throwable) {
            // jdk1.8u40から、Alertクラスによるダイアログがサポートされた.
            // 使い方は以下引用
            // http://code.makery.ch/blog/javafx-dialogs-official/
            val alert = Alert(AlertType.ERROR).apply {
                title = "Exception Dialog"
                headerText = ex::class.simpleName
                contentText = ex.message
            }

            // Create expandable Exception.
            StringWriter().use {
                PrintWriter(it).use {
                    ex.printStackTrace(it)
                }

                alert.dialogPane.expandableContent = FXMLLoader(this::class.java.getResource("ErrorDialogExpandableContent.fxml")).apply {
                    // FXMLで指定されたコントローラクラスをインスタンス化する.
                    setControllerFactory {
                        try {
                            it.newInstance()
                        } catch (ex: Exception) {
                            throw RuntimeException(ex)
                        }
                    }
                }.load<GridPane>().apply {
                    (children[1] as TextArea).text = it.toString()
                }
            }

            alert.showAndWait()
        }
    }
}
