package jp.seraphyware.simpleapp1

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils

/**
 * 簡単なFXMLアプリケーション作成例.

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp : Application() {
    /**
     * アプリケーション開始
     */
    override fun start(primaryStage: Stage) {
        try {
            // ステージのタイトル
            primaryStage.title = "SimpleApp Sample"

            // FXMLをロードする.
            primaryStage.scene = Scene(FXMLLoader.load<Parent>(this::class.java.getResource("${this::class.simpleName}.fxml")))

            // ステージの表示
            primaryStage.show()
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }
}
