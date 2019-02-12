//
//  UIImage+Extensions.h
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Extensions)

//  拉伸图片
+ (UIImage *)strechImage:(NSString *)imageName;

//  缩放图片
- (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)size;

@end
