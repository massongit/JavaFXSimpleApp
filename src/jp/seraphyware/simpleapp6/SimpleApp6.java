package jp.seraphyware.simpleapp6;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jp.seraphyware.utils.XMLResourceBundleControl;

/**
 * ボタンとメニューバーのニーモニックとアクセラレータの使用例.
 * (およびMacの場合のニーモニック非表示対応)
 *
 * @author seraphy
 */
public class SimpleApp6 extends Application implements Initializable {

    /**
	 * ロガー
	 */
	private final Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * ルート要素
	 */
	@FXML
	private BorderPane root;

	/**
	 * 入れ子になったFXMLのルートがパイントされる.
	 * フィールド名は子FXMLのidと同じでなければならない.
	 */
	@FXML
	private BorderPane dirTextField;

	/**
	 * 入れ子になったFXMLでインスタンス化されたコントローラがバインドされる
	 * フィールド名は"子FXMLのid + Controller"でなければならない.
	 */
	@FXML
	private DirTextFieldController dirTextFieldController;

	@FXML
	private TextField txtNamePattern;

	@FXML
	private CheckBox chkSubdir;

	@FXML
	private Button btnOK;

	/**
	 * "OK"メニューアイテム
	 */
	@FXML
	private MenuItem menuitemOk;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLをリソースから取得する.
		// (タブオーダーもFXMLの定義順になる.)
		// (FXML中に「@参照」による相対位置指定がある場合は、このURL相対位置となる.)
		URL fxml = getClass().getResource(getClass().getSimpleName() + ".fxml");

		// XMLリソースバンドルを読み込む
		ResourceBundle rb = ResourceBundle.getBundle(getClass().getName(),
				new XMLResourceBundleControl());

		// FXMLをロードする.
		// (ローカライズするのでリソースバンドルも指定する)
		// (同時にincludeしている子FMXLも読み込まれるが、
		// その時には同じリソースバンドルが使われる)
		FXMLLoader ldr = new FXMLLoader(fxml, rb);

		// このインスタンス自身をコントローラとする.
		// @FXMLアノテーションによりFXMLと結び付けられる.
		ldr.setController(this);

		// FXMLで指定されたコントローラクラスをインスタンス化する.
		ldr.setControllerFactory(cls -> {
			try {
				logger.info("controller: " + cls);
				return cls.newInstance();

			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		});

		// FXMLをロードする.
		Parent root = ldr.load();

		// ステージのタイトル
		primaryStage.setTitle(rb.getString("title"));

		// ステージの表示
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		// ディレクトリ選択テキストにフォーカスを当てる
		dirTextField.requestFocus();

		// ウィンドウを×ボタンで閉じることを検知するためのリスナ
		primaryStage.setOnCloseRequest(evt -> {
			logger.info("★WindowCloseRequest: " + evt);
			// evt.consume(); // このイベントをconsumeすると×ボタンが効かなくなる
		});

		// ウィンドウが閉じられることを検知するためのリスナ
		primaryStage.setOnHiding(evt -> {
			logger.info("★WindowHiding: " + evt);
		});
	}

    @Override
	public void stop() {
		// アプリケーションの終了時に呼び出される
		logger.info("Applcation#stoped");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// OKボタンの活性制御
		btnOK.disableProperty().bind(
				Bindings.or(
						dirTextFieldController.textProperty().isEmpty(),
						txtNamePattern.textProperty().isEmpty()
						));

		// コマンドメニューの活性制御
		menuitemOk.disableProperty().bind(btnOK.disabledProperty());
	}

	@FXML
	protected void onOK(ActionEvent evt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("実行！");

		StringBuilder buf = new StringBuilder();
		buf.append("dir=").append(dirTextFieldController.getText()).append("\r\n")
				.append("namePattern=").append(txtNamePattern.getText())
				.append("\r\n").append("subdir=")
				.append(chkSubdir.isSelected() ? "Yes" : "No");
		alert.setContentText(buf.toString());

		alert.showAndWait();
	}

	@FXML
	protected void onCancel(ActionEvent evt) {
		// 表示しているステージに対してクローズを要求する.
		((Stage) root.getScene().getWindow()).close();
	}

	@FXML
	protected void onAbout(ActionEvent evt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("About!");
		alert.show();
	}

    /**
	 * エントリポイント
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		launch(args);
	}
}
