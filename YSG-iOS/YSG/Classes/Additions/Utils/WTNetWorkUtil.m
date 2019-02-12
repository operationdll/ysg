//
//  WTNetWorkUtil.m
//  WTSample
//
//  Created by mac on 15/6/1.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkUtil.h"
#import <AFNetworking.h>

#import <MBProgressHUD.h>
#import "WTUtil.h"
#import "WTNetWorkFileCheckRequest.h"
#import "WTNetWorkFileCheckResponse.h"

@interface WTNetWorkUtil()


@end

@implementation WTNetWorkUtil

- (instancetype)init
{
    self = [super init];
    if (self) {
        
    }
    return self;
}

#pragma mark - Public Method

+ (NSString *)replaceCharacter:(NSString *)oStr withString:(NSString *)nStr inString:(NSString *)string
{
    if (oStr && nStr) {
        NSMutableString *_str = [NSMutableString stringWithString:string];
        [_str replaceOccurrencesOfString:oStr withString:nStr options:NSCaseInsensitiveSearch range:NSMakeRange(0, _str.length)];
        return _str;
    }
    else
        return oStr;
}

+ (NSString *)stringOfRandom
{
    return @"ODDAWBAETSVQPAHM";
}

/**
 *  请求Opinion Server Api 接口方法
 *
 *  @param request 自定义的Request请求
 *  @param success 请求成功,返回Response对象
 *  @param failure 请求失败
 */
+ (void)requestWTApi:(WTNetWorkBaseRequest *)request
             success:(void (^)(WTNetWorkBaseResponse *responseObject))success
             failure:(void (^)(NSError *error))failure
{
    if (!request) {
        NSLog(@"[ERROR] request cannot be NULL");
        if (failure) {
            failure([NSError errorWithDomain:@"" code:-1 userInfo:nil]);
        }
        return;
        
    }
    
    AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    manager.responseSerializer.acceptableContentTypes = [NSSet setWithObjects:@"text/plain", @"text/html",@"application/json", @"text/json", @"text/javascript",nil];
    if (request.requestType == GET) {
        [manager POST:request.stringOfApiURLString parameters:request.paramDic progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
            [self successrequest:request success:success failure:failure rsp:responseObject];
        } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
            [self failfailure:failure Error:error];
        }];
    }else{
        [manager GET:request.stringOfApiURLString parameters:request.paramDic progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
            [self successrequest:request success:success failure:failure rsp:responseObject];
        } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
            [self failfailure:failure Error:error];
        }];
    }
    
}

+ (void)successrequest:(WTNetWorkBaseRequest *)request
               success:(void (^)(WTNetWorkBaseResponse *responseObject))success
               failure:(void (^)(NSError *error))failure
                   rsp:(id)responseObject{
    NSDictionary *dic = responseObject;
    if (dic) {
        if ([[dic objectForKey:@"code"] intValue] == 0) {
            NSString *responseClassName =  [WTNetWorkUtil replaceCharacter:@"Request" withString:@"Response" inString:NSStringFromClass([request class])];
            WTNetWorkBaseResponse *response = [[NSClassFromString(responseClassName) alloc] initWithDictionary:dic];
            success(response);
            return;
        }else{
            if (!dic || ![dic objectForKey:@"code"]) {
                failure([NSError errorWithDomain:@"接口返回数据格式出错" code:-1 userInfo:nil]);
                return ;
            }else{
                failure([NSError errorWithDomain:dic[@"msg"] code:-1 userInfo:nil]);
            }
        }
    }
}

+ (void)failfailure:(void (^)(NSError *error))failure Error:(NSError * _Nonnull)error{
    if (failure) {
        failure(error);
    }else{
        NSLog(@"[ERROR] failure not exist!");
    }
}


+ (void)requestWTApi:(WTNetWorkBaseRequest *)request
              inView:(UIView *)view
             success:(void (^)(WTNetWorkBaseResponse *responseObject))success;
{
    MBProgressHUD *progressHUD = nil;
    if (view) {
        progressHUD = [[MBProgressHUD alloc] initWithView:view];
        [view addSubview:progressHUD];
        progressHUD.center = view.center;
        [progressHUD setMode:MBProgressHUDModeIndeterminate];
        [progressHUD setLabelText:@"正在请求"];
        [progressHUD show:YES];
    }
    [WTNetWorkUtil requestWTApi:request success:^(WTNetWorkBaseResponse *responseObject) {
        WTNetWorkBaseResponse *response = responseObject;
        if (response.isSuccess) {
            success(responseObject);
        }
        if(!response.isSuccess && progressHUD){
            [progressHUD setMode:MBProgressHUDModeText];
            [progressHUD setLabelText:response.msg];
            [progressHUD hide:YES afterDelay:1];
        }else if (response.isSuccess && progressHUD){
            [progressHUD hide:YES];
        }
    } failure:^(NSError *error) {
        [progressHUD setMode:MBProgressHUDModeText];
        [progressHUD setLabelText:@"请求失败"];
        [progressHUD hide:YES afterDelay:1];
    }];
}


+ (void)requestWTApi:(WTNetWorkBaseRequest *)request
              inView:(UIView *)view
             success:(void (^)(WTNetWorkBaseResponse *responseObject))success
            finished:(void (^)(NSError *error))finished
{
    MBProgressHUD *progressHUD = nil;
    if (view) {
        progressHUD = [[MBProgressHUD alloc] initWithView:view];
        [view addSubview:progressHUD];
        progressHUD.center = view.center;
        [progressHUD setMode:MBProgressHUDModeIndeterminate];
        [progressHUD setLabelText:@"正在请求"];
        [progressHUD show:YES];
    }
    [WTNetWorkUtil requestWTApi:request success:^(WTNetWorkBaseResponse *responseObject) {
        WTNetWorkBaseResponse *response = responseObject;
        if (response.isSuccess) {
            success(responseObject);
        }
        if(!response.isSuccess && progressHUD){
            [progressHUD setMode:MBProgressHUDModeText];
            [progressHUD setLabelText:response.msg];
            [progressHUD hide:YES afterDelay:1];
        }else if (response.isSuccess && progressHUD){
            [progressHUD hide:YES];
        }
        if (finished) {
            finished(nil);
        }
        
    } failure:^(NSError *error) {
        [progressHUD setMode:MBProgressHUDModeText];
        [progressHUD setLabelText:@"请求失败"];
        [progressHUD hide:YES afterDelay:1];
        if (finished) {
            finished(nil);
        }
    }];
}
@end


