package jp.seraphyware.simpleapp1

import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils
import java.io.File
import java.net.URL
import java.util.*

/**
 * 簡単なFXMLアプリケーション作成例.

 * @author seraphy, Masaya SUZUKI
 */
class SimpleAppController : Initializable {

    @FXML
    private lateinit var txtDir: TextField

    @FXML
    private lateinit var txtNamePattern: TextField

    @FXML
    private lateinit var chkSubdir: CheckBox

    @FXML
    private lateinit var btnOK: Button

    /**
     * ディレクトリ選択ダイアログ
     */
    private val dirChooser = DirectoryChooser()

    override fun initialize(location: URL, resources: ResourceBundle?) {
        // ダイアログのタイトルの設定
        this.dirChooser.title = "フォルダの選択"

        // OKボタンの活性制御
        this.btnOK.disableProperty().bind(Bindings.or(this.txtDir.textProperty().isEmpty, this.txtNamePattern.textProperty().isEmpty))

        // ディレクトリ選択テキストにフォーカスを当てる
        this.txtDir.requestFocus()
    }

    @FXML
    fun onBrowse(evt: ActionEvent) {
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

    @FXML
    fun onOK() = Alert(AlertType.INFORMATION).apply {
        headerText = "実行！"
        contentText = listOf("dir=${txtDir.text}", "namePattern=${txtNamePattern.text}", "subdir=" + if (chkSubdir.isSelected) "Yes" else "No").joinToString(System.lineSeparator())
        showAndWait()
    }

    /**
     * 現在ボタンを表示しているシーンを表示しているステージに対してクローズを要求する.
     */
    @FXML
    fun onCancel(evt: ActionEvent) = ((evt.source as Button).scene.window as Stage).close()
}
