import java.io.File

object JavaReceiptOcr {
    @Throws(Exception::class)
    fun main(args: Array<String?>?) {
        val receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt" // Receipt OCR API endpoint
        val imageFile = File("receipt.png")
        val resultFile = File("result.JSON")
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
                resultFile.writeText(EntityUtils.toString(response.getEntity())) // Receipt OCR result in JSON
            }
        }
    }
}
