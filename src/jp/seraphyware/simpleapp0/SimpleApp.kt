package jp.seraphyware.simpleapp0

import javafx.application.Application
import javafx.stage.Stage
import jp.seraphyware.utils.ErrorDialogUtils

class SimpleApp : Application() {
    override fun start(primaryStage: Stage) {
        try {
            primaryStage.show()
        } catch (ex: Exception) {
            ErrorDialogUtils.showException(ex)
        }
    }
}
