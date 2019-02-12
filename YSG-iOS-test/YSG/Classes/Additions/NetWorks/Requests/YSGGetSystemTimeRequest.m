//
//  YSGGetSystemTimeRequest.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGGetSystemTimeRequest.h"

@implementation YSGGetSystemTimeRequest
- (instancetype)init
{
    self = [super init];
    if (self) {
        self.moduleName = @"system";
        self.methodName = @"getTime";
    }
    return self;
}
@end
