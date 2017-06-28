package jp.seraphyware.simpleapp3

import javafx.beans.binding.Bindings
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * FXMLカスタムコンポーネントを使うアプリケーション作成例.
 * (リソースバンドル使用)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp3Controller : Initializable {
    @FXML
    private lateinit var dirTextField: DirTextField

    @FXML
    private lateinit var txtNamePattern: TextField

    @FXML
    private lateinit var chkSubdir: CheckBox

    @FXML
    private lateinit var btnOK: Button

    override fun initialize(location: URL, resources: ResourceBundle) {
        // OKボタンの活性制御
        this.btnOK.disableProperty().bind(Bindings.or(this.dirTextField.textProperty.isEmpty, this.txtNamePattern.textProperty().isEmpty))

        // ディレクトリ選択テキストにフォーカスを当てる
        this.dirTextField.requestFocus()
    }

    @FXML
    private fun onOK() = Alert(AlertType.INFORMATION).apply {
        headerText = "実行！"
        contentText = listOf("dir=${dirTextField.text}", "namePattern=${txtNamePattern.text}", "subdir=" + if (chkSubdir.isSelected) "Yes" else "No").joinToString(System.lineSeparator())
        showAndWait()
    }

    /**
     * 現在ボタンを表示しているシーンを表示しているステージに対してクローズを要求する.
     */
    @FXML
    private fun onCancel(evt: ActionEvent) = ((evt.source as Button).scene.window as Stage).close()
}
