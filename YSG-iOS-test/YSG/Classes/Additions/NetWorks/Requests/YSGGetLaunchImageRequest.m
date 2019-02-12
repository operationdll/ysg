//
//  YSGGetLaunchImageRequest.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGGetLaunchImageRequest.h"

@implementation YSGGetLaunchImageRequest
- (instancetype)init
{
    self = [super init];
    if (self) {
        self.moduleName = @"appStartPic";
        self.methodName = @"getEffectiveAppStartPic";
    }
    return self;
}
@end
