package ch.proximeety.proximeety.util.extensions

import android.graphics.*
import android.media.ExifInterface


fun Bitmap.rotate(orientation: Int): Bitmap? {
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_NORMAL -> return this
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
            matrix.setRotate(180f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_TRANSPOSE -> {
            matrix.setRotate(90f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
        ExifInterface.ORIENTATION_TRANSVERSE -> {
            matrix.setRotate(-90f)
            matrix.postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
        else -> return this
    }
    return try {
        val bmRotated = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
        recycle()
        bmRotated
    } catch (e: OutOfMemoryError) {
        e.printStackTrace()
        null
    }
}

fun Bitmap.getRoundedCroppedBitmap(): Bitmap? {
    val widthLight = width
    val heightLight = height

    val output = Bitmap.createBitmap(
        width, height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(output)
    val paintColor = Paint()
    paintColor.flags = Paint.ANTI_ALIAS_FLAG
    val rectF = RectF(Rect(0, 0, widthLight, heightLight))
    canvas.drawRoundRect(rectF, (widthLight / 2).toFloat(), (heightLight / 2).toFloat(), paintColor)
    val paintImage = Paint()
    paintImage.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    canvas.drawBitmap(this, 0f, 0f, paintImage)
    return output
}
