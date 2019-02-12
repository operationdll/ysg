//
//  JSInterface+YSG.h
//  YSG
//
//  Created by Apple on 2017/6/29.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "JSInterface.h"

@interface JSInterface (YSG)

- (void)sendInfoToNative:(id)params;

/**
 获取js存储信息
 */
- (void)getInfoForKey:(id)params :(void(^)(NSString *response))callBack;


@end
