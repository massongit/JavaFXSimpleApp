package jp.seraphyware.simpleapp3

import javafx.beans.property.StringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.stage.DirectoryChooser
import jp.seraphyware.utils.ErrorDialogUtils
import jp.seraphyware.utils.XMLResourceBundleControl
import java.io.File
import java.io.IOException
import java.util.*

class DirTextField : BorderPane() {
    @FXML
    private lateinit var txtDir: TextField

    /**
     * ディレクトリ選択ダイアログ
     */
    private val dirChooser = DirectoryChooser()

    val textProperty: StringProperty
        get() = this.txtDir.textProperty()

    val text: String
        get() = this.textProperty.get()

    /**
     * コンストラクタ
     */
    init {
        val rb = ResourceBundle.getBundle(this::class.qualifiedName, XMLResourceBundleControl())
        val ldr = FXMLLoader(this::class.java.getResource("${this::class.simpleName}.fxml"), rb)

        // このインスタンス自身がルートオブジェクト
        ldr.setRoot(this)

        // このインスタンス自身がコントローラ
        ldr.setController(this)

        try {
            // ルートを指定済みなので、このインスタンスにFXMLがロードされる.
            ldr.load<Any>()
        } catch (ex: IOException) {
            ErrorDialogUtils.showException(ex)
        }

        // ダイアログのタイトル設定
        this.dirChooser.title = rb.getString("this.dirChooser.title")

        // フォーカスリスナ
        this.focusedProperty().addListener { _, _, newValue ->
            if (newValue!!) {
                this.txtDir.requestFocus()
            }
        }
    }

    @FXML
    private fun onBrowse(evt: ActionEvent) {
        try {
            val srcDir = this.txtDir.textProperty().get()

            if (srcDir != null && !srcDir.isEmpty()) {
                val initDir = File(srcDir)
                if (initDir.isDirectory) {
                    this.dirChooser.initialDirectory = initDir
                }
            }

            val selectedDir = this.dirChooser.showDialog((evt.source as Button).scene.window)

            if (selectedDir != null) {
                this.txtDir.apply {
                    text = selectedDir.absolutePath
                    requestFocus()
                }
            }
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }
}
