package com.dqgb.MPlatform.widget.common;

import com.dqgb.MPlatform.widget.util.FileUtils;

import java.io.File;

public interface WidgetConst {

    //应用缓存地址
    String FILE_DIR_NAME = "yhgl";

    String resCodeSet = "resCodeSet";

    String fileName = "yhgl";

    String file_path = FileUtils.getSDPath() + File.separator + FILE_DIR_NAME + File.separator;

    String arcgisId = "9yNxBahuPiGPbsdi";

    //时间格式化
    String FORMATER_normal = "yyyy-MM-dd HH:mm:ss";
    String FORMATER_min = "yyyy-MM-dd HH:mm";
    String FORMATER_hour = "yyyy-MM-dd HH";
    String FORMATER_day = "yyyy-MM-dd";
    String FORMATER_month = "yyyy-MM";

    String BLUE = "BLUE";
    String GREEN = "GREEN";
    String GREY = "GREY";
    String RED = "RED";
    String YELLOW = "YELLOW";

}
