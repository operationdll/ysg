//
//  WTNetWorkBaseResponse.h
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import <Jastor.h>

@interface WTNetWorkBaseResponse : Jastor

@property(nonatomic,strong) NSNumber            *code;
@property(nonatomic,strong) NSString            *msg;
@property(nonatomic,strong) NSDictionary        *data;

- (BOOL)isSuccess;

@end
