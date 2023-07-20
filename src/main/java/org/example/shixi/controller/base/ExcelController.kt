package org.example.shixi.controller.base

import com.alibaba.excel.EasyExcel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.extern.slf4j.Slf4j
import org.example.shixi.controller.RestResponse
import org.example.shixi.service.base.ExcelUserListener
import org.example.shixi.service.base.UserService
import org.example.shixi.tables.entity.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import jakarta.servlet.http.HttpServletResponse
import org.example.shixi.config.ExcelConfig
import org.springframework.security.crypto.password.PasswordEncoder


@Tag(name = "使用excel插入")
@Slf4j
@RestController
@RequestMapping("/excelUser")
class ExcelController {
    @Autowired
    private val userService: UserService? = null
    @Autowired
    private val passwordEncoder:PasswordEncoder? =null
    private val log = LoggerFactory.getLogger(ResourceController::class.java)
    @Operation(summary = "新增")
    @PostMapping
    fun add(@RequestPart excelFile: MultipartFile) : RestResponse<Boolean> {
        return try {
            log.info("/role#post:{}",excelFile.name)
            val inputStream: InputStream = excelFile.inputStream
            EasyExcel.read(inputStream,UserEntity::class.java,
                ExcelUserListener(userService, passwordEncoder)
            ).sheet().doRead()
            RestResponse.ok("导出成功")
        }catch (e:Exception){
            RestResponse.badRequest(e.message)
        }
    }
    @Operation(summary = "导出")
    @PutMapping
    fun export(@RequestParam fileName: String,httpServletResponse: HttpServletResponse){
        return try {
            log.info("/role#post:{}",fileName)
            val userServiceList:List<UserEntity> = userService!!.list()
            httpServletResponse.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            httpServletResponse.characterEncoding = "utf-8"
            httpServletResponse.setHeader("Content-disposition", "attachment;filename*=utf-8''$fileName.xlsx")
                EasyExcel.write(httpServletResponse.outputStream, UserEntity::class.java).registerWriteHandler(
                    ExcelConfig(userServiceList.size,0,1)
                ).sheet("模板").doWrite(userServiceList)
        }catch (e:Exception){
            log.info("Exception:{}",e.message)
        }
    }
}