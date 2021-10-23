package cn.abstractmgs.model.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tblDemo")
public class Text {
    @TableField(value = "exhibits_label", property = "label")
    private String label;

    @TableField(value = "exhibits_text", property = "text")
    private String text;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
