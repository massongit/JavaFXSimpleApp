package jp.seraphyware.utils

import java.io.IOException
import java.util.*

/**
 * XMLプロパティ形式のリソースバンドルを読み込み可能にするコントローラ.<br>
 * リソース名はロケールおよびOSの種類によって識別されます.<br>
 * ロケールによる、標準のリソースバンドルの形式のサフィックスで検索され、
 * 該当のプロパティファイルがある場合は、サフィックスなしのものとマージされた結果として返されます.<br>
 *
 * @author seraphy, Masaya SUZUKI
 */
class XMLResourceBundleControl : ResourceBundle.Control() {
    /**
     * 拡張子
     */
    private val extension = "xml"

    /**
     * このコントローラが対象する拡張子の一覧を返す.
     * "xml"だけが有効である.

     * @param baseName ベース名
     * *
     * @return 対象となる拡張子一覧
     */
    override fun getFormats(baseName: String): List<String> = listOf(extension)

    /**
     * 新しいリソースバンドルを生成して返す.
     * xml形式のプロパティファイルから現在のロケールに従って取得したリソースバンドルを返す.
     * XMLプロパティファイルが存在すれば、それを重ねて読み込んだものが返されます.<br></br>

     * @return リソースバンドル
     */
    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    override fun newBundle(baseName: String, locale: Locale, format: String, loader: ClassLoader, reload: Boolean): ResourceBundle? {
        if (extension == format) {
            // ロケールと結合したリソース名を求める
            val plainBundleName = this.toBundleName(baseName, locale)

            // XMLプロパティを重ねてロードする.
            val props = Properties()
            var isOfLoadedProps = false
            for (bundleName in listOf<String>(plainBundleName, listOf<String>(plainBundleName, System.getProperty("os.name").toLowerCase(Locale.ENGLISH).replace(" ", "")).joinToString("_"))) {
                // 対応するフォーマットと結合したリソース名を求める
                val url = loader.getResource(this.toResourceName(bundleName, format))

                // XMLプロパティを上書きロードする.
                if (url != null) {
                    props.loadFromXML(url.openStream())
                    isOfLoadedProps = true
                }
            }

            if (isOfLoadedProps) {
                // 少なくとも、どちらかのXMLプロパティが読み込めた場合、
                // XMLプロパティをリソースバンドルに接続する.
                return object : ResourceBundle() {
                    override fun handleGetObject(key: String): Any = props.getProperty(key)

                    override fun getKeys(): Enumeration<String> = Collections.enumeration(props.stringPropertyNames())
                }
            }
        }

        // ロードできず.
        return null
    }
}
