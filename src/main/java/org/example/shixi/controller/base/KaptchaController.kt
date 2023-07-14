package org.example.shixi.controller.base

import com.google.code.kaptcha.Producer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Slf4j
@Controller
@RequestMapping("/sys/verifyCode")
@RequiredArgsConstructor
@Tag(name = "验证码接口")
class KaptchaController {
    @Autowired
    private val producer: Producer? = null

    @GetMapping("/image")
    @Operation(summary = "图片格式")
    @Throws(IOException::class)
    fun image(@Parameter(hidden = true) response: HttpServletResponse, @Parameter(hidden = true) httpSession: HttpSession) {
        val image = createImage(httpSession)
        //响应图片
        response.contentType = MimeTypeUtils.IMAGE_JPEG_VALUE
        ImageIO.write(image, "jpeg", response.outputStream)
    }

    private fun createImage(httpSession: HttpSession): BufferedImage {
        //生成验证码
        val verifyCode: String = producer?.createText() ?: ""
        //保存到 session 中（或redis中）
        httpSession.setAttribute(VERIFY_CODE_KEY, verifyCode)
        //生成图片
        return producer!!.createImage(verifyCode)
    }

    companion object {
        const val VERIFY_CODE_KEY = "vc"
    }
}