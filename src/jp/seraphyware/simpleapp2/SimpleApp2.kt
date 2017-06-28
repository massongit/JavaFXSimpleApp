package jp.seraphyware.simpleapp2

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils
import jp.seraphyware.utils.XMLResourceBundleControl
import java.util.*

/**
 * 簡単なFXMLアプリケーション作成例2.
 * (リソースバンドル使用)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp2 : Application() {
    override fun start(primaryStage: Stage) {
        try {
            // XMLリソースバンドルを読み込む
            val rb = ResourceBundle.getBundle(this::class.qualifiedName, XMLResourceBundleControl())

            // ステージのタイトル
            primaryStage.title = rb.getString("title")

            // FXMLをロードする.
            // (ローカライズするのでリソースバンドルも指定する)
            primaryStage.scene = Scene(FXMLLoader.load<Parent>(this::class.java.getResource("${this::class.simpleName}.fxml"), rb))

            // ステージの表示
            primaryStage.show()
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }
}
