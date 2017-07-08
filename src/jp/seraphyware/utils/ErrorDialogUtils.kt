package jp.seraphyware.utils

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
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
            val alert = Alert(AlertType.ERROR, ex.message).apply {
                title = "Exception Dialog"
                headerText = ex::class.simpleName
            }

            // Create expandable Exception.
            val sw = StringWriter()

            PrintWriter(sw).use {
                ex.printStackTrace(it)
            }

            val textArea = TextArea(sw.toString()).apply {
                isEditable = false
                isWrapText = true
                maxWidth = Double.MAX_VALUE
                maxHeight = Double.MAX_VALUE
            }

            GridPane.setVgrow(textArea, Priority.ALWAYS)
            GridPane.setHgrow(textArea, Priority.ALWAYS)

            // Set expandable Exception into the dialog pane.
            alert.dialogPane.expandableContent = GridPane().apply {
                maxWidth = Double.MAX_VALUE
                add(Label("The exception stacktrace was:"), 0, 0)
                add(textArea, 0, 1)
            }

            // エラーダイアログを表示
            alert.showAndWait()
        }
    }
}
