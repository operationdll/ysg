//
//  WTNetWorkBaseRequest.h
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Jastor.h"
#import "XZConstans.h"
typedef enum : NSUInteger {
    POST,
    GET,
} RequestType;
@interface WTNetWorkBaseRequest : Jastor

@property(nonatomic,strong) NSString                *apiDomain;
@property(nonatomic,strong) NSString                *moduleName;
@property(nonatomic,strong) NSString                *methodName;
@property(nonatomic,strong) NSMutableDictionary     *paramDic;
@property (nonatomic ,strong) NSMutableDictionary   *header;
@property(nonatomic,assign) RequestType             requestType;

- (NSString *)stringOfApiURLString;

- (NSString *)getPostBody;
- (NSString *)getEncrptPostDataString:(NSString *)aesKey;

@end
