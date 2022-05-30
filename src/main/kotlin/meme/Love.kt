package org.laolittle.plugin.draw.meme

import org.jetbrains.skia.*
import org.laolittle.plugin.Fonts
import org.laolittle.plugin.draw.makeFromResource
import org.laolittle.plugin.usedBy

private val loveFont = Fonts["MiSans-DemiBold"] usedBy "Love生成器"
private val paint = Paint().apply{
    color = Color.makeRGB(155,8,3)
}

fun loveBuilder(text: String): Surface{
    val image = Image.makeFromResource("/Love/love_background.jpg")
    val height = image.height
    val width = image.width
    return Surface.makeRasterN32Premul(width,height).apply {
        canvas.apply{
            drawImage(image,0F,0F,paint)
            rotate(5f)
            val fontSize = getFontSize(text,width)
            val textLine = TextLine.make(text, loveFont.makeWithSize(fontSize))
            drawTextLine(
                textLine,
                getCentral(textLine,width),
                getBaseLine(fontSize, height),
                paint
            )
            rotate(-5f)
        }
    }
}

/**
 * 获取字体大小
 */
fun getFontSize(text: String,width:Int): Float{
    val full = countFullChar(text)
    return if(text.length == 1)
        200f + (text.length - full)*10f
    else if(text.length == 2)
        150f + (text.length - full)*10f
    else if(text.length == 3)
        120f + (text.length - full)*10f
    else
        (width * 0.5f)/((text.length-full)*0.6f + full)
}

/**
 * 获取文字中心位置
 */
fun getCentral(textLine: TextLine,width: Int): Float{
    return (width - textLine.width) / 2 + 29f
}

/**
 * 获取文字基线位置
 */
fun getBaseLine(fontSize: Float,height:Int): Float{
    return height * 0.12f + fontSize/2
}

/**
 * 计算全宽字符个数
 */
fun countFullChar(text:String):Int{
    var num = 0
    for(tmp in text){
        if (isChinese(tmp) or isJapanese(tmp))
            num ++
    }
    return num
}

/**
 * 判断是否为中文或中文字符
 */
fun isChinese(ch: Char): Boolean {
    //获取此字符的UniCodeBlock
    val ub = Character.UnicodeBlock.of(ch)
    //  GENERAL_PUNCTUATION 判断中文的“号
    //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    if (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION) {
        return true
    }
    return false
}
/**
 * 判断是否为日文
 */
fun isJapanese(ch: Char):Boolean {
    val japaneseUnicodeBlocks = HashSet<Character.UnicodeBlock>()
    japaneseUnicodeBlocks.add(Character.UnicodeBlock.HIRAGANA)
    japaneseUnicodeBlocks.add(Character.UnicodeBlock.KATAKANA)
    japaneseUnicodeBlocks.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
    return japaneseUnicodeBlocks.contains(Character.UnicodeBlock.of(ch))
}