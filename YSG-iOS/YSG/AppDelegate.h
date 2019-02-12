//
//  AppDelegate.h
//  YSG
//
//  Created by Apple on 2017/6/20.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

+ (void)addUMTagAlias:(NSDictionary *)dic;
+ (void)removeAllTagAlias:(NSDictionary *)dic;

@end

