package jp.seraphyware.simpleapp6

import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.WindowEvent
import jp.seraphyware.utils.ErrorDialogUtils
import jp.seraphyware.utils.XMLResourceBundleControl
import java.util.*
import java.util.logging.Logger

/**
 * ボタンとメニューバーのニーモニックとアクセラレータの使用例.
 * (およびMacの場合のニーモニック非表示対応)

 * @author seraphy, Masaya SUZUKI
 */
class SimpleApp6 : Application() {
    /**
     * ロガー
     */
    private val logger = Logger.getLogger(this::class.simpleName)

    override fun start(primaryStage: Stage) {
        try {
            // XMLリソースバンドルを読み込む
            val rb = ResourceBundle.getBundle(this::class.qualifiedName, XMLResourceBundleControl())

            // ステージのタイトル
            primaryStage.title = rb.getString("title")

            FXMLLoader(this::class.java.getResource("${this::class.simpleName}.fxml"), rb).apply {
                // FXMLで指定されたコントローラクラスをインスタンス化する.
                setControllerFactory {
                    try {
                        logger.info("controller: $it")
                        it.newInstance()
                    } catch (ex: Exception) {
                        throw RuntimeException(ex)
                    }
                }

                // FXMLをロードする.
                primaryStage.scene = Scene(load<Parent>())
            }

            // ステージのアイコン
            primaryStage.icons.add(Image(this::class.java.getResourceAsStream("icon.png")))

            // 終了確認ダイアログ.
            Alert(AlertType.CONFIRMATION).apply {
                title = rb.getString("closeConfirmAlertTitle")
                headerText = rb.getString("closeConfirmAlertHeaderText")

                // ウィンドウを×ボタンで閉じることを検知するためのリスナ
                primaryStage.onCloseRequest = EventHandler<WindowEvent> {
                    logger.info("★onCloseRequest")

                    // "OK"でなければ、クローズ要求イベントを消してしまう.
                    if (showAndWait().get() != ButtonType.OK) {
                        it.consume()
                    }
                }
            }

            // ウィンドウが閉じられることを検知するためのリスナ
            primaryStage.onHiding = EventHandler<WindowEvent> {
                this.logger.info("★onHiding")
            }

            // ステージの表示
            primaryStage.show()
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }

    /**
     * アプリケーションが停止する場合のハンドラ
     */
    override fun stop() = this.logger.info("Applcation#stoped")
}
