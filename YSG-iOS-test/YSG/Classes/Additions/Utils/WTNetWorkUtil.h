//
//  WTNetWorkUtil.h
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "WTNetWorkBaseRequest.h"
#import "WTNetWorkBaseResponse.h"
#import "WTNetWorkFileRequest.h"
#import "WTNetWorkFileResponse.h"
@interface WTNetWorkUtil : NSObject

/**
 *  请求Opinion Server Api 接口方法
 *
 *  @param request 自定义的Request请求
 *  @param success 请求成功,返回Response对象
 *  @param failure 请求失败
 */
+ (void)requestWTApi:(WTNetWorkBaseRequest *)request
             success:(void (^)(WTNetWorkBaseResponse *responseObject))success
             failure:(void (^)(NSError *error))failure;

+ (void)uploadImageAF:(WTNetWorkFileRequest *)request
              success:(void (^)(WTNetWorkFileResponse *responseObject))success
              failure:(void (^)(NSError *error))failure;
+ (void)uploadImage:(NSString *)filePath
            success:(void (^)(WTNetWorkFileResponse *responseObject))success
            failure:(void (^)(NSError *error))failure;


@end
