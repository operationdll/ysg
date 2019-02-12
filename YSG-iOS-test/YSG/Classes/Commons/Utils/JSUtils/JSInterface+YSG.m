//
//  JSInterface+YSG.m
//  YSG
//
//  Created by Apple on 2017/6/29.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "JSInterface+YSG.h"

@implementation JSInterface(YSG)
- (void)sendInfoToNative:(id)params{
    NSLog(@"sendInfoToNative :%@",params);
}

- (void)getInfoForKey:(id)params :(void(^)(NSString *response))callBack{
    NSString *key = (NSString *)params;
    NSString *value = [NSString stringWithFormat:@"'%@'",[[NSUserDefaults standardUserDefaults] valueForKey:key]];
    callBack(value);
}

@end
