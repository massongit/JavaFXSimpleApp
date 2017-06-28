package jp.seraphyware.simpleapp6

import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * ボタンとメニューバーのニーモニックとアクセラレータの使用例.
 * (およびMacの場合のニーモニック非表示対応)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp6Controller : Initializable {
    /**
     * ルート要素
     */
    @FXML
    private lateinit var root: BorderPane

    /**
     * 入れ子になったFXMLのルートがパイントされる.
     * フィールド名は子FXMLのidと同じでなければならない.
     */
    @FXML
    private lateinit var dirTextField: BorderPane

    /**
     * 入れ子になったFXMLでインスタンス化されたコントローラがバインドされる
     * フィールド名は"子FXMLのid + Controller"でなければならない.
     */
    @FXML
    private lateinit var dirTextFieldController: DirTextFieldController

    @FXML
    private lateinit var txtNamePattern: TextField

    @FXML
    private lateinit var chkSubdir: CheckBox

    @FXML
    private lateinit var btnOK: Button

    @FXML
    private lateinit var menuClose: MenuItem

    /**
     * "OK"メニューアイテム
     */
    @FXML
    private lateinit var menuItemOk: MenuItem

    /**
     * コントローラの初期化のためにFXMLのバインド後に呼び出される.
     */
    override fun initialize(location: URL, resources: ResourceBundle) {
        // OKボタンの活性制御
        this.btnOK.disableProperty().bind(Bindings.or(this.dirTextFieldController.textProperty.isEmpty, this.txtNamePattern.textProperty().isEmpty))

        // コマンドメニューの活性制御
        this.menuItemOk.disableProperty().bind(this.btnOK.disabledProperty())

        // Macの場合はFile::Closeメニューアイテムは削除する (システムメニューがあるため)
        if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac")) {
            this.menuClose.parentMenu.items.remove(this.menuClose)
        }

        // ディレクトリ選択テキストにフォーカスを当てる
        this.dirTextField.requestFocus()
    }

    @FXML
    private fun onOK() = Alert(AlertType.INFORMATION).apply {
        headerText = "実行！"
        contentText = listOf("dir=${dirTextFieldController.text}", "namePattern=${txtNamePattern.text}", "subdir=" + if (chkSubdir.isSelected) "Yes" else "No").joinToString(System.lineSeparator())
        showAndWait()
    }

    /**
     * 表示しているステージに対してクローズを要求する.
     */
    @FXML
    private fun onCancel() = (this.root.scene.window as Stage).close()

    @FXML
    private fun onAbout() = Alert(AlertType.INFORMATION).apply {
        headerText = "About!"
        show()
    }
}
