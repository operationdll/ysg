//
//  WTNetWorkBaseRequest.m
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkBaseRequest.h"

#import "WTNetWorkUtil.h"

@implementation WTNetWorkBaseRequest

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.paramDic = [[NSMutableDictionary alloc] init];
        self.apiDomain = kApiDomainURL;
    }
    return self;
}

- (void)setValue:(id)value forKey:(NSString *)key
{
    if (value) {
        [self.paramDic setObject:value forKey:key];
    }
}

- (NSString *)stringOfApiURLString
{
    return [NSString stringWithFormat:@"%@%@/%@",self.apiDomain,self.moduleName,self.methodName];
}

- (NSString *)getPostBody
{
    NSData *data = [NSJSONSerialization dataWithJSONObject:self.paramDic options:NSJSONWritingPrettyPrinted error:nil];
    NSString *string = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    return string;
}

- (NSString *)getEncrptPostDataString:(NSString *)aesKey
{
    NSData *data = [NSJSONSerialization dataWithJSONObject:self.paramDic options:0 error:nil];
    
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    NSData *dataJSONObject = [NSJSONSerialization dataWithJSONObject:dictionary options:0 error:nil];
    NSString *jsonString = [[NSString alloc] initWithData:dataJSONObject encoding:NSUTF8StringEncoding];
    
    return jsonString;
}


@end
