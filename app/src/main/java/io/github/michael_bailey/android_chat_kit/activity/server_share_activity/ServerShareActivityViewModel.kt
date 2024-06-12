package io.github.michael_bailey.android_chat_kit.activity.server_share_activity

import android.graphics.Bitmap
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.michael_bailey.android_chat_kit.activity.Activity
import io.github.michael_bailey.android_chat_kit.data_type.sharable.SharableServerInfoData
import io.github.michael_bailey.android_chat_kit.extension.view_model.gen
import io.github.michael_bailey.android_chat_kit.repository.MutableServerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class ServerShareActivityViewModel @Inject constructor(
	private val savedServerRepository: MutableServerRepository
): ViewModel(), DefaultLifecycleObserver {
	
	val _serverName = MutableStateFlow("")
	val _serverHost = MutableStateFlow("")
	val _serverPort = MutableStateFlow<Int?>(null)
	val _jsonQRCode = MutableStateFlow<(@Composable () -> Bitmap)?>(null)
	
	val serverName = _serverName.asLiveData()
	val serverHost = 	_serverHost.asLiveData()
	val serverPort =	_serverPort.asLiveData()
	val jsonQRCode =	_jsonQRCode.asLiveData()
	
	override fun onCreate(owner: LifecycleOwner) = gen {
		super.onCreate(owner)
		
		owner as Activity<*>
		
		val intent = owner.intent
		
		val server_data = intent.extras?.getParcelable("data", SharableServerInfoData::class.java)
		
		
		
		if (server_data == null) {
			owner.finish()
			return@gen
		}
		
		_serverName.value = server_data.name
		_serverHost.value = server_data.hostname
		_serverPort.value = server_data.port
		
		_jsonQRCode.value = @Composable {
			
			encodeAsBitmap("chat://${server_data.hostname}:${server_data.port}")
		}
	}
	
	@Throws(WriterException::class)
	@Composable
	fun encodeAsBitmap(str: String?): Bitmap {
		
		val bg = MaterialTheme.colorScheme.background.toArgb()
		val fg = MaterialTheme.colorScheme.onBackground.toArgb()
		
		val writer = QRCodeWriter()
		val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400)
		val w = bitMatrix.width
		val h = bitMatrix.height
		val pixels = IntArray(w * h)
		for (y in 0 until h) {
			for (x in 0 until w) {
				pixels[y * w + x] = if (bitMatrix[x, y]) bg else fg
			}
		}
		val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
		return bitmap
	}
}
