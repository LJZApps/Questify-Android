import de.ljz.questify.data.datastore.AppUser
import de.ljz.questify.data.datastore.AppUserSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

suspend fun testAppUserSerialization(appUser: AppUser) {
  // Simuliertes OutputStream
  val outputStream = ByteArrayOutputStream()
  val inputStream = ByteArrayInputStream(outputStream.toByteArray())

  // Serialisieren
  AppUserSerializer.writeTo(appUser, outputStream)

  // Deserialisieren
  val deserializedUser = AppUserSerializer.readFrom(ByteArrayInputStream(outputStream.toByteArray()))

  println("Original: $appUser")
  println("Deserialized: $deserializedUser")
}