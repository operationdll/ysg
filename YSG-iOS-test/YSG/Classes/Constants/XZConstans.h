//
//  XZConstans.h
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#ifndef XZConstans_h
#define XZConstans_h
//  字体宏
#define WT_FONTSYS(size) ([UIFont systemFontOfSize:(size)])
#define WT_FONTBOLDSYS(size) ([UIFont boldSystemFontOfSize:(size)])

//  颜色宏
#define WT_RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f alpha:1]
#define WT_RGBACOLOR(r,g,b,a) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f alpha:(a)]
#define WT_RGBA(r,g,b,a) (r)/255.0f, (g)/255.0f, (b)/255.0f, (a)
#define WT_RGBColor(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]
#define WT_HEXSTRCOLOR(hex) [UIColor colorWithHexString:hex]

//  图片宏
#define WT_IMAGE(name) [UIImage imageNamed:(name)]

//  获取沙箱文件
#define GetFileInAppData(file) [NSHomeDirectory() stringByAppendingPathComponent:[NSString stringWithFormat:@"Documents/AppData/%@",file]]
#define GetFileInLogData(file) [NSHomeDirectory() stringByAppendingPathComponent:[NSString stringWithFormat:@"Documents/Logs/%@",file]]
#define GetFileInImgData(file) [NSHomeDirectory() stringByAppendingPathComponent:[NSString stringWithFormat:@"Documents/ImgData/%@",file]]
#define GetFileInAppModuleData(module,file) [NSHomeDirectory() stringByAppendingPathComponent:[NSString stringWithFormat:@"Documents/AppData/%@/%@",module,file]]
//  获取沙箱路径
#define GetAppDataDirectory [NSHomeDirectory() stringByAppendingPathComponent:@"Documents/AppData"]
#define GetLogDataDirectory [NSHomeDirectory() stringByAppendingPathComponent:@"Documents/Logs"]
#define GetImgDataDirectory [NSHomeDirectory() stringByAppendingPathComponent:@"Documents/ImgData"]
#define GetAppModuleDirectory(module) [NSHomeDirectory() stringByAppendingPathComponent:[NSString stringWithFormat:@"Documents/AppData/%@",module]]

// 屏幕宽高
#define kScreenHeight [UIScreen mainScreen].bounds.size.height
#define kScreenWidth  [UIScreen mainScreen].bounds.size.width

//#define KSecretKey             @"CtUyV$8MGoK8u5L*P0Q50T/b8S9iclS*LQqo"
#define KSecretKey             @"crnzarsddhnnfb4cy0fymt8d14xroma5"

//本地服务器，每次重启服务器会改变
//#define kApiHomeURL              @"http://192.168.178.32:8080/"

//雅诗阁测试服务器
#define kApiHomeURL             @"http://47.52.220.96:80/"

//线上服务器
//#define kApiHomeURL           @"http://118.190.164.26:8080/"

//新版本 https服务器
//#define kApiHomeURL         @"https://webview.easyiservice.com/"

//#define kApiHomeURL           @"http://192.168.0.59:8080/"
#define kApiDomainURL         @"https://service.easyiservice.com/"

#endif /* XZConstans_h */
