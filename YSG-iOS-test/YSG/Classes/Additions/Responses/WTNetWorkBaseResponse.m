//
//  WTNetWorkBaseResponse.m
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkBaseResponse.h"

@implementation WTNetWorkBaseResponse

@synthesize data        = _data;

- (BOOL)isSuccess
{
    return (self.code.intValue == 1);
}


@end
