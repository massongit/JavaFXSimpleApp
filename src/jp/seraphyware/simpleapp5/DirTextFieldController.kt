package jp.seraphyware.simpleapp5

import javafx.beans.property.StringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.stage.DirectoryChooser
import jp.seraphyware.utils.ErrorDialogUtils
import java.io.File
import java.net.URL
import java.util.*

class DirTextFieldController : Initializable {
    @FXML
    private lateinit var root: BorderPane

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

    override fun initialize(location: URL, resources: ResourceBundle) {
        // ダイアログのタイトル設定
        this.dirChooser.title = resources.getString("this.dirChooser.title")

        // フォーカスリスナ
        this.root.focusedProperty().addListener { _, _, newValue ->
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
