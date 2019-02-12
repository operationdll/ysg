//
//  UIImage+Extensions.m
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "UIImage+Extensions.h"

@implementation UIImage (Extensions)

+ (UIImage *)strechImage:(NSString *)imageName
{
    UIImage *img = [UIImage imageNamed:imageName];
    img = [img stretchableImageWithLeftCapWidth:img.size.width/2 topCapHeight:img.size.height/2];
    return img;
}

- (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)size{
//  用一个固定大小的size
    UIGraphicsBeginImageContext(size);
//    把图片按比例放在这里面
    [img drawInRect:CGRectMake(0, 0, size.width, size.height)];
//    再取得当前图片
    UIImage* scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return scaledImage;
}

@end
