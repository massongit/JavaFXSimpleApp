package jp.seraphyware.simpleapp4

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils
import jp.seraphyware.utils.XMLResourceBundleControl
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * 入れ子になったFXMLを使うアプリケーション作成例.
 * (リソースバンドル使用)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp4 : Application() {
    override fun start(primaryStage: Stage) {
        val logger = Logger.getLogger(this::class.simpleName)

        try {
            // XMLリソースバンドルを読み込む
            val rb = ResourceBundle.getBundle(this::class.qualifiedName, XMLResourceBundleControl())

            // ステージのタイトル
            primaryStage.title = rb.getString("title")

            FXMLLoader(this::class.java.getResource("${this::class.simpleName}.fxml"), rb).apply {
                // FXMLで指定されているコントローラのインスタンスを取得する為のファクトリ
                setControllerFactory {
                    try {
                        logger.info("要求されたコントローラクラス: $it")
                        it.newInstance()
                    } catch (ex: Exception) {
                        logger.log(Level.SEVERE, "コントローラの作成に失敗: $ex", ex)
                        throw RuntimeException(ex)
                    }
                }

                // FXMLをロードする.
                primaryStage.scene = Scene(load<Parent>())
            }

            // ステージの表示
            primaryStage.show()
        } catch (ex: Exception) {
            logger.log(Level.SEVERE, "エラー発生: $ex", ex)
            ErrorDialogUtils.showException(ex)
        }
    }
}
