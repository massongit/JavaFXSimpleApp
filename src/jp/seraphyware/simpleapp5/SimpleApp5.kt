package jp.seraphyware.simpleapp5

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils
import jp.seraphyware.utils.XMLResourceBundleControl
import java.util.*

/**
 * CSSを使うFXMLを使うアプリケーション作成例.
 * (リソースバンドル, CSS, 背景画像リソース使用)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp5 : Application() {
    override fun start(primaryStage: Stage) {
        try {
            // XMLリソースバンドルを読み込む
            val rb = ResourceBundle.getBundle(this::class.qualifiedName, XMLResourceBundleControl())

            // ステージのタイトル
            primaryStage.title = rb.getString("title")

            FXMLLoader(this::class.java.getResource("${this::class.simpleName}.fxml"), rb).apply {
                setControllerFactory {
                    try {
                        println("controller: $it")
                        it.newInstance()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        throw RuntimeException(ex)
                    }
                }

                // FXMLをロードする.
                primaryStage.scene = Scene(load<Parent>())
            }

            // ステージの表示
            primaryStage.show()
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }
}
