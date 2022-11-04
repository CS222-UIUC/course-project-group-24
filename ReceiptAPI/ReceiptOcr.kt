
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

fun ReceiptOcr(file_name: String): String {
    val receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt" // Receipt OCR API endpoint
    val imageFile = File("receipt.png")
    val output = ""

    HttpClients.createDefault().use { client ->
        val post = HttpPost(receiptOcrEndpoint)
        post.setEntity(
            MultipartEntityBuilder.create()
                .addTextBody("client_id", "TEST") // Use 'TEST' for testing purpose
                .addTextBody("recognizer", "auto") // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                //.addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
                .addPart("file", FileBody(imageFile)) // the image file
                .build()
        )
        client.execute(post).use { response ->
            output = EntityUtils.toString(response.getEntity()) // Receipt OCR result
        }
    }
    return output
}